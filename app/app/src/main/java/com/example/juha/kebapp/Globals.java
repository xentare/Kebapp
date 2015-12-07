package com.example.juha.kebapp;

import android.app.Application;
import android.provider.Settings;
import android.provider.Settings.Secure;

/**
 * Created by Juha on 3.11.2015.
 */
public class Globals extends Application {

    //Global variables
    private static final int PERMISSION_REQUEST_ACCESS_GEO = 1;
    private static final String apiUrl = "http://student.labranet.jamk.fi/~H4113/kebapi/api/";

    public int get_PERMISSION_REQUEST_ACCESS_GEO(){
        return this.PERMISSION_REQUEST_ACCESS_GEO;
    }

    public String get_apiUrl(){
        return this.apiUrl;
    }

}
