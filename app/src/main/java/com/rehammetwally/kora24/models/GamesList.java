package com.rehammetwally.kora24.models;

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.rehammetwally.kora24.utils.StringsUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GamesList {
    @SerializedName("games")
    public List<Games> games;
    @SerializedName("message")
    public String message;
    private static final String TAG = "GamesList";

    public class Games {
//        public Tournaments tournaments;
        @SerializedName("id")
        public int id;
        @SerializedName("c_title")
        public String c_title;
        @SerializedName("c_logo")
        public String c_logo;
        @SerializedName("created_at")
        public String created_at;
        @SerializedName("updated_at")
        public String updated_at;
        @SerializedName("games")
        public List<Data> games;

        public String icon() {
            if (c_logo != null) {
                Log.e(TAG, "icon: " + "http://kora24life.tk/kora24/public/CompetitionLogo/" + c_logo);
                return "http://kora24life.tk/kora24/public/CompetitionLogo/" + c_logo;
            }
            return null;
        }


        public class Data {
            @SerializedName("id")
            public int id;
            @SerializedName("home_team_id")
            public int home_team_id;
            @SerializedName("away_team_id")
            public int away_team_id;
            @SerializedName("match_time")
            public String match_time;
            @SerializedName("match_date")
            public String match_date;
            @SerializedName("stage")
            public String stage;
            @SerializedName("competition_id")
            public int competition_id;
            @SerializedName("s_title")
            public String s_title;
            @SerializedName("hometeam")
            public Team hometeam;
            @SerializedName("awayteam")
            public Team awayteam;

            public String time() {
                return StringsUtils.updateTime(match_time, "م", "ص");
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
        }
    }

}
