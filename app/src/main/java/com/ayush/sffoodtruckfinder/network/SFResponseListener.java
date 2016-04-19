package com.ayush.sffoodtruckfinder.network;

import android.content.Context;

import com.ayush.sffoodtruckfinder.model.ApiErrorResponse;
import com.ayush.sffoodtruckfinder.model.ErrorObject;
import com.ayush.sffoodtruckfinder.utils.AppConstant;

import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ayushkedia on 16/04/16.
 */
public class SFResponseListener<T> implements Callback<T> {
    private WeakReference<Context> mActivityContext;
    private static List<SFResponseListener> requestList = new ArrayList<>();
    private boolean isCancelled = false;
    private WeakReference<ResponseListener> mResponseListenerWeakReference;

    public SFResponseListener(Context context) {
        mActivityContext = new WeakReference<>(context);
        requestList.add(this);
    }

    @Override
    public void success(T t, Response response) {
        if (!isCancelled) {
            if (mResponseListenerWeakReference != null) {
                ResponseListener lResponseListener = mResponseListenerWeakReference.get();
                if (lResponseListener != null) {
                    lResponseListener.onResponse(t);
                }
            } else {
                onSuccess(t);
            }
        }
        requestList.remove(this);
    }

    @Override
    public void failure(RetrofitError error) {
        int statusCode = 0;
        if (error.getResponse() != null) {
            statusCode = error.getResponse().getStatus();
        }
        requestList.remove(this);
        if (isCancelled)
            return;
        if (error.isNetworkError()) {
            if (error.getCause() instanceof SocketTimeoutException) {
                _onError(new ErrorObject(AppConstant.IErrorCode.defaultErrorCode, AppConstant.Constant.TIME_OUT_CONNECTION));
                return;
            }
            if (error.getCause() instanceof UnknownHostException) {
                _onError(new ErrorObject(AppConstant.IErrorCode.defaultErrorCode, AppConstant.Constant.NO_INTERNET_CONNECTION));
                return;
            }
        }

        ApiErrorResponse errorResponse;
        try {
            errorResponse = (ApiErrorResponse) error.getBodyAs(ApiErrorResponse.class);
        } catch (Exception e) {
            _onError(new ErrorObject(AppConstant.IErrorCode.defaultErrorCode, AppConstant.Constant.SOMETHING_WRONG_ERROR));
            return;
        }

        ErrorObject errorObject = null;
        if (errorResponse != null) {
            errorObject = errorResponse.error;
        } else {
            errorObject = new ErrorObject(AppConstant.IErrorCode.defaultErrorCode, AppConstant.Constant.SOMETHING_WRONG_ERROR);
        }

        switch (statusCode) {
            case 400:
                _onError(errorObject);
                return;
            case 500:
                _onError(errorObject);
                return;
            default:
                _onError(new ErrorObject(AppConstant.IErrorCode.defaultErrorCode, AppConstant.Constant.SOMETHING_WRONG_ERROR));

        }
        _onError(errorObject);
        return;
    }


    void _onError(ErrorObject error) {

        if (mResponseListenerWeakReference != null) {
            ResponseListener lResponseListener = mResponseListenerWeakReference.get();
            if (lResponseListener != null) {
                lResponseListener.onError(error);
            }
        } else {
            onError(error);
        }
    }

    public void onSuccess(T result) {
    }

    public void onError(ErrorObject error) {
    }

}

