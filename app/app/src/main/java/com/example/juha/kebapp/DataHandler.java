package com.example.juha.kebapp;

import android.app.Activity;
import android.content.Context;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Juha on 6.11.2015.
 */
public class DataHandler {

    Context context;
    Activity activity;
    String restaurants = "http://student.labranet.jamk.fi/~H4113/kebapi/api/restaurant";
    String search = "http://student.labranet.jamk.fi/~H4113/kebapi/api/restaurant/search/";
    String openings = "http://student.labranet.jamk.fi/~H4113/kebapi/api/restaurant/openings/";
    String comments = "http://student.labranet.jamk.fi/~H4113/kebapi/api/comment/";
    String googleApiLatLng = "http://maps.google.com/maps/api/geocode/json?address=";
    String googleApiAddress = "http://maps.google.com/maps/api/geocode/json?latlng=";

    public interface RequestCallback{
        void onSuccess(String result);
        void onError(VolleyError error);
    }

    public DataHandler(Activity activity){
        this.activity = activity;
        this.context = activity.getApplicationContext();
    }

    public void postRestaurant(final Map<String,String> params, final RequestCallback requestCallback){
        HttpRequest.httpPostRequest(context, restaurants, params, new HttpRequest.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                requestCallback.onSuccess(result);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    public void requestAddressFromLatLong(String LatLng, final RequestCallback requestCallback){
        HttpRequest.httpGetRequest(context, googleApiAddress + LatLng, new HttpRequest.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                requestCallback.onSuccess(result);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    public void requestGeocodeFromAddress(String address, final RequestCallback requestCallback){
        try {
            HttpRequest.httpGetRequest(context, googleApiLatLng + URLEncoder.encode(address,"UTF-8"), new HttpRequest.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    requestCallback.onSuccess(result);
                }

                @Override
                public void onError(VolleyError error) {

                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /*
    *TODO: Make one proper request function.
     */
    public void requestComments(String restaurant_id,final RequestCallback requestCallback){
        HttpRequest.httpGetRequest(context, comments+restaurant_id, new HttpRequest.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                requestCallback.onSuccess(result);
            }

            @Override
            public void onError(VolleyError error) {

                requestCallback.onError(error);
            }
        });
    }

    public void requestRestaurants(final RequestCallback requestCallback){
        HttpRequest.httpGetRequest(context, restaurants, new HttpRequest.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                requestCallback.onSuccess(result);
            }

            @Override
            public void onError(VolleyError error) {
                requestCallback.onError(error);
            }
        });
    }
    public void requestRestaurantsSearch(String query, final RequestCallback requestCallback){
        HttpRequest.httpGetRequest(context, search+query, new HttpRequest.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                requestCallback.onSuccess(result);
            }

            @Override
            public void onError(VolleyError error) {
                requestCallback.onError(error);
            }
        });
    }

    public void requestRestaurantOpenings(String query, final RequestCallback requestCallback){
        HttpRequest.httpGetRequest(context, openings+query, new HttpRequest.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                requestCallback.onSuccess(result);
            }

            @Override
            public void onError(VolleyError error) {
                requestCallback.onError(error);
            }
        });
    }

    public void postComment(final Map<String,String> params, final RequestCallback requestCallback){
        HttpRequest.httpPostRequest(context, comments, params, new HttpRequest.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                requestCallback.onSuccess(result);
            }

            @Override
            public void onError(VolleyError error) {
                requestCallback.onError(error);
            }
        });
    }

}
