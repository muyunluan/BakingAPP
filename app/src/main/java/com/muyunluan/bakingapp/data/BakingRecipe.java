package com.muyunluan.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Fei Deng on 6/27/17.
 * Copyright (c) 2017 Muyunluan. All rights reserved.
 */

public class BakingRecipe implements Parcelable {

    private int mId;
    private String mName;
    private ArrayList<BakingIngredient> mIngredients;
    private ArrayList<BakingStep> mSteps;
    private int mServings;
    private String mImageSource;

    public BakingRecipe(int mId, String mName, ArrayList<BakingIngredient> mIngredients, ArrayList<BakingStep> mSteps, int mServings) {
        this.mId = mId;
        this.mName = mName;
        this.mIngredients = mIngredients;
        this.mSteps = mSteps;
        this.mServings = mServings;
    }

    protected BakingRecipe(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mIngredients = in.createTypedArrayList(BakingIngredient.CREATOR);
        mSteps = in.createTypedArrayList(BakingStep.CREATOR);
        mServings = in.readInt();
        mImageSource = in.readString();
    }

    public static final Creator<BakingRecipe> CREATOR = new Creator<BakingRecipe>() {
        @Override
        public BakingRecipe createFromParcel(Parcel in) {
            return new BakingRecipe(in);
        }

        @Override
        public BakingRecipe[] newArray(int size) {
            return new BakingRecipe[size];
        }
    };

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

    public String getmImageSource() {
        return mImageSource;
    }

    public void setmImageSource(String mImageSource) {
        this.mImageSource = mImageSource;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeTypedList(mIngredients);
        dest.writeTypedList(mSteps);
        dest.writeInt(mServings);
        dest.writeString(mImageSource);
    }




    /* class for Ingredient */
    public static class BakingIngredient implements Parcelable {
        private int mQuantity;
        private String mMeasure;
        private String mIngredient;

        public BakingIngredient(int mQuantity, String mMeasure, String mIngredient) {
            this.mQuantity = mQuantity;
            this.mMeasure = mMeasure;
            this.mIngredient = mIngredient;
        }

        protected BakingIngredient(Parcel in) {
            mQuantity = in.readInt();
            mMeasure = in.readString();
            mIngredient = in.readString();
        }

        public static final Creator<BakingIngredient> CREATOR = new Creator<BakingIngredient>() {
            @Override
            public BakingIngredient createFromParcel(Parcel in) {
                return new BakingIngredient(in);
            }

            @Override
            public BakingIngredient[] newArray(int size) {
                return new BakingIngredient[size];
            }
        };

        @Override
        public String toString() {
            return this.mIngredient + " ( " + this.mQuantity + " " + this.mMeasure + " )";
//                    "BakingIngredient{" +
//                    "mQuantity=" + mQuantity +
//                    ", mMeasure='" + mMeasure + '\'' +
//                    ", mIngredient='" + mIngredient + '\'' +
//                    '}';
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mQuantity);
            dest.writeString(this.mMeasure);
            dest.writeString(this.mIngredient);
        }

    }
    /* End of class for Ingredient */

    /* class for Step */
    public static class BakingStep implements Parcelable {
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

        protected BakingStep(Parcel in) {
            mId = in.readInt();
            mShortDescription = in.readString();
            mDescription = in.readString();
            mVideoUrl = in.readString();
            mThumbnailUrl = in.readString();
        }

        public static final Creator<BakingStep> CREATOR = new Creator<BakingStep>() {
            @Override
            public BakingStep createFromParcel(Parcel in) {
                return new BakingStep(in);
            }

            @Override
            public BakingStep[] newArray(int size) {
                return new BakingStep[size];
            }
        };

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mId);
            dest.writeString(this.mShortDescription);
            dest.writeString(this.mDescription);
            dest.writeString(this.mVideoUrl);
            dest.writeString(mThumbnailUrl);
        }
    }
    /* End of class for Step */
}
