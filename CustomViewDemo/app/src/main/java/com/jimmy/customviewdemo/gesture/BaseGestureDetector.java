package com.jimmy.customviewdemo.gesture;

import android.content.Context;
import android.view.MotionEvent;

public abstract class BaseGestureDetector {
	protected boolean mGestureInProgress;
	protected MotionEvent mPreEvent;
	protected MotionEvent mCurEvent;
	protected Context mContext;

	public BaseGestureDetector(Context context) {
		this.mContext = context;
	}

	public boolean onTouchEvent(MotionEvent event) {
		if (mGestureInProgress) {
			handleInProgressEvent(event);
		} else {
			handleStartProgressEvent(event);
		}

		return true;
	}

	protected abstract void handleInProgressEvent(MotionEvent event);

	protected abstract void handleStartProgressEvent(MotionEvent event);

	protected abstract void updateStateByEvent(MotionEvent event);

	protected void resetState() {
		if (mPreEvent != null) {
			mPreEvent.recycle();
			mPreEvent = null;
		}
		if (mCurEvent != null) {
			mCurEvent.recycle();
			mCurEvent = null;
		}
		mGestureInProgress = false;
	}

}
