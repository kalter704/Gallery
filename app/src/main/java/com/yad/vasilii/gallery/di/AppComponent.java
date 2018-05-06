package com.yad.vasilii.gallery.di;

import com.yad.vasilii.gallery.data.roomdatabase.*;
import com.yad.vasilii.gallery.domain.repositories.*;
import com.yad.vasilii.gallery.presentation.ui.gallery.*;
import com.yad.vasilii.gallery.presentation.ui.imageview.*;

import javax.inject.*;

import dagger.*;

@Singleton
@Component(modules = {AppModule.class, RepositoriesModule.class})
public interface AppComponent {

    GalleryDatabase getGalleryDatabase();

    CategoriesRepository getCategoriesRepository();

    ImagesRepository getImagesRepository();

    void inject(GalleryFragment galleryFragment);

    void inject(ImageViewFragment imageViewFragment);
}
