package com.ayush.sffoodtruckfinder.utils;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ayushkedia on 16/04/16.
 */
public class AppUtil {

    private static AppUtil appUtil;
    private static Toast mToast;
    private Context context;

    private AppUtil() {
        //private Constructor
    }

    public static AppUtil getInstance() {
        if (appUtil == null) {
            appUtil = new AppUtil();
        }
        return appUtil;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public static double getDistance(double lat1, double lon1, double lat2, double lon2, boolean isKm) {
        double radlat1 = Math.PI * lat1 / 180;
        double radlat2 = Math.PI * lat2 / 180;
        double theta = lon1 - lon2;
        double radtheta = Math.PI * theta / 180;
        double dist = Math.sin(radlat1) * Math.sin(radlat2) + Math.cos(radlat1) * Math.cos(radlat2) * Math.cos(radtheta);
        dist = Math.acos(dist);
        dist = dist * 180 / Math.PI;
        dist = dist * 60 * 1.1515;
        if (isKm) {
            dist = dist * 1.609344;
        }
        return dist;
    }

    public static void openGoogleMap(Context context, LatLng src, LatLng dest) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?&daddr=" + dest.latitude + "," + dest.longitude));
//        +"&daddr="+dest.latitude+","+dest.longitude)
        context.startActivity(intent);
    }

    public static double getDistanceBetweenPoints(LatLng latLng) {
        double theta = PreferenceKeeper.getUserLng() - latLng.longitude;
        double dist = Math.sin(deg2rad(PreferenceKeeper.getUserLat())) * Math.sin(deg2rad(latLng.latitude)) + Math.cos(deg2rad(PreferenceKeeper.getUserLat())) * Math.cos(deg2rad(latLng.latitude))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = (dist * 60 * 1.1515) * 1.609344;
        return dist;
    }

    public static double deg2rad(double theta) {
        return theta * (Math.PI / 180.0);
    }

    public static double rad2deg(double dist) {
        return dist * (180 / Math.PI);
    }

    public static boolean checkLocationService(String providers, Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(providers);
    }

    public static boolean isLocationServiceTurnedOff(Context context) {
        return (!AppUtil.checkLocationService(LocationManager.GPS_PROVIDER, context) && !AppUtil.checkLocationService(LocationManager.NETWORK_PROVIDER, context));
    }

    public static void showLongToast(Context context, String msg) {
        showToast(context, msg, Toast.LENGTH_LONG);
    }

    public static void showShortToast(Context context, String msg) {
        showToast(context, msg, Toast.LENGTH_SHORT);
    }

    private static void showToast(Context context, String msg, int duration) {
        if (mToast != null)
            mToast.cancel();
        mToast = Toast.makeText(context, msg, duration);
        mToast.show();
    }
}
