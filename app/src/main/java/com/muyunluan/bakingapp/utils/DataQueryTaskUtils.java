package com.muyunluan.bakingapp.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.muyunluan.bakingapp.data.BakingRecipe;
import com.muyunluan.bakingapp.ui.RecipeListAdapter;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import static com.google.android.exoplayer2.mediacodec.MediaCodecInfo.TAG;

/**
 * Created by Fei Deng on 8/3/17.
 * Copyright (c) 2017 Muyunluan. All rights reserved.
 */

public class DataQueryTaskUtils extends AsyncTask<Void, Void, ArrayList<BakingRecipe>> {

    private Context mContext;
    private ArrayList<BakingRecipe> mBakingRecipeData = new ArrayList<>();
    private RecipeListAdapter mRecipesAdapter;
    private RecyclerView mRecipeListView;

    public DataQueryTaskUtils(Context context,
                              ArrayList<BakingRecipe> bakingRecipes,
                              RecipeListAdapter recipeListAdapter,
                              RecyclerView recyclerView) {
        mContext = context;
        mBakingRecipeData = bakingRecipes;
        mRecipesAdapter = recipeListAdapter;
        mRecipeListView = recyclerView;
    }

    @Override
    protected ArrayList<BakingRecipe> doInBackground(Void... params) {

        try {
            String jsonRecipeResponse = NetworkUtils
                    .getResponseFromHttpUrl(OpenRecipeJsonUtils.RECIPE_URL);
            OpenRecipeJsonUtils.getRecipesFromJson(jsonRecipeResponse, mBakingRecipeData);
            //Log.i(TAG, "doInBackground: updated Baking Recipe data size - " + mBakingRecipeData.size());
            return mBakingRecipeData;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<BakingRecipe> bakingRecipes) {
        if (null != bakingRecipes && 0 < bakingRecipes.size()) {
            mRecipesAdapter = new RecipeListAdapter(new ArrayList<BakingRecipe>());
            mRecipeListView.setAdapter(mRecipesAdapter);
            //Log.i(TAG, "onPostExecute: post Recipes len - " + bakingRecipes.size());
            mRecipesAdapter.addAll(bakingRecipes);
            mRecipesAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(mContext, "Empty Recipe data", Toast.LENGTH_LONG).show();
            Log.e(TAG, "onPostExecute: empty Recipe data");
        }
    }
}
