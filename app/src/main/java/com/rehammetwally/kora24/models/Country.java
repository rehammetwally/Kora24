package com.rehammetwally.kora24.models;

import com.google.gson.annotations.SerializedName;


public class Country {
    @SerializedName("id")
    public int id;
    @SerializedName("country_name")
    public String country_name;


    public int hashCode(){
        return id;
    }
    public String toString(){
        return country_name;
    }
}

