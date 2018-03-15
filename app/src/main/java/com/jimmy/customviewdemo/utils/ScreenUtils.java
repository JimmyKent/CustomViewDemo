package com.jimmy.customviewdemo.utils;

import java.lang.reflect.Field;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.jimmy.customviewdemo.App;

public class ScreenUtils {
	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return statusBarHeight;
	}


	public static int[] getScreen(Context context) {
		int width, height;
		Display display;
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		display = wm.getDefaultDisplay();
		if (android.os.Build.VERSION.SDK_INT >= 13) {
			Point size = new Point();
			display.getSize(size);
			width = size.x;
			height = size.y;
		} else {
			width = display.getWidth();
			height = display.getHeight();
		}
		return new int[] {width, height};
	}

	/** 
	 * 得到除去通知栏和虚拟按键的高度
	 * @return int  
	 */
	public static int getScreenHeight() {
		Context context = App.getInstance().getApplicationContext();
		return ScreenUtils.getScreen(context)[1]//屏幕总高，已经除去了虚拟按键
				- ScreenUtils.getStatusBarHeight(context);//通知栏
	}

	public static int getScreenWidth() {
		Context context = App.getInstance().getApplicationContext();
		return ScreenUtils.getScreen(context)[0];//屏幕总高，已经除去了虚拟按键

	}

}
