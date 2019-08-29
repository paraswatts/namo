package com.teamnamo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ActivityManager;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.jean.jcplayer.JcPlayerManagerListener;
import com.example.jean.jcplayer.general.JcStatus;
import com.example.jean.jcplayer.general.errors.OnInvalidPathListener;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AudioActivity extends AppCompatActivity implements OnInvalidPathListener, JcPlayerManagerListener {
    private JcPlayerView player;

    @BindView(R.id.rv_audio_list)
    RecyclerView rv_audio_list;
    AudioModal audioModal;
    AudioAdapter audioAdapter;
    List<JcAudio> audioModalList=new ArrayList<>();
    @BindView(R.id.adView)
    AdView adView;
    Ads ads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        ButterKnife.bind(this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(null, null, Color.parseColor("#F97D09"));

            setTaskDescription(td);
            getWindow().setStatusBarColor(Color.parseColor("#F97D09"));
            //getWindow().setNavigationBarColor(Color.parseColor("#000000"));

        }
        player = findViewById(R.id.jcplayer);

        getSupportActionBar().setTitle(getResources().getString(R.string.bhajans));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        try {
            JSONObject songs= new JSONObject(loadJSONFromAsset());

            JSONArray jsonArray= songs.getJSONArray("songs");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject= jsonArray.getJSONObject(i);
                audioModalList.add(JcAudio.createFromURL(jsonObject.getString("title"),jsonObject.getString("url")));
            }

            //player.kill();
            player.pause();
            player.initPlaylist(audioModalList, AudioActivity.this);
            adapterSetup();

        } catch (Exception e) {
            e.printStackTrace();
        }
        ads=new Ads(this);
        ads.googleAdBanner(this, adView);
    }

    protected void adapterSetup() {
        audioAdapter = new AudioAdapter(player.getMyPlaylist(),AudioActivity.this);
        audioAdapter.setOnItemClickListener(new AudioAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                try {
                    player.playAudio(player.getMyPlaylist().get(position));
                    player.createNotification();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //rv_notification.setNestedScrollingEnabled(false);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv_audio_list.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        rv_audio_list.setAdapter(audioAdapter);
        player.createNotification(R.mipmap.ic_launcher);
        ((SimpleItemAnimator) rv_audio_list.getItemAnimator()).setSupportsChangeAnimations(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("songs.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void onCompletedAudio() {

    }

    @Override
    public void onContinueAudio(JcStatus jcStatus) {

    }

    @Override
    public void onJcpError(Throwable throwable) {

    }

    @Override
    public void onPaused(JcStatus jcStatus) {

    }

    @Override
    public void onPlaying(JcStatus jcStatus) {

        player.createNotification();
    }

    @Override
    public void onPreparedAudio(JcStatus jcStatus) {
        player.createNotification();

    }

    @Override
    public void onStopped(JcStatus jcStatus) {

    }

    @Override
    public void onTimeChanged(JcStatus jcStatus) {
        updateProgress(jcStatus);

    }

    private void updateProgress(final JcStatus jcStatus) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // calculate progress
                float progress = (float) (jcStatus.getDuration() - jcStatus.getCurrentPosition())
                        / (float) jcStatus.getDuration();
                progress = 1.0f - progress;
                audioAdapter.updateProgress(jcStatus.getJcAudio(), progress);
            }
        });
    }

    @Override
    public void onPathError(JcAudio jcAudio) {

    }


    @Override
    protected void onStop() {
        super.onStop();
        player.createNotification();
    }


    @Override
    protected void onPause() {
        super.onPause();
        player.createNotification();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //player.createNotification();

        player.kill();
    }
}
