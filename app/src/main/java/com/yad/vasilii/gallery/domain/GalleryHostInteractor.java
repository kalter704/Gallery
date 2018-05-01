package com.yad.vasilii.gallery.domain;

import com.yad.vasilii.gallery.data.roomdatabase.models.*;
import com.yad.vasilii.gallery.domain.repositories.*;

import java.util.*;

import javax.inject.*;

import io.reactivex.*;

public class GalleryHostInteractor {

    private CategoriesRepository mCategoriesRepository;

    @Inject
    public GalleryHostInteractor(CategoriesRepository categoriesRepository) {
        mCategoriesRepository = categoriesRepository;
    }

    public Single<List<String>> getCategories() {
        return mCategoriesRepository.getCategories().map(categories -> {
            List<String> titles = new ArrayList<>();
            for (Category category : categories) {
                titles.add(category.getTitle());
            }
            return titles;
        });
    }
}
