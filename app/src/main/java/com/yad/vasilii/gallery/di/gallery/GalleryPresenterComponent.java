package com.yad.vasilii.gallery.di.gallery;

import com.yad.vasilii.gallery.di.*;
import com.yad.vasilii.gallery.di.scopes.*;
import com.yad.vasilii.gallery.presentation.mvp.gallery.*;

import dagger.*;

@Presenter
@Component(dependencies = AppComponent.class, modules = {GalleryModule.class})
public interface GalleryPresenterComponent {

    GalleryPresenter getGalleryPresenter();

}
