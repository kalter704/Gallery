package com.yad.vasilii.gallery.presentation.mvp.gallery;

import com.arellomobile.mvp.*;
import com.yad.vasilii.gallery.domain.*;
import com.yad.vasilii.gallery.domain.models.*;
import com.yad.vasilii.gallery.presentation.global.*;
import com.yad.vasilii.gallery.presentation.mvp.global.*;

import java.util.*;

import javax.inject.*;

@InjectViewState
public class GalleryPresenter extends BasePresenter<GalleryView> {

    private static final int ON_THE_PAGE = 50;

    private final SchedulersProvider mSchedulersProvider;

    private final GalleryInteractor mGalleryInteractor;

    private final String mQuery;

    private int mPage;

    private boolean isLoading;

    private List<Image> mImages;

    @Inject
    public GalleryPresenter(SchedulersProvider schedulersProvider,
            GalleryInteractor galleryInteractor, @Named("query") String query) {
        mSchedulersProvider = schedulersProvider;
        mGalleryInteractor = galleryInteractor;
        mQuery = query;
        mPage = 1;
        isLoading = false;
        mImages = new ArrayList<>();
    }

    public void onCreateView() {
        if (mImages.size() != 0) {
            getViewState().showImages(mImages);
        } else {
            loadMore();
        }
    }

    public void onLoadMore() {
        loadMore();
    }

    private void loadMore() {
        if (isLoading) {
            return;
        }
        isLoading = true;
        mDisposables.add(mGalleryInteractor.getImages(mQuery, mPage, ON_THE_PAGE)
                .subscribeOn(mSchedulersProvider.io()).observeOn(mSchedulersProvider.ui())
                .subscribe(this::successLoad, this::handleError));
    }

    private void successLoad(List<Image> images) {
        mPage++;
        mImages.addAll(images);
        getViewState().showImages(images);
        isLoading = false;
    }

    private void handleError(Throwable throwable) {
        throwable.printStackTrace();
        isLoading = false;
    }

}
