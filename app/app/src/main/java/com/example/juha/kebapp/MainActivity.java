package com.example.juha.kebapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Text;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    Globals g;
    GPSTracker gpsTracker;
    HttpRequest httpRequest;
    GoogleMap map;
    MapHandler mapHandler;


    /*
    * Configures map and implements an intent call listener to GPS button
    * TODO: 0.1 Prompt "Go to GPS settings or not" dialog
    * TODO: 1.0 Google maps like GPS allowing dialog
     */
    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        this.mapHandler = new MapHandler(map);
        map.setMyLocationEnabled(true);
        map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                gpsTracker.checkProviders();
                if(!gpsTracker.isGPSEnabled) {
                    Intent gpsOptionsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(gpsOptionsIntent);
                }
                return false;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions();
        g = (Globals)getApplication();
        gpsTracker = new GPSTracker(getApplicationContext());
        //Google maps fucks you in the ass since there is already R.id.map from some shit
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.mapFragment);
            mapFragment.getMapAsync(this);
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

    public void onClickGPS(View view) {
        HttpRequest.getRestaurants(getApplicationContext(),new HttpRequest.VolleyCallback(){
            @Override
            public void onSuccess(String result) {
                TextView textView = (TextView)findViewById(R.id.requestTextView);
                mapHandler.addRestaurant(JSONParser.parse(result));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if(requestCode == g.get_PERMISSION_REQUEST_ACCESS_GEO()){
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //PERMISSION GRANTED
                    Log.d("GPS/NETWORK","Permission granted");
                } else {
                    //PERMISSION DENIED
                    Log.d("GPS/NETWORK","Permission denied");
                }
            }
    }
    /*
    * Request permissions to use GPS and network coarse location
    */
    public void requestPermissions(){
        int coarsePermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int gpsPermissionCheck = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);
        Log.d("GPS","Checking permissions");
        if (coarsePermissionCheck != PackageManager.PERMISSION_GRANTED || gpsPermissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.d("GPS","Requesting permissions");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, g.get_PERMISSION_REQUEST_ACCESS_GEO());
        }
    }


}
