package com.yad.vasilii.gallery.presentation.mvp.global;

import com.arellomobile.mvp.*;

import io.reactivex.disposables.*;

public class BasePresenter<View extends MvpView> extends MvpPresenter<View> {

    protected CompositeDisposable mDisposables;

    public BasePresenter() {
        mDisposables = new CompositeDisposable();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDisposables.dispose();
    }
}
