package com.example.juha.kebapp.Fragments;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.example.juha.kebapp.DataHandler;
import com.example.juha.kebapp.JSONParser;
import com.example.juha.kebapp.MainActivity;
import com.example.juha.kebapp.R;
import com.example.juha.kebapp.Restaurant;
import com.example.juha.kebapp.RestaurantActivity;
import com.example.juha.kebapp.RestaurantArrayAdapter;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Juha on 12.11.2015.
 */
public class ListFragmentTab extends Fragment implements AdapterView.OnItemClickListener{

    private RestaurantArrayAdapter adapter;
    private ArrayList<Restaurant> restaurants;

    /*
    * Requests all restaurants and puts them in the listview. Also sets distance in km based on current location and sorts the list.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getDataHandler().requestRestaurants(new DataHandler.RequestCallback() {
            @Override
            public void onSuccess(String restaurants) {
                getView().findViewById(R.id.spinner).setVisibility(View.GONE);
                setRestaurants(restaurants);
            }
            @Override
            public void onError(VolleyError error) {
                //TODO: Handle errors
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        restaurants = new ArrayList<>();
        adapter = new RestaurantArrayAdapter(getContext(),restaurants);
        ListView listView = (ListView)view.findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ((MainActivity)getActivity()).showRestaurantActivity(restaurants.get(position));
    }

    private void setRestaurants(String restaurants){
        Location location = ((MainActivity) getActivity()).getGpsTracker().getLocation();
        if(location != null) {
            adapter.addAll(Restaurant.setDistances(JSONParser.parseRestaurants(restaurants), location));
        } else {
            adapter.addAll(JSONParser.parseRestaurants(restaurants));
        }
    }
}
