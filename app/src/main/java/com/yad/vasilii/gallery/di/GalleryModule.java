package com.yad.vasilii.gallery.di;

import com.yad.vasilii.gallery.di.scopes.*;

import javax.inject.*;

import dagger.*;

@Module
public class GalleryModule {

    private final String mQuery;

    public GalleryModule(String query) {
        mQuery = query;
    }

    @Named("query")
    @Presenter
    @Provides
    String provideQueryString() {
        return mQuery;
    }
}
