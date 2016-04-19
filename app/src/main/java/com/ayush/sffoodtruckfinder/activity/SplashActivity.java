package com.ayush.sffoodtruckfinder.activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.ayush.sffoodtruckfinder.R;
import com.ayush.sffoodtruckfinder.utils.LocationService;
import com.ayush.sffoodtruckfinder.utils.OnLocationReceived;
import com.ayush.sffoodtruckfinder.utils.PreferenceKeeper;

public class SplashActivity extends AppCompatActivity implements OnLocationReceived {

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        PreferenceKeeper.setContext(getApplicationContext());
        initLocationService();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Creating an Intent that will start the MapActivity. */
                Intent mainIntent = new Intent(SplashActivity.this, MapActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void initLocationService() {
        /* Initialising Location service. */
        LocationService.getLocationService(this);
        if (LocationService.get() != null)
            LocationService.get().setOnLocationReceiver(this);
    }

    @Override
    public void onLocationReceive(Location location) {
        /* Saving User Location in the preference for further use. */
        PreferenceKeeper.setLocation(location.getLatitude(), location.getLongitude());
    }
}
