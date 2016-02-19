package com.jimmy.customviewdemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jimmy.customviewdemo.R;

public class ProfilePhotoLayout extends ViewGroup {

	private ImageView mProfilePhoto;
	private ImageView mMenu;
	private TextView mTitle;
	private TextView mSubtitle;

	public ProfilePhotoLayout(Context context) {
		this(context, null);
	}

	public ProfilePhotoLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ProfilePhotoLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);

	}

	public ProfilePhotoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

	}

	private void init() {

	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mProfilePhoto = (ImageView) findViewById(R.id.mProfilePhoto);
		mMenu = (ImageView) findViewById(R.id.mMenu);
		mTitle = (TextView) findViewById(R.id.mTitle);
		mSubtitle = (TextView) findViewById(R.id.mSubtitle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		Log.d("onMeasure", "onMeasure");
		// 1. Setup initial constraints.
		int widthConstraints = getPaddingLeft() + getPaddingRight();
		int heightConstraints = getPaddingTop() + getPaddingBottom();
		int width = 0;
		int height = 0;

		// 2. Measure the ProfilePhoto
		measureChildWithMargins(mProfilePhoto, widthMeasureSpec, widthConstraints, heightMeasureSpec, heightConstraints);

		// 3. Update the contraints.
		widthConstraints += mProfilePhoto.getMeasuredWidth();
		width += mProfilePhoto.getMeasuredWidth();
		height = Math.max(mProfilePhoto.getMeasuredHeight(), height);

		// 4. Measure the Menu.
		measureChildWithMargins(mMenu, widthMeasureSpec, widthConstraints, heightMeasureSpec, heightConstraints);

		// 5. Update the constraints.
		widthConstraints += mMenu.getMeasuredWidth();
		width += mMenu.getMeasuredWidth();
		height = Math.max(mMenu.getMeasuredHeight(), height);

		// 6. Prepare the vertical MeasureSpec.
		int verticalWidthMeasureSpec =
				MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec) - widthConstraints,
						MeasureSpec.getMode(widthMeasureSpec));

		int verticalHeightMeasureSpec =
				MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec) - heightConstraints,
						MeasureSpec.getMode(heightMeasureSpec));

		// 7. Measure the Title.
		measureChildWithMargins(mTitle, verticalWidthMeasureSpec, 0, verticalHeightMeasureSpec, 0);

		// 8. Measure the Subtitle.
		measureChildWithMargins(mSubtitle, verticalWidthMeasureSpec, 0, verticalHeightMeasureSpec,
				mTitle.getMeasuredHeight());

		// 9. Update the sizes.
		width += Math.max(mTitle.getMeasuredWidth(), mSubtitle.getMeasuredWidth());
		height = Math.max(mTitle.getMeasuredHeight() + mSubtitle.getMeasuredHeight(), height);

		// 10. Set the dimension for this ViewGroup.
		setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int width = 0;
		View profilePhoto = getChildAt(0);
		profilePhoto.layout(width, 0, profilePhoto.getMeasuredWidth(), profilePhoto.getMeasuredHeight());
		width += profilePhoto.getMeasuredWidth();

		/*View title = getChildAt(1);
		title.layout(width, 0, title.getMeasuredWidth(), title.getMeasuredHeight());
		width += title.getMeasuredWidth();

		View subTitle = getChildAt(2);
		subTitle.layout(width, 0, subTitle.getMeasuredWidth(), subTitle.getMeasuredHeight());
		width += subTitle.getMeasuredWidth();*/

		View menu = getChildAt(3);
		menu.layout(width, 0, menu.getMeasuredWidth(), menu.getMeasuredHeight());
		//width += menu.getMeasuredWidth();
	}

	@Override
	protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed,
			int parentHeightMeasureSpec, int heightUsed) {
		MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

		int childWidthMeasureSpec =
				getChildMeasureSpec(parentWidthMeasureSpec, widthUsed + lp.leftMargin + lp.rightMargin, lp.width);

		int childHeightMeasureSpec =
				getChildMeasureSpec(parentHeightMeasureSpec, heightUsed + lp.topMargin + lp.bottomMargin,
						lp.height);

		child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
	}


	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {

		return new MarginLayoutParams(getContext(), attrs);
	}
}
