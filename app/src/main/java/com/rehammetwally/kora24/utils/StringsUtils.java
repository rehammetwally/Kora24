package com.rehammetwally.kora24.utils;


import android.util.Log;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class StringsUtils {
    private static final String TAG = "StringsUtils";

    public static String toString(int value) {
        return String.valueOf(value);
    }

    public static String formatNumber(int value) {
//        (String.format(Locale.ENGLISH, "x%d", scaleValue)
//        DecimalFormat df = new DecimalFormat("#00", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        return String.format(Locale.ENGLISH, "%,d", Long.parseLong(String.valueOf(value)));
    }

    public static String covertTimeToText(String dataDate) {

        String convTime = "";

        String prefix = "";
        String suffix = "منذ";
//        if (dataDate != null) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date pasTime = null;
            if (dataDate != null) {
                pasTime = dateFormat.parse(dataDate);

                Date nowTime = new Date();

                long dateDiff = nowTime.getTime() - pasTime.getTime();

                long second = TimeUnit.MILLISECONDS.toSeconds(dateDiff);
                long minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff);
                long hour = TimeUnit.MILLISECONDS.toHours(dateDiff);
                long day = TimeUnit.MILLISECONDS.toDays(dateDiff);

                if (second < 60) {
                    convTime = suffix + " " + second + " ثانية ";
                } else if (minute < 60) {
                    convTime = suffix + " " + minute + " دقيقة ";
                } else if (hour < 24) {
                    convTime = suffix + " " + hour + " ساعة ";
                } else if (day >= 7) {
                    if (day > 360) {
                        convTime = suffix + " " + (day / 360) + " عام ";
                    } else if (day > 30) {
                        convTime = suffix + " " + (day / 30) + " شهر ";
                    } else {
                        convTime = suffix + " " + (day / 7) + " اسبوع ";
                    }
                } else if (day < 7) {
                    convTime = suffix + " " + day + " يوم ";
                }
                Log.e(TAG, "covertTimeToText: " + convTime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("ConvTimeE", e.getMessage());
        }
//        }
        return convTime;
    }


    // Used to convert 24hr format to 12hr format with AM/PM values
    public static String updateTime(String time, String pm, String am) {
        int hours = Integer.parseInt(time.split(":")[0].trim());
        int mins = Integer.parseInt(time.split(":")[1].trim());

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = pm;
        } else if (hours == 0) {
            hours += 12;
            timeSet = am;
        } else if (hours == 12)
            timeSet = pm;
        else
            timeSet = am;


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);
        String hour = "";
        if (hours < 10)
            hour = "0" + hours;
        else
            hour = String.valueOf(hours);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hour).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        return aTime;
    }

    // Used to convert 24hr format to 12hr format with AM/PM values
    public static String setTime(int hours, int mins) {

        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);
        String hour = "";
        if (hours < 10)
            hour = "0" + hours;
        else
            hour = String.valueOf(hours);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hour).append(':')
                .append(minutes).append(" ").toString();

        return aTime;
    }
}
