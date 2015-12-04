package com.example.juha.kebapp;

import android.location.Location;
import android.location.LocationManager;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Juha on 2.11.2015.
 * Class to represent map drawn objects
 * http://www.parcelabler.com/
 */
public class Restaurant implements Parcelable {

    public String id;
    public int stars;
    public String name;
    public String address;
    public Double latitude;
    public Double longitude;
    public Float distance;

    public Restaurant(){

    }

    public static class DistanceComparator implements Comparator<Restaurant>{
        @Override
        public int compare(Restaurant lhs, Restaurant rhs) {
          return lhs.distance.compareTo(rhs.distance);
        }
    }

    public Restaurant(String id, String name, String address, Double latitude, Double longitude, int stars){
        this.id = id;
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.stars = stars;
    }

    protected Restaurant(Parcel in) {
        id = in.readString();
        stars = in.readInt();
        name = in.readString();
        address = in.readString();
        latitude = in.readByte() == 0x00 ? null : in.readDouble();
        longitude = in.readByte() == 0x00 ? null : in.readDouble();
        distance = in.readByte() == 0x00 ? null : in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(stars);
        dest.writeString(name);
        dest.writeString(address);
        if (latitude == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(latitude);
        }
        if (longitude == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(longitude);
        }
        if(distance == null){
            dest.writeByte((byte) (0x00));
        }else {
            dest.writeByte((byte) (0x01));
            dest.writeFloat(distance);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Restaurant> CREATOR = new Parcelable.Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public static List<Restaurant> setDistances(List<Restaurant> restaurants, Location location){
        float[] results = new float[3];
        for (Restaurant r : restaurants) {
            Location.distanceBetween(r.latitude, r.longitude, location.getLatitude(), location.getLongitude(), results);
            r.distance = results[0] / 1000;
        }
        Collections.sort(restaurants, new Restaurant.DistanceComparator());
        return restaurants;
    }

}