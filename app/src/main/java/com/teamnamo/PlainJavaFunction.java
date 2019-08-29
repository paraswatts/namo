package com.teamnamo;

/**
 * Created by Jass on 11-08-2018.
 */


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Created by MPROGA1\vikram.singh on 29/5/17.
 */

public class PlainJavaFunction {
    /***************************************
     * Get Class Name
     ********************************************/


    public static String getDateAndTime(String timeStamp){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();

        long time = 0;
        try {
//            time = sdf.parse(model.getCreatedOn()).getTime();
            time = sdf.parse(timeStamp).getTime();
            sdf.setTimeZone(TimeZone.getDefault());
            sdf.format(time);
        } catch (Exception e) {
        }
        sdf = new SimpleDateFormat("dd MMM yyyy hh:mm a");
        return sdf.format(time);

//
    }
}

//https://github.com/AllenCoder/SuperUtils/blob/master/apputils/src/main/java/com/allen/apputils/RegexUtils.java
//https://github.com/AllenCoder/SuperUtils/blob/master/apputils/src/main/java/com/allen/apputils/NumberUtil.java
