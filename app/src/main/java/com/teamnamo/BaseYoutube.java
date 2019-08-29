package com.teamnamo;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jass on 18-04-2018.
 */

public class BaseYoutube {

    private static final String BASE_URL = "https://www.googleapis.com/youtube/";

    //with logging
    private static final Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();

    public static <S> S createService(
            Class<S> serviceClass) {
            builder.client(httpClient.build());
        Retrofit retrofit = builder.build();

        return retrofit.create(serviceClass);
    }


    ////with logging
/*//    private static Retrofit.Builder builder =
//            new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create());
//
//    private static Retrofit retrofit = builder.build();
//
//    private static HttpLoggingInterceptor logging =
//            new HttpLoggingInterceptor()
//                    .setLevel(HttpLoggingInterceptor.Level.BODY);
//
//    private static OkHttpClient.Builder httpClient =
//            new OkHttpClient.Builder();
//
//    public static <S> S createService(
//            Class<S> serviceClass) {
//        if (!httpClient.interceptors().contains(logging)) {
//            httpClient.addInterceptor(logging);
//            builder.client(httpClient.build());
//            retrofit = builder.build();
//        }
//
//        return retrofit.create(serviceClass);*/

    //without logging
/*
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();

    public static <S> S createService(
            Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
*/


/*
    //public static final String BASE_URL = ""+ getResources().getString(R.string.banner_ad_sample)R.string.notification_url;
//    public static Retrofit retrofit = null;

    public static Retrofit getApi()
    {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

//        if (BuildConfig.DEBUG)
            okHttpClientBuilder.addInterceptor(logging);

        if(retrofit==null)
        {
            retrofit = new Retrofit.Builder().baseUrl("http://mypu.herokuapp.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClientBuilder.build())
                    .build();
        }
        return retrofit;
    }
    */

}
