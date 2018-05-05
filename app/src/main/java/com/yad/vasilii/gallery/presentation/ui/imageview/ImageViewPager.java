package com.yad.vasilii.gallery.presentation.ui.imageview;

import android.support.v4.app.*;

import java.util.*;

public class ImageViewPager extends FragmentPagerAdapter {

    private List<ImageViewFragment> mImageFragments;

    public ImageViewPager(FragmentManager fm) {
        super(fm);
        mImageFragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return mImageFragments.get(position);
    }

    @Override
    public int getCount() {
        return mImageFragments.size();
    }

    public void addFragments(List<ImageViewFragment> fragments) {
        mImageFragments.addAll(fragments);
        notifyDataSetChanged();
    }
}
