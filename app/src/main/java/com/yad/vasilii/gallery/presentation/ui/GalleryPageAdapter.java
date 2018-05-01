package com.yad.vasilii.gallery.presentation.ui;

import android.support.annotation.*;
import android.support.v4.app.*;

import java.util.*;

public class GalleryPageAdapter extends FragmentPagerAdapter {

    private List<GalleryFragment> mFragments;

    private List<String> mTitles;

    public GalleryPageAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new ArrayList<>();
    }

    public GalleryPageAdapter(FragmentManager fm, List<GalleryFragment> fragments, List<String> titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    public void addFragment(GalleryFragment fragment) {
        mFragments.add(fragment);
        notifyDataSetChanged();
    }

    public void setFragments(List<GalleryFragment> fragments, List<String> titles) {
        mFragments = fragments;
        mTitles = titles;
        notifyDataSetChanged();
    }

//    public void addFragments(List<GalleryFragment> fragments) {
//        mFragments.addAll(fragments);
//    }
//
//    public void clear() {
//        mFragments.clear();
//    }
//
//    public void showFragments(List<GalleryFragment> fragments) {
//        mFragments.clear();
//        mFragments.addAll(fragments);
//    }

}
