package com.rehammetwally.kora24.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MessageMatch implements Serializable {
    @SerializedName("match")
    public Match match;
    @SerializedName("message")
    public String message;
}
