package com.example.juha.kebapp;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Juha on 3.11.2015.
 */
public class MapHandler {

    GoogleMap map;

    public MapHandler(GoogleMap map){
        this.map = map;
    }

    public void addRestaurant(LatLng latLng){
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("Here, Evin is here!!");
        map.addMarker(options);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
    }

}
