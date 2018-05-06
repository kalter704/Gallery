package com.yad.vasilii.gallery.presentation.mvp.gallery;

import com.arellomobile.mvp.*;
import com.arellomobile.mvp.viewstate.strategy.*;
import com.yad.vasilii.gallery.domain.models.*;

import java.util.*;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface GalleryView extends MvpView {

    void showImages(List<Image> images);

    void showBigMessageNoNetwork();

    void showMessageNoNetwork();

    void showLoading();

}
