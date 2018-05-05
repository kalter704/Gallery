package com.yad.vasilii.gallery.data.roomdatabase.dao;

import com.yad.vasilii.gallery.data.roomdatabase.models.*;

import android.arch.persistence.room.*;

import java.util.*;

@Dao
public interface CachedImageDao {

    @Query("SELECT * FROM CachedImage WHERE `query`=:query AND page=:page AND perPage=:perPage")
    List<CachedImage> getImagesByQueryAndPageAndPerPage(String query, int page, int perPage);

    @Insert
    void insertAll(List<CachedImage> images);

    @Query("DELETE FROM CachedImage")
    void deleteAll();

}
