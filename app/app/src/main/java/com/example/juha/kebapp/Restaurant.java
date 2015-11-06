package com.example.juha.kebapp;

/**
 * Created by Juha on 2.11.2015.
 * Class to represent map drawn objects
 */
public class Restaurant {

    public int id;
    public int stars;
    public String name;
    public String address;
    public Double latitude;
    public Double longitude;


    public Restaurant(int id, String name, String address, Double latitude, Double longitude, int stars){
        this.id = id;
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.stars = stars;
    }

}
