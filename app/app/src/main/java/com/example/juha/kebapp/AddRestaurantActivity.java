package com.example.juha.kebapp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Juha on 4.12.2015.
 */
public class AddRestaurantActivity extends AppCompatActivity {

    private DataHandler dataHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataHandler = new DataHandler(this);
        setContentView(R.layout.add_restaurant_activity);
    }

    public void getAddressFromLocationClick(View view){

    }

    public void sendButtonClick(View view){
        EditText address = (EditText)findViewById(R.id.editTextAddRestaurantAddress);
        dataHandler.requestGeocodeFromAddress(address.getText().toString(), new DataHandler.RequestCallback() {
            @Override
            public void onSuccess(String result) {
                postRestaurant(JSONParser.parseLatLng(result));
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    private void postRestaurant(LatLng latLng){
        Map<String,String> params = new HashMap<>();
        EditText address = (EditText)findViewById(R.id.editTextAddRestaurantAddress);
        EditText name = (EditText)findViewById(R.id.editTextAddRestaurantName);
        params.put("name",name.getText().toString());
        params.put("address", address.getText().toString());
        params.put("latitude",Double.toString(latLng.latitude));
        params.put("longitude", Double.toString(latLng.longitude));

        dataHandler.postRestaurant(params, new DataHandler.RequestCallback() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

}
