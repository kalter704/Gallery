package com.yad.vasilii.gallery.presentation.ui.galleryhost;

import com.arellomobile.mvp.*;
import com.arellomobile.mvp.presenter.*;
import com.yad.vasilii.gallery.R;
import com.yad.vasilii.gallery.di.*;
import com.yad.vasilii.gallery.global.*;
import com.yad.vasilii.gallery.presentation.mvp.galleryhost.*;
import com.yad.vasilii.gallery.presentation.ui.gallery.*;
import com.yad.vasilii.gallery.presentation.ui.settings.*;

import android.content.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v4.view.*;
import android.view.*;

import java.util.*;

import butterknife.*;

public class GalleryHostActivity extends MvpAppCompatActivity implements GalleryHostView {

    @BindView(R.id.container)
    ViewPager mViewPager;

    @BindView(R.id.tabs)
    TabLayout mTabLayout;

    @InjectPresenter
    GalleryHostPresenter mPresenter;

    private List<GalleryFragment> mPages;

    @ProvidePresenter
    GalleryHostPresenter provideGalleryHostPresenter() {
        return DaggerPresentersComponent.builder()
                .appComponent(((GalleryApplication) getApplicationContext()).getAppComponent())
                .build().getGalleryHostPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(findViewById(R.id.toolbar));

        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.yad.vasilii.gallery.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showFragments(List<String> fragmentsTitles) {
        setFragmentsToViewPager(fragmentsTitles);
    }

    @Override
    public void showWait(String message) {

    }

    private void setFragmentsToViewPager(List<String> fragmentsTitles) {
        mPages = new ArrayList<>();
        for (String title : fragmentsTitles) {
            mPages.add(GalleryFragment.newInstance(title));
        }
        mViewPager.setAdapter(new GalleryPageAdapter(getSupportFragmentManager(), mPages, fragmentsTitles));
    }
}
