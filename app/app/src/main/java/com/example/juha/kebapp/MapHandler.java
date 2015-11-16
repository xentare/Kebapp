package com.example.juha.kebapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.location.Location;
import android.provider.ContactsContract;
import android.support.annotation.MainThread;

import com.android.volley.VolleyError;
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
    private MainActivity activity;

    public HashMap<String, Restaurant> getRestaurantMarkerMap() {
        return restaurantMarkerMap;
    }

    public MapHandler(GoogleMap map, Activity activity) {
        this.map = map;
        this.activity = (MainActivity)activity;
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

    /*
    * Load and place markers
     */
    public void initialize(){
        activity.getDataHandler().requestRestaurants(new DataHandler.RequestCallback() {
            @Override
            public void onSuccess(List<Restaurant> restaurants) {
                for (Restaurant restaurant:restaurants) {
                    addRestaurantMarker(restaurant);
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    public interface MarkerClickCallback {
        void onMarkerClick(Marker marker);
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
