package com.jimmy.customviewdemo.ui.movie_seat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jimmy.customviewdemo.R;

import java.util.ArrayList;
import java.util.List;


public class MoviewSeatView extends View {

    private int[][] mData;

    // 0 - 空位; 1 - 已售; 2 -  选中; -1 - 过道
    private Bitmap mNormalSeatEmpty, mNormalSeatSelled, mNormalSeatSelected, mNormalSeatHallway;
    // 原始座位大小
    private int mOriginalSeatSize = 40;

    private int mDownClickPosition, mUpClickPosition;
    private List<Integer> mSelectedSeats = new ArrayList<Integer>();

    public void setData(int[][] data) {
        mData = data;
        for (int i = 0; i < mData.length; i++) {
            for (int j = 0; j < mData[0].length; j++) {
                if (mData[i][j] == 2) {
                    //该账号有预定了位置，但是没有付钱
                    mSelectedSeats.add((Integer) mData[i][j]);
                }
            }
        }
    }

    public MoviewSeatView(Context context) {
        this(context, null);
    }

    public MoviewSeatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mNormalSeatHallway = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.seat_hallway);
        mNormalSeatSelled = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.seat_selled);
        mNormalSeatSelected = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.seat_select);
        mNormalSeatEmpty = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.seat_empty);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // setMeasuredDimension(newBoxSize * column, newBoxSize * row);
        setMeasuredDimension(measure(widthMeasureSpec), measure(heightMeasureSpec));
    }

    private int measure(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                result = specSize;
            }
        }
        return result;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap emptySeat = Bitmap.createScaledBitmap(mNormalSeatEmpty, mOriginalSeatSize, mOriginalSeatSize, true);
        Bitmap hallwaySeat = Bitmap.createScaledBitmap(mNormalSeatHallway, mOriginalSeatSize, mOriginalSeatSize, true);
        Bitmap selledSeat = Bitmap.createScaledBitmap(mNormalSeatSelled, mOriginalSeatSize, mOriginalSeatSize, true);
        Bitmap selectedSeat = Bitmap.createScaledBitmap(mNormalSeatSelected, mOriginalSeatSize, mOriginalSeatSize, true);


        for (int i = 0; i < mData.length; i++) {
            for (int j = 0; j < mData[i].length; j++) {
                // 0 - 空位; 1 - 已售; 2 -  选中; -1 - 过道

                switch (mData[i][j]) {
                    case 0:
                        canvas.drawBitmap(emptySeat, j * mOriginalSeatSize, i * mOriginalSeatSize, null);
                        break;
                    case 1:
                        canvas.drawBitmap(selledSeat, j * mOriginalSeatSize, i * mOriginalSeatSize, null);
                        break;
                    case 2:
                        canvas.drawBitmap(selectedSeat, j * mOriginalSeatSize, i * mOriginalSeatSize, null);
                        break;
                    case -1:
                        canvas.drawBitmap(hallwaySeat, j * mOriginalSeatSize, i * mOriginalSeatSize, null);
                        break;
                }


            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                //记录当前位置
                //转换为数组中的位置
                mDownClickPosition = getSeatPosition(event);
                break;
            case MotionEvent.ACTION_UP:
                mUpClickPosition = getSeatPosition(event);
                //点击
                if (mUpClickPosition == mDownClickPosition && mUpClickPosition != -1) {
                    replyClick(mUpClickPosition);

                    /*if (mSelectedSeats.contains(upClickPosition)) {
                        mSelectedSeats.remove(mSelectedSeats.indexOf(upClickPosition));
                        //mData的数据也要修改
                        invalidate();
                    } else if (!unavaliableSeat.contains(upClickPosition)) {
                        mSelectedSeats.add(upClickPosition);
                        //mData的数据也要修改
                        invalidate();
                    }*/
                }

                break;

        }

        return true;
    }

    private void replyClick(int clickPosition) {
        if (clickPosition == -1) {
            return;
        }
        switch (mData[clickPosition / mData[0].length][clickPosition % mData[0].length]) {
            // 0 - 空位; 1 - 已售; 2 -  选中; -1 - 过道
            case 0:
                mSelectedSeats.add((Integer) clickPosition);
                //mData的数据也要修改
                mData[clickPosition / mData[0].length][clickPosition % mData[0].length] = 2;
                invalidate();
                break;
            case 2:
                mSelectedSeats.remove((Integer) clickPosition);
                //mData的数据也要修改
                mData[clickPosition / mData[0].length][clickPosition % mData[0].length] = 0;
                invalidate();
                break;

        }

    }

    private int getSeatPosition(MotionEvent event) {
        int newBoxSize = mOriginalSeatSize;
        float currentXPosition = event.getX();
        float currentYPosition = event.getY();
        for (int i = 0; i < mData.length; i++) {
            for (int j = 0; j < mData[0].length; j++) {
                if ((j * newBoxSize) < currentXPosition && currentXPosition < j * newBoxSize + newBoxSize
                        && (i * newBoxSize) < currentYPosition && currentYPosition < i * newBoxSize + newBoxSize) {
                    return i * mData.length + j;
                }
            }
        }
        return -1;
    }
}
