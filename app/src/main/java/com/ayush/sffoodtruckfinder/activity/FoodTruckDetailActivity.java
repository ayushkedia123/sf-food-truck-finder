package com.ayush.sffoodtruckfinder.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ayush.sffoodtruckfinder.R;
import com.ayush.sffoodtruckfinder.utils.AppConstant;
import com.ayush.sffoodtruckfinder.utils.AppUtil;
import com.google.android.gms.maps.model.LatLng;

public class FoodTruckDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewApplicantName;
    private TextView textViewAddress;
    private TextView textViewDistanceFromUser;
    private TextView textViewPermit;
    private TextView textViewDayHours;
    private TextView textViewFoodItems;
    private String applicant;
    private String address;
    private String applicantLat;
    private String applicantLng;
    private String permit;
    private String foodItems;
    private String dayHours;
    private double distanceFromUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_truck_detail);
        initUI();
        distanceFromUser = AppUtil.getDistanceBetweenPoints(new LatLng(Double.parseDouble(applicantLat), Double.parseDouble(applicantLng)));
        textViewDistanceFromUser.setText(String.format("%.2f", distanceFromUser) + " km");

    }

    private void initUI() {
        textViewApplicantName = (TextView) findViewById(R.id.tv_applicant_name);
        textViewAddress = (TextView) findViewById(R.id.tv_location);
        textViewDistanceFromUser = (TextView) findViewById(R.id.tv_distance_from_user);
        textViewPermit = (TextView) findViewById(R.id.tv_permit);
        textViewDayHours = (TextView) findViewById(R.id.tv_day_hours);
        textViewFoodItems = (TextView) findViewById(R.id.tv_food_items);
        applicant = getIntent().getStringExtra(AppConstant.intentExtras.Applicant);
        address = getIntent().getStringExtra(AppConstant.intentExtras.Address);
        applicantLat = getIntent().getStringExtra(AppConstant.intentExtras.Applicant_Lat);
        applicantLng = getIntent().getStringExtra(AppConstant.intentExtras.Applicant_Lng);
        permit = getIntent().getStringExtra(AppConstant.intentExtras.Permit);
        foodItems = getIntent().getStringExtra(AppConstant.intentExtras.FoodItems);
        dayHours = getIntent().getStringExtra(AppConstant.intentExtras.DayHours);
        textViewApplicantName.setText(applicant);
        textViewAddress.setText(address);
        textViewPermit.setText(permit);
        textViewFoodItems.setText(foodItems);

        if (dayHours != null)
            textViewDayHours.setText(dayHours);
        else
            textViewDayHours.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_left_to_right, R.anim.anim_right_to_left);
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}
