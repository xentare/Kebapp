package com.example.juha.kebapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import java.util.List;

/**
 * Created by Juha on 6.11.2015.
 */
public class DataHandler implements HttpRequest.VolleyCallback {

    Context context;
    MapHandler mapHandler;
    Activity activity;
    String url = "http://student.labranet.jamk.fi/~H4113/kebapi/api/restaurant";

    public DataHandler(Activity activity){
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.mapHandler = ((MainActivity)activity).mapHandler;
    }

    public void requestRestaurant(String id){
        HttpRequest.httpGetRequest(context,url+"/"+id,this);
    }

    public void requestRestaurants(){
        HttpRequest.httpGetRequest(context, url, this);
    }

    @Override
    public void onSuccess(String result) {
        List<Restaurant> restaurants = JSONParser.parseRestaurants(result);
        mapHandler.handleRestaurantRequest(restaurants);
    }

    @Override
    public void onError(VolleyError error) {
        HttpRequest.httpGetRequest(context, url, this);
    }
}
