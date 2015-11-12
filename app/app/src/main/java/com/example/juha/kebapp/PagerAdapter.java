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
public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs){
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                ListFragmentTab tab1 = new ListFragmentTab();
                return tab1;
            case 1:
                SearchFragmentTab tab2 = new SearchFragmentTab();
                return tab2;
            case 2:
                MapFragmentTab tab3 = new MapFragmentTab();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
