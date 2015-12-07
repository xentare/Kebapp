package com.example.juha.kebapp.Fragments;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.android.volley.VolleyError;
import com.example.juha.kebapp.DataHandler;
import com.example.juha.kebapp.JSONParser;
import com.example.juha.kebapp.MainActivity;
import com.example.juha.kebapp.R;
import com.example.juha.kebapp.Restaurant;
import com.example.juha.kebapp.RestaurantArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Juha on 12.11.2015.
 */
public class SearchFragmentTab extends Fragment implements AdapterView.OnItemClickListener{

    private RestaurantArrayAdapter adapter;
    private ArrayList<Restaurant> restaurants;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.search,container,false);

        restaurants = new ArrayList<>();
        adapter = new RestaurantArrayAdapter(getContext(),restaurants);
        ListView listView = (ListView)view.findViewById(R.id.listView2);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        final ProgressBar spinner = (ProgressBar)view.findViewById(R.id.spinner);
        final SearchView searchView = (SearchView)view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                spinner.setVisibility(View.VISIBLE);
                adapter.clear();
                ((MainActivity)getActivity()).getDataHandler().requestRestaurantsSearch(searchView.getQuery().toString(), new DataHandler.RequestCallback() {
                    @Override
                    public void onSuccess(String result) {
                        spinner.setVisibility(View.GONE);
                        setRestaurants(result);
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return view;
    }

    private void setRestaurants(String restaurants){
        Location location = ((MainActivity) getActivity()).getGpsTracker().getLocation();
        if(location != null) {
            adapter.addAll(Restaurant.setDistances(JSONParser.parseRestaurants(restaurants), location));
        } else {
            adapter.addAll(JSONParser.parseRestaurants(restaurants));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ((MainActivity)getActivity()).showRestaurantActivity(restaurants.get(position));
    }
}
