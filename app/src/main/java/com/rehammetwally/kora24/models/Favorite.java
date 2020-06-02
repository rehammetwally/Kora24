package com.rehammetwally.kora24.models;

public class Favorite {
    public int id;
    public int logo;
    public String name;
    public int favorite;

    public Favorite(int logo, String name, int favorite) {
        this.logo = logo;
        this.name = name;
        this.favorite = favorite;
    }
}
