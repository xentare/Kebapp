package com.example.juha.kebapp;

import android.Manifest;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.juha.kebapp.Fragments.GPSDialogFragment;
import com.google.android.gms.maps.model.Marker;

public class MainActivity extends AppCompatActivity{

    Globals g;
    GPSTracker gpsTracker;
    MapHandler mapHandler;
    private DataHandler dataHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.dataHandler = new DataHandler(this);
        g = (Globals)getApplication();
        gpsTracker = new GPSTracker(getApplicationContext());
        requestPermissions();
        gpsTracker.getLocation();

        if (!gpsTracker.isGPSEnabled) {
            Log.d("GPS", "GPS alert dialog");
            showGPSDialog();
        }
        //Setup Tabs and View Pager for fragments
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab1_name));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab2_name));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab3_name));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        gpsTracker.getLocation();
    }

    void showGPSDialog(){
        DialogFragment dialogFragment = new GPSDialogFragment();
        dialogFragment.show(getFragmentManager(), "dialog");
    }

    void showRestaurantActivity(){
        Intent restaurantIntent = new Intent(MainActivity.this, RestaurantActivity.class);
        startActivity(restaurantIntent);
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
    * Toggle Open/Close overlay fragment. If hide is set to true, will always hide.
     */
    public void setOverlayFragmentVisible(boolean visible){
        Fragment fragment = getFragmentManager().findFragmentById(R.id.overlayFragmentFrame);
        if(!visible && fragment != null) {
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .hide(fragment).commit();
        } /*else {
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .show(fragment).commit();
        }*/
    }

    public void createOverlayFragment(Marker marker){
        //setOverlayFragmentVisible(true);
        Fragment fragment = new OverlayFragment();
        Bundle args = new Bundle();
        if(mapHandler.getRestaurantMarkerMap().containsKey(marker.getId())){
            args.putParcelable("restaurant", mapHandler.getRestaurantMarkerMap().get(marker.getId()));
        }
        fragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.overlayFragmentFrame,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void onClickGPS(View view) {

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

    public DataHandler getDataHandler(){
        return this.dataHandler;
    }

}
