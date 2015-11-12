package com.example.juha.kebapp;

import android.graphics.Bitmap;
import android.location.Location;
import android.support.annotation.MainThread;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Juha on 3.11.2015.
 */
public class MapHandler{


    private HashMap<String, Restaurant> restaurantMarkerMap;
    private GoogleMap map;

    public HashMap<String, Restaurant> getRestaurantMarkerMap() {
        return restaurantMarkerMap;
    }

    public MapHandler(GoogleMap map) {
        this.map = map;
        restaurantMarkerMap = new HashMap<>();

        initialize();

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // createOverlayFragment(marker);
                return false;
            }
        });
        map.setMyLocationEnabled(true);
        map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

            }
        });
        map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                return false;
            }
        });

    }

    public void initialize(){

    }

    public interface MarkerClickCallback {
        void onMarkerClick(Marker marker);
    }

    public void handleRestaurantRequest(List<Restaurant> restaurants) {
        if (restaurants != null) {
            for (Restaurant restaurant : restaurants) {
                addRestaurantMarker(restaurant);
            }
        }
    }

    public void addRestaurantMarker(Restaurant restaurant) {

        MarkerIcon icon = new MarkerIcon(restaurant.name, restaurant.stars);

        MarkerOptions options = new MarkerOptions()
                .position(new LatLng(restaurant.latitude, restaurant.longitude))
                .title(restaurant.name)
                .snippet("Stars: " + Integer.toString(restaurant.stars));
        Marker marker = map.addMarker(options);

        restaurantMarkerMap.put(marker.getId(), restaurant);
    }

}
