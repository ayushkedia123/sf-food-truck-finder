package com.ayush.sffoodtruckfinder.network;

import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


import retrofit.Profiler;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.client.OkClient;

/**
 * Created by ayushkedia on 16/04/16.
 */
public class ClientGenerator {

    private static final String API_BASE_URL = "http://data.sfgov.org/resource";
    private static String TAG = ClientGenerator.class.getSimpleName();

    private static RestAdapter.Builder builder = new RestAdapter.Builder()
            .setEndpoint(API_BASE_URL)
            .setLog(new AndroidLog("api"))
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setClient(getOkClient());

    public static <S> S createService(Class<S> serviceClass) {

        builder.setProfiler(new Profiler() {
            @Override
            public Object beforeCall() {
                return null;
            }

            @Override
            public void afterCall(RequestInformation requestInfo, long elapsedTime, int statusCode, Object beforeCallData) {
                Log.d(TAG, String.format("HTTP %d %s %s (%dms)",
                        statusCode, requestInfo.getMethod(), requestInfo.getRelativePath(), elapsedTime));
            }
        });
        RestAdapter adapter = builder.build();
        return adapter.create(serviceClass);
    }

    public static OkClient getOkClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(8, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(8, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(8, TimeUnit.SECONDS);
        OkClient _client = new OkClient(okHttpClient);
        okHttpClient.networkInterceptors().add(new HeaderInterceptor());
        return _client;
    }

    static class HeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            request = request.newBuilder()
                    .addHeader("Vary", "Accept-Encoding")
                    .build();
            Response response = chain.proceed(request);
            Log.d(TAG, String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
            return response;
        }
    }


}

