package com.teamnamo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {

    List<PhotosModal> items;
    int itemLayout;
    Activity context;
    public PhotosAdapter(List<PhotosModal> items, Activity context) {
        this.items = items;
        this.context=context;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.photo_item, parent, false);
        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        PhotosModal item = items.get(position);
        Resources r = context.getResources();
        int px8 = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                8,
                r.getDisplayMetrics()
        );
        int px4 = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                4,
                r.getDisplayMetrics()
        );
        holder.tv_photo.setText(item.getTitle());
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(8f);
        circularProgressDrawable.setCenterRadius(40f);
        int[] colors1 = {context.getResources().getColor(R.color.googleBlue),
                context.getResources().getColor(R.color.googleRed),
                context.getResources().getColor(R.color.googleYellow),
                context.getResources().getColor(R.color.googleGreen)};
        circularProgressDrawable.setColorSchemeColors(colors1);
        circularProgressDrawable.start();
        circularProgressDrawable.setStrokeCap(Paint.Cap.ROUND);
        Log.e("image url",item.getUrl()+"===");
        Glide
                .with(context)
                .load(item.getUrl())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        //((StaffViewHolder) holder).staff_image.setImageResource(R.drawable.img_avatar);
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .apply(new RequestOptions().placeholder(circularProgressDrawable))
                .into(holder.iv_video);

        LinearLayout.LayoutParams cardLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
         if(position == items.size()-1){
            cardLayoutParams.setMargins(px8, px8, px8, px8);
            holder.feed_card.setLayoutParams(cardLayoutParams);
        }
        else{
            cardLayoutParams.setMargins(px8, px8, px8, 0);
            holder.feed_card.setLayoutParams(cardLayoutParams);

        }
    }

    @Override public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_video;
        TextView tv_photo,tv_download;
        LinearLayout feed_card;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_download=itemView.findViewById(R.id.tv_download);
            iv_video= itemView.findViewById(R.id.iv_photo);
            tv_photo= itemView.findViewById(R.id.tv_photo);
            feed_card= itemView.findViewById(R.id.feed_card);
            iv_video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PhotosModal item = items.get(getAdapterPosition());
                    Intent intent = new Intent(context,FullScreenImage.class);
                    intent.putExtra("image",item.getUrl());
                    context.startActivity(intent);
                }
            });
            tv_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PhotosModal item = items.get(getAdapterPosition());
                    Download download = new Download(context);
                    download.savePic(item.getUrl());
                }
            });
        }
    }
}