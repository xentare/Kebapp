package com.example.juha.kebapp;

import android.app.Activity;
import android.content.Context;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.util.List;

/**
 * Created by Juha on 6.11.2015.
 */
public class DataHandler implements HttpRequest.VolleyCallback {

    Context context;
    Activity activity;
    String url = "http://student.labranet.jamk.fi/~H4113/kebapi/api/restaurant";

    public interface RequestCallback{
        void onSuccess(List<Restaurant> restaurants);
        void onError(VolleyError error);
    }

    public DataHandler(Activity activity){
        this.activity = activity;
        this.context = activity.getApplicationContext();
    }

    public void requestRestaurant(String id){
        HttpRequest.httpGetRequest(context,url+"/"+id,this);
    }

    public void requestRestaurants(final RequestCallback requestCallback){
        HttpRequest.httpGetRequest(context, url, new HttpRequest.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                requestCallback.onSuccess(JSONParser.parseRestaurants(result));
            }

            @Override
            public void onError(VolleyError error) {
                requestCallback.onError(error);
            }
        });
    }

    @Override
    public void onSuccess(String result) {
        List<Restaurant> restaurants = JSONParser.parseRestaurants(result);

    }

    @Override
    public void onError(VolleyError error) {
        HttpRequest.httpGetRequest(context, url, this);
    }
}
