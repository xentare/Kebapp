package com.example.juha.kebapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juha on 07/11/15.
 */
public class JSONParser {

    public JSONParser(){

    }

    /*
    *TODO: Make proper parser function. Could parse in every class..?
     */
    public static List<Restaurant> parseRestaurants(String data){

        List<Restaurant> restaurants = new ArrayList<>();

        try{
            JSONArray array = new JSONArray(data);

            for(int i = 0;i < array.length();i++){
                Restaurant res = new Restaurant();
                JSONObject obj = array.getJSONObject(i);
                res.id = obj.getString("restaurant_id");
                res.name = obj.getString("name");
                res.address = obj.getString("address");
                res.latitude = obj.getDouble("latitude");
                res.longitude = obj.getDouble("longitude");
                res.stars = obj.getInt("rating");
                restaurants.add(res);
            }
            return restaurants;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> parseComments(String data){
        List<String> comments = new ArrayList<>();

        try{
            JSONArray array = new JSONArray(data);

            for(int i = 0;i<array.length();i++){
                JSONObject obj = array.getJSONObject(i);
                comments.add(obj.getString("text"));
            }
            return comments;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}

