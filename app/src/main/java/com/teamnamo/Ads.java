package com.teamnamo;

import android.content.Context;
import android.content.ContextWrapper;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by Jass on 05-01-2018.
 */

public class Ads extends ContextWrapper {
    Context context;
    public Ads(Context context) {
        super(context);
        this.context = context;
    }

    public void googleAdBanner(Context mContext, final AdView mAdView) {
        mAdView.loadAd(new AdRequest.Builder().addTestDevice("BBEF09570A85263FD3FC932F0955C384").build());
//        mAdView.setAdListener(new AdListener() {
//            @Override
//            public void onAdClosed() {
//                super.onAdClosed();
//            }
//
//            @Override
//            public void onAdFailedToLoad(int i) {
//
//                Log.e("add oad failed",i+"");
//                super.onAdFailedToLoad(i);
//            }
//
//            @Override
//            public void onAdLeftApplication() {
//                super.onAdLeftApplication();
//            }
//
//            @Override
//            public void onAdOpened() {
//                super.onAdOpened();
//            }
//
//            @Override
//            public void onAdLoaded() {
//                super.onAdLoaded();
//            }
//        });

    }

//    public InterstitialAd googleAdInterstitial(final Context context) {
//        mInterstitialAd = new InterstitialAd(context);
//        mInterstitialAd.setAdUnitId(context.getResources().getString(R.string.interstitial_ad));
//        load_googleAdInterstitial();
//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdClosed() {
//                super.onAdClosed();
//            }
//
//            @Override
//            public void onAdFailedToLoad(int i) {
//                if(DEBUG_BUILD) {
//                    Log.e("af failed", "========to load");
//                }
//                super.onAdFailedToLoad(i);
//                switch (i) {
//                    case 1: {
//                        if(DEBUG_BUILD) {
//                            Log.e("onAdFailedToLoad: ", "ERROR_CODE_INTERNAL_ERROR");
//                        }
//                        break;
//                    }
//                    case 2: {
//                        if(DEBUG_BUILD) {
//                            Log.e("onAdFailedToLoad: ", "ERROR_CODE_INVALID_REQUEST");
//                        }
//                        break;
//                    }
//                    case 3: {
//                        if(DEBUG_BUILD) {
//                            Log.e("onAdFailedToLoad: ", "ERROR_CODE_NETWORK_ERROR");
//                        }
//                        break;
//                    }
//                    case 4: {
//                        if(DEBUG_BUILD) {
//                            Log.e("onAdFailedToLoad: ", "ERROR_CODE_NO_FILL");
//                        }
//                        break;
//                    }
//                }
////               Toast.makeText(context,""+i,Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAdLeftApplication() {
//                super.onAdLeftApplication();
//            }
//
//            @Override
//            public void onAdOpened() {
//                super.onAdOpened();
//            }
//
//            @Override
//            public void onAdLoaded() {
//                super.onAdLoaded();
//            }
//        });
//        return mInterstitialAd;
//    }

//    private void load_googleAdInterstitial() {
//        if (mInterstitialAd != null) {
//            if(DEBUG_BUILD) {
//                Log.e("load ad if", "load inte");
//            }
//            if(!DEBUG_BUILD) {
//                //mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("A9E864B70E48B909F2139BA1A052683F").build());
//            }
//        }
//        else {
//            if(DEBUG_BUILD) {
//                Log.e("load ad elsse", "load inte");
//            }
//            googleAdInterstitial(this);
//        }
//    }





}
