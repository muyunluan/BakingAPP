package com.muyunluan.bakingapp.widget;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.muyunluan.bakingapp.R;
import com.muyunluan.bakingapp.data.BakingRecipe;
import com.muyunluan.bakingapp.data.Constants;
import com.muyunluan.bakingapp.data.SharedPreferenceUtil;
import com.muyunluan.bakingapp.utils.NetworkUtils;
import com.muyunluan.bakingapp.utils.OpenRecipeJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * Created by Fei Deng on 7/26/17.
 * Copyright (c) 2017 Muyunluan. All rights reserved.
 */

public class RecipeAppRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final String TAG = RecipeAppRemoteViewsFactory.class.getSimpleName();

    private Context mContext;

    private ArrayList<BakingRecipe> mBakingRecipeData = new ArrayList<>();
    private SharedPreferenceUtil sharedPreferenceUtil;

    public RecipeAppRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(mContext);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        try {
            new RecipeQueryTask().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, "onDataSetChanged: error to query recipe date - " + e.getMessage() );
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),
                                        R.layout.recipe_app_widget_list_item);
        boolean hasFavorite = true;
        BakingRecipe bakingRecipe;

        //check if there is a Favorite one
        for (int i = 0; i < mBakingRecipeData.size(); i++) {
            if (sharedPreferenceUtil.getRecipeIsFavorite(i + 1)) {
                bakingRecipe = mBakingRecipeData.get(i);

                // use default pic
                remoteViews.setImageViewResource(R.id.img_recipe,
                        Constants.recipeImages[i]);

                remoteViews.setTextViewText(R.id.recipe_title,
                        bakingRecipe.getmName());

                remoteViews.setTextViewText(R.id.recipe_serving,
                        String.format(Locale.US, " Servings: %s", bakingRecipe.getmServings()));

                remoteViews.setImageViewResource(R.id.img_favorite, R.mipmap.ic_star);
                break;
            }
            hasFavorite = false;
        }

        // no Favorite, use first recipe
        if (!hasFavorite) {
            bakingRecipe = mBakingRecipeData.get(0);

            // use default pic
            remoteViews.setImageViewResource(R.id.img_recipe,
                    Constants.recipeImages[0]);

            remoteViews.setTextViewText(R.id.recipe_title,
                    bakingRecipe.getmName());

            remoteViews.setTextViewText(R.id.recipe_serving,
                    String.format(Locale.US, " Servings: %s", bakingRecipe.getmServings()));

            remoteViews.setImageViewResource(R.id.img_favorite, R.mipmap.ic_unstar);
        }
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    // only show the first favorite one or first recipe if there is no favorite
    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public class RecipeQueryTask extends AsyncTask<Void, Void, ArrayList<BakingRecipe>> {
        @Override
        protected ArrayList<BakingRecipe> doInBackground(Void... params) {
            try {
                String jsonRecipeResponse = NetworkUtils
                        .getResponseFromHttpUrl(OpenRecipeJsonUtils.RECIPE_URL);
                OpenRecipeJsonUtils.getRecipesFromJson(jsonRecipeResponse, mBakingRecipeData);
                Log.i(TAG, "doInBackground: updated Baking Recipe data size - " + mBakingRecipeData.size());
                return mBakingRecipeData;
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
