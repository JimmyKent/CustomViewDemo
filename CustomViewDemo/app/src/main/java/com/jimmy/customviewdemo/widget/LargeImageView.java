package com.jimmy.customviewdemo.widget;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.jimmy.customviewdemo.gesture.MoveGestureDetector;

public class LargeImageView extends View {
	private BitmapRegionDecoder mDecoder;
	/** 图片的宽度和高度 */
	private int mImageWidth, mImageHeight;
	/** 绘制的区域 */
	private volatile Rect mRect = new Rect();
	private MoveGestureDetector mDetector;

	private static final BitmapFactory.Options options = new BitmapFactory.Options();

	static {
		options.inPreferredConfig = Bitmap.Config.RGB_565;
	}

	public void setInputStream(InputStream is) {
		try {
			mDecoder = BitmapRegionDecoder.newInstance(is, false);
			BitmapFactory.Options tmpOptions = new BitmapFactory.Options();
			// Grab the bounds for the scene dimensions
			tmpOptions.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(is, null, tmpOptions);
			mImageWidth = tmpOptions.outWidth;
			mImageHeight = tmpOptions.outHeight;
			Log.d("LargeImageView", "mImageHeight:"+mImageHeight);

			requestLayout();
			invalidate();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				if (is != null)
					is.close();
			} catch (Exception e) {
			}
		}
	}

	public void init() {
		mDetector = new MoveGestureDetector(getContext(), new MoveGestureDetector.SimpleMoveGestureDetector() {
			@Override
			public boolean onMove(MoveGestureDetector detector) {
				int moveX = (int) detector.getMoveX();
				int moveY = (int) detector.getMoveY();

				if (mImageWidth > getWidth()) {//图片的宽度大于当前空间的宽度
					mRect.offset(-moveX, 0);
					checkWidth();
					invalidate();
				}
				if (mImageHeight > getHeight()) {
					mRect.offset(0, -moveY);
					checkHeight();
					invalidate();
				}

				return true;
			}
		});
	}


	private void checkWidth() {
		Rect rect = mRect;
		int imageWidth = mImageWidth;
		int imageHeight = mImageHeight;

		if (rect.right > imageWidth) {
			rect.right = imageWidth;
			rect.left = imageWidth - getWidth();
		}

		if (rect.left < 0) {
			rect.left = 0;
			rect.right = getWidth();
		}
	}


	private void checkHeight() {
		Rect rect = mRect;
		int imageWidth = mImageWidth;
		int imageHeight = mImageHeight;

		if (rect.bottom > imageHeight) {
			rect.bottom = imageHeight;
			rect.top = imageHeight - getHeight();
		}

		if (rect.top < 0) {
			rect.top = 0;
			rect.bottom = getHeight();
		}
	}


	public LargeImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mDetector.onTouchEvent(event);
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Bitmap bm = mDecoder.decodeRegion(mRect, options);
		canvas.drawBitmap(bm, 0, 0, null);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int width = getMeasuredWidth();
		int height = getMeasuredHeight();

		int imageWidth = mImageWidth;
		int imageHeight = mImageHeight;

		mRect.left = imageWidth / 2 - width / 2;//初始化保证显示的框在图片的正中间
		mRect.top = imageHeight / 2 - height / 2;
		mRect.right = mRect.left + width;
		mRect.bottom = mRect.top + height;
		
		Log.d("LargeImageView", "mRect.left:"+mRect.left);
		Log.d("LargeImageView", "mRect.top:"+mRect.top);
		Log.d("LargeImageView", "mRect.right:"+mRect.right);
		Log.d("LargeImageView", "mRect.bottom:"+mRect.bottom);

	}


}
