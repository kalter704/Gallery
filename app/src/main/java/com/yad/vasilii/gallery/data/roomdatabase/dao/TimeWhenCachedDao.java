package com.yad.vasilii.gallery.data.roomdatabase.dao;

import com.yad.vasilii.gallery.data.roomdatabase.models.*;

import android.arch.persistence.room.*;

import java.util.*;

@Dao
public interface TimeWhenCachedDao {

    @Query("SELECT * FROM TimeWhenCached")
    List<TimeWhenCached> getAll();

    @Insert
    void insert(TimeWhenCached image);

    @Query("DELETE FROM TimeWhenCached")
    void deleteAll();
}
