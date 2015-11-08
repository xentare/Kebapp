package com.example.juha.kebapp;

import android.graphics.Bitmap;
import android.support.annotation.MainThread;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by Juha on 3.11.2015.
 */
public class MapHandler{

    GoogleMap map;

    public MapHandler(GoogleMap map){
        this.map = map;
    }

    public interface MarkerClickCallback{
        void onMarkerClick(Marker marker);
    }

    public void handleRestaurantRequest(List<Restaurant> restaurants){
        if(restaurants != null){
            for(Restaurant restaurant:restaurants){
                addRestaurant(restaurant);
            }
        }
    }

    public void addRestaurant(Restaurant restaurant){

        MarkerIcon icon = new MarkerIcon(restaurant.name,restaurant.stars);

        MarkerOptions options = new MarkerOptions()
                .position(new LatLng(restaurant.latitude,restaurant.longitude))
                .title(restaurant.name)
                .snippet("Stars: "+Integer.toString(restaurant.stars));
        Marker marker = map.addMarker(options);
    }

}
