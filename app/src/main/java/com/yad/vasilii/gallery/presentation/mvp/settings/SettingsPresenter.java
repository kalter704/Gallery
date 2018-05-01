package com.yad.vasilii.gallery.presentation.mvp.settings;

import com.arellomobile.mvp.*;
import com.yad.vasilii.gallery.data.roomdatabase.models.*;
import com.yad.vasilii.gallery.domain.*;
import com.yad.vasilii.gallery.presentation.global.*;
import com.yad.vasilii.gallery.presentation.mvp.global.*;

import java.util.*;

import javax.inject.*;

import io.reactivex.*;

@InjectViewState
public class SettingsPresenter extends BasePresenter<SettingsView> {

    private SchedulersProvider mSchedulersProvider;

    private SettingsInteractor mSettingsInteractor;

    @Inject
    public SettingsPresenter(SchedulersProvider schedulersProvider,
            SettingsInteractor settingsInteractor) {
        mSchedulersProvider = schedulersProvider;
        mSettingsInteractor = settingsInteractor;
    }

    @Override
    protected void onFirstViewAttach() {
        executeOnIOShowOnUI(mSettingsInteractor.getCategories());
    }

    public void onClickAddButton() {
        getViewState().showAddDialog();
    }

    public void onAddCategory(String title) {
        executeOnIOShowOnUI(mSettingsInteractor.addCategory(title));
    }

    public void onDeleteCategory(Category category) {
        executeOnIOShowOnUI(mSettingsInteractor.deleteCategory(category));
    }

    private void executeOnIOShowOnUI(Single<List<Category>> categoriesSingle) {
        mDisposables.add(categoriesSingle.subscribeOn(mSchedulersProvider.io())
                .observeOn(mSchedulersProvider.ui())
                .subscribe(categories -> getViewState().showCategories(categories),
                        Throwable::printStackTrace));
    }
}
