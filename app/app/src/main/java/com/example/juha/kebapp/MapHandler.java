package com.example.juha.kebapp;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Juha on 3.11.2015.
 */
public class MapHandler implements GoogleMap.OnMarkerClickListener {

    GoogleMap map;

    public MapHandler(GoogleMap map){
        this.map = map;
    }

    public void addRestaurant(LatLng latLng, String name, int rating){
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title(name)
                .snippet("Stars: "+Integer.toString(rating));
        map.addMarker(options);
        map.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
