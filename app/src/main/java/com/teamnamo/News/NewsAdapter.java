package com.teamnamo.News;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.teamnamo.PlainJavaFunction;
import com.teamnamo.R;
import com.teamnamo.ShareScreenShot;
import com.teamnamo.webView;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private static OnItemClickListener mListener;

    private List<NewsModal> items;
    private int itemLayout;
    Activity context;
    public NewsAdapter(List<NewsModal> items, Activity context) {
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
                R.layout.news_item, parent, false);
        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        NewsModal item = items.get(position);
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
        holder.tv_title.setText(item.getTitle());
        holder.tv_report_by.setText(item.getSource());

        holder.tv_timestamp.setText(PlainJavaFunction.getDateAndTime(item.getPublishedat()));
        holder.bt_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String postLink = item.getUrl()+System.getProperty("line.separator")+System.getProperty("line.separator")+System.getProperty("line.separator")+"https://play.google.com/store/apps/details?id=com.teamnamo";

                ShareScreenShot shareScreenShot = new ShareScreenShot(context);
                shareScreenShot.takeScreenshot(item.getUrl(),postLink,holder.feed_card,holder.bt_share,context);

//                Intent intent = new Intent();
//                // set intent action type
//                intent.setAction(Intent.ACTION_SEND);
//                //set mime type
//                intent.setType("text/plain");
//
//                //grant permisions for all apps that can handle given intent
//
//
//
//                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share news");
//                intent.putExtra(android.content.Intent.EXTRA_TEXT, postLink);
//
//                try {
//                    context.startActivity(Intent.createChooser(intent, "Share via"));
//                } catch (Exception e) {
//                    Log.e("exception screenshot",e.getMessage()+"====");
//                    Toast.makeText(context, "No App Available", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        holder.open_detail_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, webView.class);
                intent.putExtra("url",item.getUrl());
                context.startActivity(intent);
            }
        });
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

        if (item.getUrltoimage()!=null ||!TextUtils.isEmpty(item.getUrltoimage())) {
            Glide
                    .with(context)
                    .load(item.getUrltoimage())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            //((DayAdapter.ViewHolder) holder).iv_progress.setVisibility(View.GONE);
//                                ((FeedViewHolder) holder).image.setVisibility(View.VISIBLE);
                            Glide.with(context).asBitmap().load(R.drawable.placeholder).
                                    into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            Drawable drawable = new BitmapDrawable(context.getResources(), resource);
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                                ((ViewHolder) holder).iv_feedImage.setImageDrawable(drawable);
                                            }
                                        }


                                    });
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            //((DayAdapter.ViewHolder) holder).iv_progress.setVisibility(View.GONE);
                            ((ViewHolder) holder).iv_feedImage.setVisibility(View.VISIBLE);
                            return false;
                        }

//
                    })
                    .apply(new RequestOptions().placeholder(circularProgressDrawable))
                    .into(((ViewHolder) holder).iv_feedImage);

            Glide.with(context).asBitmap().load(item.getUrltoimage()). apply(bitmapTransform(new BlurTransformation(100))).
                    into(new SimpleTarget<Bitmap>(300, 200) {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            Drawable drawable = new BitmapDrawable(context.getResources(), resource);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                ((ViewHolder) holder).rl_image.setBackground(drawable);
                            }
                        }


                    });

        } else {
            //((DayAdapter.ViewHolder) holder).iv_progress.setVisibility(View.GONE);
            Glide.with(context).asBitmap().load(R.drawable.placeholder). apply(bitmapTransform(new BlurTransformation(100))).
                    into(new SimpleTarget<Bitmap>(300, 200) {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            Drawable drawable = new BitmapDrawable(context.getResources(), resource);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                ((ViewHolder) holder).rl_image.setBackground(drawable);
                            }
                        }


                    });
            ((ViewHolder) holder).iv_feedImage.setImageResource(R.drawable.placeholder);
            // ((FeedViewHolder) holder).image.setBackground(context.getDrawable(R.drawable.placeholder));
        }



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

        ImageView iv_feedImage;
        TextView tv_title,tv_timestamp,tv_report_by;
        LinearLayout open_detail_news;
        CardView feed_card;
        ImageButton bt_share;
        RelativeLayout rl_image;
        public ViewHolder(View itemView) {
            super(itemView);
            bt_share= itemView.findViewById(R.id.bt_share);
            iv_feedImage= itemView.findViewById(R.id.iv_feedImage);
            tv_title= itemView.findViewById(R.id.tv_title);
            open_detail_news= itemView.findViewById(R.id.open_detail_news);
            rl_image=itemView.findViewById(R.id.rl_image);
            tv_timestamp=itemView.findViewById(R.id.tv_timestamp);
            feed_card=itemView.findViewById(R.id.feed_card);

            tv_report_by=itemView.findViewById(R.id.tv_report_by);
        }
    }
}