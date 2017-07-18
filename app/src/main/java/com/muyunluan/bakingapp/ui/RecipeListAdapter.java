package com.muyunluan.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.muyunluan.bakingapp.R;
import com.muyunluan.bakingapp.data.BakingRecipe;
import com.muyunluan.bakingapp.data.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Fei Deng on 6/28/17.
 * Copyright (c) 2017 Muyunluan. All rights reserved.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    private static final String TAG = RecipeListAdapter.class.getSimpleName();

    private Context mContext;
    private ArrayList<BakingRecipe> mBakingRecipe;

    public RecipeListAdapter(ArrayList<BakingRecipe> mBakingRecipe) {
        this.mBakingRecipe = mBakingRecipe;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.recipe_list_item, parent, false);

        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(rootView);
        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, final int position) {
        BakingRecipe bakingRecipe = mBakingRecipe.get(position);
        //Log.i(TAG, "onBindViewHolder: get Baking Recipe - " + bakingRecipe.toString());

        if (!TextUtils.isEmpty(bakingRecipe.getmImageSource())) {
            Picasso.with(mContext).load(bakingRecipe.getmImageSource()).into(holder.recipeImg);
        } else {
            holder.recipeImg.setImageResource(Constants.recipeImages[position]);
        }
        holder.recipeNameTv.setText(bakingRecipe.getmName());
        holder.recipeServingTv.setText(String.format(Locale.US, " Servings: %s", bakingRecipe.getmServings()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RecipeDetailsActivity.class);
                intent.putExtra(Constants.KEY_INDREDIENT, mBakingRecipe.get(position).getmIngredients());
                intent.putExtra(Constants.KEY_STEP, mBakingRecipe.get(position).getmSteps());
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

    public void clear(){
        mBakingRecipe.clear();
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

        public RecipeViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.card_recipe);
            recipeImg = (ImageView) itemView.findViewById(R.id.img_recipe);
            recipeNameTv = (TextView) itemView.findViewById(R.id.recipe_title);
            recipeNameTv.setBackgroundColor(Color.argb(20, 0, 0, 0));
            recipeServingTv = (TextView) itemView.findViewById(R.id.recipe_serving);
            recipeServingTv.setBackgroundColor(Color.argb(20, 0, 0, 0));
        }
    }
}
