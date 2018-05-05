package com.yad.vasilii.gallery.presentation.mvp.imageview;

import com.arellomobile.mvp.*;

import javax.inject.*;

@InjectViewState
public class ImageViewPresenter extends MvpPresenter<ImageViewer> {

    private int mPage;

    @Inject
    public ImageViewPresenter(@Named("page") int page) {
        mPage = page;
    }

    @Override
    protected void onFirstViewAttach() {
        getViewState().showPage(mPage);
    }
}
