package com.yad.vasilii.gallery.di.scopes;

import java.lang.annotation.*;

import javax.inject.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Scope
@Documented
@Retention(RUNTIME)
public @interface Presenter {
}
