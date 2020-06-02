package com.rehammetwally.kora24.models;

import com.google.gson.annotations.SerializedName;

public class Season {
    @SerializedName("id")
    public int id;
    @SerializedName("s_title")
    public String s_title;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("updated_at")
    public String updated_at;

    public int hashCode(){
        return id;
    }
    public String toString(){
        return s_title;
    }
}
