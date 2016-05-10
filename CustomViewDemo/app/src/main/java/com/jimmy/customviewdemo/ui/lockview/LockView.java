package com.jimmy.customviewdemo.ui.lockview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.jimmy.customviewdemo.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;


public class LockView extends View {

    private static final int LOCK_VIEW_WIDTH = 420;
    private static final int DEFAULT_CELL_WIDTH = LOCK_VIEW_WIDTH / 7;
    private static final int DEFAULT_CELL_STROKE_WIDTH = DEFAULT_CELL_WIDTH / 30;

    private CellBean[] mCells = new CellBean[9];

    private int mCellWidth;
    private int mCellRadius;
    private int mCellStrokeWidth;
    private int mSpace;

    private Paint mNormalPaint;
    private Paint mSelectedPaint;
    private int mWidth;
    private int mHeight;

    private float mCurrentX;
    private float mCurrentY;
    private boolean mFinish = false;

    private List<Integer> mSelectedCells = new ArrayList<Integer>();


    public LockView(Context context) {
        this(context, null);
    }

    public LockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mCellWidth = DensityUtils.dp2px(getContext(), DEFAULT_CELL_WIDTH);
        mCellRadius = mCellWidth >> 1;
        mCellStrokeWidth = DensityUtils.dp2px(getContext(), DEFAULT_CELL_STROKE_WIDTH);
        mSpace = DensityUtils.dp2px(getContext(), DEFAULT_CELL_WIDTH / 2);
        //初始化画笔
        mNormalPaint = new Paint();
        mNormalPaint.setColor(Color.WHITE);
        mNormalPaint.setStrokeWidth(mCellStrokeWidth);
        mNormalPaint.setStyle(Paint.Style.STROKE);
        mNormalPaint.setAntiAlias(true);
        //初始化被选中的画笔
        mSelectedPaint = new Paint();
        mSelectedPaint.setColor(Color.CYAN);
        mSelectedPaint.setStrokeWidth(mCellStrokeWidth);
        mSelectedPaint.setStyle(Paint.Style.STROKE);
        mSelectedPaint.setAntiAlias(true);

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
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //TODO  设置标志位，如果给了精确值，根据精确值重新分配 DEFAULT_CELL_WIDTH等值
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
                canvas.drawLine(cell.getCenterX(), cell.getCenterY(), nextCell.getCenterX(), nextCell.getCenterY(), mSelectedPaint);
                cell = nextCell;
            }
        }

        if (!mFinish) {
            canvas.drawLine(cell.getCenterX(), cell.getCenterY(), mCurrentX, mCurrentY, mSelectedPaint);
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
                handleMoveEvent(event);
                break;
        }
        return true;
    }

    private void handleDownEvent(MotionEvent event) {
        int index = findCellIndex(event.getX(), event.getY());
        if (index == -1) {
            mCells[index].setSelected(true);
            mSelectedCells.add((Integer) index);
        }
        invalidate();
        mCurrentX = event.getX();
        mCurrentY = event.getY();
    }

    private void handleMoveEvent(MotionEvent event) {
        int index = findCellIndex(event.getX(), event.getY());
        if (index != -1) {
            mCells[index].setSelected(true);
            mSelectedCells.add((Integer) index);
            invalidate();
        }
        mCurrentX = event.getX();
        mCurrentY = event.getY();
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
}
