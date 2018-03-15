package com.jimmy.customviewdemo.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup {

	public FlowLayout(Context context) {
		super(context);

	}

	public FlowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//如果xml设置的是精确值，直接使用
		//如果是wrap_content，就要使用测量的宽高
		//测量的目的调用setMeasuredDimension方法设置当前view的宽高的
		int exactlyWidth = MeasureSpec.getSize(widthMeasureSpec);
		int exactlyHeight = MeasureSpec.getSize(heightMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		//如果是wrap_content的模式
		int measureWidth = 0;
		int measureHeight = 0;

		//每行的宽高
		int lineWidth = 0;
		int lineHeight = 0;

		int cCount = getChildCount();
		for (int i = 0; i < cCount; i++) {
			View child = getChildAt(i);
			measureChild(child, widthMeasureSpec, heightMeasureSpec);
			//得到一个子view的实际宽高，包括margin
			//子view的getLayoutParams得到的是父view的LayoutParams，这里设置了marginLayoutParams
			MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
			int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
			int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

			//如果换行
			if (exactlyWidth - getPaddingLeft() - getPaddingRight() < lineWidth + childWidth) {
				//取最大的行宽
				measureWidth = Math.max(measureWidth, lineWidth);
				//重置宽度
				lineWidth = childWidth;
				//叠加行高，这样做的好处是换了行叠加上一行的行高，这样就准确了，不然如果当前的child（每行第一个）高度不是最高的话，就出现不准确
				measureHeight += lineHeight;
				lineHeight = childWidth;
			} else {//未换行
				//叠加行宽
				lineWidth += childWidth;
				lineHeight = Math.max(lineHeight, childHeight);
			}
			if (i == cCount - 1) {
				measureWidth = Math.max(measureHeight, lineWidth);
				measureHeight += lineHeight;
			}
		}
		Log.e("TAG", "exactlyWidth:" + exactlyWidth + ",exactlyHeight: " + exactlyHeight);
		setMeasuredDimension(//
				widthMode == MeasureSpec.EXACTLY ? exactlyWidth : measureWidth + getPaddingLeft() + getPaddingRight(),//
				heightMode == MeasureSpec.EXACTLY ? exactlyHeight : measureHeight + getPaddingTop()
						+ getPaddingBottom()//
		);
	}

	private List<List<View>> mAllView = new ArrayList<>();
	private List<Integer> mLineHeight = new ArrayList<>();

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		mAllView.clear();
		mLineHeight.clear();

		int lineWidth = 0;
		int lineHeight = 0;

		List<View> lineViews = new ArrayList<>();

		int cCount = getChildCount();
		for (int i = 0; i < cCount; i++) {//储存所有的view
			View child = getChildAt(i);
			MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
			int childWidth = child.getMeasuredWidth();
			int childHeight = child.getMeasuredHeight();

			//换行
			if (getWidth() - getPaddingLeft() - getPaddingRight() < lineWidth + childWidth + lp.leftMargin
					+ lp.rightMargin) {
				//记录LineHeight
				mLineHeight.add(lineHeight);
				//记录当前行的Views
				mAllView.add(lineViews);
				//重置我们的行宽和行高
				lineWidth = 0;
				lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
				//重置每行的view集合
				lineViews = new ArrayList<View>();
			}
			lineViews.add(child);
			lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
			lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
		}
		//处理最后一行，这里不能放进for循环里面
		mLineHeight.add(lineHeight);
		mAllView.add(lineViews);

		//子view的左边位置和上面位置
		int childLeft = getPaddingLeft();
		int childTop = getPaddingTop();

		//有多少行
		int lineNum = mAllView.size();
		//放置View
		for (int i = 0; i < lineNum; i++) {
			//当前行的所有view
			lineViews = mAllView.get(i);
			lineHeight = mLineHeight.get(i);
			for (int j = 0; j < lineViews.size(); j++) {//放置每行里面的view
				View child = lineViews.get(j);
				if (child.getVisibility() == View.GONE) {
					continue;
				}
				MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
				int cl = childLeft + lp.leftMargin;
				int ct = childTop + +lp.topMargin;
				int cr = cl + child.getMeasuredWidth();
				int cb = ct + child.getMeasuredHeight();
				//放置子view
				child.layout(cl, ct, cr, cb);

				childLeft += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
			}
			childLeft = getPaddingLeft();
			childTop += lineHeight;
		}
	}

	@Override
	protected LayoutParams generateLayoutParams(LayoutParams p) {//这个方法不行
		return new MarginLayoutParams(p);
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {//一定要这个方法
		return new MarginLayoutParams(getContext(), attrs);
	}

}
