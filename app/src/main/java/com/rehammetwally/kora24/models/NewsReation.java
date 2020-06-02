package com.rehammetwally.kora24.models;

import com.google.gson.annotations.SerializedName;

public class NewsReation {
    @SerializedName("likes")
    public int likes;
    @SerializedName("dislikes")
    public int dislikes;
    @SerializedName("views")
    public int views;
    @SerializedName("comments")
    public int comments;

}
