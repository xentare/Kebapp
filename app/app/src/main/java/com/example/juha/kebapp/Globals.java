package com.example.juha.kebapp;

import android.app.Application;

/**
 * Created by Juha on 3.11.2015.
 */
public class Globals extends Application {

    //Global variables
    private static final int PERMISSION_REQUEST_ACCESS_GEO = 1;

    public int get_PERMISSION_REQUEST_ACCESS_GEO(){
        return this.PERMISSION_REQUEST_ACCESS_GEO;
    }
}
