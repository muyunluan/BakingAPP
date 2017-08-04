package com.muyunluan.bakingapp.data.idling;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.muyunluan.bakingapp.data.BakingRecipe;
import com.muyunluan.bakingapp.ui.RecipeListAdapter;
import com.muyunluan.bakingapp.utils.DataQueryTaskUtils;

import java.util.ArrayList;

/**
 * Created by Fei Deng on 8/3/17.
 * Copyright (c) 2017 Muyunluan. All rights reserved.
 */

public class RecipeFetcher {

    private static final String TAG = RecipeFetcher.class.getSimpleName();

    private static final int DELAY_MILLIS = 3000;

    public interface DelayerCallback {
        void onDone(ArrayList<BakingRecipe> bakingRecipes);
    }

    public static void fetchRecipes(Context context,
                             final ArrayList<BakingRecipe> bakingRecipes,
                             RecipeListAdapter recipeListAdapter,
                             RecyclerView recyclerView,
                             final DelayerCallback callback,
                             @Nullable final SimpleIdlingResource idlingResource) {
        if (null != idlingResource) {
            idlingResource.setIdleState(false);
        }

        new DataQueryTaskUtils(
                context,
                bakingRecipes,
                recipeListAdapter,
                recyclerView).execute();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onDone(bakingRecipes);
                    Log.i(TAG, "run: call onDone");
                    if (idlingResource != null) {
                        idlingResource.setIdleState(true);
                    }
                }
            }
        }, DELAY_MILLIS);
    }
}
