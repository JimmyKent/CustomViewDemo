package com.jimmy.customviewdemo.parallax;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jimmy.customviewdemo.R;
import com.jimmy.customviewdemo.utils.DensityUtils;

public class ParallaxView2 extends RelativeLayout {

    public static final int MAX_HEIGHT = (int) DensityUtils.dip2px(300);//px
    public static final int MIN_HEIGHT = (int) DensityUtils.dip2px(100);
    public static final int EFFECTIVE_MAX_OFFSET = MAX_HEIGHT - MIN_HEIGHT;
    //充值按钮的宽高
    private static final int MAX_CHARGE_WIDTH = (int) DensityUtils.dip2px(300);
    private static final int MAX_CHARGE_HEIGHT = (int) DensityUtils.dip2px(100);
    //余额文字的最大最小值
    private static final float MAX_BALANCE_TEXT_SIZE = DensityUtils.dip2px(30);
    private static final float MIN_BALANCE_TEXT_SIZE = DensityUtils.dip2px(14);
    private static final float MAX_CHARGE_TEXT_SIZE = DensityUtils.dip2px(16);
    private static final float MIN_CHARGE_TEXT_SIZE = DensityUtils.dip2px(12);

    //
    private static final int MAX_BALANCE_MARGIN_TOP = (int) DensityUtils.dip2px(80);
    private static final int MIN_BALANCE_MARGIN_TOP = 10;//px XXX 这个也是算出来的
    private static final int MIN_BALANCE_MARGIN_LEFT = 10;//px

    //charge btn
    private static final int MAX_CHARGE_MARGIN_TOP = (int) DensityUtils.dip2px(150);

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
        mRootView = findViewById(R.id.view_root);
        mBalanceTV = (TextView) findViewById(R.id.tv_balance);
        mChargeBtn = (Button) findViewById(R.id.btn_charge);
        mRecordTV = (TextView) findViewById(R.id.tv_record);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //XXX 横竖屏切换会触发这里，然后重新计算位置
        // Rect = Xp * Yp = Xl * Yl
        //mark Yp and Yl change them
        //here judge is screen direction change
        //if yes, need change two things : chargeBtn size and rootView size
        Log.e("haha", "onSizeChanged");
        refresh();
    }

    private void measureChargeBtn() {
        mChargeBtn.setTextSize(getChargeTextSize());
    }


    private void measureRecordText() {

    }


    private void refresh() {
        refreshRootView();
        refreshBalanceTV();
        refreshChargeBtn();
        refreshRecordTV();
    }


    private void refreshRootView() {
        ViewGroup.LayoutParams lp = mRootView.getLayoutParams();
        lp.height = getHeightByOffset();
    }

    private int getHeightByOffset() {
        return MAX_HEIGHT - mOffset;
    }

    private void refreshBalanceTV() {
        scaleBalance();
        translationBalance();
    }

    private void scaleBalance() {
        mBalanceTV.setTextSize(getBalanceTextSize());
    }

    private float getBalanceTextSize() {
        mBalanceTextSize = MAX_BALANCE_TEXT_SIZE - mOffset * (MAX_BALANCE_TEXT_SIZE - MIN_BALANCE_TEXT_SIZE) / EFFECTIVE_MAX_OFFSET;
        return mBalanceTextSize;
    }

    private void translationBalance() {
        final int currentHalfMarginLeft = (getMeasuredWidth() - mBalanceTV.getMeasuredWidth()) / 2;//XXX name error
        int cl = currentHalfMarginLeft - mOffset * (currentHalfMarginLeft - MIN_BALANCE_MARGIN_LEFT) / EFFECTIVE_MAX_OFFSET;
        int ct = MAX_BALANCE_MARGIN_TOP - mOffset * (MAX_BALANCE_MARGIN_TOP - MIN_BALANCE_MARGIN_TOP) / EFFECTIVE_MAX_OFFSET;
        //int cr = cl + mBalanceTV.getMeasuredWidth();
        //int cb = ct + mBalanceTV.getMeasuredHeight();
        //Log.e("haha", "cl:" + cl + "，ct:" + ct + "，cr:" + cr + "，cb:" + cb);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mBalanceTV.getLayoutParams();
        lp.setMargins(cl, ct, 0, 0);
    }

    private void refreshChargeBtn() {
        scaleCharge();
        translationCharge();
        //MAX_CHARGE_MARGIN_TOP
    }

    private void scaleCharge() {
        changeChargeTextSize();
        changeChargeBtnSize();
    }

    private void changeChargeTextSize() {
        mChargeBtn.setTextSize(getChargeTextSize());
    }

    private float getChargeTextSize() {
        mChargeTextSize = MAX_CHARGE_TEXT_SIZE - mOffset / EFFECTIVE_MAX_OFFSET * (MAX_CHARGE_TEXT_SIZE - MIN_CHARGE_TEXT_SIZE);
        return mChargeTextSize;
    }

    private void changeChargeBtnSize() {

    }

    private void translationCharge() {
        final int currentMarginLeft = (getMeasuredWidth() - mChargeBtn.getMeasuredWidth()) / 2;//XXX
        int cr = currentMarginLeft - mOffset * (currentMarginLeft - MIN_BALANCE_MARGIN_LEFT) / EFFECTIVE_MAX_OFFSET;
        int ct = MAX_BALANCE_MARGIN_TOP - mOffset * (MAX_BALANCE_MARGIN_TOP - MIN_BALANCE_MARGIN_TOP) / EFFECTIVE_MAX_OFFSET;
        int cl = mRootView.getMeasuredWidth() - mChargeBtn.getMeasuredWidth() - cr;
        //int cr = cl + mBalanceTV.getMeasuredWidth();
        //int cb = ct + mBalanceTV.getMeasuredHeight();
        //Log.e("haha", "cl:" + cl + "，ct:" + ct + "，cr:" + cr + "，cb:" + cb);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mChargeBtn.getLayoutParams();
        lp.setMargins(cl, ct, 0, 0);
    }

    private void refreshRecordTV() {
        //mRecordTV
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
