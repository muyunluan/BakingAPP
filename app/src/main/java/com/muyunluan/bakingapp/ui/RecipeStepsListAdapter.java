package com.muyunluan.bakingapp.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.muyunluan.bakingapp.R;
import com.muyunluan.bakingapp.data.BakingRecipe;
import com.muyunluan.bakingapp.ui.RecipeDetailsFragment.OnListFragmentInteractionListener;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Fei Deng on 6/30/17.
 * Copyright (c) 2017 Muyunluan. All rights reserved.
 */

public class RecipeStepsListAdapter extends RecyclerView.Adapter<RecipeStepsListAdapter.ViewHolder> {

    private final ArrayList<BakingRecipe.BakingStep> mStepsList;
    private final OnListFragmentInteractionListener mListener;

    public RecipeStepsListAdapter(ArrayList<BakingRecipe.BakingStep> bakingSteps, OnListFragmentInteractionListener listener) {
        mStepsList = bakingSteps;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_recipe_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mStep = mStepsList.get(position);
        holder.mIdTv.setText(String.format(Locale.US, "%d. ", holder.mStep.getmId()));
        holder.mShortDesTv.setText(holder.mStep.getmShortDescription());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStepsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdTv;
        public final TextView mShortDesTv;
        public BakingRecipe.BakingStep mStep;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdTv = (TextView) view.findViewById(R.id.tv_step_id);
            mShortDesTv = (TextView) view.findViewById(R.id.tv_step_short_des);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mShortDesTv.getText() + "'";
        }
    }
}
