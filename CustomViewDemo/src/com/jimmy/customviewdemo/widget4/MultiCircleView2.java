package com.jimmy.customviewdemo.widget4;

import com.jimmy.customviewdemo.listener.MulCircleClickListener;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 多重圈圈圈
 */
public class MultiCircleView2 extends View {
	private static final float STROKE_WIDTH = 1F / 256F, // 描边宽度占比
			SPACE = 1F / 64F,// 大圆小圆线段两端间隔占比
			LINE_LENGTH = 3F / 32F, // 线段长度占比
			CRICLE_LARGER_RADIU = 3F / 32F,// 大圆半径
			CRICLE_SMALL_RADIU = 5F / 64F,// 小圆半径
			ARC_RADIU = 1F / 8F,// 弧半径
			ARC_TEXT_RADIU = 5F / 32F;// 弧围绕文字半径

	private Paint strokePaint, textPaint, arcPaint;// 描边画笔和文字画笔

	private int size;// 控件边长

	private float strokeWidth;// 描边宽度
	private float ccX, ccY;// 中心圆圆心坐标
	private float largeCricleRadiu, smallCricleRadiu;// 大圆半径和小圆半径
	private float lineLength;// 线段长度
	private float space;// 大圆小圆线段两端间隔
	private float textOffsetY;// 文本的Y轴偏移值

	private Region mCenterCircleRegion = new Region();


	private MulCircleClickListener mClickListener;

	public MultiCircleView2(Context context) {
		this(context, null);
	}

	public MultiCircleView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 初始化画笔
		initPaint(context);
	}

	public void setCircleClickListener(MulCircleClickListener listener) {
		this.mClickListener = listener;
	}

	/**
	 * 初始化画笔
	 * @param context
	 */
	private void initPaint(Context context) {
		/*
		 * 初始化描边画笔
		 */
		strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		strokePaint.setStyle(Paint.Style.STROKE);
		strokePaint.setColor(Color.WHITE);
		strokePaint.setStrokeCap(Paint.Cap.ROUND);

		/*
		 * 初始化文字画笔
		 */
		textPaint =
				new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.SUBPIXEL_TEXT_FLAG
						| Paint.LINEAR_TEXT_FLAG);
		textPaint.setColor(Color.WHITE);
		textPaint.setTextSize(30);
		textPaint.setTextAlign(Paint.Align.CENTER);

		textOffsetY = (textPaint.descent() + textPaint.ascent()) / 2;//使文字居中
		Log.d("MultiCircleView2", "textOffsetY:" + textOffsetY);

		arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		arcPaint.setStrokeCap(Paint.Cap.ROUND);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// 强制长宽一致
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// 获取控件边长
		size = w;

		// 参数计算
		calculation();
	}

	/*
	 * 参数计算
	 */
	private void calculation() {
		// 计算描边宽度
		strokeWidth = STROKE_WIDTH * size;

		// 计算大圆半径
		largeCricleRadiu = size * CRICLE_LARGER_RADIU;

		// 计算小圆半径
		smallCricleRadiu = size * CRICLE_SMALL_RADIU;

		// 计算线段长度
		lineLength = size * LINE_LENGTH;

		// 计算大圆小圆线段两端间隔
		space = size * SPACE;

		// 计算中心圆圆心坐标
		ccX = size / 2;
		ccY = size / 2 + size * CRICLE_LARGER_RADIU;

		// 设置参数
		setPara();
	}

	/**
	 * 设置参数
	 */
	private void setPara() {
		// 设置描边宽度
		strokePaint.setStrokeWidth(strokeWidth);
		arcPaint.setStrokeWidth(strokeWidth);
	}

	@Override
	protected void onDraw(Canvas canvas) {//XXX
		// 绘制背景
		canvas.drawColor(0xFFF29B76);
		//画中心圆
		drawCenterCircle(canvas);
		//画左上角圆
		drawLeftTopCircle(canvas);
	}

	private void drawCenterCircle(Canvas canvas) {
		canvas.drawCircle(ccX, ccY, largeCricleRadiu, strokePaint);
		canvas.drawText("Jimmy", ccX, ccY - textOffsetY, textPaint);
		Path pathShortSize = new Path();
		// 用来装载Path边界值的RectF对象  
		RectF rectShortSize = new RectF();

		// 添加圆形到Path
		pathShortSize.addCircle(ccX, ccY, largeCricleRadiu, Path.Direction.CCW);
		// 计算边界  
		pathShortSize.computeBounds(rectShortSize, true);
		// 将Path转化为Region  
		mCenterCircleRegion.setPath(pathShortSize, new Region((int) rectShortSize.left, (int) rectShortSize.top,
				(int) rectShortSize.right, (int) rectShortSize.bottom));
	}

	private void drawLeftTopCircle(Canvas canvas) {
		// 锁定画布
		canvas.save();
		canvas.translate(ccX, ccY);//移动到圆心的地方
		canvas.rotate(60);//绕左上角的（0，0位置旋转 ）
		//(-largeCricleRadiu, 0)，这个点在坐标轴转了之后就是目标点
		canvas.drawLine(-largeCricleRadiu, 0, -lineLength * 2, 0, strokePaint);
		canvas.drawCircle(-3 * largeCricleRadiu, 0, largeCricleRadiu, strokePaint);
		canvas.save();
		canvas.translate(0 - 3 * largeCricleRadiu, 0);
		canvas.rotate(-60);
		canvas.drawText("Apple", 0, -textOffsetY, textPaint);
		canvas.restore();
		canvas.restore();
	}


	

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		//http://blog.sina.com.cn/s/blog_62f189570101da3d.html 另一种思路，基于位置的思路

			case MotionEvent.ACTION_DOWN:
				if (mCenterCircleRegion.contains((int) event.getX(), (int) event.getY())) {
					//如果中间的圆被点击了
					Log.d("ACTION_DOWN", "ccX:" + ccX + ",ccY:" + //
							",event.getX():" + event.getX() + ",event.getY():" + event.getY());
					mClickListener.OnCenterClick();
				}
				break;

			default:
				break;
		}

		return super.onTouchEvent(event);
	}
}
