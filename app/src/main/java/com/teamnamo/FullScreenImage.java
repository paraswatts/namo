package com.teamnamo;


import android.app.ActivityManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FullScreenImage extends AppCompatActivity implements View.OnClickListener {

    PhotoView photoView;
    ImageView iv_close_alarm;
    RelativeLayout full_image;
    @BindView(R.id.ll_download)
    LinearLayout ll_download;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

// Hide both the navigation bar and the status bar.
// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
// a general rule, you should design your app to hide the status bar whenever you
// hide the navigation bar.
        setContentView(R.layout.full_image);
        ButterKnife.bind(this);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(null, null, Color.parseColor("#000000"));

            setTaskDescription(td);
            getWindow().setStatusBarColor(Color.parseColor("#000000"));
            //getWindow().setNavigationBarColor(Color.parseColor("#000000"));

        }
        full_image= findViewById(R.id.full_image);
        photoView = findViewById(R.id.iv_alarm);

        iv_close_alarm = findViewById(R.id.iv_close_alarm);
        iv_close_alarm.setOnClickListener(this);

//        if(getIntent().getBooleanExtra("fromProfile",false)){
//            full_image.setBackgroundColor(getResources().getColor(R.color.black));
//            Glide.with(this)
//                    .load(getIntent().getStringExtra("image"))
//                    .apply(RequestOptions.circleCropTransform())
//                    .into(photoView);
//        }
//        else{

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(FullScreenImage.this);
        circularProgressDrawable.setStrokeWidth(8f);
        circularProgressDrawable.setCenterRadius(40f);
        int[] colors1 = {getResources().getColor(R.color.googleBlue),
                getResources().getColor(R.color.googleRed),
                getResources().getColor(R.color.googleYellow),
                getResources().getColor(R.color.googleGreen)};
        circularProgressDrawable.setColorSchemeColors(colors1);
        circularProgressDrawable.start();
        circularProgressDrawable.setStrokeCap(Paint.Cap.ROUND);
            Glide
                    .with(this)
                    .load(getIntent().getStringExtra("image"))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            //imageView.setVisibility(View.GONE);
                            //Log.e("load failed","glide");
//                                ((FeedViewHolder) holder).image.setVisibility(View.VISIBLE);
                            return true;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            // Log.e("load success","glide");
                            return false;
                        }

//
                    })
                    .apply(new RequestOptions().placeholder(circularProgressDrawable))

                    .into(photoView);
        ll_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Download download = new Download(FullScreenImage.this);
                download.savePic(getIntent().getStringExtra("image"));
            }
        });
        //}
    }



    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.iv_close_alarm){
            finish();
        }
    }


}