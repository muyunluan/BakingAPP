package com.muyunluan.bakingapp.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Fei Deng on 7/25/17.
 * Copyright (c) 2017 Muyunluan. All rights reserved.
 */

public class SharedPreferenceUtil {

    private static final String TAG = SharedPreferenceUtil.class.getSimpleName();

    private static final String PREFERENCE_NAME = "favorites_file";

    private static final String KEY_FAVORITE = "key_favorite";

    private static SharedPreferenceUtil sharedPreferenceUtil;
    private SharedPreferences sharedPreferences;

    private SharedPreferenceUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferenceUtil getInstance(Context context) {
        if (null == sharedPreferenceUtil) {
            sharedPreferenceUtil = new SharedPreferenceUtil(context);
        }

        return sharedPreferenceUtil;
    }

    public void setFavoriteRecipeWithId(boolean isFavorite, int id) {
        sharedPreferences.edit().putBoolean(KEY_FAVORITE + id, isFavorite).apply();
    }

    public boolean getRecipeIsFavorite(int id) {
        return sharedPreferences.getBoolean(KEY_FAVORITE + id, false);
    }
}
