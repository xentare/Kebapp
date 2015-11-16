package com.example.juha.kebapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.example.juha.kebapp.DataHandler;
import com.example.juha.kebapp.MainActivity;
import com.example.juha.kebapp.R;
import com.example.juha.kebapp.Restaurant;
import com.example.juha.kebapp.RestaurantArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juha on 12.11.2015.
 */
public class ListFragmentTab extends Fragment {

    RestaurantArrayAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ((MainActivity)getActivity()).getDataHandler().requestRestaurants(new DataHandler.RequestCallback() {
            @Override
            public void onSuccess(List<Restaurant> restaurants) {
                adapter.addAll(restaurants);
            }
            @Override
            public void onError(VolleyError error) {
                //TODO: Handle errors
            }
        });
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        adapter = new RestaurantArrayAdapter(getContext(),restaurants);
        ListView listView = (ListView)view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        return view;
    }
}
