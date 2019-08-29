package com.teamnamo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

public class ShareScreenShot {
    private boolean DEBUG_BUILD = BuildConfig.DEBUG;
    Activity activity;
    public ShareScreenShot(Activity activity){
        this.activity = activity;
    }
    public void takeScreenshot(String title, String url, CardView cardView, ImageView share_feed, Context context) {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        //if(TabActivity.verifyStoragePermissions(activity)) {
        takePermission(title,url,cardView,share_feed,context);
        //}
    }

    public void sendScreenshot(String title, String url, CardView cardView, ImageView share_feed, Context context){
        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyPu";
//            if(share_feed!=null)
//                share_feed.setVisibility(View.INVISIBLE);
            // create bitmap screen capture
            if(DEBUG_BUILD) {
                Log.e("title,url,cardView", title + url + cardView + share_feed);
                Log.e("here", "sharing");
            }
            cardView.setDrawingCacheEnabled(true);
            cardView.buildDrawingCache(true);
            Bitmap bm = Bitmap.createBitmap(cardView.getDrawingCache());
            cardView.setDrawingCacheEnabled(false);
            String fileName = "MyPu" + ".png";
            File dir = new File(mPath);
            File imageFile = new File(mPath, fileName);
            if (!dir.exists()) {
                dir.mkdir();

            }
            if(DEBUG_BUILD) {
                Log.e("imagefile", imageFile.getAbsolutePath());
            }
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bm.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
//            if(share_feed!=null)
//                share_feed.setVisibility(View.VISIBLE);

            openScreenshot(imageFile, title, url,context);

        } catch (Throwable e) {
            if(DEBUG_BUILD) {
                Log.e("here", e.getMessage() + "error sc");
            }
            e.printStackTrace();
        }
    }

    public void takePermission(String title, String url, CardView cardView, ImageView share_feed, Context context)
    {
        Dexter.withActivity(activity)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            sendScreenshot(title,url,cardView,share_feed,context);
                        }


                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog(activity);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(activity, "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void showSettingsDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings(activity);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    private void openSettings(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, 101);
    }
    public void takePermissionViewPager(String title, String url, ViewPager cardView, String check, Context context)
    {
        Dexter.withActivity(activity)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            if(DEBUG_BUILD) {
                                Log.e("permission", "view pager");
                            }
                            sendScreenshotViewPager(title,url,cardView,check,context);
                        }


                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog(activity);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(activity, "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();

    }
    public void sendScreenshotViewPager(String title, String url, ViewPager cardView, String check, Context context){
        try {
            if(check.equals("video"))
                throw new Exception();

            if(check.equals("photos")) {
                cardView.setCurrentItem(1);
            }
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            try {
                                String mPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TeamNamo";
                                // create bitmap screen capture
                                cardView.setDrawingCacheEnabled(true);
                                cardView.buildDrawingCache(true);
                                Bitmap bm = Bitmap.createBitmap(cardView.getDrawingCache());
                                Resources r = activity.getResources();
                                int px30 = (int) TypedValue.applyDimension(
                                        TypedValue.COMPLEX_UNIT_DIP,
                                        30,
                                        r.getDisplayMetrics()
                                );
                                Bitmap resizedbitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight() - px30);

                                cardView.setDrawingCacheEnabled(false);
                                String fileName = "TeamNamo" + ".png";
                                File dir = new File(mPath);
                                File imageFile = new File(mPath, fileName);
                                if (!dir.exists()) {
                                    dir.mkdir();

                                }

                                FileOutputStream outputStream = new FileOutputStream(imageFile);
                                int quality = 100;
                                resizedbitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                                outputStream.flush();
                                outputStream.close();

                                if (check.equals("photos"))
                                    cardView.setCurrentItem(0);
                                openScreenshot(imageFile, title, url,context);
                            }catch (Exception e){

                            }

                        }
                    },
                    300);
            // image naming and path  to include sd card  appending name you choose for file


        } catch (Exception e) {
            e.printStackTrace();
            try {
                String mPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/mypu";
                String fileName = "MyPu" + ".png";
                File dir = new File(mPath);
                File imageFile = new File(mPath, fileName);
                if (!dir.exists()) {
                    dir.mkdir();

                }

                FileOutputStream outputStream = new FileOutputStream(imageFile);
                int quality = 100;
                Bitmap myLogo = BitmapFactory.decodeResource(activity.getResources(), R.drawable.placeholder);

                myLogo.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                outputStream.flush();
                outputStream.close();


                openScreenshot(imageFile, title, url,context);

            }catch (Exception e1){
                e1.printStackTrace();
            }
        }
    }
    public void takeScreenshotViewPager(String title, String url, ViewPager cardView, String check, Context context) {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
//        if(TabActivity.verifyStoragePermissions(activity)) {
        takePermissionViewPager(title,url,cardView,check,context);
        //}
    }

    private void openScreenshot(File imageFile, String title, String url, Context context) {
        //get uri of image file

        Uri uri;
        if (Build.VERSION.SDK_INT < 24) {
            uri = Uri.fromFile(imageFile);
        } else {
            uri = FileProvider.getUriForFile(context, context.getPackageName(), imageFile);
// Uri.parse(imageFile.getPath()); // My work-around for new SDKs, doesn't work on older ones.
        }

        if(DEBUG_BUILD) {
            Log.e("here", "sending screenshot"+uri+"imageFile.getPath()"+imageFile.getAbsolutePath()    );
        }
//        Uri uri = Uri.fromFile(imageFile);
        //intialize the intent
        Intent intent = new Intent();
        // set intent action type
        intent.setAction(Intent.ACTION_SEND);
        //set mime type

        //grant permisions for all apps that can handle given intent


        List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setType("image/png");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
        intent.putExtra(android.content.Intent.EXTRA_TEXT, url);
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        try {
            activity.startActivity(Intent.createChooser(intent, "Share via"));
        } catch (Exception e) {
            Log.e("exception screenshot",e.getMessage()+"====");
            Toast.makeText(activity, "No App Available", Toast.LENGTH_SHORT).show();
        }
    }
}
