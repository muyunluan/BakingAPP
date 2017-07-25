package com.muyunluan.bakingapp.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Fei Deng on 7/24/17.
 * Copyright (c) 2017 Muyunluan. All rights reserved.
 */

public class FavoriteDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favoriteRecipeDb.db";

    public static final int VERSION = 1;

    public FavoriteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE "
                +
                FavoriteContract.FavoriteEntry.TABLE_NAME + " ("
                +
                FavoriteContract.FavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +
                FavoriteContract.FavoriteEntry.COLUMN_ID + " TEXT UNIQUE NOT NULL, "
                +
                FavoriteContract.FavoriteEntry.COLUMN_NAME + " TEXT NOT NULL, "
                +
                FavoriteContract.FavoriteEntry.COLUMN_SERVINGS + " INTEGER NOT NULL, "
                +
                FavoriteContract.FavoriteEntry.COLUMN_IMAGE + " TEXT, "
                +
                "UNIQUE (" + FavoriteContract.FavoriteEntry.COLUMN_ID + ") ON CONFLICT IGNORE );";

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteContract.FavoriteEntry.TABLE_NAME);
        onCreate(db);
    }
}
