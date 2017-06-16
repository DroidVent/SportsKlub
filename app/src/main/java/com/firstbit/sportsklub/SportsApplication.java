package com.firstbit.sportsklub;

import android.app.Application;

import com.firstbit.sportsklub.network.SportsKlubAPI;
import com.firstbit.sportsklub.util.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hp-pc on 23-05-2017.
 */

public class SportsApplication extends Application {
    private static SportsApplication sInstance;
    private static Retrofit retrofit;
    private static SportsKlubAPI sportsAPI;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        initRetrofit();
    }

    private void initRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sportsAPI = retrofit.create(SportsKlubAPI.class);
    }

    public static SportsApplication getInstance() {
        return sInstance;
    }

    public SportsKlubAPI getSportsAPI() {
        return sportsAPI;

    }
}
