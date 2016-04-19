package com.ayush.sffoodtruckfinder.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by ayushkedia on 16/04/16.
 */
public class PreferenceKeeper {

    private static Context context;

    public static void setContext(Context context1) {
        context = context1;
    }

    public static void setLocation(Double lat, Double lng) {
        if (lat != null && lng != null) {
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
            pref.edit().putString(AppConstant.PreferenceKeeperNames.LATITUDE, "" + lat).apply();
            pref.edit().putString(AppConstant.PreferenceKeeperNames.LONGITUDE, "" + lng).apply();
        }
    }

    public static double getUserLat() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return Double.parseDouble(pref.getString(AppConstant.PreferenceKeeperNames.LATITUDE, "0"));
    }

    public static double getUserLng() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return Double.parseDouble(pref.getString(AppConstant.PreferenceKeeperNames.LONGITUDE, "0"));
    }

}
