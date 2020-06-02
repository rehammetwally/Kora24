package com.rehammetwally.kora24.models;

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.rehammetwally.kora24.utils.StringsUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Match implements Serializable {
    private static final String TAG = "Match";
    @SerializedName("id")
    public int id;
    @SerializedName("competition_id")
    public int competition_id;
    @SerializedName("home_team_id")
    public int home_team_id;
    @SerializedName("away_team_id")
    public int away_team_id;
    @SerializedName("home_team_goal")
    public Integer home_team_goal;
    @SerializedName("away_team_goal")
    public Integer away_team_goal;
    @SerializedName("TV_channel")
    public String TV_channel;
    @SerializedName("commentor")
    public String commentor;
    @SerializedName("stadium")
    public String stadium;
    @SerializedName("s_title")
    public String s_title;
    @SerializedName("match_time")
    public String match_time;
    @SerializedName("match_date")
    public String match_date;
    @SerializedName("stage")
    public String stage;
    @SerializedName("c_title")
    public String c_title;
    @SerializedName("c_logo")
    public String c_logo;
    @SerializedName("hometeam")
    public Team hometeam;
    @SerializedName("awayteam")
    public Team awayteam;

    public String homeGoals() {
        if (home_team_goal == null) {
            return StringsUtils.toString(0);
        } else {
            return StringsUtils.toString(home_team_goal);
        }
    }

    public String awayGoals() {
        if (away_team_goal == null) {
            return StringsUtils.toString(0);
        } else {
            return StringsUtils.toString(away_team_goal);
        }
    }


    public String day() {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = input.parse(match_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE", new Locale("ar"));
        String day = outFormat.format(date);

        SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy", new Locale("ar"));
        Calendar calendar = Calendar.getInstance();
        String weekDay = outFormat.format(calendar.getTime());
        String weekDate = output.format(calendar.getTime());
        String dateStr = output.format(date);

        Log.e(TAG, "day: " + dateStr);
        Log.e(TAG, "day: " + weekDate);
        if (weekDay.equals(day) && weekDate.equals(dateStr)) {
            day = "اليوم";
        }
        Log.e(TAG, "day: " + day);
        return day;
    }

    public String time() {
        return StringsUtils.updateTime(match_time, "م", "ص");
    }

    public String date() {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = input.parse(match_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy", new Locale("ar"));
        String dateStr = output.format(date);
        return dateStr;
    }

    public String icon() {
        return "http://kora24life.tk/kora24/public/CompetitionLogo/" + c_logo;
    }



    public long id() {
        return id;
    }
}
