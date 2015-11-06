package com.example.juha.kebapp;

import android.Manifest;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    Globals g;
    GPSTracker gpsTracker;
    HttpRequest httpRequest;
    GoogleMap map;
    MapHandler mapHandler;


    /**
    * Configures map and implements an intent call listener to GPS button
    * TODO: 0.1 Prompt "Go to GPS settings or not" dialog
    * TODO: 1.0 Google maps like GPS allowing dialog
     */
    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        this.mapHandler = new MapHandler(map);
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
    }

    public void initCamera(){
        Log.d("MAP", "Moving camera to my location");
        if(gpsTracker.canGetLocation) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gpsTracker.latitude, gpsTracker.longitude), 15));
            map.setOnMyLocationChangeListener(null);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        g = (Globals)getApplication();
        gpsTracker = new GPSTracker(getApplicationContext());
        requestPermissions();
        //Init map
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        gpsTracker.getLocation();
        if (!gpsTracker.isGPSEnabled) {
            Log.d("GPS", "GPS alert dialog");
            showGPSDialog();
        }
    }

    void showGPSDialog(){
        DialogFragment dialogFragment = new GPSDialogFragment();
        dialogFragment.show(getFragmentManager(), "dialog");
    }

    public void onGPSDialogPositiveClick(){
        Intent gpsOptionsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(gpsOptionsIntent);
    }

    public void onGPSDialogNegativeClick() {
        Toast toast = Toast.makeText(getApplicationContext(), "Kebapp won't work properly without GPS", Toast.LENGTH_SHORT);
        toast.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
    * Fragment open/close DEMO
     */
    public void onclickOpenFragment(View view){
        Fragment fragment = getFragmentManager().findFragmentById(R.id.OverlayFragment);
        if(fragment.isVisible()) {
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .hide(fragment).commit();
        } else {
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .show(fragment).commit();
        }
    }



    public void onClickGPS(View view) {
        HttpRequest.getRestaurants(getApplicationContext(), new HttpRequest.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                //TextView textView = (TextView)findViewById(R.id.requestTextView);
                mapHandler.addRestaurant(JSONParser.parse(result));
            }
        });
    }

    /**
     * Remember to put gpsTracker.getLocation() here somehow
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == g.get_PERMISSION_REQUEST_ACCESS_GEO()){
                if(grantResults.length > 0){
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.d("GPS/NETWORK", "GPS permission granted");
                    }
                    if(grantResults[1] == PackageManager.PERMISSION_GRANTED){
                        Log.d("GPS/NETWORK", "Network permission granted");
                    }
                } else {
                    Log.d("GPS/NETWORK","Permissions denied");
                }
            }
    }
    /**
    * Request permissions to use GPS and network coarse location
    */
    public void requestPermissions(){
        int coarsePermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int gpsPermissionCheck = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);
        Log.d("GPS","Checking permissions");
        if (coarsePermissionCheck != PackageManager.PERMISSION_GRANTED || gpsPermissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.d("GPS","Requesting permissions");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, g.get_PERMISSION_REQUEST_ACCESS_GEO());
        } else {
            Log.d("GPS", "Permissions already granted");
        }
    }


}
