package com.example.juha.kebapp;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.Format;
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

    public static String parseOpenings(String data){

        String openings;
        try{
            JSONArray array = new JSONArray(data);
            JSONObject obj = array.getJSONObject(0);
            openings = (obj.getString("start_hour"));
            openings += "-";
            openings += (obj.getString("end_hour"));
            Log.d("asd",openings);
            return openings;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Comment> parseComments(String data){
        List<Comment> comments = new ArrayList<>();

        try{
            JSONArray array = new JSONArray(data);

            for(int i = 0;i<array.length();i++){
                JSONObject obj = array.getJSONObject(i);
                Comment comment = new Comment();
                comment.text = obj.getString("text");
                comment.id = obj.getString("comment_id");
                comment.upVotes = obj.getString("up_votes");
                comment.downVotes = obj.getString("down_votes");
                comments.add(comment);
            }
            return comments;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static LatLng parseLatLng(String data){
        try{
            JSONObject obj = new JSONObject(data);
            JSONArray array = obj.getJSONArray("results");
            JSONObject object = array.getJSONObject(0);
            object = object.getJSONObject("geometry").getJSONObject("location");
            return new LatLng(object.getDouble("lat"),object.getDouble("lng"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}

