package com.muyunluan.bakingapp;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.muyunluan.bakingapp.ui.RecipeListFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static boolean isTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigateHome();
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigateHome();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void navigateHome() {
        RecipeListFragment recipeListFragment = new RecipeListFragment();
        if (null != recipeListFragment) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, recipeListFragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            Log.e(TAG, "navigateHome: empty RecipeListFragment");
        }
    }
}
