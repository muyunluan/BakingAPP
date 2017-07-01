package com.muyunluan.bakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.muyunluan.bakingapp.R;
import com.muyunluan.bakingapp.data.BakingRecipe;
import com.muyunluan.bakingapp.data.Constants;
import com.muyunluan.bakingapp.ui.dummy.DummyContent;
import com.muyunluan.bakingapp.ui.dummy.DummyContent.DummyItem;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Fei Deng on 6/30/17.
 * Copyright (c) 2017 Muyunluan. All rights reserved.
 */


public class RecipeDetailsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    private ArrayList<BakingRecipe.BakingIngredient> mIngredients = new ArrayList<>();
    private boolean hasIngredients = false;

    private ArrayList<BakingRecipe.BakingStep> mSteps = new ArrayList<>();
    private boolean hasSteps = false;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeDetailsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RecipeDetailsFragment newInstance(int columnCount) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);

            mIngredients = getArguments().getParcelableArrayList(Constants.KEY_INDREDIENT);
            if (null != mIngredients && mIngredients.size() > 0) {
                hasIngredients = true;
                Log.i(TAG, "onCreate: get Ingredients size - " + mIngredients.size());
            } else {
                hasIngredients = false;
                Log.e(TAG, "onCreate: empty Ingredients or error to get Bundle info");
            }

            mSteps = getArguments().getParcelableArrayList(Constants.KEY_STEP);
            if (null != mSteps && mSteps.size() > 0) {
                hasSteps = true;
                Log.i(TAG, "onCreate: get Steps size - " + mSteps.size());
            } else {
                hasSteps = false;
                Log.e(TAG, "onCreate: empty Steps or error to get Bundle info");
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_list, container, false);

        TextView mRecipeTv = (TextView) view.findViewById(R.id.tv_ingredients);
        if (hasIngredients) {
            StringBuilder ingredientsDetail = new StringBuilder("Ingredients: \n");
            for (BakingRecipe.BakingIngredient bakingIngredient : mIngredients) {
                ingredientsDetail.append(bakingIngredient.toString() + "\n");
            }
            mRecipeTv.setText(ingredientsDetail.toString());
            Log.i(TAG, "onCreateView: get Ingredients - \n" + ingredientsDetail.toString());
        } else {
            Log.e(TAG, "onCreateView: empty Ingredients");
            //TODO: use string.xml
            mRecipeTv.setText("NULL Ingredients");
        }
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_steps_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new MyItemRecyclerViewAdapter(DummyContent.ITEMS, mListener));

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
