/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.teamnamo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.teamnamo.BreakingNews.BreakingNewsActivity;
import com.teamnamo.News.NewsActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Random;


//appsfolders.com/2017/09/10/saveandgetarraylistofobjectsinsharedprefs/


public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private static boolean DEBUG_BUILD = com.teamnamo.BuildConfig.DEBUG;

    //    private static final String TAG = "MyFirebaseMsgService";
    int bundleNotificationId = 100;
    int singleNotificationId = 100;
    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ

//        appSharedPref = new AppSharedPreferences();
//        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        Log.e("remote message", remoteMessage.getData() + "" + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {

            //Log.e("notifiaiton",appSharedPreferences.getEventSubcription()+"day "+appSharedPreferences.getSpecialDaysSubscription()+"poll "+appSharedPreferences.getPollSubscription()+"feed" +appSharedPreferences.getFeedSubscription()+"job "+appSharedPreferences.getJobsSubscription());
//            if(DEBUG_BUILD) {
//                if (!TextUtils.isEmpty(remoteMessage.getFrom()) && remoteMessage.getFrom().equals("/topics/namo-news-test")) {
//                    Intent intent;
//
//                        intent= new Intent(this, NewsActivity.class);
//
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                    sendNotification("News - " + remoteMessage.getData().get("title"),
//                            remoteMessage.getData().get("description"),intent, intent, (remoteMessage.getData().get("image")),
//                           R.drawable.ic_stat_namo, "news", R.color.colorPrimary);
//                }
//                else if (!TextUtils.isEmpty(remoteMessage.getFrom()) && remoteMessage.getFrom().equals("/topics/namo-bk-news-test")) {
//                    Intent intent;
//
//                    intent= new Intent(this, BreakingNewsActivity.class);
//                    intent.putExtra("image",remoteMessage.getData().get("image"));
//
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                    sendNotification("Breaking News - " + remoteMessage.getData().get("title"),
//                            remoteMessage.getData().get("description"),intent, intent, (remoteMessage.getData().get("image")),
//                            R.drawable.ic_stat_namo, "breaking", R.color.colorAccent);
//                }
//
//            }
//            else{
                if (!TextUtils.isEmpty(remoteMessage.getFrom()) && remoteMessage.getFrom().equals("/topics/namo-news-live")) {
                    Intent intent;

                    intent= new Intent(this, NewsActivity.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    sendNotification("News - " + remoteMessage.getData().get("title"),
                            remoteMessage.getData().get("description"),intent, intent, (remoteMessage.getData().get("image")),
                            R.drawable.ic_stat_namo, "news", R.color.colorPrimary);
                }
                else if (!TextUtils.isEmpty(remoteMessage.getFrom()) && remoteMessage.getFrom().equals("/topics/namo-bk-news-live")) {
                    Intent intent;

                    intent= new Intent(this, BreakingNewsActivity.class);
                    intent.putExtra("image",remoteMessage.getData().get("image"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    sendNotification("Breaking news - " + remoteMessage.getData().get("title"),
                            remoteMessage.getData().get("description"),intent, intent, (remoteMessage.getData().get("image")),
                            R.drawable.ic_stat_namo, "breaking", R.color.colorAccent);
                }
          // }

            }
//            remoteMessage.getData().get("day");


            // Check if message contains a notification payload.
            if (remoteMessage.getNotification() != null) {
                sendBroadcast();
            }
    }
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.



    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }
    // [END receive_message]




//     * Handle time allotted to BroadcastReceivers.
//    private void handleNow() {
//        Log.d(TAG, "Short lived task is done.");
//    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageTitle, String messageBody, Intent detailIntent, Intent intent, String imageUrl, int image, String type, int colorId) {
        Log.e("singleNotificationId",singleNotificationId+"=====");

        int random= new Random().nextInt((1000 - 0) + 1) + 0;
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(messageTitle);
        bigPictureStyle.setSummaryText(Html.fromHtml(messageBody).toString());
        if(DEBUG_BUILD) {
            Log.e("getBitmapFromURL(image", messageTitle + "dsfsd"+messageBody);
        }
        if(getBitmapFromURL(imageUrl)!=null) {
            bigPictureStyle.bigPicture(getBitmapFromURL(imageUrl));
        }
        //        Intent intent = new Intent(this, c);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if(DEBUG_BUILD) {
            Log.e("string type", imageUrl + "");
        }

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        int notId = 0;
        String contentGroupTitle = "";
        if(DEBUG_BUILD) {
            Log.e("type is ", type + "");
        }
        if(type.equals("news")) {
            contentGroupTitle="News Notifications";
            bundleNotificationId = 100;
        }
        else if(type.equals("breaking")) {
            contentGroupTitle="Breaking News Notifications";
            bundleNotificationId = 500;
        }

//        if (singleNotificationId == bundleNotificationId) {
//            singleNotificationId = bundleNotificationId + 1;
//            Log.e("singleNotificationId1",singleNotificationId+"=====");
//
//        }
//        else {
            singleNotificationId=random;
            Log.e("singleNotificationId2",singleNotificationId+"=====");

       // }
        PendingIntent pendingIntent1 = PendingIntent.getActivity(this, bundleNotificationId /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
//        String channelId = getString(R.string.default_notification_channel_id);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, singleNotificationId /* Request code */, detailIntent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder summaryNotificationBuilder;
        summaryNotificationBuilder = new NotificationCompat.Builder(this, "bundle_channel_id")
                .setGroup(type+bundleNotificationId)
                .setGroupSummary(true)
                .setContentTitle(contentGroupTitle)
                .setContentText(contentGroupTitle)
                .setSound(defaultSoundUri)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(contentGroupTitle))

                .setContentIntent(pendingIntent1);


        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this,"channel_id")
                        .setContentTitle(messageTitle)
                        .setGroupSummary(false)
                        // .setContentTitle("Pu WiFi")
                        .setGroup(type+bundleNotificationId)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);





        if(imageUrl!=null && !TextUtils.isEmpty(imageUrl.trim())){
            if(getBitmapFromURL(imageUrl)!=null) {
                notificationBuilder.setStyle(bigPictureStyle);
            }
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(image);
            summaryNotificationBuilder.setSmallIcon(image);
            summaryNotificationBuilder.setColor(getResources().getColor(colorId));
            notificationBuilder.setColor(getResources().getColor(colorId));
        } else {
            summaryNotificationBuilder.setSmallIcon(image);
            notificationBuilder.setSmallIcon(image);
        }
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = type;
            if (notificationManager.getNotificationChannels().size() < 2) {
                NotificationChannel groupChannel = new NotificationChannel("bundle_channel_id", "bundle_channel_name", NotificationManager.IMPORTANCE_LOW);
                notificationManager.createNotificationChannel(groupChannel);
                NotificationChannel channel = new NotificationChannel("channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }
        }


        notificationManager.notify(singleNotificationId /* ID of notification */, notificationBuilder.build());
        notificationManager.notify(bundleNotificationId, summaryNotificationBuilder.build());

    }

    private int getNotificationId(int sumaryId) {
        SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("notification", Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt("notId", sumaryId) + 1;
        while (id == sumaryId) {
            id++;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("notId", id);
        editor.apply();
        return id;
    }



    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onNewToken(String s) {
        if(DEBUG_BUILD) {
            Log.e("new firebase token", s);
        }

    }

    private void sendBroadcast() {
//        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("updateNotification");
        Bundle bundle = new Bundle();
        bundle.putInt("FLAG", 1);

        intent.putExtras(bundle);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }



}
