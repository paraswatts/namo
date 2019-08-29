package com.teamnamo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.teamnamo.BreakingNews.BreakingNewsActivity;
import com.teamnamo.News.NewsActivity;
import com.teamnamo.Youtube.YoutubeActivity;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ApiInterface customAdApiInterface;
    @BindView(R.id.ll_about)
    LinearLayout ll_about;
    @BindView(R.id.ll_overview)
    LinearLayout ll_overview;
    @BindView(R.id.ll_youtube)
    LinearLayout ll_youtube;
    @BindView(R.id.ll_education)
    LinearLayout ll_education;
    @BindView(R.id.ll_bhajan)
    LinearLayout ll_bhajan;
    @BindView(R.id.ll_photos)
    LinearLayout ll_photos;
    @BindView(R.id.ll_share)
    LinearLayout ll_share;
    @BindView(R.id.ll_rate)
    LinearLayout ll_rate;
    @BindView(R.id.ll_play)
    LinearLayout ll_play;
    @BindView(R.id.iv_play)
    ImageView iv_play;

    private MediaPlayer mediaPlayer;

    static String satsangUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(null, null, Color.parseColor("#F97D09"));

            setTaskDescription(td);
            getWindow().setStatusBarColor(Color.parseColor("#F97D09"));
            getWindow().setNavigationBarColor(Color.parseColor("#F97D09"));

        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ll_about.setOnClickListener(this);
        ll_overview.setOnClickListener(this);
        ll_youtube.setOnClickListener(this);
        ll_education.setOnClickListener(this);
        ll_bhajan.setOnClickListener(this);
        ll_photos.setOnClickListener(this);
        ll_share.setOnClickListener(this);
        ll_play.setOnClickListener(this);
        ll_rate.setOnClickListener(this);

        iv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer!=null){
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                        iv_play.setImageResource(R.drawable.ic_play_button);
                    }
                    else{
                        mediaPlayer.start();
                        iv_play.setImageResource(R.drawable.ic_pause);
                    }
                }
            }
        });
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        Configuration config = getBaseContext().getResources().getConfiguration();


    }


    @Override
    protected void onPause() {
        if(mediaPlayer!=null){
            mediaPlayer.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {

//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    // DO your work here
//                    // get the data
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                // update UI
//                                mediaPlayer = new MediaPlayer();
//                                mediaPlayer.setDataSource(MainActivity.this, Uri.parse("https://firebasestorage.googleapis.com/v0/b/team-namo-346de.appspot.com/o/Vande%20Matram%20-%20AR%20Rehman%20320Kbps.mp3?alt=media&token=b7fd9999-7873-4b76-980a-12d40e5a0c7d"));
//                                mediaPlayer.prepare();
//
//                                if (mediaPlayer != null) {
//
//                                    mediaPlayer.start();
//                                    if (mediaPlayer.isPlaying()) {
//                                        iv_play.setImageResource(R.drawable.ic_pause);
//                                    } else {
//                                        iv_play.setImageResource(R.drawable.ic_play_button);
//                                    }
//                                }
//                                }catch (Exception e){
//
//                                }
//                            }
//                        });
//
//                }
//            }).start();
        super.onResume();
    }




    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ll_about) {
            startActivity(new Intent(MainActivity.this, NewsActivity.class));
        }
        if (view.getId() == R.id.ll_overview) {
            startActivity(new Intent(MainActivity.this, BreakingNewsActivity.class));

        }
        if (view.getId() == R.id.ll_youtube) {
            startActivity(new Intent(MainActivity.this, YoutubeActivity.class));

        }
        if (view.getId() == R.id.ll_education) {
            Intent intent= new Intent(MainActivity.this,webView.class);
            intent.putExtra("url","http://pmonradio.nic.in/");
            startActivity(intent);
        }
        if (view.getId() == R.id.ll_bhajan) {
            Intent intent= new Intent(MainActivity.this,webView.class);
            intent.putExtra("url","https://www.narendramodi.in/");
            startActivity(intent);
        }
        if (view.getId() == R.id.ll_photos) {

        }
        if (view.getId() == R.id.ll_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "" +
                    getString(R.string.download_link)
            );
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
        if (view.getId() == R.id.ll_play) {

        }

        if (view.getId() == R.id.ll_rate) {
            final String appPackageName = "com.teamnamo"; // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }

    }
}
