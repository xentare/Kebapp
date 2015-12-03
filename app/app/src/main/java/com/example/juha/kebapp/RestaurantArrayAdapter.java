package com.example.juha.kebapp;


import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Juha on 12.11.2015.
 */
public class RestaurantArrayAdapter extends ArrayAdapter<Restaurant>{


   public RestaurantArrayAdapter(Context context, ArrayList<Restaurant> restaurants){
        super(context,0,restaurants);
   }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Restaurant restaurant = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.restaurant_item,parent,false);
        }

        RatingBar ratingBar = (RatingBar)convertView.findViewById(R.id.ratingBar);
        TextView textView = (TextView)convertView.findViewById(R.id.restaurantName);
        TextView distance = (TextView)convertView.findViewById(R.id.distance);
        if(restaurant.distance != null) {
            distance.setText(String.format("%.1f",restaurant.distance)+"km");
        }
        ratingBar.setRating(restaurant.stars);
        textView.setText(restaurant.name);
        return convertView;
    }

}
