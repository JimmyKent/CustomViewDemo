package com.jimmy.customviewdemo.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.Log;

public class PicUtils {

	public static final Bitmap grey(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Bitmap faceIconGreyBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(faceIconGreyBitmap);
		Paint paint = new Paint();
		ColorMatrix colorMatrix = new ColorMatrix();
		colorMatrix.setSaturation(0);
		ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(colorMatrix);
		paint.setColorFilter(colorMatrixFilter);
		canvas.drawBitmap(bitmap, 0, 0, paint);
		return faceIconGreyBitmap;
	}

	public static final Bitmap dark(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Bitmap faceIconGreyBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(faceIconGreyBitmap);
		Paint paint = new Paint();
		ColorMatrix colorMatrix = new ColorMatrix(//
				new float[] {//
				0.5F, 0, 0, 0, 0,//
						0, 0.5F, 0, 0, 0,//
						0, 0, 0.5F, 0, 0,//
						0, 0, 0, 1, 0,//
				});
		//colorMatrix.setSaturation(0);
		ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(colorMatrix);
		paint.setColorFilter(colorMatrixFilter);
		canvas.drawBitmap(bitmap, 0, 0, paint);
		return faceIconGreyBitmap;
	}

	public static final Bitmap change1(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Bitmap faceIconGreyBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(faceIconGreyBitmap);
		Paint paint = new Paint();
		ColorMatrix colorMatrix =
				new ColorMatrix(new float[] {-1, 0, 0, 1, 1, 0, -1, 0, 1, 1, 0, 0, -1, 1, 1, 0, 0, 0, 1, 0,});
		ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(colorMatrix);
		paint.setColorFilter(colorMatrixFilter);
		canvas.drawBitmap(bitmap, 0, 0, paint);
		return faceIconGreyBitmap;
	}

	public static final Bitmap change2(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Bitmap faceIconGreyBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(faceIconGreyBitmap);
		Paint paint = new Paint();
		ColorMatrix colorMatrix =
				new ColorMatrix(new float[] {0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0,});
		ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(colorMatrix);
		paint.setColorFilter(colorMatrixFilter);
		canvas.drawBitmap(bitmap, 0, 0, paint);
		return faceIconGreyBitmap;
	}

	public static final Bitmap change3(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Bitmap faceIconGreyBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(faceIconGreyBitmap);
		Paint paint = new Paint();
		ColorMatrix colorMatrix =
				new ColorMatrix(new float[] {1.438F, -0.122F, -0.016F, 0, -0.03F, -0.062F, 1.378F, -0.016F, 0, 0.05F,
						-0.062F, -0.122F, 1.483F, 0, -0.02F, 0, 0, 0, 1, 0,});
		ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(colorMatrix);
		paint.setColorFilter(colorMatrixFilter);
		canvas.drawBitmap(bitmap, 0, 0, paint);
		return faceIconGreyBitmap;
	}

	public static final Bitmap removeGreen(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Bitmap faceIconGreyBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(faceIconGreyBitmap);
		Paint paint = new Paint();
		paint.setColorFilter(new LightingColorFilter(0xFFFF00FF, 0x00000000));
		canvas.drawBitmap(bitmap, 0, 0, paint);
		return faceIconGreyBitmap;
	}

	public static final Bitmap red(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Bitmap bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bm);
		Paint paint = new Paint();
		paint.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.DARKEN));
		canvas.drawBitmap(bitmap, 0, 0, paint);
		return bm;
	}


	public static final Bitmap avoidXfermodeTarget(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Bitmap bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bm);
		Paint paint = new Paint();
		// 先绘制位图 
		canvas.drawBitmap(bitmap, 0, 0, null);
		//当画布中有跟0XFFFFFFFF色不一样的地方时候才“染”色 
		//XXX Deprecated AvoidXfermode avoidXfermode = new AvoidXfermode(0XFFFFFFFF, 0, AvoidXfermode.Mode.TARGET);
		// “染”什么色是由我们自己决定的  
		paint.setARGB(255, 211, 53, 243);
		// 设置AV模式  
		//XXX Deprecated paint.setXfermode(avoidXfermode);
		// 画一个位图大小一样的矩形
		//canvas.drawRect(0, 0, ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight(), paint);
		canvas.drawRect(0, 0, width, height, paint);
		Log.d("avoidXfermodeTarget", "bitmap width:" + bm.getWidth() + ",height" + bm.getHeight());
		Log.d("avoidXfermodeTarget", "canvas width:" + canvas.getWidth() + ",height" + canvas.getHeight());
		return bm;
	}


}
