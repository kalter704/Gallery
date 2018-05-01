package com.yad.vasilii.gallery.presentation.global;

import javax.inject.*;

import io.reactivex.*;
import io.reactivex.android.schedulers.*;
import io.reactivex.schedulers.*;

public class SchedulersProvider {

    @Inject
    public SchedulersProvider() {
    }

    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

    public Scheduler io() {
        return Schedulers.io();
    }

}
