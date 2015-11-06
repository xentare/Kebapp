package com.example.juha.kebapp;

import android.content.Context;

/**
 * Created by Juha on 6.11.2015.
 */
public class DataHandler extends HttpRequest implements HttpRequest.VolleyCallback {

    Context context;
    String url = "http://student.labranet.jamk.fi/~H4113/kebapi/api/restaurant";

    public DataHandler(Context context){
        this.context = context;
        httpGetRequest(context,url,this);
    }

    @Override
    public void onSuccess(String result) {
        JSONParser.parse(result);
    }
}
