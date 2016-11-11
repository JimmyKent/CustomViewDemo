package com.jimmy.customviewdemo.parallax;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jimmy.customviewdemo.R;
import com.jimmy.customviewdemo.utils.DensityUtils;

/**
 * Created by jinguochong on 16-11-1.
 * layoutDependsOn方法在每次layout发生变化时都会调用，
 * 我们需要在dependency控件发生变化时返回True，
 * 在我们的例子中是用户在屏幕上滑动时（因为Toolbar发生了移动），然后我们需要让child做出相应的反应。
 * <p>
 * 层级原因这个不会显示：
 * 解决一：ToolBar设相同的文字和按钮：通过visible和invisible的方式；
 * 二： FloatingActionButton
 */
public class BalanceBehavior extends CoordinatorLayout.Behavior<TextView> {

    private final int density = DensityUtils.getDensity();

    private int MAX_BALANCE_TEXT_SIZE = 28;//sp
    private int MIN_BALANCE_TEXT_SIZE = 18;//sp
    private int EFFECTIVE_MAX_OFFSET = 52 * density;//px
    private int MAX_BALANCE_MARGIN_LEFT;
    private int MIN_BALANCE_MARGIN_LEFT = 24 * density;//dp
    private int START_BALANCE_MARGIN_TOP = 20 * density;
    private int END_BALANCE_MARGIN_TOP = 21 * density;


    private final static String TAG = "behavior";
    private Context mContext;

    private float mBalanceTextSize;

    private int mOffset;


    private TextView mBalanceTV;

    public BalanceBehavior(Context context, AttributeSet attrs) {
        mContext = context;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {
        if (MAX_BALANCE_MARGIN_LEFT == 0) {
            MAX_BALANCE_MARGIN_LEFT = (parent.getWidth() - child.getWidth()) / 2;
            Log.e("jgc", "MAX_BALANCE_MARGIN_LEFT:" + MAX_BALANCE_MARGIN_LEFT);//484
            Log.e("jgc", "parent.getWidth():" + parent.getWidth());//1080
            Log.e("jgc", "child.getWidth():" + child.getWidth());//111
        }
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency) {
        mBalanceTV = child;
        mOffset = (int) -dependency.getY();

        //Log.e("jgc", "mOffset:" + mOffset);
        refreshBalanceTV();
        return true;
    }

    private void refreshBalanceTV() {
        scaleBalance();
        translationBalance();
    }

    private void scaleBalance() {
        mBalanceTV.setTextSize(/*TypedValue.COMPLEX_UNIT_PX, */getBalanceTextSize());
    }

    private float getBalanceTextSize() {
        mBalanceTextSize = MAX_BALANCE_TEXT_SIZE - mOffset * (MAX_BALANCE_TEXT_SIZE - MIN_BALANCE_TEXT_SIZE) / EFFECTIVE_MAX_OFFSET;

        return mBalanceTextSize;
    }

    private void translationBalance() {


        int cl = MAX_BALANCE_MARGIN_LEFT - mOffset * (MAX_BALANCE_MARGIN_LEFT - MIN_BALANCE_MARGIN_LEFT) / EFFECTIVE_MAX_OFFSET;

        int ct = (int) (START_BALANCE_MARGIN_TOP - mOffset * (START_BALANCE_MARGIN_TOP - END_BALANCE_MARGIN_TOP) / EFFECTIVE_MAX_OFFSET - 0.5);
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) mBalanceTV.getLayoutParams();
        lp.setMargins(cl, ct, 0, 0);
        mBalanceTV.setLayoutParams(lp);
        mBalanceTV.setVisibility(View.VISIBLE);
        Log.e("jgc", "cl:" + cl + ", ct:" + ct);
    }

    @Override
    public void onDependentViewRemoved(CoordinatorLayout parent, TextView child, View dependency) {
        //super.onDependentViewRemoved(parent, child, dependency);

    }
}
