package com.application.cars.cars.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by kailash on 04-11-2017.
 */


public class Constant {

    public static SharedPreferences addToPreference(Context context, String key, long value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edtor = prefs.edit();
        edtor.putLong(key, value);
        edtor.commit();
        return prefs;
    }

    public static Long getValueForPrefKey(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (prefs.contains(key)) {
            return prefs.getLong(key, 0l);
        }
        return 0l;
    }
}
