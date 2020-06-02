package com.rehammetwally.kora24.models;

import com.google.gson.annotations.SerializedName;

public class TeamCompetition {
    @SerializedName("t_name")
    public String t_name;
    @SerializedName("t_logo")
    public String t_logo;
    @SerializedName("c_title")
    public String c_title;
    @SerializedName("c_logo")
    public String c_logo;
    @SerializedName("competition_id")
    public int competition_id;
    @SerializedName("team_id")
    public int team_id;
}
