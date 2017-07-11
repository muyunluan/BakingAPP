package com.muyunluan.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.muyunluan.bakingapp.R;
import com.muyunluan.bakingapp.data.BakingRecipe;
import com.muyunluan.bakingapp.data.Constants;

import java.util.ArrayList;

/**
 * Created by Fei Deng on 6/30/17.
 * Copyright (c) 2017 Muyunluan. All rights reserved.
 */

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeDetailsFragment.OnListFragmentInteractionListener {

    private static final String TAG = RecipeDetailsActivity.class.getSimpleName();

    private ArrayList<BakingRecipe.BakingStep> mSteps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();

        if (null != getIntent()) {
            Bundle args = new Bundle();

            if (getIntent().hasExtra(Constants.KEY_INDREDIENT)) {
                args.putParcelableArrayList(Constants.KEY_INDREDIENT, getIntent().getParcelableArrayListExtra(Constants.KEY_INDREDIENT));
            } else {
                Log.e(TAG, "onCreate: No required Ingredients info being sent");
            }

            if (getIntent().hasExtra(Constants.KEY_STEP)) {
                args.putParcelableArrayList(Constants.KEY_STEP, getIntent().getParcelableArrayListExtra(Constants.KEY_STEP));
                mSteps = getIntent().getParcelableArrayListExtra(Constants.KEY_STEP);
            } else {
                Log.e(TAG, "onCreate: No required Steps info being sent");
            }

            recipeDetailsFragment.setArguments(args);
        }

        transaction.add(R.id.recipe_details_frame, recipeDetailsFragment).commit();
    }

    @Override
    public void onListFragmentInteraction(BakingRecipe.BakingStep step) {
        Toast.makeText(this, "recipe clicked", Toast.LENGTH_LONG).show();

        Log.i(TAG, "onListFragmentInteraction: go to Step - " + step.toString());

        Intent intent = new Intent(this, StepDisplayActivity.class);
        intent.putExtra("step", step);
        intent.putExtra("steps", mSteps);
        startActivity(intent);
    }
}
