package com.muyunluan.bakingapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.muyunluan.bakingapp.R;
import com.muyunluan.bakingapp.ui.dummy.DummyContent;

/**
 * Created by Fei Deng on 6/30/17.
 * Copyright (c) 2017 Muyunluan. All rights reserved.
 */

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeDetailsFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction().add(R.id.recipe_details_frame, new RecipeDetailsFragment()).commit();
        }
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        
    }
}
