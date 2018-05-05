package com.yad.vasilii.gallery.data.roomdatabase.dao;

import com.yad.vasilii.gallery.data.roomdatabase.models.*;

import android.arch.persistence.room.*;

import java.util.*;

@Dao
public interface CategoryDao {

    @Query("SELECT * FROM Category")
    List<Category> getAll();

    @Insert
    void insert(Category category);

    @Delete
    void delete(Category category);

}
