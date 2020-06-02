package com.rehammetwally.kora24.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MessageSeasons {
    @SerializedName("season")
    public List<Season> season;
    @SerializedName("message")
    public String message;
}
