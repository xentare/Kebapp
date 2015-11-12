package com.example.juha.kebapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Juha on 2.11.2015.
 * Class to represent map drawn objects
 * http://www.parcelabler.com/
 */
public class Restaurant implements Parcelable {

    public int id;
    public int stars;
    public String name;
    public String address;
    public Double latitude;
    public Double longitude;

    public Restaurant(){

    }

    public Restaurant(int id, String name, String address, Double latitude, Double longitude, int stars){
        this.id = id;
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.stars = stars;
    }


    protected Restaurant(Parcel in) {
        id = in.readInt();
        stars = in.readInt();
        name = in.readString();
        address = in.readString();
        latitude = in.readByte() == 0x00 ? null : in.readDouble();
        longitude = in.readByte() == 0x00 ? null : in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
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
}