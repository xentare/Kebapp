package com.example.juha.kebapp;

/**
 * Created by Juha on 2.11.2015.
 * Class to represent map drawn objects
 */
public class Restaurant {

    private int id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;


    public Restaurant(int id, String name, String address, Double latitude, Double longitude){
        this.id = id;
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }

}
