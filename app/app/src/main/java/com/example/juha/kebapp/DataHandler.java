package com.example.juha.kebapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import java.util.List;

/**
 * Created by Juha on 6.11.2015.
 */
public class DataHandler extends HttpRequest implements HttpRequest.VolleyCallback {

    Context context;
    MapHandler mapHandler;
    String url = "http://student.labranet.jamk.fi/~H4113/kebapi/api/restaurant";

    public DataHandler(Context context, MapHandler mapHandler){
        this.context = context;
        this.mapHandler = mapHandler;
        httpGetRequest(context,url,this);
    }

    public void requestRestaurants(){
        httpGetRequest(context,url,this);
    }

    @Override
    public void onSuccess(String result) {
        List<Restaurant> restaurants = JSONParser.parseRestaurants(result);
        mapHandler.handleRestaurantRequest(restaurants);
    }

    @Override
    public void onError(VolleyError error) {
        httpGetRequest(context,url,this);
    }
}
