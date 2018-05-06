package com.yad.vasilii.gallery.di.imageview;

import com.yad.vasilii.gallery.di.scopes.*;

import javax.inject.*;

import dagger.*;

@Module
public class ImageViewModule {

    private int mPage;

    public ImageViewModule(int page) {
        mPage = page;
    }

    @Named("page")
    @Presenter
    @Provides
    public int providePageNumber() {
        return mPage;
    }
}
