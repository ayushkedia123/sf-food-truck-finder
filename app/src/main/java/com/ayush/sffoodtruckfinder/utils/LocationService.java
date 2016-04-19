package com.ayush.sffoodtruckfinder.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.List;

/**
 * Singleton class to provide the services related to the getting the current location.
 */
public class LocationService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, OnLocationReceived {
    private Activity context;
    private static final long LOCATION_TIME_INTERVAL = 3 * 60 * 1000;
    private static final long LOCATION_DISTANCE_INTERVAL = 500;
    private OnLocationReceived onLocationReceiver;
    private Location location = null;
    private LocationRequest mLocationRequest;
    public GoogleApiClient mGoogleApiClient;
    private boolean isPlayServicesExist;


    /**
     * private constructor.
     */
    private LocationService() {
    }

    // Static reference for holding the locationService instance.
    private static LocationService locationServiceInstance;

    public void setOnLocationReceiver(OnLocationReceived onLocationReceiver) {
        this.onLocationReceiver = onLocationReceiver;
    }


    /**
     * Method to get the singleton object of LocationService.
     */


    public static LocationService getLocationService(Context context) {

//        GooglePlayServicesUtil.isGooglePlayServicesAvailable()
        if (locationServiceInstance == null) {
            locationServiceInstance = new LocationService();
        }
        locationServiceInstance.context = (Activity) context;
        locationServiceInstance.requestLocationServices();
//        locationServiceInstance.onLocationReceiver = locationServiceInstance;
        return locationServiceInstance;
    }

    public static LocationService get() {
        return locationServiceInstance;
    }

//    public void retry() {
//        requestLocationServices();
//    }

    public void removeLocationService() {
        if (!isPlayServicesExist) {
            return;
        }
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
        mGoogleApiClient = null;
    }

    public static boolean checkPlayServices(Activity context) {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            AppUtil.showLongToast(context, "This device is not supported");
            return false;
        }
        return true;
    }

    public void requestLocationServices() {
        isPlayServicesExist = checkPlayServices(context);
        if (!isPlayServicesExist) {
            getLocationFromLocationManager();
            onLocationReceive(location);
            return;
        }
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        } else {
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
        }
    }

    private void getLocationFromLocationManager() {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        List<String> providers = manager.getProviders(criteria, true);
        if (providers != null) {
            for (String bestProvider : providers) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                location = manager.getLastKnownLocation(bestProvider);
                if (location != null) {
                    break;
                }
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        onLocationReceive(location);
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(LOCATION_TIME_INTERVAL);// Update location every second
        mLocationRequest.setFastestInterval(LOCATION_TIME_INTERVAL);
        mLocationRequest.setSmallestDisplacement(LOCATION_DISTANCE_INTERVAL);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        try {
            mGoogleApiClient.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        if (location != null) {
            onLocationReceive(location);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("connection", "onConnectionFailed " + connectionResult.getErrorCode());
        mGoogleApiClient.connect();
    }

    public void onLocationReceive(Location location) {
        if (location != null) {
            PreferenceKeeper.setLocation(location.getLatitude(), location.getLongitude());

            if (onLocationReceiver != null) {
                onLocationReceiver.onLocationReceive(location);
            }
            this.location = location;
            Intent intent = new Intent(context.getPackageName() + ":LS");
            intent.putExtra("lat", location.getLatitude());
            intent.putExtra("lng", location.getLongitude());
            context.sendBroadcast(intent);

        }
    }
}

