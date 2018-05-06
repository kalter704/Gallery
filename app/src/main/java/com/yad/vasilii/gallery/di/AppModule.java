package com.yad.vasilii.gallery.di;

import com.yad.vasilii.gallery.R;
import com.yad.vasilii.gallery.data.*;
import com.yad.vasilii.gallery.data.roomdatabase.*;
import com.yad.vasilii.gallery.presentation.global.*;

import android.arch.persistence.room.*;
import android.content.*;

import javax.inject.*;

import dagger.*;

@Module
public class AppModule {

    private Context mContext;

    public AppModule(Context context) {
        mContext = context;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return mContext.getApplicationContext();
    }

    @Singleton
    @Provides
    GalleryDatabase provideGalleryDatabase() {
        return Room.databaseBuilder(mContext, GalleryDatabase.class,
                mContext.getString(R.string.database_name)).build();
    }

    @Singleton
    @Provides
    SchedulersProvider provideSchedulersProvider() {
        return new SchedulersProvider();
    }

    @Singleton
    @Provides
    CalendarManager provideCalendarManager() {
        return new CalendarManager();
    }

}
