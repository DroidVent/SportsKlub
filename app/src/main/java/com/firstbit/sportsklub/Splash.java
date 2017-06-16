package com.firstbit.sportsklub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firstbit.sportsklub.homescreen.data.SportsCategory;
import com.firstbit.sportsklub.network.SportsKlubAPI;
import com.firstbit.sportsklub.util.AppUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hp-pc on 20-05-2017.
 */

public class Splash extends Activity {
    private TextView mOutputText;
    ProgressBar mProgress;

    private ArrayList<SportsCategory> sportsCategories;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        init();
        if (AppUtil.isDeviceOnline(this))
            getCategories();
        else
            Toast.makeText(Splash.this, "Please check internet connection", Toast.LENGTH_LONG).show();
    }

    private void getCategories() {
        mProgress.setVisibility(View.VISIBLE);
        SportsKlubAPI sportsAPI = SportsApplication.getInstance().getSportsAPI();
        Call<ArrayList<SportsCategory>> categoryResponseCall = sportsAPI.getCategories();
        categoryResponseCall.enqueue(new Callback<ArrayList<SportsCategory>>() {
            @Override
            public void onResponse(Call<ArrayList<SportsCategory>> call, Response<ArrayList<SportsCategory>> response) {
                mProgress.setVisibility(View.GONE);
                if (response != null)
                    if (response.code() == 200)
                    {
                        sportsCategories = response.body();

                        Intent intent = new Intent(Splash.this, DrawerActivity.class);
                        intent.putExtra("categories", sportsCategories);
                        startActivity(intent);
                        finish();
                    }
            }

            @Override
            public void onFailure(Call<ArrayList<SportsCategory>> call, Throwable t) {
                mProgress.setVisibility(View.GONE);
                t.printStackTrace();
                Toast.makeText(Splash.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void init() {
        sportsCategories = new ArrayList<>();
        mOutputText = (TextView)findViewById(R.id.mOutputText);
        mProgress = (ProgressBar)findViewById(R.id.progressbar);
    }


}
