package com.ayush.sffoodtruckfinder.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ayush.sffoodtruckfinder.R;
import com.ayush.sffoodtruckfinder.activity.FoodTruckDetailActivity;
import com.ayush.sffoodtruckfinder.adapter.FoodTruckListAdapter;
import com.ayush.sffoodtruckfinder.model.FoodTruck;
import com.ayush.sffoodtruckfinder.utils.AppConstant;

import java.util.ArrayList;

/**
 * Created by ayushkedia on 18/04/16.
 */
public class FoodTruckListFragment extends Fragment {

    private ArrayList<FoodTruck> foodTrucksList;
    private View rootView;
    private ListView foodTruckListView;
    private FoodTruckListAdapter foodTruckListAdapter;

    public static FoodTruckListFragment getInstanceOf(Context context, ArrayList<FoodTruck> foodTrucks) {
        FoodTruckListFragment foodTruckListFragment = new FoodTruckListFragment();
        foodTruckListFragment.setFoodTruckList(foodTrucks);
        return foodTruckListFragment;
    }

    public void setFoodTruckList(ArrayList<FoodTruck> foodTrucksList) {
        this.foodTrucksList = foodTrucksList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_list_view, null);
            foodTruckListView = (ListView) rootView.findViewById(R.id.rv_foodtruck_list);
            initListeners();
            foodTruckListAdapter = new FoodTruckListAdapter(getActivity(), foodTrucksList);
            foodTruckListView.setAdapter(foodTruckListAdapter);
        }
        return rootView;
    }

    private void initListeners() {

        foodTruckListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent mainIntent = new Intent(getActivity(), FoodTruckDetailActivity.class);
                mainIntent.putExtra(AppConstant.intentExtras.Address, foodTrucksList.get(position).address);
                mainIntent.putExtra(AppConstant.intentExtras.Applicant, foodTrucksList.get(position).applicant);
                mainIntent.putExtra(AppConstant.intentExtras.Applicant_Lat, foodTrucksList.get(position).latitude);
                mainIntent.putExtra(AppConstant.intentExtras.Applicant_Lng, foodTrucksList.get(position).longitude);
                mainIntent.putExtra(AppConstant.intentExtras.DayHours, foodTrucksList.get(position).dayshours);
                mainIntent.putExtra(AppConstant.intentExtras.FoodItems, foodTrucksList.get(position).fooditems);
                mainIntent.putExtra(AppConstant.intentExtras.Permit, foodTrucksList.get(position).permit);
                getActivity().startActivity(mainIntent);
                getActivity().overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
            }
        });
    }

}
