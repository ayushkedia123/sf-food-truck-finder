package com.ayush.sffoodtruckfinder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ayush.sffoodtruckfinder.R;
import com.ayush.sffoodtruckfinder.model.FoodTruck;
import com.ayush.sffoodtruckfinder.utils.AppUtil;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by ayushkedia on 18/04/16.
 */
public class FoodTruckListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<FoodTruck> foodTrucks;
    private double distanceFromUser;
    private Comparator<FoodTruck> sortByDistance;

    public FoodTruckListAdapter(Context context, ArrayList<FoodTruck> foodTrucks) {
        this.context = context;
        this.foodTrucks = foodTrucks;
        initializeComparatorByDistance();
        Collections.sort(foodTrucks, sortByDistance);
    }

    @Override
    public int getCount() {
        return foodTrucks.size();
    }

    @Override
    public Object getItem(int position) {
        return foodTrucks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView == null) {
            holder = new ChildViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_food_truck_list, null);
            holder.tvApplicant = (TextView) convertView.findViewById(R.id.tv_list_item_applicant);
            holder.tvDistanceFromUser = (TextView) convertView.findViewById(R.id.tv_list_item_distance_from_user);
            holder.tvLocation = (TextView) convertView.findViewById(R.id.tv_list_item_location);
            holder.tvPermit = (TextView) convertView.findViewById(R.id.tv_list_item_permit);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }

        FoodTruck foodTruck = foodTrucks.get(position);

        holder.tvApplicant.setText(foodTruck.applicant);
        holder.tvLocation.setText(foodTruck.address);
        holder.tvPermit.setText(foodTruck.permit);
        if (foodTruck.latitude != null && foodTruck.longitude != null) {
            distanceFromUser = AppUtil.getDistanceBetweenPoints(new LatLng(Double.parseDouble(foodTruck.latitude), Double.parseDouble(foodTruck.longitude)));
            holder.tvDistanceFromUser.setText(String.format("%.2f", distanceFromUser) + " km");
        } else
            holder.tvDistanceFromUser.setVisibility(View.GONE);
        return convertView;
    }


    /**
     * child view holder.
     */
    private class ChildViewHolder {
        TextView tvApplicant;
        TextView tvLocation;
        TextView tvDistanceFromUser;
        TextView tvPermit;
    }

    private void initializeComparatorByDistance() {
        sortByDistance = new Comparator<FoodTruck>() {
            @Override
            public int compare(FoodTruck lhs, FoodTruck rhs) {
                if (lhs.latitude != null && rhs.latitude != null) {
                    Double distanceLHS = AppUtil.getDistanceBetweenPoints(new LatLng(Double.parseDouble(lhs.latitude), Double.parseDouble(lhs.longitude)));
                    Double distanceRHS = AppUtil.getDistanceBetweenPoints(new LatLng(Double.parseDouble(rhs.latitude), Double.parseDouble(rhs.longitude)));
                    return (distanceLHS.compareTo(distanceRHS));
                }
                return 0;
            }
        };
    }
}
