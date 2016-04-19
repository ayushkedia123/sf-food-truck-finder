package com.ayush.sffoodtruckfinder.fragment;

import android.os.Bundle;

import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by ayushkedia on 19/04/16.
 */
public class MapFragment extends SupportMapFragment {

    public static SupportMapFragment getInstance() {
        return SupportMapFragment.newInstance();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }
}
