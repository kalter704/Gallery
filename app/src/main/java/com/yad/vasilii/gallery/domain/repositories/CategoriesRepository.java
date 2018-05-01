package com.yad.vasilii.gallery.domain.repositories;

import com.yad.vasilii.gallery.data.roomdatabase.models.*;

import java.util.*;

import io.reactivex.*;

public interface CategoriesRepository {

    Single<List<Category>> getCategories();

}
