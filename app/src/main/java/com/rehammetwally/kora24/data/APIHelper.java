package com.rehammetwally.kora24.data;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
//import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIHelper {

    private static Api apiService;

    public static Api getApiService() {

        String BASE_URL ="http://kora24life.tk/kora24/public/api/";
        Log.e("BASE_URL", BASE_URL);

        if (apiService == null) {
//            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//            OkHttpClient clientLog = new OkHttpClient.Builder()
//                    .addInterceptor(logging)
//                    .build();
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
//                    .addInterceptor(logging)
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .build();
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson)).build();
            apiService = retrofit.create(Api.class);
        }
        return apiService;
    }
}
