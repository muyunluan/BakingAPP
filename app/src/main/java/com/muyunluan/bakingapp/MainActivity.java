package com.muyunluan.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.muyunluan.bakingapp.ui.RecipeListFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static boolean isTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (null == savedInstanceState) {
            navigateHome();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigateHome();
    }


    private void navigateHome() {
        RecipeListFragment recipeListFragment = new RecipeListFragment();
        if (null == findViewById(R.id.fragment_container_grid)) {
            isTablet = false;
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, recipeListFragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            isTablet = true;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_grid, recipeListFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
