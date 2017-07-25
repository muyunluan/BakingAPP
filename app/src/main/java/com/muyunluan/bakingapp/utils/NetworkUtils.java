package com.muyunluan.bakingapp.utils;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Fei Deng on 6/27/17.
 * Copyright (c) 2017 Muyunluan. All rights reserved.
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static String getResponseFromHttpUrl(String urlStr) throws IOException {
        if (null == urlStr || TextUtils.isEmpty(urlStr)) {
            Log.e(TAG, "getResponseFromHttpUrl: empty input URL string");
            return null;
        }

        Uri uri = Uri.parse(urlStr);
        URL url = buildUrl(uri);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static URL buildUrl(Uri builtUri) {
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }
}
