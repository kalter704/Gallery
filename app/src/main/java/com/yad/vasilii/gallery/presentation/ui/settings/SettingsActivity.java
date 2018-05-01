package com.yad.vasilii.gallery.presentation.ui.settings;

import com.arellomobile.mvp.*;
import com.arellomobile.mvp.presenter.*;
import com.yad.vasilii.gallery.R;
import com.yad.vasilii.gallery.data.roomdatabase.models.*;
import com.yad.vasilii.gallery.di.*;
import com.yad.vasilii.gallery.global.*;
import com.yad.vasilii.gallery.presentation.mvp.settings.*;

import android.os.*;
import android.support.design.widget.*;
import android.support.v7.widget.*;

import java.util.*;

import butterknife.*;

public class SettingsActivity extends MvpAppCompatActivity implements SettingsView,
        InputCategoryDialogFragment.OnDialogListener {

    @BindView(R.id.categories_list)
    RecyclerView mRecyclerView;

    @InjectPresenter
    SettingsPresenter mPresenter;

    @ProvidePresenter
    SettingsPresenter provideSettingsPresenter() {
        return DaggerPresentersComponent.builder()
                .appComponent(((GalleryApplication) getApplication()).getAppComponent()).build()
                .getSettingsPresenter();
    }

    private CategoriesRecyclerAdapter mCategoriesRecyclerAdapter;

    private InputCategoryDialogFragment mInputDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setSupportActionBar(findViewById(R.id.toolbar));

        ButterKnife.bind(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(
                view -> mPresenter.onClickAddButton());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCategoriesRecyclerAdapter = new CategoriesRecyclerAdapter();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mCategoriesRecyclerAdapter);

        mCategoriesRecyclerAdapter.setListener(category -> mPresenter.onDeleteCategory(category));
    }

    @Override
    public void showCategories(List<Category> categories) {
        mCategoriesRecyclerAdapter.setCategories(categories);
    }

    @Override
    public void showAddDialog() {
        showDialog();
    }

    private void showDialog() {
        if (mInputDialog != null && mInputDialog.isVisible()) {
            mInputDialog.dismiss();
            mInputDialog = null;
        }
        mInputDialog = new InputCategoryDialogFragment();
        mInputDialog.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void add(String title) {
        mPresenter.onAddCategory(title);
    }

    @Override
    public void cancel() {

    }
}
