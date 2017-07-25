package com.muyunluan.bakingapp.data.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Fei Deng on 7/24/17.
 * Copyright (c) 2017 Muyunluan. All rights reserved.
 */

public class FavoriteContract {
    public static final String AUTHORITY = "com.muyunluan.bakingapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_FAVORITES = "favorites";

    public static final class FavoriteEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();

        public static final String CONTENT_TYPE =
                                "vnd.android.cursor.dir/" + AUTHORITY + "/" + PATH_FAVORITES;

        public static final String TABLE_NAME = "favorites";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_INGREDIENTS = "ingredients";
        public static final String COLUMN_STEPS = "steps";
        public static final String COLUMN_SERVINGS = "servings";
        public static final String COLUMN_IMAGE = "image";

    }
}
