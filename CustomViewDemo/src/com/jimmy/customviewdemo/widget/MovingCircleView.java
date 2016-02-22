package com.jimmy.customviewdemo.widget;

import com.jimmy.customviewdemo.utils.ScreenUtils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MovingCircleView extends View implements Runnable {

	private Paint mPaint;
	private int mRadiu;

	public MovingCircleView(Context context) {
		this(context, null);

	}

	public MovingCircleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}

	private void initPaint() {
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//实例化画笔并打开抗锯齿 
		/*画笔样式分三种： 
		 * 1.Paint.Style.STROKE：描边 
		 * 2.Paint.Style.FILL_AND_STROKE：描边并填充 
		 * 3.Paint.Style.FILL：填充 
		 */
		mPaint.setStyle(Paint.Style.STROKE);
		// 设置画笔颜色为浅灰色  
		mPaint.setColor(Color.LTGRAY);
		/* 
		 * 设置描边的粗细，单位：像素px 
		 * 注意：当setStrokeWidth(0)的时候描边宽度并不为0而是只占一个像素 
		 */
		mPaint.setStrokeWidth(10);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawCircle(ScreenUtils.getScreenWidth() / 2, ScreenUtils.getScreenHeight() / 2, mRadiu, mPaint);
	}

/*	public synchronized void setRadiu(int radiu) {
		this.mRadiu = radiu;
	}*/

	@Override
	public void run() {
		while (true) {
			try {
				if (mRadiu <= 200) {
					//如果半径小于200则自加否则大于200后重置半径值以实现往复
					mRadiu += 10;
					//刷新View  
					postInvalidate();
				} else {
					mRadiu = 0;
				}
				//每执行一次暂停40毫秒
				Thread.sleep(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
