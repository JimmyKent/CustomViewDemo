package com.jimmy.customviewdemo.ui.lockview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


import com.jimmy.customviewdemo.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

//TODO 做一个9个位置都是图片的，可以自己设置图片
public class LockView extends View {

    private static final int LOCK_VIEW_WIDTH = 420;
    private static final int DEFAULT_CELL_WIDTH = LOCK_VIEW_WIDTH / 7;
    private static final int DEFAULT_CELL_STROKE_WIDTH = DEFAULT_CELL_WIDTH / 30;

    private int NORMAL_INNER_COLOR = 0XEE898989;// 内圆颜色
    private int NORMAL_OUTER_COLOR = 0XEED9D9D9;// 外圆颜色

    private int SELECTED_INNER_COLOR = 0XEE33FF11;// 选中时内圆颜色
    private int SELECTED_OUTER_COLOR = 0XCCFFCC12;// 选中时外圆颜色

    private int ERROR_INNER_COLOR = 0XFFEA0945;// 密码错误时内圆颜色
    private int ERROR_OUTER_COLOR = 0XFF901032;// 密码错误时外圆颜色

    private Paint mArrowPaint;

    private Paint mLinePaint;
    private Paint mNormalPaint;
    private Paint mSelectedPaint;
    private Paint mErrorPaint;


    private int lineColor = this.SELECTED_INNER_COLOR;// 选中时线条颜色
    private int arrowColor = this.SELECTED_INNER_COLOR;// 选中时箭头颜色

    private int errorArrowColor = this.ERROR_INNER_COLOR;// 密码错误时箭头颜色
    private int errorlineColor = this.ERROR_INNER_COLOR;// 密码错误时线条颜色

    private CellBean[] mCells = new CellBean[9];
    private volatile boolean mIsInit = false;

    private int mCellWidth;
    private int mCellRadius;
    private int mLineWidth;
    private int mSpace;


    private int mWidth;
    private int mHeight;

    private float mCurrentX;
    private float mCurrentY;
    private boolean mFinish = false;

    private List<Integer> mSelectedCells = new ArrayList<Integer>();

    private CellBean mPreCellBean,mCurCellBean;


    public LockView(Context context) {
        this(context, null);
    }

    public LockView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    /*public void setPointNum(int number){
        mCells = new CellBean[number];
    }*/


    private void init() {
        if (mCells == null) {
            return;
        }
        Log.e("LockView", "mWidth:" + mWidth);
        Log.e("LockView", "mHeight:" + mHeight);
        //点的宽度
        mCellWidth = (mWidth == 0 ? DensityUtils.dp2px(getContext(), DEFAULT_CELL_WIDTH) : mWidth / 5);
        //点的半径
        mCellRadius = mCellWidth >> 1;
        //线条的宽度
        mLineWidth = (mWidth == 0 ? DensityUtils.dp2px(getContext(), DEFAULT_CELL_STROKE_WIDTH) : mCellWidth / 5);
        //点与点之间的空隙
        mSpace = mCellRadius;

        //初始化画笔
        mLinePaint = new Paint();
        mLinePaint.setColor(this.lineColor);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(mCellRadius / 8);

        mNormalPaint = new Paint();
        mNormalPaint.setColor(NORMAL_INNER_COLOR);
        mNormalPaint.setStrokeWidth(mLineWidth);
        mNormalPaint.setStyle(Paint.Style.STROKE);
        mNormalPaint.setAntiAlias(true);
        //初始化被选中的画笔
        mSelectedPaint = new Paint();
        mSelectedPaint.setColor(Color.CYAN);
        mSelectedPaint.setStrokeWidth(mLineWidth);
        mSelectedPaint.setStyle(Paint.Style.STROKE);
        mSelectedPaint.setAntiAlias(true);

        mErrorPaint = mSelectedPaint;

        CellBean cell;
        float x;
        float y;
        //初始化每个九宫格对象
        for (int i = 0; i < mCells.length; i++) {
            x = mSpace * (i % 3 + 1) + mCellWidth * (i % 3) + mCellRadius;
            y = mSpace * (i / 3 + 1) + mCellWidth * (i / 3) + mCellRadius;
            cell = new CellBean(x, y);
            mCells[i] = cell;
        }

        mIsInit = true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = getRealSize(widthMeasureSpec);
        mHeight = getRealSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    private int getRealSize(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.AT_MOST || mode == MeasureSpec.UNSPECIFIED) {
            return mCellWidth * 3 + mSpace * 4;
        } else {
            return size;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!mIsInit) {
            init();
        }
        drawCell(canvas);
        drawLine(canvas);
    }


    private void drawCell(Canvas canvas) {
        /*for (int i = 0; i < mCells.length; i++) {
            canvas.drawCircle(mCells[i].getCenterX(), mCells[i].getCenterY(), mCellRadius, mCells[i].isSelected() ? mSelectedPaint : mNormalPaint);
        }*/
        for (CellBean cell : mCells) {
            canvas.drawCircle(cell.getCenterX(), cell.getCenterY(), mCellRadius, cell.isSelected() ? mSelectedPaint : mNormalPaint);
        }
    }

    private void drawLine(Canvas canvas) {
        if (mSelectedCells.size() == 0) {
            return;
        }

        CellBean cell = mCells[mSelectedCells.get(0)];
        CellBean nextCell;
        if (mSelectedCells.size() > 1) {
            for (int i = 1; i < mSelectedCells.size(); i++) {//从1开始
                nextCell = mCells[mSelectedCells.get(i)];
                canvas.drawLine(cell.getCenterX(), cell.getCenterY(), nextCell.getCenterX(), nextCell.getCenterY(), mLinePaint);
                cell = nextCell;
            }

        }
        if (!mFinish) {
            canvas.drawLine(cell.getCenterX(), cell.getCenterY(), mCurrentX, mCurrentY, mLinePaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //TODO 可以点击选择
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mFinish) {//清空所有信息，重来
                    for (CellBean cell : mCells) {
                        cell.setSelected(false);
                    }
                    mFinish = false;
                    mSelectedCells.clear();
                    invalidate();
                    return false;
                }
                handleDownEvent(event);
                break;
            case MotionEvent.ACTION_UP:
                mFinish = true;
                Toast.makeText(getContext(), mSelectedCells.toString(), Toast.LENGTH_SHORT).show();
                break;
            case MotionEvent.ACTION_MOVE:
                mFinish = false;
                handleMoveEvent(event);
                break;
        }
        return true;
    }

    private void handleDownEvent(MotionEvent event) {
        int index = findCellIndex(event.getX(), event.getY());
        if (index != -1) {
            mCells[index].setSelected(true);
            mSelectedCells.add((Integer) index);
        }
        mCurrentX = event.getX();
        mCurrentY = event.getY();
        invalidate();
    }

    private void handleMoveEvent(MotionEvent event) {
        int index = findCellIndex(event.getX(), event.getY());
        if (index != -1) {
            mCells[index].setSelected(true);
            mSelectedCells.add((Integer) index);
        }
        mCurrentX = event.getX();
        mCurrentY = event.getY();
        invalidate();
    }

    private int findCellIndex(float x, float y) {
        //和电影院的计算位置不同的原因是因为这个是圆的，电影院那个是方的
        float cellX, cellY;
        int result = -1;
        for (int i = 0; i < mCells.length; i++) {
            if (mCells[i].isSelected()) {
                continue;
            }
            cellX = mCells[i].getCenterX();
            cellY = mCells[i].getCenterY();

            float tempX = cellX - x;
            float tempY = cellY - y;
            float distance = (float) Math.sqrt(tempX * tempX + tempY * tempY);
            if (distance < mCellRadius) {
                result = i;
                break;
            }
        }
        return result;
    }

    class CellBean {

        private float mCenterX;
        private float mCenterY;
        private boolean mSelected;

        public CellBean() {
        }

        public CellBean(float mCenterX, float mCenterY) {

            this.mCenterX = mCenterX;
            this.mCenterY = mCenterY;
        }

        public float getCenterX() {
            return mCenterX;
        }

        public void setCenterX(float mCenterX) {
            this.mCenterX = mCenterX;
        }

        public float getCenterY() {
            return mCenterY;
        }

        public void setCenterY(float mCenterY) {
            this.mCenterY = mCenterY;
        }

        public boolean isSelected() {
            return mSelected;
        }

        public void setSelected(boolean mSelected) {
            this.mSelected = mSelected;
        }
    }

}
