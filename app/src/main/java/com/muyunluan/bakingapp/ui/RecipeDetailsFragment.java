package com.muyunluan.bakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.muyunluan.bakingapp.R;
import com.muyunluan.bakingapp.data.BakingRecipe;
import com.muyunluan.bakingapp.data.Constants;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Fei Deng on 6/30/17.
 * Copyright (c) 2017 Muyunluan. All rights reserved.
 */


public class RecipeDetailsFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;

    private ArrayList<BakingRecipe.BakingIngredient> mIngredients = new ArrayList<>();
    private boolean hasIngredients = false;

    private ArrayList<BakingRecipe.BakingStep> mSteps = new ArrayList<>();
    private boolean hasSteps = false;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeDetailsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            mIngredients = getArguments().getParcelableArrayList(Constants.KEY_INDREDIENT);
            if (null != mIngredients && mIngredients.size() > 0) {
                hasIngredients = true;
                //Log.i(TAG, "onCreate: get Ingredients size - " + mIngredients.size());
            } else {
                hasIngredients = false;
                Log.e(TAG, "onCreate: empty Ingredients or error to get Bundle info");
            }

            mSteps = getArguments().getParcelableArrayList(Constants.KEY_STEP);
            if (null != mSteps && mSteps.size() > 0) {
                hasSteps = true;
                Log.i(TAG, "onCreate: get Steps size - " + mSteps.size());
            } else {
                hasSteps = false;
                Log.e(TAG, "onCreate: empty Steps or error to get Bundle info");
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        TextView mRecipeTv = (TextView) view.findViewById(R.id.tv_ingredients);
        if (hasIngredients) {
            StringBuilder ingredientsDetail = new StringBuilder("Ingredients: \n");
            for (BakingRecipe.BakingIngredient bakingIngredient : mIngredients) {
                ingredientsDetail.append(bakingIngredient.toString() + "\n");
            }
            mRecipeTv.setText(ingredientsDetail.toString());
            //Log.i(TAG, "onCreateView: get Ingredients - \n" + ingredientsDetail.toString());
        } else {
            Log.e(TAG, "onCreateView: empty Ingredient");
            mRecipeTv.setText(getString(R.string.empty_ingredient));
        }

        if (hasSteps) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_steps_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new RecipeStepsListAdapter(mSteps, mListener));
        } else {
            Log.e(TAG, "onCreateView: empty Steps");
        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(int position);
    }
}
