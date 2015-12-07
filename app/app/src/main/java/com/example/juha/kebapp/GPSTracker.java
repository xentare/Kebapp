package com.example.juha.kebapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.maps.LocationSource;


/**
 * Created by Juha on 3.11.2015.
 */
public class GPSTracker extends Service implements LocationListener {

    private final Context context;

    boolean isGPSEnabled = false;
    private boolean isNetworkEnabled = false;
    private boolean canGetLocation = false;
    private Location location;

    private static final long MIN_DISTANCE_CHANGE_DOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 20;


    private LocationManager locationManager;

    public GPSTracker(Context context) {
        this.context = context;
    }

    public Location requestLocation() {

        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            //getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(isNetworkEnabled || isGPSEnabled) {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_DOR_UPDATES, this);
                    Log.d("Network", "Network enabled");
                    if(locationManager != null){
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                }
                if (isGPSEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_DOR_UPDATES, this);
                    Log.d("GPS","GPS enabled");
                    if(locationManager != null){
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return location;
    }

    public void removeUpdates(){
        try {
            locationManager.removeUpdates(GPSTracker.this);
        } catch(SecurityException e){
            e.printStackTrace();
        }
    }

    public Location getLocation(){
        return this.location;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("LOCATION",Double.toString(location.getLatitude())+","+Double.toString(location.getLongitude()));
        this.location = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("GPS","provider "+status+" "+extras.toString());
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("GPS",provider+" provider enabled");
        if(provider == "gps") {
            this.isGPSEnabled = true;
        } else if(provider == "network"){
            this.isNetworkEnabled = true;
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("GPS",provider+" provider disabled");
        if(provider == "gps") {
            this.isGPSEnabled = false;
        } else if(provider == "network"){
            this.isNetworkEnabled = false;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
