package com.yad.vasilii.gallery.di;

import com.yad.vasilii.gallery.di.scopes.*;
import com.yad.vasilii.gallery.presentation.mvp.imageview.*;

import dagger.*;

@Presenter
@Component(dependencies = AppComponent.class, modules = {ImageViewModule.class})
public interface ImageViewPresenterComponent {

    ImageViewPresenter getImageViewPresenter();

}
