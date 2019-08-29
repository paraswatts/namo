package com.teamnamo.Youtube;

import android.content.Context;
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
import com.teamnamo.R;

import java.util.List;

public class VideoAdapter  extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private static OnItemClickListener mListener;

    private List<VideoModal> items;
    private int itemLayout;
    Context context;
    public VideoAdapter(List<VideoModal> items, Context context) {
        this.items = items;
        this.context=context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.video_item, parent, false);
        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        VideoModal item = items.get(position);
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
        holder.tv_video.setText(item.getTitle());
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

        Glide
                .with(context)
                .load(item.getThumbnail())
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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_video;
        TextView tv_video;
        CardView feed_card;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_video= itemView.findViewById(R.id.iv_video);
            tv_video= itemView.findViewById(R.id.tv_video);
            feed_card= itemView.findViewById(R.id.feed_card);
            feed_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("audio adapter clicked",mListener+"=======");
                    // Triggers click upwards to the adapter on click
                    if (mListener != null) mListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}