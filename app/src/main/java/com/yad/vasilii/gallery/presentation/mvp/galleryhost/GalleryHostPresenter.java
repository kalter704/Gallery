package com.yad.vasilii.gallery.presentation.mvp.galleryhost;

import com.arellomobile.mvp.*;
import com.yad.vasilii.gallery.domain.*;
import com.yad.vasilii.gallery.presentation.global.*;
import com.yad.vasilii.gallery.presentation.mvp.global.*;

import java.util.*;

import javax.inject.*;

@InjectViewState
public class GalleryHostPresenter extends BasePresenter<GalleryHostView> {

    private GalleryHostInteractor mGalleryHostInteractor;

    private SchedulersProvider mSchedulersProvider;

    private List<String> mCategories;

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
                        .subscribe(this::successLoadCategories, Throwable::printStackTrace));
    }

    private void successLoadCategories(List<String> categories) {
        if (!isEqualCategories(mCategories, categories)) {
            mCategories = categories;
            getViewState().showFragments(categories);
        }
    }

    private boolean isEqualCategories(List<String> c1, List<String> c2) {
        if (c1 == null || c2 == null) {
            return false;
        }
        if (c1.size() != c2.size()) {
            return false;
        }
        for (int i = 0; i < c1.size(); i++) {
            if (!c1.get(i).equals(c2.get(i))) {
                return false;
            }
        }
        return true;
    }

}
