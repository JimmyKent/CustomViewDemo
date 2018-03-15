package com.jimmy.customviewdemo.utils;

import android.content.Context;

import com.jimmy.customviewdemo.App;

public class DensityUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(float dpValue) {
        Context context = App.getInstance().getApplicationContext();
        if (null != context && context.getResources() != null && context.getResources().getDisplayMetrics() != null) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }

        return (int) dpValue;
    }

    public static float dip2px(float dpValue) {
        Context context = App.getInstance().getApplicationContext();
        if (null != context && context.getResources() != null && context.getResources().getDisplayMetrics() != null) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }

        return dpValue;
    }

    public static int dp2px(Context context, float dpValue) {
        if (null != context && context.getResources() != null && context.getResources().getDisplayMetrics() != null) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }

        return (int) dpValue;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        Context context = App.getInstance().getApplicationContext();
        if (null != context && context.getResources() != null && context.getResources().getDisplayMetrics() != null) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
        }

        return (int) pxValue;
    }

    public static int getDensity() {
        Context context = App.getInstance().getApplicationContext();
        if (null != context && context.getResources() != null && context.getResources().getDisplayMetrics() != null) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) scale;
        }
        return 0;
    }

}