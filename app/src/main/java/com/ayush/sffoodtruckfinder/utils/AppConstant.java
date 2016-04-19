package com.ayush.sffoodtruckfinder.utils;

/**
 * Created by ayushkedia on 16/04/16.
 */
public class AppConstant {

    public interface IErrorCode {
        String defaultErrorCode = "ER001";
        String notInternetConnErrorCode = "ER002";
    }

    public interface IErrorMessage {
        String defaultErrorMessage = "We are facing technical issues at the moment. Please try again later";
        String notInternetConnectMessage = "Please check internet connection";
    }

    public interface Constant {
        public static final String NO_INTERNET_CONNECTION = "No internet connection.";
        public static final String TIME_OUT_CONNECTION = "We could not complete your request.\nPlease try again later.";
        public static final String SOMETHING_WRONG_ERROR = "Something went wrong!!\nPlease try again later.";
        public static final String LOCTION_NOT_FETCH_ERROR_MESSAGE = "We could not fetch the city according to your current location.";
    }

    public interface intentExtras {
        String Applicant = "selected applicant";
        String Address = "address";
        String Permit = "permit";
        String FoodItems = "fooditems";
        String DayHours = "dayhours";
        String Applicant_Lat = "latititude";
        String Applicant_Lng = "longitude";
    }

    public interface PreferenceKeeperNames {
        String LATITUDE = "lat";
        String LONGITUDE = "lng";
    }

}
