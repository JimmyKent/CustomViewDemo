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

import java.util.LinkedList;
import java.util.List;


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

    private List<Integer> mSelectedCells = new LinkedList<Integer>();

    private int mPreIndex = -1, mCurIndex = -1;


    public LockView(Context context) {
        this(context, null);
    }

    public LockView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }
    //TODO
    /*public void setPointNum(int number){
        mCells = new CellBean[number];
    }*/


    private void init() {//mIsInit是内存共享，所以这里不用加锁
        if (mCells == null) {
            return;
        }
        Log.e("LockView", "mWidth:" + mWidth);
        Log.e("LockView", "mHeight:" + mHeight);
        //点的宽度
        mCellWidth = (mWidth == 0 ? DEFAULT_CELL_WIDTH : mWidth / 5);
        //点的半径
        mCellRadius = mCellWidth >> 1;
        //线条的宽度
        mLineWidth = (mWidth == 0 ? DEFAULT_CELL_STROKE_WIDTH : mCellWidth / 5);
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
        //设置标志位，如果给了精确值，根据精确值重新分配 DEFAULT_CELL_WIDTH等值

        mWidth = getRealSize(widthMeasureSpec);
        mHeight = getRealSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    private int getRealSize(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.AT_MOST || mode == MeasureSpec.UNSPECIFIED) {
            //TODO 这里好像有问题
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
                    mFinish = false;
                    for (CellBean cell : mCells) {
                        cell.setSelected(false);
                    }
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
                //TODO 这里可以控制重绘次数，比如在位置在cellbean的范围内才重绘
                mFinish = false;
                handleMoveEvent(event);
                break;
        }
        return true;
    }

    private void handleDownEvent(MotionEvent event) {
        int index = findCellIndex(event.getX(), event.getY());
        //TODO 这里如果起始不是点，应该也要可以才行
        if (index == -1) {
            return;
        }
        if (index != -1) {
            mCurIndex = index;
            mCells[index].setSelected(true);
            mSelectedCells.add((Integer) index);
        }
        mCurrentX = event.getX();
        mCurrentY = event.getY();
        invalidate();
    }

    private void handleMoveEvent(MotionEvent event) {
        mCurrentX = event.getX();
        mCurrentY = event.getY();
        int index = findCellIndex(mCurrentX, mCurrentY);
        //TODO 这里如果出现1，3等情况
        if (index != -1) {
            mPreIndex = mCurIndex;
            mCurIndex = index;
            if (mPreIndex == -1) {//第一个点
                mCells[index].setSelected(true);
                mSelectedCells.add((Integer) index);
            } else {
                switch (10 * mPreIndex + mCurIndex) {
                    case 2:
                    case 6:
                    case 8:
                    case 17:
                    case 20:
                    case 26:
                    case 28:
                    case 35:
                    case 53:
                    case 60:
                    case 62:
                    case 68:
                    case 71:
                    case 80:
                    case 82:
                    case 86:
                        setCrossCellBean();
                        break;
                    default:
                        mCells[index].setSelected(true);
                        mSelectedCells.add((Integer) index);
                        break;
                }
                //TODO 两个变量是否可以改switch语句
                /*if (mPreIndex == 0 && mCurIndex == 2) {
                    setCrossCellBean();
                } else if (mPreIndex == 0 && mCurIndex == 6) {
                    setCrossCellBean();
                } else if (mPreIndex == 0 && mCurIndex == 8) {
                    setCrossCellBean();
                } else if (mPreIndex == 1 && mCurIndex == 7) {
                    setCrossCellBean();
                } else if (mPreIndex == 2 && mCurIndex == 0) {
                    setCrossCellBean();
                } else if (mPreIndex == 2 && mCurIndex == 6) {
                    setCrossCellBean();
                } else if (mPreIndex == 2 && mCurIndex == 8) {
                    setCrossCellBean();
                } else if (mPreIndex == 3 && mCurIndex == 5) {
                    setCrossCellBean();
                } else if (mPreIndex == 5 && mCurIndex == 3) {
                    setCrossCellBean();
                } else if (mPreIndex == 6 && mCurIndex == 0) {
                    setCrossCellBean();
                } else if (mPreIndex == 6 && mCurIndex == 2) {
                    setCrossCellBean();
                } else if (mPreIndex == 6 && mCurIndex == 8) {
                    setCrossCellBean();
                } else if (mPreIndex == 7 && mCurIndex == 1) {
                    setCrossCellBean();
                } else if (mPreIndex == 8 && mCurIndex == 0) {
                    setCrossCellBean();
                } else if (mPreIndex == 8 && mCurIndex == 2) {
                    setCrossCellBean();
                } else if (mPreIndex == 8 && mCurIndex == 6) {
                    setCrossCellBean();
                } else {
                    mCells[index].setSelected(true);
                    mSelectedCells.add((Integer) index);
                }*/
            }
        }
        invalidate();
    }

    private void setCrossCellBean() {
        mCells[(mPreIndex + mCurIndex) / 2].setSelected(true);
        mCells[mCurIndex].setSelected(true);
        mSelectedCells.add((Integer) (mPreIndex + mCurIndex) / 2);
        mSelectedCells.add((Integer) mCurIndex);
        mPreIndex = (mPreIndex + mCurIndex) / 2;
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
