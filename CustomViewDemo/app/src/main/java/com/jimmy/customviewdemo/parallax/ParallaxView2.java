package com.jimmy.customviewdemo.parallax;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jimmy.customviewdemo.R;
import com.jimmy.customviewdemo.utils.DensityUtils;
import com.jimmy.log.KLog;

/**
 * Created by jinguochong on 16-10-28.
 * maxHeight
 * minHeight
 * 属性动画从maxHeight ～ minHeight之间
 * <p>
 * scale:
 * offset   0    - infinite
 * height   max  - min
 * <p>
 * animator:
 * balance text translation from center to leftTop
 * charge btn translation from center to rightTop and scale 1 - 0.5
 * record text translation -x
 * <p>
 * <p>
 * //多写一个和需要悬浮的部分一模一样的layout，先把浮动区域的可见性设置为gone。当浮动区域滑动到顶部的时候，就把浮动区域B的可见性设置为VISIBLE。这样看起来就像悬浮在顶部不动了。
 */
public class ParallaxView2 extends RelativeLayout {

    private static final int MAX_HEIGHT = (int) DensityUtils.dip2px(300);//px
    private static final int MIN_HEIGHT = (int) DensityUtils.dip2px(100);
    private static final int EFFECTIVE_MAX_OFFSET = MAX_HEIGHT - MIN_HEIGHT;
    //充值按钮的宽高
    private static final int MAX_CHARGE_WIDTH = (int) DensityUtils.dip2px(300);
    private static final int MAX_CHARGE_HEIGHT = (int) DensityUtils.dip2px(100);
    //文字的最大最小值
    private static final float MAX_BALANCE_TEXT_SIZE = DensityUtils.dip2px(30);
    private static final float MIN_BALANCE_TEXT_SIZE = DensityUtils.dip2px(14);
    private static final float MAX_CHARGE_TEXT_SIZE = DensityUtils.dip2px(30);
    private static final float MIN_CHARGE_TEXT_SIZE = DensityUtils.dip2px(14);

    private static final String STRING_RECORD = "消费记录";
    private static final String STRING_CHARGE = "充值";

    private View mRootView;
    private TextView mBalanceTV;
    private Button mChargeBtn;
    private TextView mRecordTV;
    private OnClickListener mChargeOnClickListener;

    private int mOffset;//跟随滚动的距离
    private String mBalanceStr = "";
    private float mBalanceTextSize = MAX_BALANCE_TEXT_SIZE;
    private float mChargeTextSize = MAX_CHARGE_TEXT_SIZE;


    public ParallaxView2(Context context) {
        this(context, null);
    }

    public ParallaxView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void setOffset(int offset) {
        mOffset = offset;
        if (mOffset <= EFFECTIVE_MAX_OFFSET) {
            changeBalanceTextSize();
            translationBalance();
            refresh();
        }
    }

    public void setBalance(String balance) {
        mBalanceStr = balance;
        mBalanceTV.setText(mBalanceStr);
        refresh();
    }

    public void setChargeClick(OnClickListener chargeOnClickListener) {
        mChargeOnClickListener = chargeOnClickListener;
    }

    private void initView(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.view_parallax, this);
        mBalanceTV = (TextView) findViewById(R.id.tv_balance);
        mBalanceTV.setTextSize(getBalanceTextSize());
        mRootView = findViewById(R.id.view_root);
    }

    private void changeBalanceTextSize() {
        mBalanceTV.setTextSize(getBalanceTextSize());
    }

    private float getBalanceTextSize() {
        mBalanceTextSize = MAX_BALANCE_TEXT_SIZE - mOffset * (MAX_BALANCE_TEXT_SIZE - MIN_BALANCE_TEXT_SIZE) / EFFECTIVE_MAX_OFFSET;

        return mBalanceTextSize;
    }

    private void translationBalance() { //XXX
        //mBalanceTextSize
    }

    private void measureChargeBtn() {
        mChargeBtn.setTextSize(getChargeTextSize());
    }

    private float getChargeTextSize() {
        mChargeTextSize = MAX_BALANCE_TEXT_SIZE - mOffset / EFFECTIVE_MAX_OFFSET * (MAX_BALANCE_TEXT_SIZE - MIN_CHARGE_TEXT_SIZE);
        return mChargeTextSize;
    }

    private void measureRecordText() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //XXX 应该在这里动态改变的
        Log.e("haha", "onDraw:");//XXX 不触发
        ViewGroup.LayoutParams lp = mRootView.getLayoutParams();
        lp.height = getHeightByOffset();

    }

    private void layoutBalanceTV(int parentHeight) {


        final int initMarginTop = (int) DensityUtils.dip2px(80);
        final int initMarginLeft = (getWidth() - mBalanceTV.getMeasuredWidth()) / 2;
        final int endMarginTop = 10;//px XXX 这个也是算出来的
        final int endMarginLeft = 10;//px

        int cl = initMarginLeft - mOffset / EFFECTIVE_MAX_OFFSET * (initMarginLeft - endMarginLeft);
        int ct = initMarginTop - mOffset / EFFECTIVE_MAX_OFFSET * (initMarginTop - endMarginTop);
        int cr = cl + mBalanceTV.getMeasuredWidth();
        int cb = ct + mBalanceTV.getMeasuredHeight();
        KLog.e("haha", "cl:" + cl + "，ct:" + ct + "，cr:" + cr + "，cb:" + cb);
        mBalanceTV.layout(cl, ct, cr, cb);
    }


    private void refresh() {
        ViewGroup.LayoutParams lp = mRootView.getLayoutParams();
        lp.height = getHeightByOffset();
        invalidate();
        requestLayout();
    }

    private int getHeightByOffset() {
        return MAX_HEIGHT - mOffset;
    }

    /*public static interface OffsetChangeListener{
        void offsetChange(int offset);
    }*/
}
