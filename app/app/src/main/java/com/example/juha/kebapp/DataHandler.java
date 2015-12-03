package com.example.juha.kebapp;

import android.app.Activity;
import android.content.Context;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

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
    String comments = "http://student.labranet.jamk.fi/~H4113/kebapi/api/comment/";

    public interface RequestCallback{
        void onSuccess(String result);
        void onError(VolleyError error);
    }

    public DataHandler(Activity activity){
        this.activity = activity;
        this.context = activity.getApplicationContext();
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
    public void requestRestaurants(String query, final RequestCallback requestCallback){
        HttpRequest.httpGetRequest(context, restaurants+"/"+query, new HttpRequest.VolleyCallback() {
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
