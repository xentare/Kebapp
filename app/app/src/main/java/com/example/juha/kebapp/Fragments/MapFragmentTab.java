package com.example.juha.kebapp.Fragments;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.juha.kebapp.MainActivity;
import com.example.juha.kebapp.MapHandler;
import com.example.juha.kebapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Juha on 13.11.2015.
 * Map Fragment created as a nested fragment inside this one, since PageAdapter fragments can't be any other type
 * (eg. Cant have SupportMapFragment as a tab) Map is loaded synchronously which is bad tho...
 * http://stackoverflow.com/questions/18369639/maps-v2-with-viewpager
 */
public class MapFragmentTab extends Fragment{

    private SupportMapFragment fragment;
    private MapHandler mapHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.map_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d("Fragment","Creating new MapActivity");
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        fragment = (SupportMapFragment) fm.findFragmentById(R.id.mapFragment);
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.mapFragment, fragment).commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mapHandler == null){
            mapHandler = new MapHandler(fragment.getMap(),getActivity());
        }
    }


}
