package com.muyunluan.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.muyunluan.bakingapp.R;
import com.muyunluan.bakingapp.data.BakingRecipe;

import java.util.ArrayList;

/**
 * Created by Fei Deng on 6/28/17.
 * Copyright (c) 2017 Muyunluan. All rights reserved.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    private static final String TAG = RecipeListAdapter.class.getSimpleName();

    private Context mContext;
    private ArrayList<BakingRecipe> mBakingRecipe;
    private int mRecipeCount;

    public RecipeListAdapter(ArrayList<BakingRecipe> mBakingRecipe) {
        this.mBakingRecipe = mBakingRecipe;
        mRecipeCount = 0;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.recipe_list_item, parent, false);

        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(rootView);
//        BakingRecipe bakingRecipe = mBakingRecipe.get(mRecipeCount);
//        Log.i(TAG, "onBindViewHolder: get Baking Recipe - " + bakingRecipe.toString());
//        recipeViewHolder.recipeNameTv.setText(bakingRecipe.getmName());
//        Log.i(TAG, "onCreateViewHolder: get name - " + bakingRecipe.getmName());
//        recipeViewHolder.recipeServingTv.setText(String.valueOf(bakingRecipe.getmServings()));
        mRecipeCount++;

        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        BakingRecipe bakingRecipe = mBakingRecipe.get(position);
        //Log.i(TAG, "onBindViewHolder: get Baking Recipe - " + bakingRecipe.toString());
        holder.recipeImg.setImageResource(bakingRecipe.getmImageSource());
        holder.recipeNameTv.setText(bakingRecipe.getmName());
        holder.recipeServingTv.setText(" Servings: " + bakingRecipe.getmServings());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RecipeDetailsActivity.class);
                mContext.startActivity(intent);
            }
        });

    }

    public void addAll(ArrayList<BakingRecipe> bakingRecipes) {
        if (null != bakingRecipes && !bakingRecipes.isEmpty()) {
            mBakingRecipe.clear();
            for (BakingRecipe bakingRecipe : bakingRecipes) {
                mBakingRecipe.add(bakingRecipe);
            }
            //Log.i(TAG, "addAll: added num of Recipes - " + mBakingRecipe.size());
        }
    }

    @Override
    public int getItemCount() {
        return mBakingRecipe.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private ImageView recipeImg;
        private TextView recipeNameTv;
        private TextView recipeServingTv;
        private Button shareBt;
        private Button moreBt;

        public RecipeViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.card_recipe);
            recipeImg = (ImageView) itemView.findViewById(R.id.img_recipe);
            recipeNameTv = (TextView) itemView.findViewById(R.id.recipe_title);
            recipeNameTv.setBackgroundColor(Color.argb(20, 0, 0, 0));
            recipeServingTv = (TextView) itemView.findViewById(R.id.recipe_serving);
            recipeServingTv.setBackgroundColor(Color.argb(20, 0, 0, 0));
            shareBt = (Button) itemView.findViewById(R.id.btn_share);
            moreBt = (Button) itemView.findViewById(R.id.btn_more);
        }
    }
}
