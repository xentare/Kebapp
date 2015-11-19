package com.example.juha.kebapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.example.juha.kebapp.DataHandler;
import com.example.juha.kebapp.JSONParser;
import com.example.juha.kebapp.MainActivity;
import com.example.juha.kebapp.R;
import com.example.juha.kebapp.Restaurant;
import com.example.juha.kebapp.RestaurantActivity;
import com.example.juha.kebapp.RestaurantArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juha on 12.11.2015.
 */
public class ListFragmentTab extends Fragment implements AdapterView.OnItemClickListener{

    RestaurantArrayAdapter adapter;
    ArrayList<Restaurant> restaurants;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        ((MainActivity)getActivity()).getDataHandler().requestRestaurants(new DataHandler.RequestCallback() {
            @Override
            public void onSuccess(String restaurants) {
                adapter.addAll(JSONParser.parseRestaurants(restaurants));
            }
            @Override
            public void onError(VolleyError error) {
                //TODO: Handle errors
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
