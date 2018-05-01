package com.yad.vasilii.gallery.di;

import com.yad.vasilii.gallery.di.scopes.*;
import com.yad.vasilii.gallery.presentation.mvp.galleryhost.*;

import dagger.*;

@Presenter
@Component(dependencies = {AppComponent.class})
public interface PresentersComponent {

    GalleryHostPresenter getGalleryHostPresenter();

}
