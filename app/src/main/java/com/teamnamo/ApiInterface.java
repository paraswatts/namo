package com.teamnamo;

import com.google.gson.JsonElement;

import org.json.JSONObject;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    //Custom ads api




    @GET("news")
    Call<JsonElement> getNews(
            @Query("pageNo") int pageNo
    );

    @GET("breakingNews")
    Call<JsonElement> getBreakingNews(
            @Query("pageNo") int pageNo
    );
    @GET("v3/search?key=AIzaSyCCHuayCrwwcRAUZ__zTYyOP-ax5FD4R9E&channelId=UC1NF71EwP41VdjAU1iXdLkw&part=snippet,id&order=date&maxResults=20&type=video")
    Call<JsonElement> getVideos();

    @GET("v3/search")
    Call<JsonElement> getNextVideos(
            @Query("key") String key,
            @Query("channelId") String channelId,
            @Query("part") String part,
            @Query("order") String order,
            @Query("maxResults") String maxResults,
            @Query("type") String type,
            @Query("pageToken") String pageToken
    );

}
