package com.rehammetwally.kora24.utils;

import android.content.SharedPreferences;

public class PreferencesHelper {
    private final SharedPreferences preferences;

    public PreferencesHelper(SharedPreferences sharedPreferences) {
        preferences = sharedPreferences;
    }

    public void putString(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

    public String getString(String key) {
        return preferences.getString(key, null);
    }

    public void putDouble(String key, Double value) {
        preferences.edit().putLong(key, Double.doubleToRawLongBits(value)).apply();
    }

    public Double getDouble(String key) {
        return Double.longBitsToDouble(preferences.getLong(key, 0));
    }

    public void putBoolean(String key, boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean( String key) {
        return preferences.getBoolean(key, false);
    }

    public void putInt(String key, int value) {
        preferences.edit().putInt(key, value).apply();
    }

    public int getInt( String key) {
        return preferences.getInt(key, 0);
    }

    public void clear() {
        preferences.edit().clear().apply();
    }

    public void remove(String key)
    {
        preferences.edit().remove(key).apply();
    }

    public boolean contains(String key)
    {
        return  preferences.contains(key);
    }
}
