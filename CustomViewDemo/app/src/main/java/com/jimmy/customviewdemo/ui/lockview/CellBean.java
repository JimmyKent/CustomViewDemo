package com.jimmy.customviewdemo.ui.lockview;

/**
 * Created by Administrator on 2016/5/10.
 */
public class CellBean {

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
