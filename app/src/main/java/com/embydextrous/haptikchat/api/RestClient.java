package com.embydextrous.haptikchat.api;

import android.content.Context;
import android.content.ContextWrapper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient extends ContextWrapper {

    private static final String BASE_URL = "http://haptik.mobi/android/";
    private static RestClient restClient;
    private ApiService apiService;
    private static OkHttpClient okHttpClient;
    private static final int READ_TIMEOUT = 10 * 1000;
    private static final int CONNECTION_TIMEOUT = 15 * 1000;

    private RestClient(Context base) {
        super(base.getApplicationContext());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL).client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public static RestClient getInstance(Context context) {
        if (restClient==null)
            restClient = new RestClient(context);
        return restClient;
    }

    public ApiService getApiService() {
        return apiService;
    }

    private static OkHttpClient getOkHttpClient() {
        if (okHttpClient==null) {
            okHttpClient = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                    .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                    .build();
        }
        return okHttpClient;
    }
}
