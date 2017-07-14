package com.muyunluan.bakingapp.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muyunluan.bakingapp.R;
import com.muyunluan.bakingapp.data.BakingRecipe;
import com.muyunluan.bakingapp.data.Constants;
import com.muyunluan.bakingapp.data.NetworkUtils;
import com.muyunluan.bakingapp.data.OpenRecipeJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import static com.muyunluan.bakingapp.MainActivity.isTablet;

/**
 * Created by Fei Deng on 6/28/17.
 * Copyright (c) 2017 Muyunluan. All rights reserved.
 */

public class RecipeListFragment extends Fragment {

    private static final String TAG = RecipeListFragment.class.getSimpleName();

    private ArrayList<BakingRecipe> mBakingRecipeData = new ArrayList<>();
    private RecipeListAdapter mRecipesAdapter;
    private RecyclerView mRecipeListView;

    public RecipeListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != savedInstanceState) {
            if (null != savedInstanceState.<BakingRecipe>getParcelableArrayList(Constants.KEY_SAVED_RECIPES)) {
                mBakingRecipeData.clear();
                mBakingRecipeData.addAll(savedInstanceState.<BakingRecipe>getParcelableArrayList(Constants.KEY_SAVED_RECIPES));
            } else {
                Log.d(TAG, "onCreate: no saved Recipe list");
            }
        } else {
            Log.d(TAG, "onCreate: no savedInstanceState");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        mRecipesAdapter = new RecipeListAdapter(new ArrayList<BakingRecipe>());

        mRecipeListView = (RecyclerView) view.findViewById(R.id.rv_recipes);
        if (!isTablet) {
            mRecipeListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            mRecipeListView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }

        mRecipeListView.setHasFixedSize(true);
        mRecipeListView.setAdapter(mRecipesAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (0 == mBakingRecipeData.size()) {
            //no saved data, fetch from API
            new RecipeQueryTask().execute();
        } else {
            //update with saved data
            mRecipesAdapter.clear();
            mRecipesAdapter.addAll(mBakingRecipeData);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Constants.KEY_SAVED_RECIPES, mBakingRecipeData);
    }

    public class RecipeQueryTask extends AsyncTask<Void, Void, ArrayList<BakingRecipe>> {

        @Override
        protected ArrayList<BakingRecipe> doInBackground(Void... params) {

            try {
                String jsonRecipeResponse = NetworkUtils.getResponseFromHttpUrl(OpenRecipeJsonUtils.RECIPE_URL);
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
                Log.e(TAG, "onPostExecute: empty Recipe data");
            }
        }
    }
}
