package com.yad.vasilii.gallery.presentation.mvp.settings;

import com.arellomobile.mvp.*;
import com.arellomobile.mvp.viewstate.strategy.*;
import com.yad.vasilii.gallery.data.roomdatabase.models.*;

import java.util.*;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface SettingsView extends MvpView {

    void showCategories(List<Category> categories);

    void showAddDialog();

}
