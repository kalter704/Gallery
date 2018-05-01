package com.yad.vasilii.gallery.presentation.mvp.galleryhost;

import com.arellomobile.mvp.*;
import com.arellomobile.mvp.viewstate.strategy.*;

import java.util.*;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface GalleryHostView extends MvpView {

    void showFragments(List<String> fragmentsTitle);

    void showWait(String message);

}
