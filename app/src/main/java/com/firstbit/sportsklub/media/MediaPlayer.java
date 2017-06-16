package com.firstbit.sportsklub.media;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.firstbit.sportsklub.R;
import com.github.rtoshiro.view.video.FullscreenVideoLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.IOException;

/**
 * Created by hp-pc on 22-05-2017.
 */

public class MediaPlayer extends AppCompatActivity {
    FullscreenVideoLayout videoView;
    String videoPath;
    private ProgressBar progressBar;
    InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getBundle();
        setContentView(R.layout.media);
        init();


        try {
            videoView.setVideoPath(videoPath);
            videoView.requestFocus();

            videoView.setOnPreparedListener(new android.media.MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(android.media.MediaPlayer mediaPlayer) {

                    progressBar.setVisibility(View.GONE);
                    videoView.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void init() {
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        videoView = (FullscreenVideoLayout) this.findViewById(R.id.videoview);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));

        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice("25EFEFDFADF8C247CE4B2A7A488A2F56")
                .build();
        mInterstitialAd.loadAd(adRequest);
    }
    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }
    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            videoPath = bundle.getString("video_path");
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showInterstitial();
    }
}
