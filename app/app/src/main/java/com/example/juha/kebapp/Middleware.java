package com.example.juha.kebapp;

import android.app.DownloadManager;
import android.content.Context;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Juha on 31/10/15.
 */
public class Middleware{

    public void httpRequest(Context context){

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://student.labranet.jamk.fi/~H4113/kebapi/api/restaurant/4";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("response",response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error","Failed to HTTP!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    public void httpPostRequest(Context context){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://student.labranet.jamk.fi/~H4113/kebapi/api/restaurant";

        // Request a string response from the provided URL.
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("response",response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error","HTTP REQUEST FAILED");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("name", "Tesoman Pizza Kebab");
                params.put("latitude", "12.0000");
                params.put("longitude", "13.000");
                params.put("address","Tesomankatu");
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(postRequest);
    }
}
