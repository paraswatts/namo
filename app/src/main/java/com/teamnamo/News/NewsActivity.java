package com.teamnamo.News;

import android.app.ActivityManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdView;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.gson.JsonElement;
import com.teamnamo.Ads;
import com.teamnamo.ApiInterface;
import com.teamnamo.BaseApi;
import com.teamnamo.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    ApiInterface customAdApiInterface;
    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout shimmer_view_container;
    @BindView(R.id.rv_video_list)
    RecyclerView rv_video_list;
    NewsAdapter newsAdapter;
    List<NewsModal> newsModalList = new ArrayList<>();
    NewsModal newsModal;
    private String youtubeAPI = "AIzaSyCCHuayCrwwcRAUZ__zTYyOP-ax5FD4R9E";
    YouTubePlayer youTubePlayer,youTubePlayer1;
    private static final int DELAY_MILLIS = 10;
    boolean isFullScreen;
    private static String pageToken="";
    LinearLayoutManager linearLayoutManager;
    private boolean isLastPage = false;
    public static final String KEY_TRANSITION = "KEY_TRANSITION";
    private boolean isLoading = false;
    @BindView(R.id.adView)
    AdView adView;
    int pageNumber=1;
    Ads ads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(null, null, Color.parseColor("#F97D09"));

            setTaskDescription(td);
            getWindow().setStatusBarColor(Color.parseColor("#F97D09"));
            //getWindow().setNavigationBarColor(Color.parseColor("#000000"));

        }
        ads=new Ads(this);
        ads.googleAdBanner(this, adView);

        newsAdapter = new NewsAdapter(newsModalList,this);
        linearLayoutManager = new LinearLayoutManager(this);
        //rv_notification.setNestedScrollingEnabled(false);
        rv_video_list.setLayoutManager(linearLayoutManager);

        rv_video_list.setAdapter(newsAdapter);
        srl.setVisibility(View.GONE);
        getSupportActionBar().setTitle(getResources().getString(R.string.about_us));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(false);
                getNews();
            }
        });

        getNews();


        rv_video_list.addOnScrollListener(recyclerViewOnScrollListener);




    }

    @Override
    protected void onDestroy() {
        removeListeners();
        pageToken="";
        super.onDestroy();
    }

    private void removeListeners(){
        rv_video_list.removeOnScrollListener(recyclerViewOnScrollListener);
    }
    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = linearLayoutManager.getChildCount();
            int totalItemCount = linearLayoutManager.getItemCount();
            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();


            if (!isLoading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= 20) {
                    loadMoreItems();
                }
            }
        }
    };

    public void loadMoreItems(){
        shimmer_view_container.startShimmer();
        shimmer_view_container.setVisibility(View.VISIBLE);

        customAdApiInterface= BaseApi.createService(ApiInterface.class);
        Call<JsonElement> call = customAdApiInterface.getNews(pageNumber);
//        if(isNetworkAvailable(getApplicationContext())) {
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                shimmer_view_container.stopShimmer();
                shimmer_view_container.setVisibility(View.GONE);

                try {


                    if (response.isSuccessful()) {

                        JSONObject jsonObject= new JSONObject(response.body().toString());
                        if(jsonObject!=null){
                            pageNumber++;
                            JSONArray jsonArray= jsonObject.getJSONArray("articles");
                            for(int i =0;i<jsonArray.length();i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                JSONObject source= jsonObject1.getJSONObject("source");
                                newsModal = new NewsModal(source.getString("name"),
                                        jsonObject1.getString("url"),
                                        jsonObject1.getString("title"),
                                        jsonObject1.getString("publishedat"),
                                        jsonObject1.getString("urltoimage")
                                );
                                newsModalList.add(newsModal);
                            }

                            if(newsModalList.size()>0){
                                srl.setVisibility(View.VISIBLE);
                                newsAdapter.notifyDataSetChanged();
                            }
                        }

                    } else {

                    }
                }catch (Exception e){

                    // pushToDefault();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                shimmer_view_container.stopShimmer();
                shimmer_view_container.setVisibility(View.GONE);

            }
        });
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


    public void getNews(){
        srl.setVisibility(View.GONE);
        pageNumber=1;
        shimmer_view_container.startShimmer();
        shimmer_view_container.setVisibility(View.VISIBLE);

        customAdApiInterface= BaseApi.createService(ApiInterface.class);
        Call<JsonElement> call = customAdApiInterface.getNews(pageNumber);
//        if(isNetworkAvailable(getApplicationContext())) {
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                shimmer_view_container.stopShimmer();
                shimmer_view_container.setVisibility(View.GONE);

                try {


                    if (response.isSuccessful()) {

                        JSONObject jsonObject= new JSONObject(response.body().toString());
                        if(jsonObject!=null){
                            newsModalList.clear();

                            pageNumber++;

                            JSONArray jsonArray= jsonObject.getJSONArray("articles");
                            for(int i =0;i<jsonArray.length();i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                JSONObject source= jsonObject1.getJSONObject("source");
                                newsModal = new NewsModal(source.getString("name"),
                                       jsonObject1.getString("url"),
                                        jsonObject1.getString("title"),
                                        jsonObject1.getString("publishedat"),
                                        jsonObject1.getString("urltoimage")
                                );
                                newsModalList.add(newsModal);
                            }

                            if(newsModalList.size()>0){
                                srl.setVisibility(View.VISIBLE);
                                newsAdapter.notifyDataSetChanged();
                            }
                        }

                    } else {
                    }
                }catch (Exception e){

                    // pushToDefault();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                shimmer_view_container.stopShimmer();
                shimmer_view_container.setVisibility(View.GONE);
            }
        });
    }
}
