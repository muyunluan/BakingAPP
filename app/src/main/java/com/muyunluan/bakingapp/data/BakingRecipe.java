package com.muyunluan.bakingapp.data;

import java.util.ArrayList;

/**
 * Created by Fei Deng on 6/27/17.
 * Copyright (c) 2017 Muyunluan. All rights reserved.
 */

public class BakingRecipe {

    private int mId;
    private String mName;
    private ArrayList<BakingIngredient> mIngredients;
    private ArrayList<BakingStep> mSteps;
    private int mServings;
    private int mImageSource;

    public BakingRecipe(int mId, String mName, ArrayList<BakingIngredient> mIngredients, ArrayList<BakingStep> mSteps, int mServings) {
        this.mId = mId;
        this.mName = mName;
        this.mIngredients = mIngredients;
        this.mSteps = mSteps;
        this.mServings = mServings;
    }

    @Override
    public String toString() {
        return "BakingRecipe{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mIngredients=" + mIngredients +
                ", mSteps=" + mSteps +
                ", mServings=" + mServings +
                ", mImageSource='" + mImageSource + '\'' +
                '}';
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public ArrayList<BakingIngredient> getmIngredients() {
        return mIngredients;
    }

    public void setmIngredients(ArrayList<BakingIngredient> mIngredients) {
        this.mIngredients = mIngredients;
    }

    public ArrayList<BakingStep> getmSteps() {
        return mSteps;
    }

    public void setmSteps(ArrayList<BakingStep> mSteps) {
        this.mSteps = mSteps;
    }

    public int getmServings() {
        return mServings;
    }

    public void setmServings(int mServings) {
        this.mServings = mServings;
    }

    public int getmImageSource() {
        return mImageSource;
    }

    public void setmImageSource(int mImageSource) {
        this.mImageSource = mImageSource;
    }


    /* class for Ingredient */
    public static class BakingIngredient {
        private int mQuantity;
        private String mMeasure;
        private String mIngredient;

        public BakingIngredient(int mQuantity, String mMeasure, String mIngredient) {
            this.mQuantity = mQuantity;
            this.mMeasure = mMeasure;
            this.mIngredient = mIngredient;
        }

        @Override
        public String toString() {
            return "BakingIngredient{" +
                    "mQuantity=" + mQuantity +
                    ", mMeasure='" + mMeasure + '\'' +
                    ", mIngredient='" + mIngredient + '\'' +
                    '}';
        }

        public int getmQuantity() {
            return mQuantity;
        }

        public void setmQuantity(int mQuantity) {
            this.mQuantity = mQuantity;
        }

        public String getmMeasure() {
            return mMeasure;
        }

        public void setmMeasure(String mMeasure) {
            this.mMeasure = mMeasure;
        }

        public String getmIngredient() {
            return mIngredient;
        }

        public void setmIngredient(String mIngredient) {
            this.mIngredient = mIngredient;
        }
    }
    /* End of class for Ingredient */

    /* class for Step */
    public static class BakingStep {
        private int mId;
        private String mShortDescription;
        private String mDescription;
        private String mVideoUrl;
        private String mThumbnailUrl;

        public BakingStep(int mId, String mShortDescription, String mDescription, String mVideoUrl, String mThumbnailUrl) {
            this.mId = mId;
            this.mShortDescription = mShortDescription;
            this.mDescription = mDescription;
            this.mVideoUrl = mVideoUrl;
            this.mThumbnailUrl = mThumbnailUrl;
        }

        @Override
        public String toString() {
            return "BakingStep{" +
                    "mId=" + mId +
                    ", mShortDescription='" + mShortDescription + '\'' +
                    ", mDescription='" + mDescription + '\'' +
                    ", mVideoUrl='" + mVideoUrl + '\'' +
                    ", mThumbnailUrl='" + mThumbnailUrl + '\'' +
                    '}';
        }

        public int getmId() {
            return mId;
        }

        public void setmId(int mId) {
            this.mId = mId;
        }

        public String getmShortDescription() {
            return mShortDescription;
        }

        public void setmShortDescription(String mShortDescription) {
            this.mShortDescription = mShortDescription;
        }

        public String getmDescription() {
            return mDescription;
        }

        public void setmDescription(String mDescription) {
            this.mDescription = mDescription;
        }

        public String getmVideoUrl() {
            return mVideoUrl;
        }

        public void setmVideoUrl(String mVideoUrl) {
            this.mVideoUrl = mVideoUrl;
        }

        public String getmThumbnailUrl() {
            return mThumbnailUrl;
        }

        public void setmThumbnailUrl(String mThumbnailUrl) {
            this.mThumbnailUrl = mThumbnailUrl;
        }
    }
    /* End of class for Step */
}
