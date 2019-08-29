package com.teamnamo.BreakingNews;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.teamnamo.PlainJavaFunction;
import com.teamnamo.R;

import java.util.List;

public class BreakingNewsAdapter extends RecyclerView.Adapter<BreakingNewsAdapter.ViewHolder> {
    private static OnItemClickListener mListener;

    private List<BreakingNewsModal> items;
    private int itemLayout;
    Context context;
    public BreakingNewsAdapter(List<BreakingNewsModal> items, Context context) {
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
                R.layout.breaking_new_item, parent, false);
        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        BreakingNewsModal item = items.get(position);
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
        LinearLayout.LayoutParams cardLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        if(position == items.size()-1){
            cardLayoutParams.setMargins(px8, px8, px8, px8);
            holder.feed_card.setLayoutParams(cardLayoutParams);
        }
        else{
            cardLayoutParams.setMargins(px8, px8, px8, 0);
            holder.feed_card.setLayoutParams(cardLayoutParams);

        }
        holder.tv_title.setText(item.getDescription());
        holder.tv_timestamp.setText(PlainJavaFunction.getDateAndTime(item.getPublishedat()));
        holder.bt_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String postLink = item.getDescription()+System.getProperty("line.separator")+"https://play.google.com/store/apps/details?id=com.teamnamo";
                Intent intent = new Intent();
                // set intent action type
                intent.setAction(Intent.ACTION_SEND);
                //set mime type
                intent.setType("text/plain");

                //grant permisions for all apps that can handle given intent



                intent.putExtra(Intent.EXTRA_SUBJECT, "Share news");
                intent.putExtra(Intent.EXTRA_TEXT, postLink);

                try {
                    context.startActivity(Intent.createChooser(intent, "Share via"));
                } catch (Exception e) {
                    Log.e("exception screenshot",e.getMessage()+"====");
                    Toast.makeText(context, "No App Available", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView feed_card;

        TextView tv_title,tv_timestamp,tv_report_by;
        ImageButton bt_share;
        public ViewHolder(View itemView) {
            super(itemView);

            tv_title= itemView.findViewById(R.id.tv_title);

            tv_timestamp=itemView.findViewById(R.id.tv_timestamp);
            bt_share= itemView.findViewById(R.id.bt_share);
            feed_card=itemView.findViewById(R.id.feed_card);

            tv_report_by=itemView.findViewById(R.id.tv_report_by);
        }
    }
}