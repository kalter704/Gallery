package com.yad.vasilii.gallery.di;

import com.yad.vasilii.gallery.data.*;
import com.yad.vasilii.gallery.data.network.*;
import com.yad.vasilii.gallery.data.repositories.*;
import com.yad.vasilii.gallery.data.roomdatabase.*;
import com.yad.vasilii.gallery.domain.repositories.*;

import javax.inject.*;

import dagger.*;

@Module(includes = {AppModule.class, NetworkModule.class})
public class RepositoriesModule {

    @Singleton
    @Provides
    CategoriesRepository provideCategoriesRepository(GalleryDatabase gdb) {
        return new CategoriesRepositoryImpl(gdb);
    }

    @Singleton
    @Provides
    ImagesRepository provideImagesRepository(PixabayApiService pixabayApiService,
            GalleryDatabase gdb, CalendarManager cm) {
        return new ImagesWithCacheRepositoryImpl(
                new ImagesRepositoryImpl(pixabayApiService),
                gdb,
                cm
        );
    }

}
