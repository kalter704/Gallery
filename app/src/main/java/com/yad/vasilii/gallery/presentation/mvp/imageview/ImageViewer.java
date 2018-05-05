package com.yad.vasilii.gallery.presentation.mvp.imageview;

import com.arellomobile.mvp.*;
import com.arellomobile.mvp.viewstate.strategy.*;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface ImageViewer extends MvpView {

    void showPage(int page);

}
