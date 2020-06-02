package com.rehammetwally.kora24.models;

import com.google.gson.annotations.SerializedName;
import com.rehammetwally.kora24.utils.StringsUtils;

import java.util.List;

public class CompetitionMatch {
    @SerializedName("match")
    public List<Match> match;
    @SerializedName("message")
    public String message;
//
//    public class Match {
//        @SerializedName("competition_id")
//        public int competition_id;
//        @SerializedName("home_team_id")
//        public int home_team_id;
//        @SerializedName("away_team_id")
//        public int away_team_id;
//        @SerializedName("s_title")
//        public String s_title;
//        @SerializedName("match_time")
//        public String match_time;
//        @SerializedName("match_date")
//        public String match_date;
//        @SerializedName("stage")
//        public String stage;
//        @SerializedName("c_title")
//        public String c_title;
//        @SerializedName("c_logo")
//        public String c_logo;
//        @SerializedName("hometeam")
//        public Team hometeam;
//        @SerializedName("awayteam")
//        public Team awayteam;
//        public String time() {
//            return StringsUtils.updateTime(match_time, "م", "ص");
//        }
//
//        public int hashCode() {
//            return 0;
//        }
//
//        public String toString() {
//            return awayteam.t_name + " - " + hometeam.t_name;
//        }
//
//    }
}
