package com.yad.vasilii.gallery.domain;

import com.yad.vasilii.gallery.data.roomdatabase.models.*;
import com.yad.vasilii.gallery.domain.repositories.*;

import java.util.*;

import javax.inject.*;

import io.reactivex.*;

public class SettingsInteractor {

    private CategoriesRepository mCategoriesRepository;

    @Inject
    public SettingsInteractor(CategoriesRepository categoriesRepository) {
        mCategoriesRepository = categoriesRepository;
    }

    public Single<List<Category>> getCategories() {
        return mCategoriesRepository.getCategories();
    }

    public Single<List<Category>> addCategory(String title) {
        if (title == null || title.length() == 0) {
            return mCategoriesRepository.getCategories();
        }
        return mCategoriesRepository.addCategory(title).toSingleDefault(true)
                .flatMap((o) -> mCategoriesRepository.getCategories());
    }

    public Single<List<Category>> deleteCategory(Category category) {
        return mCategoriesRepository.deleteCategory(category).toSingleDefault(true)
                .flatMap((o) -> mCategoriesRepository.getCategories());
    }
}
