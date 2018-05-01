package com.yad.vasilii.gallery.presentation.mvp.galleryhost;

import com.arellomobile.mvp.*;
import com.yad.vasilii.gallery.domain.*;
import com.yad.vasilii.gallery.presentation.global.*;
import com.yad.vasilii.gallery.presentation.mvp.global.*;

import javax.inject.*;

@InjectViewState
public class GalleryHostPresenter extends BasePresenter<GalleryHostView> {

    private GalleryHostInteractor mGalleryHostInteractor;

    private SchedulersProvider mSchedulersProvider;

    @Inject
    public GalleryHostPresenter(SchedulersProvider schedulersProvider,
            GalleryHostInteractor galleryHostInteractor) {
        mSchedulersProvider = schedulersProvider;
        mGalleryHostInteractor = galleryHostInteractor;
    }

    public void onResume() {
        loadCategories();
    }

    private void loadCategories() {
        mDisposables
                .add(mGalleryHostInteractor.getCategories().subscribeOn(mSchedulersProvider.io())
                        .observeOn(mSchedulersProvider.ui()).doOnSubscribe(
                                (disposable) -> getViewState().showWait("Загрузка данных..."))
                        .subscribe(categories -> getViewState().showFragments(categories),
                                Throwable::printStackTrace));
    }

}
