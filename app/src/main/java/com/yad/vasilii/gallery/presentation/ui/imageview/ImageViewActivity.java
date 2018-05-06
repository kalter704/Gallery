package com.yad.vasilii.gallery.presentation.ui.imageview;

import com.arellomobile.mvp.*;
import com.arellomobile.mvp.presenter.*;
import com.yad.vasilii.gallery.R;
import com.yad.vasilii.gallery.di.*;
import com.yad.vasilii.gallery.di.imageview.*;
import com.yad.vasilii.gallery.di.gallery.*;
import com.yad.vasilii.gallery.domain.models.*;
import com.yad.vasilii.gallery.global.*;
import com.yad.vasilii.gallery.presentation.mvp.gallery.*;
import com.yad.vasilii.gallery.presentation.mvp.imageview.*;

import android.content.*;
import android.os.*;
import android.support.v4.view.*;
import android.support.v7.widget.*;

import java.util.*;

import butterknife.*;

public class ImageViewActivity extends MvpAppCompatActivity implements GalleryView, ImageViewer {

    private static final String EXTRA_TITLE = "extra_title";

    private static final String EXTRA_POSITION = "extra_position";

    public static Intent createIntent(Context context, String title, int position) {
        Intent i = new Intent(context, ImageViewActivity.class);
        i.putExtra(EXTRA_TITLE, title);
        i.putExtra(EXTRA_POSITION, position);
        return i;
    }

    @InjectPresenter(type = PresenterType.GLOBAL)
    GalleryPresenter mGalleryPresenter;

    @InjectPresenter
    ImageViewPresenter mImageViewPresenter;

    @ProvidePresenterTag(presenterClass = GalleryPresenter.class, type = PresenterType.GLOBAL)
    String provideGalleryPresenterTag() {
        return getExtraTitle();
    }

    @ProvidePresenter(type = PresenterType.GLOBAL)
    GalleryPresenter provideGalleryPresenter() {
        AppComponent appComponent = ((GalleryApplication) getApplicationContext())
                .getAppComponent();
        return DaggerGalleryPresenterComponent.builder().appComponent(appComponent)
                .galleryModule(new GalleryModule(getExtraTitle())).build()
                .getGalleryPresenter();
    }

    @ProvidePresenter
    ImageViewPresenter provideImageViewPresenter() {
        AppComponent appComponent = ((GalleryApplication) getApplicationContext())
                .getAppComponent();
        return DaggerImageViewPresenterComponent.builder().appComponent(appComponent)
                .imageViewModule(new ImageViewModule(getExtraPage())).build()
                .getImageViewPresenter();
    }

    private String getExtraTitle() {
        return getIntent().getStringExtra(EXTRA_TITLE);
    }

    private int getExtraPage() {
        return getIntent().getIntExtra(EXTRA_POSITION, 0);
    }

    @BindView(R.id.image_view_pager)
    ViewPager mViewPager;

    private ImageViewPager mImageViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getExtraTitle());

        ButterKnife.bind(this);

        mImageViewPager = new ImageViewPager(getSupportFragmentManager());
        mViewPager.setAdapter(mImageViewPager);

        mGalleryPresenter.onCreateView();
    }

    @Override
    public void showImages(List<Image> images) {
        List<ImageViewFragment> fragments = new ArrayList<>();
        for (Image image : images) {
            fragments.add(ImageViewFragment.newInstance(image.getLargeImageUrl()));
        }
        mImageViewPager.addFragments(fragments);
    }

    @Override
    public void showPage(int page) {
        mViewPager.setCurrentItem(page);
    }
}
