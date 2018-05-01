package com.yad.vasilii.gallery.di;

import com.yad.vasilii.gallery.data.repositories.*;
import com.yad.vasilii.gallery.data.roomdatabase.*;
import com.yad.vasilii.gallery.domain.repositories.*;

import javax.inject.*;

import dagger.*;

@Module(includes = {AppModule.class})
public class RepositoriesModule {

    @Singleton
    @Provides
    CategoriesRepository provideCategoriesRepository(GalleryDatabase gdb) {
        return new CategoriesRepositoryImpl(gdb);
    }

}
