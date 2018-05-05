package com.yad.vasilii.gallery.data.roomdatabase;

import com.yad.vasilii.gallery.data.roomdatabase.dao.*;
import com.yad.vasilii.gallery.data.roomdatabase.models.*;

import android.arch.persistence.room.*;

@Database(entities = {Category.class, CachedImage.class, TimeWhenCached.class}, version = 1)
public abstract class GalleryDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao();

    public abstract CachedImageDao cachedImageDao();

    public abstract TimeWhenCachedDao timeWhenCached();

}
