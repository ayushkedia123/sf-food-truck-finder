package com.ayush.sffoodtruckfinder.utils;

import android.location.Location;

/**
 * Interface to which calls the method when the location will be updated/received in location service.
 */
public interface OnLocationReceived {
    void onLocationReceive(Location location);
}
