package com.muyunluan.bakingapp.utils;

import com.muyunluan.bakingapp.data.BakingRecipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Fei Deng on 6/27/17.
 * Copyright (c) 2017 Muyunluan. All rights reserved.
 */

public class OpenRecipeJsonUtils {

    private static final String TAG = OpenRecipeJsonUtils.class.getSimpleName();

    public static final String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    // Keys for Recipe
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_INGREDIENTS = "ingredients";
    private static final String KEY_STEPS = "steps";
    private static final String KEY_SERVINGS = "servings";
    private static final String KEY_IMAGE = "image";

    // Keys for Ingredient
    private static final String KEY_INGREDIENT_QUANTITY = "quantity";
    private static final String KEY_INGREDIENT_MEASURE = "measure";
    private static final String KEY_INGREDIENT = "ingredient";

    // Keys for Step
    private static final String KEY_STEP_ID = "id";
    private static final String KEY_STEP_SHORT_DESCRIPTION = "shortDescription";
    private static final String KEY_STEP_DESCRIPTION = "description";
    private static final String KEY_STEP_VIDEO_URL = "videoURL";
    private static final String KEY_STEP_THUMBNAIL_URL = "thumbnailURL";

    public static void getRecipesFromJson(String recipeJsonStr,
                                          ArrayList<BakingRecipe> parsedRecipeData)
                                            throws JSONException {

        JSONArray resultsArray = new JSONArray(recipeJsonStr);
        int arrLen = resultsArray.length();
        //Log.i(TAG, "getRecipesFromJson: Recipe objects num - " + arrLen);
        BakingRecipe bakingRecipe = null;
        for (int i = 0; i < arrLen; i++) {
            JSONObject recipeObject = resultsArray.getJSONObject(i);
            int idInt = recipeObject.getInt(KEY_ID);
            String nameStr = recipeObject.getString(KEY_NAME);

            // generate Ingredient object
            JSONArray ingredientsArray = recipeObject.getJSONArray(KEY_INGREDIENTS);
            ArrayList<BakingRecipe.BakingIngredient> ingredientArrayList = new ArrayList<>();
            BakingRecipe.BakingIngredient bakingIngredient = null;
            for (int j = 0; j < ingredientsArray.length(); j++) {
                JSONObject ingredientObject =ingredientsArray.getJSONObject(j);
                int quantityInt = ingredientObject.getInt(KEY_INGREDIENT_QUANTITY);
                String measureStr = ingredientObject.getString(KEY_INGREDIENT_MEASURE);
                String ingredientStr = ingredientObject.getString(KEY_INGREDIENT);
                bakingIngredient = new BakingRecipe.BakingIngredient(quantityInt,
                                                        measureStr, ingredientStr);
                ingredientArrayList.add(bakingIngredient);
            }

            // generate Step object
            JSONArray stepsArray = recipeObject.getJSONArray(KEY_STEPS);
            //Log.i(TAG, "getRecipesFromJson: get step array size - " + stepsArray.length());
            ArrayList<BakingRecipe.BakingStep> stepArrayList = new ArrayList<>();
            BakingRecipe.BakingStep bakingStep = null;
            for (int k = 0; k < stepsArray.length(); k++) {
                JSONObject stepObject = stepsArray.getJSONObject(k);
                int stepIdInt = stepObject.getInt(KEY_STEP_ID);
                String shortDescriptionStr = stepObject.getString(KEY_STEP_SHORT_DESCRIPTION);
                String descriptionStr = stepObject.getString(KEY_STEP_DESCRIPTION);
                String videoURLStr = stepObject.getString(KEY_STEP_VIDEO_URL);
                String thumbnailURLStr = stepObject.getString(KEY_STEP_THUMBNAIL_URL);
                bakingStep = new BakingRecipe.BakingStep(stepIdInt, shortDescriptionStr, descriptionStr, videoURLStr, thumbnailURLStr);
                stepArrayList.add(bakingStep);
            }

            int servingInt = recipeObject.getInt(KEY_SERVINGS);

            // generate Recipe object
            bakingRecipe = new BakingRecipe(idInt, nameStr, ingredientArrayList, stepArrayList, servingInt);
            // set up source for image
            String imageUrlStr = recipeObject.getString(KEY_IMAGE);
            bakingRecipe.setmImageSource(imageUrlStr);
            parsedRecipeData.add(bakingRecipe);
        }
    }

}
