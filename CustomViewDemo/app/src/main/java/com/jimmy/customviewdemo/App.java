package com.jimmy.customviewdemo;

import android.app.Application;

import com.jimmy.log.KLog;


public class App extends Application {
	private static App mApp;

	@Override
	public void onCreate() {
		super.onCreate();
		KLog.init(true, "jimmy");
		KLog.e("Application onCreate");
		mApp = this;
	}


	public static App getInstance() {
		return mApp;
	}
}
