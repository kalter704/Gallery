package com.yad.vasilii.gallery.test;

import com.yad.vasilii.gallery.presentation.global.SchedulersProvider;

import io.reactivex.*;
import io.reactivex.schedulers.*;

public class TestScheduleProviders extends SchedulersProvider {

    @Override
    public Scheduler ui() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }
}
