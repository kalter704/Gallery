package com.yad.vasilii.gallery.presentation.mvp.galleryhost;

import com.arellomobile.mvp.*;
import com.yad.vasilii.gallery.domain.*;
import com.yad.vasilii.gallery.presentation.global.*;

import javax.inject.*;

import io.reactivex.disposables.*;

@InjectViewState
public class GalleryHostPresenter extends MvpPresenter<GalleryHostView> {

    private GalleryHostInteractor mGalleryHostInteractor;

    private SchedulersProvider mSchedulersProvider;

    private CompositeDisposable mDisposables;

    @Inject
    public GalleryHostPresenter(SchedulersProvider schedulersProvider,
            GalleryHostInteractor galleryHostInteractor) {
        mSchedulersProvider = schedulersProvider;
        mGalleryHostInteractor = galleryHostInteractor;
        mDisposables = new CompositeDisposable();
    }

    public void onResume() {
        loadCategories();
    }

    private void loadCategories() {
        mDisposables
                .add(mGalleryHostInteractor.getCategories().subscribeOn(mSchedulersProvider.io())
                        .observeOn(mSchedulersProvider.ui()).doOnSubscribe(
                                (disposable) -> getViewState().showWait("Загрузка данных..."))
                        .subscribe(categories -> {
                            getViewState().showFragments(categories);
                        }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDisposables.dispose();
    }
}
