package com.ayush.sffoodtruckfinder.network;

import com.ayush.sffoodtruckfinder.model.ErrorObject;

/**
 * Created by ayushkedia on 16/04/16.
 */
public interface ResponseListener {
    void onResponse(Object pResponse);
    void onError(ErrorObject error);
}
