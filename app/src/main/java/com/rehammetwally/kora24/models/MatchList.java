package com.rehammetwally.kora24.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MatchList implements Serializable {
    @SerializedName("match")
    public List<Match> match;
    @SerializedName("message")
    public String message;
}
