package com.jimmy.customviewdemo.ui.movie_seat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jimmy.customviewdemo.R;

import java.util.ArrayList;
import java.util.List;


public class MovieSeatView extends View {

    private int[][] mData;

    // 0 - 空位; 1 - 已售; 2 -  选中; -1 - 过道
    private Bitmap mNormalSeatEmpty, mNormalSeatSelled, mNormalSeatSelected, mNormalSeatHallway;
    // 原始盒子大小
    int mOriginalBoxSize = 50;
    // 原始座位大小
    private int mOriginalSeatSize = 40;

    private int mDownClickPosition, mUpClickPosition;
    private List<Integer> mSelectedSeats = new ArrayList<Integer>();

    // 座位paint
    Paint paint = new Paint();
    static final float MIN_SCALE = 0.5f;// 最小缩放比例
    static final float MAX_SCALE = 1.5f;// 最大缩放比例
    // 缩放大小
    float currScale = 1f;
    // 保留上一次缩放比例
    float prevScale = currScale;
    // 手势down时 两点的距离
    float oldDistance;
    // 两点最新距离（比如缩放）
    float newDistance;
    // 手势down时的座位位置
    int downClickPosition;
    // 手势up时的座位位置
    int upClickPosition;
    // 两点中间位置(暂时没用到，打算用在两点之间缩放时焦点在两点中点进行)
    PointF middlePoint = new PointF();
    // 记录按下的位置(用于判断移动是否超过限制如果是则认定为移动)
    PointF downPoint = new PointF();
    // 记录上一个移动坐标的位置
    PointF prevPoint = new PointF();
    // 记录padding的位置（主要是top和left）
    RectF paddingRect = new RectF();
    // 缓存影院座位表的画图
    Bitmap mSeatBitmapCache;
    // 第一次加载
    boolean isFirst = true;

    public void setData(int[][] data) {
        mData = data;
        for (int i = 0; i < mData.length; i++) {
            for (int j = 0; j < mData[0].length; j++) {
                if (mData[i][j] == 2) {
                    //该账号有预定了位置，但是没有付钱，模拟第二次进入或者从服务器加载
                    mSelectedSeats.add((Integer) mData[i][j]);
                }
            }
        }
    }

    public MovieSeatView(Context context) {
        this(context, null);
    }

    public MovieSeatView(Context context, AttributeSet attrs) {
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

    static final int NONE = 0;// 初始状态
    static final int DRAG = 1;// 拖动
    static final int ZOOM = 2;// 缩放
    // 点击状态
    private int mode = NONE;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);// 原始盒子大小
        int newBoxSize = Math.round(mOriginalBoxSize * currScale);
        int newSeatSize = Math.round(mOriginalSeatSize * currScale);

        //先画主体（所有座位）
        Bitmap emptySeat = Bitmap.createScaledBitmap(mNormalSeatEmpty, newSeatSize, newSeatSize, true);
        Bitmap hallwaySeat = Bitmap.createScaledBitmap(mNormalSeatHallway, newSeatSize, newSeatSize, true);
        Bitmap selledSeat = Bitmap.createScaledBitmap(mNormalSeatSelled, newSeatSize, newSeatSize, true);
        Bitmap selectedSeat = Bitmap.createScaledBitmap(mNormalSeatSelected, newSeatSize, newSeatSize, true);

        for (int i = 0; i < mData.length; i++) {
            for (int j = 0; j < mData[i].length; j++) {
                // 0 - 空位; 1 - 已售; 2 -  选中; -1 - 过道
                switch (mData[i][j]) {
                    case 0:
                        canvas.drawBitmap(emptySeat, j * newBoxSize, i * newBoxSize, null);
                        break;
                    case 1:
                        canvas.drawBitmap(selledSeat, j * newBoxSize, i * newBoxSize, null);
                        break;
                    case 2:
                        canvas.drawBitmap(selectedSeat, j * newBoxSize, i * newBoxSize, null);
                        break;
                    case -1:
                        canvas.drawBitmap(hallwaySeat, j * newBoxSize, i * newBoxSize, null);
                        break;
                }
            }
        }
        //画轮廓，并设置左边和顶部的padding
        int seatBitmapWidth = (int) (newBoxSize * mData[0].length);
        int seatBitmapHeight = (int) (newBoxSize * mData.length);
        Bitmap tempBitmap = Bitmap.createBitmap(seatBitmapWidth, seatBitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(tempBitmap);
        if (mode == ZOOM || mode == NONE) {
            canvas2.drawBitmap(tempBitmap, paddingRect.left, paddingRect.top, paint);
            tempBitmap.recycle();
            mSeatBitmapCache = null;
        } else {
            if (mSeatBitmapCache == null) {
                mSeatBitmapCache = tempBitmap;
            }
            canvas2.drawBitmap(tempBitmap, paddingRect.left, paddingRect.top, paint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                //记录当前位置
                downPoint.set(event.getX(), event.getY());
                prevPoint.set(event.getX(), event.getY());
                //转换为数组中的位置
                mDownClickPosition = getSeatPosition(event);
                break;
            case MotionEvent.ACTION_UP:
                //点击
                mUpClickPosition = getSeatPosition(event);
                if (mUpClickPosition == mDownClickPosition && mUpClickPosition != -1) {
                    replyClick(mUpClickPosition);
                }
                mode = NONE;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                // 保存上一次缩放大小
                prevScale = currScale;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:// 当屏幕上已经有一个点被按住，此时再按下其他点时触发。
                oldDistance = getDistance(event);
                // 如果连续两点距离大于10，则判定为多点模式
                if (oldDistance > 10f) {
                    getMiddlePoint(middlePoint, event);
                    // 标志缩放
                    mode = ZOOM;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == ZOOM) {
                    float newDist = getDistance(event);
                    if (newDist > 10f) {
                        currScale = newDist / oldDistance * prevScale;
                        // 检查scale范围
                        currScale = currScale < MIN_SCALE ? MIN_SCALE : (currScale > MAX_SCALE ? MAX_SCALE : currScale);

                        invalidate();
                    }
                } else {
                    float distanceX = event.getX() - downPoint.x;
                    float distanceY = event.getY() - downPoint.y;
                    // 判断downPoint跟点击的位置是否超过20，如果超过就判定为移动，之后都用prevPoint小位移计算
                    if (Math.sqrt(distanceX * distanceX + distanceY * distanceY) > 20) {
                        // 标志拖动
                        mode = DRAG;
                        paddingRect.left += (event.getX() - prevPoint.x);
                        paddingRect.top += (event.getY() - prevPoint.y);
                        // System.out.println(paddingRect);
                        // 判断边界
                        if (paddingRect.left > 1) {
                            paddingRect.left = 1;
                        }
                        if (paddingRect.top > 1) {
                            paddingRect.top = 1;
                        }

                        // left加屏幕宽度大于影院宽度就说明已经到右侧的边框，left不能再减少(向左移)
                        if (Math.abs(paddingRect.left) + getMeasuredWidth() > getRealWidth()) {
                            paddingRect.left = -(getRealWidth() - getMeasuredWidth() + 5);
                        }
                        // top加屏幕高度大于影院高度就说明已经到底部的边框，top不能再减少(向上移)
                        if (Math.abs(paddingRect.top) + getMeasuredHeight() > getRealHeight()) {
                            paddingRect.top = -(getRealHeight() - getMeasuredHeight() + 5);
                        }
                        // 当座位表的真实高度或者宽度都小于measure的就直接固定位置
                        if (getMeasuredWidth() > getRealWidth()) {
                            paddingRect.left = 1;
                        }
                        if (getMeasuredHeight() > getRealHeight()) {
                            paddingRect.top = 1;
                        }

                        prevPoint.set(event.getX(), event.getY());
                        invalidate();
                    }
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
        int newBoxSize = Math.round(mOriginalBoxSize * currScale);
        float currentXPosition = event.getX() + Math.abs(paddingRect.left);
        float currentYPosition = event.getY() + Math.abs(paddingRect.top);
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

    private float getDistance(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * 两点的中点
     */
    private void getMiddlePoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /**
     * 获取影院座位表实际宽度(该宽度会随着放大缩小变化)
     */
    private int getRealWidth() {
        return Math.round(mOriginalBoxSize * currScale) * mData[0].length;
    }

    /**
     * 获取影院座位表实际高度(该高度会随着放大缩小变化)
     */
    private int getRealHeight() {
        return Math.round(mOriginalBoxSize * currScale) * mData.length;
    }
}
