package com.muyunluan.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentTransaction;

import com.muyunluan.bakingapp.data.idling.SimpleIdlingResource;
import com.muyunluan.bakingapp.ui.RecipeListFragment;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;

/**
 * Created by Fei Deng on 8/2/17.
 * Copyright (c) 2017 Muyunluan. All rights reserved.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeListActivityIdlingTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    private SimpleIdlingResource simpleIdlingResource;

    private RecipeListFragment startListFragment() {
        MainActivity activity = (MainActivity) activityTestRule.getActivity();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        RecipeListFragment recipeListFragment = new RecipeListFragment();
        transaction.add(recipeListFragment, "recipeListFragment");
        transaction.commit();
        return recipeListFragment;
    }

    @Before
    public void registerIdlingResource() {
        simpleIdlingResource = startListFragment().getSimpleIdlingResource();
        Espresso.registerIdlingResources(simpleIdlingResource);
    }

    @Test
    public void clickOnRecyclerViewItem_idlingResourceTest() {

        // test if RecyclerView displays
        onView(withId(R.id.rv_recipes)).check(matches(isDisplayed()));
        // click item 1 --- Brownies
        onView(
                withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        // test if tv_ingredients displays
        onView(withId(R.id.tv_ingredients)).check(matches(isDisplayed()));
        // test if tv_ingredients contains the info for Brownies ingredient
        onView(withId(R.id.tv_ingredients)).check(matches(withText(containsString(("Bittersweet chocolate")))));
    }

    @After
    public void unregisterIdlingResource() {
        if (simpleIdlingResource != null) {
            Espresso.unregisterIdlingResources(simpleIdlingResource);
        }
    }
}
