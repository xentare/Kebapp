package com.example.juha.kebapp;


import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Juha on 12.11.2015.
 */
public class RestaurantArrayAdapter extends ArrayAdapter<String> {


   public RestaurantArrayAdapter(Context context, ArrayList<String> restaurants){
        super(context,R.layout.rowlayout,restaurants);
   }
}
