package com.ayush.sffoodtruckfinder.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.ayush.sffoodtruckfinder.R;
import com.ayush.sffoodtruckfinder.fragment.FoodTruckListFragment;
import com.ayush.sffoodtruckfinder.fragment.MapFragment;
import com.ayush.sffoodtruckfinder.model.ErrorObject;
import com.ayush.sffoodtruckfinder.model.FoodTruck;
import com.ayush.sffoodtruckfinder.network.ClientGenerator;
import com.ayush.sffoodtruckfinder.network.SFResponseListener;
import com.ayush.sffoodtruckfinder.network.RequestBuilder;
import com.ayush.sffoodtruckfinder.utils.AppConstant;
import com.ayush.sffoodtruckfinder.utils.AppUtil;
import com.ayush.sffoodtruckfinder.utils.PreferenceKeeper;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class MapActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private ArrayList<FoodTruck> mFoodTrucks = new ArrayList<FoodTruck>();

    /* Mapping FoodTruck Object with Google Pins by Marker ID */
    private HashMap<String, FoodTruck> mHashMap = new HashMap<String, FoodTruck>();
    private FoodTruckListFragment foodTruckListFragment;
    private Boolean isMapFragment = true;
    private SupportMapFragment mapFragment;
    private ImageButton iconMapListChange;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        /* Progress Bar is making visible till the api response comes. */
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setIndeterminate(true);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);
        progressBar.setVisibility(View.VISIBLE);

        /* Disabling the toggle of Map and List view till the api response comes. */
        iconMapListChange = (ImageButton) findViewById(R.id.ib_map_list);
        iconMapListChange.setEnabled(false);

        /* calling api to get the foodtruck list near user */
        getFoodTruckList();
    }

    private void loadMap() {

        mapFragment = MapFragment.getInstance();

        /* Setting up onMapReady callback */
        mapFragment.getMapAsync(this);

        if (!isFinishing()) {
            /* Replacing fragment */
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_content_fragment, mapFragment).addToBackStack(null).commit();
        }
    }

    /* Calling api to get list of foodtrucks which will be in the range of 5km from the user location. */

    private void getFoodTruckList() {
        RequestBuilder requestBuilder = ClientGenerator.createService(RequestBuilder.class);
        String userLatLng = "within_circle(location," + PreferenceKeeper.getUserLat() + "," + PreferenceKeeper.getUserLng() + "," + "5000)";
        requestBuilder.getFoodTruck(userLatLng, new SFResponseListener<ArrayList<FoodTruck>>(this) {
            @Override
            public void onSuccess(ArrayList<FoodTruck> foodTrucks) {
                mFoodTrucks = foodTrucks;
                /* loading map after successful api response. */
                loadMap();
                /* Enabling the toggle of Map and List view. */
                iconMapListChange.setEnabled(true);
            }

            @Override
            public void onError(ErrorObject error) {
                progressBar.setVisibility(View.GONE);
                if (error.getErrorMessage().equals(AppConstant.Constant.NO_INTERNET_CONNECTION)) {
                    AppUtil.showShortToast(MapActivity.this, error.getErrorMessage());
                }
            }
        });
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        String currentMarkerId;

        /* Setting Blue color marker for the user Location */
        if (!AppUtil.isLocationServiceTurnedOff(this) && (PreferenceKeeper.getUserLat() != 0 && PreferenceKeeper.getUserLng() != 0)) {
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(PreferenceKeeper.getUserLat(), PreferenceKeeper.getUserLng()))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnInfoWindowClickListener(this);

        /* Adding food truck to map and saves in map table */
        for (FoodTruck foodTruck : mFoodTrucks) {
            if (foodTruck.latitude != null && foodTruck.longitude != null) {
                currentMarkerId = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(foodTruck.latitude), Double.parseDouble(foodTruck.longitude)))
                        .title(foodTruck.applicant)
                        .snippet(foodTruck.fooditems)).getId();
                mHashMap.put(currentMarkerId, foodTruck);
            }
        }

        /* Creating an Intent that will start the FoodTruckDetail activity on clicking the marker. */
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                FoodTruck foodTruck = mHashMap.get(marker.getId());
                if (foodTruck != null) {
                    Intent mainIntent = new Intent(MapActivity.this, FoodTruckDetailActivity.class);
                    mainIntent.putExtra(AppConstant.intentExtras.Address, foodTruck.address);
                    mainIntent.putExtra(AppConstant.intentExtras.Applicant, foodTruck.applicant);
                    mainIntent.putExtra(AppConstant.intentExtras.Applicant_Lat, foodTruck.latitude);
                    mainIntent.putExtra(AppConstant.intentExtras.Applicant_Lng, foodTruck.longitude);
                    mainIntent.putExtra(AppConstant.intentExtras.DayHours, foodTruck.dayshours);
                    mainIntent.putExtra(AppConstant.intentExtras.FoodItems, foodTruck.fooditems);
                    mainIntent.putExtra(AppConstant.intentExtras.Permit, foodTruck.permit);
                    MapActivity.this.startActivity(mainIntent);
                    overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                }
                return true;
            }

        });

        /* Moves camera */
        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                PreferenceKeeper.setLocation(location.getLatitude(), location.getLongitude());
                CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
                googleMap.moveCamera(center);
                googleMap.animateCamera(zoom);
                googleMap.setOnMyLocationChangeListener(null);
            }
        });

    }

    /* Handling click for the toggling of Map and List view of foodtrucks. */

    @Override
    public void onClick(View v) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (isMapFragment) {
            foodTruckListFragment = FoodTruckListFragment.getInstanceOf(this, mFoodTrucks);
            fragmentTransaction.replace(R.id.main_content_fragment, foodTruckListFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            isMapFragment = false;
            iconMapListChange.setImageResource(R.drawable.ic_map);
        } else {
            getSupportFragmentManager().popBackStack();
            isMapFragment = true;
            iconMapListChange.setImageResource(R.drawable.ic_list_view);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        finish();
    }
}
