package com.ayush.sffoodtruckfinder.network;

import com.ayush.sffoodtruckfinder.model.FoodTruck;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by ayushkedia on 16/04/16.
 */
public interface RequestBuilder {

    @GET("/6a9r-agq8.json")
    void getFoodTruck(@Query("$where") String userLatLng,
                      Callback<ArrayList<FoodTruck>> response);

}
