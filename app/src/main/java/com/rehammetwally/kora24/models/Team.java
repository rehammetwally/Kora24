package com.rehammetwally.kora24.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Team {
    @SerializedName("id")
    public int id;
    @SerializedName("t_name")
    public String t_name;
    @SerializedName("t_logo")
    public String t_logo;
    @SerializedName("country_name")
    public String country_name;
    @SerializedName("competitions_details")
    public List<CompetitionsDetails> competitions_details;

    public String icon(){
        return "http://kora24life.tk/kora24/public/TeamLogo/"+t_logo;
    }


    public int hashCode(){
        return id;
    }
    public String toString(){
        return t_name;
    }
}
