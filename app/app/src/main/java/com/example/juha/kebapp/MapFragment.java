package com.example.juha.kebapp;

import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Juha on 11.11.2015.
 */
public class MapFragment extends FragmentActivity{


    GPSTracker gpsTracker;
    GoogleMap map;

 /*   *//**
     * Configures map and implements an intent call listener to GPS button
     * TODO: 1.0 Google maps like GPS allowing dialog
     *//*
    @Override
    public void onMapReady(GoogleMap map){
        this.map = map;
        *//*this.mapHandler = new MapHandler(map);
        dataHandler = new DataHandler(this);
        dataHandler.requestRestaurants();*//*

        //gpsTracker = new GPSTracker(getApplicationContext());

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
                if(gpsTracker.isGPSEnabled){
                    initCamera();
                }
            }
        });
        map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                return false;
            }
        });
    }*/

/*    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        //super.onCreate(savedInstanceState, persistentState);

        //Init map
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

    }*/

    public void initCamera(){
        Log.d("MAP", "Moving camera to my location");
        if(gpsTracker.canGetLocation) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gpsTracker.latitude, gpsTracker.longitude), 11));
            map.setOnMyLocationChangeListener(null);
        }
    }
}
