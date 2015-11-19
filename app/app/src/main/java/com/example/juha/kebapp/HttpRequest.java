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
public class HttpRequest{

    /**
    * Uses Volley to send HTTP request. Implements callback interface to handle responses
     */
    public static void httpGetRequest(Context context, String url, final VolleyCallback callback){
            Log.d("VOLLEY",url);
            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("VolleyResponse", response);
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VolleyErrorResponse", ""+error.toString());
                callback.onError(error);
            }
        }
        );
            queue.add(stringRequest);
    }

    public static void httpPostRequest(Context context, String url, final Map<String,String> params, final VolleyCallback callback){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("VolleyResponse", response);
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VolleyErrorResponse", ""+error.toString());
                callback.onError(error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        queue.add(postRequest);
    }

    /**
     * Callback interface for HTTP responses
     */
    public interface VolleyCallback{
        void onSuccess(String result);
        void onError(VolleyError error);
    }
}
