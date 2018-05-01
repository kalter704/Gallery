package com.yad.vasilii.gallery.data.repositories;

import com.yad.vasilii.gallery.data.roomdatabase.*;
import com.yad.vasilii.gallery.data.roomdatabase.models.*;
import com.yad.vasilii.gallery.domain.repositories.*;

import java.util.*;

import javax.inject.*;

import io.reactivex.*;

public class CategoriesRepositoryImpl implements CategoriesRepository {

    private GalleryDatabase mDB;

    @Inject
    public CategoriesRepositoryImpl(GalleryDatabase DB) {
        mDB = DB;
    }

    @Override
    public Single<List<Category>> getCategories() {
        return Single.fromCallable(() -> mDB.categoryDao().getAll());
    }
}
