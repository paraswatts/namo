package com.teamnamo;

import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ieglobe on 4/4/17.
 */

public class webView extends AppCompatActivity {
    private String mUrl;
    public WebView mWebView;
    // public ProgressBar progressBar;
    private ProgressBar progressBar;//
    @BindView(R.id.adView)
    AdView adView;
    Ads ads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
        ButterKnife.bind(this);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(null, null, Color.parseColor("#F97D09"));

            setTaskDescription(td);
            getWindow().setStatusBarColor(Color.parseColor("#F97D09"));
            //getWindow().setNavigationBarColor(Color.parseColor("#000000"));

        }

        ads=new Ads(this);
        ads.googleAdBanner(this, adView);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        //set progress bar max limit
        progressBar.setMax(100);
        //get the url from the intent of this activity called from the fragments
        mUrl = getIntent().getExtras().getString("url");
        // set the title of webview to be th url
        setTitle(mUrl);
        mWebView = (WebView) findViewById(R.id.webview);
        //mWebView = new WebView(this);
        mWebView.setWebViewClient(new myWebViewClient());
        mWebView.setWebChromeClient(new WebChromeClientDemo());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(true);
        mWebView.loadUrl(mUrl);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //setContentView(mWebView);

    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();

        return true;
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    protected void onPause() {
        if (adView != null) {
            adView.pause();
        }

        super.onPause();
    }
    
    private class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            progressBar.setProgress(100);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
        }
    }

    private class WebChromeClientDemo extends WebChromeClient {
        public void onProgressChanged(WebView view, int progress) {
            progressBar.setProgress(progress);
        }

    }
}
