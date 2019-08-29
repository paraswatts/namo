package com.teamnamo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdView;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutUs extends AppCompatActivity {
    private String youtubeAPI = "AIzaSyCCHuayCrwwcRAUZ__zTYyOP-ax5FD4R9E";
    YouTubePlayer youTubePlayer,youTubePlayer1;
    boolean isFullScreen;

    @BindView(R.id.adView)
    AdView adView;
    Ads ads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(null, null, Color.parseColor("#F97D09"));

            setTaskDescription(td);
            getWindow().setStatusBarColor(Color.parseColor("#F97D09"));
            //getWindow().setNavigationBarColor(Color.parseColor("#000000"));

        }

        getSupportActionBar().setTitle(getResources().getString(R.string.about_us));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FrameLayout containerYouTubePlayer = findViewById(R.id.youtube_holder);
        final YouTubePlayerFragment youTubePlayerFragment = YouTubePlayerFragment.newInstance();
        getFragmentManager().beginTransaction().replace(containerYouTubePlayer.getId(), youTubePlayerFragment).commit();
        youTubePlayerFragment.initialize(youtubeAPI, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer1=youTubePlayer;

                youTubePlayer1.loadVideo("4ZPAuvvdrsA");
                youTubePlayer1.setShowFullscreenButton(true);
                youTubePlayer1.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                    @Override
                    public void onFullscreen(boolean b) {
                        isFullScreen=b;
                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }
        });

        ads=new Ads(this);
        ads.googleAdBanner(this, adView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        Configuration config = getBaseContext().getResources().getConfiguration();

        String lang = settings.getString("LANG", "");

        if (! "".equals(lang) && ! config.locale.getLanguage().equals(lang)) {
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if(isFullScreen){
            if(youTubePlayer!=null) {
                youTubePlayer.setFullscreen(false);
            }
        }
        else {
            onBackPressed();
        }
        return true;
    }

    @Override
    protected void onPause() {
        if(youTubePlayer1!=null) {
            youTubePlayer1.pause();
        }
        super.onPause();
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(youTubePlayer!=null) {
            youTubePlayer.pause();
        }
    }



}
