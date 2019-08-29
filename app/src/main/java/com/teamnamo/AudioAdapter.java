package com.teamnamo;


import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jean.jcplayer.model.JcAudio;

import java.util.List;

/**
 * Created by jean on 27/06/16.
 */

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.AudioAdapterViewHolder> {
    private static final String TAG = AudioAdapter.class.getSimpleName();
    private static OnItemClickListener mListener;
    private List<JcAudio> jcAudioList;
    Context context;
    private SparseArray<Float> progressMap = new SparseArray<>();

    public AudioAdapter(List<JcAudio> jcAudioList, Context context) {
        this.jcAudioList = jcAudioList;
        this.context = context;
        setHasStableIds(true);
    }

    // Define the method that allows the parent activity or fragment to define the jcPlayerManagerListener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public AudioAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.audio_item, parent, false);
        return new AudioAdapterViewHolder(view);
//        audiosViewHolder.itemView.setOnClickListener(this);
//        return audiosViewHolder;
    }

    @Override
    public void onBindViewHolder(AudioAdapterViewHolder holder, int position) {
        String title = position + 1 + "    " + jcAudioList.get(position).getTitle();
        holder.audioTitle.setText(title);
        holder.itemView.setTag(jcAudioList.get(position));

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
        if(position == jcAudioList.size()-1){
            cardLayoutParams.setMargins(px8, px8, px8, px8);
            holder.feed_card.setLayoutParams(cardLayoutParams);
        }
        else{
            cardLayoutParams.setMargins(px8, px8, px8, 0);
            holder.feed_card.setLayoutParams(cardLayoutParams);

        }

        applyProgressPercentage(holder, progressMap.get(position, 0.0f));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Applying percentage to progress.
     *
     * @param holder     ViewHolder
     * @param percentage in float value. where 1 is equals as 100%
     */
    private void applyProgressPercentage(AudioAdapterViewHolder holder, float percentage) {
        Log.d(TAG, "applyProgressPercentage() with percentage = " + percentage);
        LinearLayout.LayoutParams progress = (LinearLayout.LayoutParams) holder.viewProgress.getLayoutParams();
        LinearLayout.LayoutParams antiProgress = (LinearLayout.LayoutParams) holder.viewAntiProgress.getLayoutParams();

        progress.weight = percentage;
        holder.viewProgress.setLayoutParams(progress);

        antiProgress.weight = 1.0f - percentage;
        holder.viewAntiProgress.setLayoutParams(antiProgress);
    }

    @Override
    public int getItemCount() {
        return jcAudioList == null ? 0 : jcAudioList.size();
    }

    public void updateProgress(JcAudio jcAudio, float progress) {
        int position = jcAudioList.indexOf(jcAudio);
        Log.d(TAG, "Progress = " + progress);


        progressMap.put(position, progress);
        if (progressMap.size() > 1) {
            for (int i = 0; i < progressMap.size(); i++) {
                if (progressMap.keyAt(i) != position) {
                    Log.d(TAG, "KeyAt(" + i + ") = " + progressMap.keyAt(i));
                    notifyItemChanged(progressMap.keyAt(i));
                    progressMap.delete(progressMap.keyAt(i));
                }
            }
        }
        notifyItemChanged(position);
    }

    // Define the mListener interface
    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    static class AudioAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView audioTitle;
        RelativeLayout feed_card;
        private View viewProgress;
        private View viewAntiProgress;
        public AudioAdapterViewHolder(View view) {
            super(view);
            this.audioTitle = view.findViewById(R.id.tv_video);
            this.feed_card = view.findViewById(R.id.feed_card);
           viewProgress = view.findViewById(R.id.song_progress_view);
            viewAntiProgress = view.findViewById(R.id.song_anti_progress_view);


            feed_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (mListener != null) mListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}