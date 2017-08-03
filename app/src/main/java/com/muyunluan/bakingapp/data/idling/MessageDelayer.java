package com.muyunluan.bakingapp.data.idling;

import android.content.Context;
import android.support.annotation.Nullable;

import com.muyunluan.bakingapp.data.BakingRecipe;

/**
 * Created by Fei Deng on 8/3/17.
 * Copyright (c) 2017 Muyunluan. All rights reserved.
 */

public class MessageDelayer {

    private static final int DELAY_MILLIS = 3000;

    interface DelayerCallback {
        void onDone(BakingRecipe bakingRecipe);
    }

    static void fetchRecipes(Context context, final DelayerCallback callback,
                             @Nullable final SimpleIdlingResource idlingResource) {
        if (null != idlingResource) {
            idlingResource.setIdleState(false);
        }


    }
}
