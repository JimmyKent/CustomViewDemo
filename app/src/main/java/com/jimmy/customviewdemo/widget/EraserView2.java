package com.jimmy.customviewdemo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.jimmy.customviewdemo.R;
import com.jimmy.customviewdemo.utils.ScreenUtils;

public class EraserView2 extends View {
	
	private static final int MIN_MOVE_DIS = 5;// 最小的移动距离：如果我们手指在屏幕上的移动距离小于此值则不会绘制
	
	private int mScreenX, mScreenY;
	private Path mPath;
	private Paint mPaint;
	private Canvas mCanvas;
	private Bitmap mFgBitmap, mBgBitmap;
	private float mPreX,mPreY;
	
	public EraserView2(Context context) {
		this(context, null);

	}

	public EraserView2(Context context, AttributeSet attrs) {
		super(context, attrs);

		initScreenParams();
		init(context);
	}

	private void initScreenParams() {
		mScreenX = ScreenUtils.getScreenWidth();
		mScreenY = ScreenUtils.getScreenHeight();
	}

	private void init(Context context) {
		//初始化path，画笔，背景和前景，画布
		mPath = new Path();
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		// 设置画笔透明度为0是关键！我们要让绘制的路径是透明的，然后让该路径与前景的底色混合“抠”出绘制路径
		mPaint.setARGB(128, 255, 0, 0);
		// 设置混合模式为DST_IN
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		// 设置画笔风格为描边
		mPaint.setStyle(Paint.Style.STROKE);
		// 设置路径结合处样式
		mPaint.setStrokeJoin(Paint.Join.MITER);
		// 设置笔触类型
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		// 设置描边宽度
		mPaint.setStrokeWidth(50);
		// 生成前景图Bitmap
		mFgBitmap = Bitmap.createBitmap(mScreenX, mScreenY, Config.ARGB_8888);
		//注入画布
		mCanvas = new Canvas(mFgBitmap);
		// 绘制画布背景为中性灰
		mCanvas.drawColor(0xFF808080);
		// 获取背景底图Bitmap
		mBgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a4);
		// 缩放背景底图Bitmap至屏幕大小
		mBgBitmap = Bitmap.createScaledBitmap(mBgBitmap, mScreenX, mScreenY, true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//绘制背景
		canvas.drawBitmap(mBgBitmap, 0, 0,null);
		//绘制前景
		canvas.drawBitmap(mFgBitmap, 0, 0,null);
		/*
		 * 这里要注意canvas和mCanvas是两个不同的画布对象
		 * 当我们在屏幕上移动手指绘制路径时会把路径通过mCanvas绘制到fgBitmap上
		 * 每当我们手指移动一次均会将路径mPath作为目标图像绘制到mCanvas上，而在上面我们先在mCanvas上绘制了中性灰色
		 * 两者会因为DST_IN模式的计算只显示中性灰，但是因为mPath的透明，计算生成的混合图像也会是透明的
		 * 所以我们会得到“橡皮擦”的效果
		 */
		//绘制手移动的路径
		mCanvas.drawPath(mPath, mPaint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//获取当前位置
		float x = event.getX();
		float y = event.getY();
		
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:// 手指接触屏幕重置路径
				mPath.reset();
				mPath.moveTo(x, y);
				mPreX = x;
				mPreY = y;
				break;
			case MotionEvent.ACTION_MOVE:// 手指移动时连接路径
				float dx = Math.abs(x - mPreX);
				float dy = Math.abs(y - mPreY);
				if (dx >= MIN_MOVE_DIS || dy >= MIN_MOVE_DIS) {
					mPath.quadTo(mPreX, mPreY, (x + mPreX) / 2, (y + mPreY) / 2);
					mPreX = x;
					mPreY = y;
				}
				break;
			default:
				break;
		}
		// 重绘视图 只重新onDraw了， 没有进行onmeasure和onLayout
		invalidate();
		return true;
	}


}
