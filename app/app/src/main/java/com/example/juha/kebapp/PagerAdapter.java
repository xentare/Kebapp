package com.example.juha.kebapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.juha.kebapp.Fragments.ListFragmentTab;
import com.example.juha.kebapp.Fragments.MapFragmentTab;
import com.example.juha.kebapp.Fragments.SearchFragmentTab;

/**
 * Created by Juha on 12.11.2015.
 */
class PagerAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs){
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new ListFragmentTab();
            case 1:
                return new SearchFragmentTab();
            case 2:
                return new MapFragmentTab();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
