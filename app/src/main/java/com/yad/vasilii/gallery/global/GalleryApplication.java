package com.yad.vasilii.gallery.global;

import com.yad.vasilii.gallery.R;
import com.yad.vasilii.gallery.data.roomdatabase.models.*;
import com.yad.vasilii.gallery.di.*;

import android.content.*;
import android.preference.*;
import android.support.multidex.*;

public class GalleryApplication extends MultiDexApplication {

    private AppComponent mAppComponent;

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = buildAppComponent();

        saveDefaultCategoriesToDatabase();
    }

    private void saveDefaultCategoriesToDatabase() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if (!sp.contains(getString(com.yad.vasilii.gallery.R.string.first_launch_key))) {
            sp.edit().putBoolean(getString(com.yad.vasilii.gallery.R.string.first_launch_key), false).apply();
            String[] titles = getResources().getStringArray(R.array.default_categories);
            new Thread(() -> {
                for (String title : titles) {
                    Category category = new Category();
                    category.setTitle(title);
                    mAppComponent.getGalleryDatabase().categoryDao().insert(category);
                }
            }).start();
        }
    }

    private AppComponent buildAppComponent() {
        return DaggerAppComponent.builder().appModule(new AppModule(this))
                .repositoriesModule(new RepositoriesModule()).build();
    }


}
