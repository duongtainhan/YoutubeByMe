package com.example.duongtainhan555.youtubebyme.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.duongtainhan555.youtubebyme.Fragment.TrendingFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private int numOfTabs;


    ViewPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TrendingFragment();
            case 1:
                return new TrendingFragment();
            case 2:
                return new TrendingFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
