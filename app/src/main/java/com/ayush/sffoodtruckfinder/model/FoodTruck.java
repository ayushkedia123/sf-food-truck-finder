package com.ayush.sffoodtruckfinder.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ayushkedia on 16/04/16.
 */

public class FoodTruck implements Parcelable {
    public String address;
    public String applicant;
    public String dayshours;
    public String fooditems;
    public String latitude;
    public String longitude;
    public String permit;

    protected FoodTruck(Parcel in) {
        address = in.readString();
        applicant = in.readString();
        dayshours = in.readString();
        fooditems = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        permit = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(applicant);
        dest.writeString(dayshours);
        dest.writeString(fooditems);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(permit);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<FoodTruck> CREATOR = new Parcelable.Creator<FoodTruck>() {
        @Override
        public FoodTruck createFromParcel(Parcel in) {
            return new FoodTruck(in);
        }

        @Override
        public FoodTruck[] newArray(int size) {
            return new FoodTruck[size];
        }
    };
}
