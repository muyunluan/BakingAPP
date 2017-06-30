package com.muyunluan.bakingapp.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muyunluan.bakingapp.R;
import com.muyunluan.bakingapp.data.BakingRecipe;
import com.muyunluan.bakingapp.data.NetworkUtils;
import com.muyunluan.bakingapp.data.OpenRecipeJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Fei Deng on 6/28/17.
 * Copyright (c) 2017 Muyunluan. All rights reserved.
 */

public class RecipeListFragment extends Fragment {

    private static final String TAG = RecipeListFragment.class.getSimpleName();

    private ArrayList<BakingRecipe> mBakingRecipeData = new ArrayList<>();
    private RecipeListAdapter mRecipesAdapter;
    private RecyclerView mRecipeListView;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public RecipeListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipeListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeListFragment newInstance(String param1, String param2) {
        RecipeListFragment fragment = new RecipeListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        mRecipesAdapter = new RecipeListAdapter(new ArrayList<BakingRecipe>());

        mRecipeListView = (RecyclerView) view.findViewById(R.id.rv_recipes);
        mRecipeListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecipeListView.setHasFixedSize(true);
        mRecipeListView.setAdapter(mRecipesAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        new RecipeQueryTask().execute();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public class RecipeQueryTask extends AsyncTask<Void, Void, ArrayList<BakingRecipe>> {

        @Override
        protected ArrayList<BakingRecipe> doInBackground(Void... params) {

            try {
                String jsonRecipeResponse = NetworkUtils.getResponseFromHttpUrl(OpenRecipeJsonUtils.RECIPE_URL);
                OpenRecipeJsonUtils.getRecipesFromJson(jsonRecipeResponse, mBakingRecipeData);
                Log.i(TAG, "doInBackground: updated Baking Recipe data size - " + mBakingRecipeData.size());
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
                Log.i(TAG, "onPostExecute: post Recipes len - " + bakingRecipes.size());
                mRecipesAdapter.addAll(bakingRecipes);
                mRecipesAdapter.notifyDataSetChanged();
            } else {
                Log.e(TAG, "onPostExecute: empty Recipe data");
            }
        }
    }
}
