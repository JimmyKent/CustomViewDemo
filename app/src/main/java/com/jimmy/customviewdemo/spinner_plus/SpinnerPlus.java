package com.jimmy.customviewdemo.spinner_plus;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

/**
 * Created by jinguochong on 15/03/2018.
 * 可以删除列表的spinner
 * 可以自定义item的spinner
 */

public class SpinnerPlus extends RelativeLayout {

    private PopupWindow ppw;

    public SpinnerPlus(Context context) {
        this(context, null);
    }

    public SpinnerPlus(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpinnerPlus(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }




}
