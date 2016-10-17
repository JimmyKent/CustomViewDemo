/*
 * Created by Storm Zhang, Feb 11, 2014.
 */

package com.jimmy.volley;

import android.app.Application;

import com.jimmy.volley.data.RequestManager;

public class VolleyApp extends Application {
	@Override
    public void onCreate() {
        super.onCreate();
        init();
    }


    private void init() {
        RequestManager.init(this);
    }
}
