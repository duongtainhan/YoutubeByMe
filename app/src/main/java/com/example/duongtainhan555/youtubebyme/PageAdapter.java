package com.example.duongtainhan555.youtubebyme;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.duongtainhan555.youtubebyme.Fragment.SearchNameFragment;
import com.example.duongtainhan555.youtubebyme.Fragment.SearchPlaylistFragment;

public class PageAdapter extends FragmentPagerAdapter {
    private int numOfTabs;

    PageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SearchNameFragment();
            case 1:
                return new SearchPlaylistFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
