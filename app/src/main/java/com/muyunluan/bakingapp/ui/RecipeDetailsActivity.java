package com.muyunluan.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.muyunluan.bakingapp.R;
import com.muyunluan.bakingapp.data.BakingRecipe;
import com.muyunluan.bakingapp.data.Constants;

import java.util.ArrayList;

import static com.muyunluan.bakingapp.MainActivity.isTablet;

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

        FragmentManager fragmentManager = getSupportFragmentManager();

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

        if (!isTablet) {
            Log.i(TAG, "onCreate: is Phone");
            fragmentManager.beginTransaction().add(R.id.recipe_details_frame, recipeDetailsFragment).commit();
        } else {
            Log.i(TAG, "onCreate: in Tabet");
            fragmentManager.beginTransaction().add(R.id.frame_recipe, recipeDetailsFragment).commit();
            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            Bundle b = new Bundle();
            b.putParcelableArrayList("steps", mSteps);
            stepDetailsFragment.setArguments(b);
            fragmentManager.beginTransaction().add(R.id.frame_step, stepDetailsFragment).commit();
        }
    }

    @Override
    public void onListFragmentInteraction(int position) {
        Intent intent = new Intent(this, StepDetailsActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("steps", mSteps);
        startActivity(intent);
    }
}
