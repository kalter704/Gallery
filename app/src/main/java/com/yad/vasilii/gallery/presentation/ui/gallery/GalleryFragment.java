package com.yad.vasilii.gallery.presentation.ui.gallery;

import com.arellomobile.mvp.*;
import com.arellomobile.mvp.presenter.*;
import com.yad.vasilii.gallery.R;
import com.yad.vasilii.gallery.di.*;
import com.yad.vasilii.gallery.di.gallery.*;
import com.yad.vasilii.gallery.domain.models.*;
import com.yad.vasilii.gallery.global.*;
import com.yad.vasilii.gallery.presentation.mvp.gallery.*;
import com.yad.vasilii.gallery.presentation.ui.imageview.*;

import android.os.*;
import android.support.v7.widget.*;
import android.util.*;
import android.view.*;

import java.util.*;

import butterknife.*;

public class GalleryFragment extends MvpAppCompatFragment implements GalleryView {

    private static final String ARG_TITLE = "arg_title";

    private static final int DEFAULT_IMAGE_WIDTH = 150; // dp

    private String mTitle;

    @BindView(R.id.gallery_recycler_view)
    RecyclerView mRecyclerView;

    private GalleryRecyclerAdapter mGalleryRecyclerAdapter;

    @InjectPresenter(type = PresenterType.GLOBAL)
    GalleryPresenter mPresenter;

    @ProvidePresenterTag(presenterClass = GalleryPresenter.class, type = PresenterType.GLOBAL)
    String provideGalleryPresenterTag() {
        return getTitleArg();
    }

    @ProvidePresenter(type = PresenterType.GLOBAL)
    GalleryPresenter provideGalleryPresenter() {
        AppComponent appComponent = ((GalleryApplication) getContext().getApplicationContext())
                .getAppComponent();
        return DaggerGalleryPresenterComponent.builder().appComponent(appComponent)
                .galleryModule(new GalleryModule(getTitleArg())).build().getGalleryPresenter();
    }

    private String getTitleArg() {
        return getArguments().getString(ARG_TITLE);
    }

    public GalleryFragment() {
    }

    public static GalleryFragment newInstance(String title) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        ButterKnife.bind(this, view);

        mGalleryRecyclerAdapter = new GalleryRecyclerAdapter(true);

        int screenWidth = getScreenWidthDP();
        int columns = getColumns(screenWidth);
        int imageHeightXP = getRealImageWidthXP(screenWidth, columns);

        mGalleryRecyclerAdapter.setImageHeight(imageHeightXP);
        mGalleryRecyclerAdapter.setOnLoadMoreListener(() -> mPresenter.onLoadMore());
        mGalleryRecyclerAdapter.setOnItemClickListener(this::startImageViewActivity);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), columns);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mGalleryRecyclerAdapter.isPositionFooter(position) ?
                        gridLayoutManager.getSpanCount() : 1;
            }
        });

        mRecyclerView.setAdapter(mGalleryRecyclerAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mPresenter.onCreateView();

        return view;
    }

    private void startImageViewActivity(int position) {
        startActivity(ImageViewActivity.createIntent(getContext(), mTitle, position));
    }

    public String getTitle() {
        return mTitle;
    }

    @Override
    public void showImages(List<Image> images) {
        mGalleryRecyclerAdapter.addImages(images);
    }

    private int getScreenWidthDP() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return (int) (displayMetrics.widthPixels / displayMetrics.density);
    }

    private int getColumns(int screenWidth) {
        return screenWidth / DEFAULT_IMAGE_WIDTH;
    }

    private int getRealImageWidthXP(int screenWidthDP, int columns) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return (int) ((float) screenWidthDP * displayMetrics.density / columns);
    }

}
