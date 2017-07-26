package com.muyunluan.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Fei Deng on 7/26/17.
 * Copyright (c) 2017 Muyunluan. All rights reserved.
 */

public class RecipeAppWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeAppRemoteViewsFactory(this.getApplicationContext());
    }
}
