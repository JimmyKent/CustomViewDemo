package com.jimmy.customviewdemo.parallax;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
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
public class ParallaxView extends RelativeLayout {

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

    private TextView mBalanceTV;
    private Button mChargeBtn;
    private TextView mRecordTV;
    private OnClickListener mChargeOnClickListener;

    private int mOffset;//跟随滚动的距离
    private String mBalanceStr = "";
    private float mBalanceTextSize = MAX_BALANCE_TEXT_SIZE;
    private float mChargeTextSize = MAX_CHARGE_TEXT_SIZE;


    public ParallaxView(Context context) {
        this(context, null);
    }

    public ParallaxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void setOffset(int offset) {
        mOffset = offset;
        KLog.e("haha", "mOffset:" + mOffset);
        if (mOffset <= EFFECTIVE_MAX_OFFSET) {
            KLog.e("haha", "refresh");
            //XXX
            measureBalanceText();
            //refresh();
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

    @SuppressWarnings("deprecation")
    private void initView(Context context) {
        mBalanceTV = new TextView(context);
        mBalanceTV.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        mBalanceTV.setTextSize(mBalanceTextSize);
        mBalanceTV.setText(mBalanceStr);
        mBalanceTV.setTextColor(getResources().getColor(R.color.black));

        mChargeBtn = new Button(context);
        mChargeBtn.setLayoutParams(new LayoutParams(MAX_CHARGE_WIDTH, MAX_CHARGE_HEIGHT));
        mChargeBtn.setTextSize(mChargeTextSize);
        mChargeBtn.setText(STRING_CHARGE);
        mChargeBtn.setTextColor(getResources().getColor(R.color.colorAccent));
        mChargeBtn.setOnClickListener(mChargeOnClickListener);

        mRecordTV = new TextView(context);
        mRecordTV.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        mRecordTV.setTextSize(DensityUtils.dip2px(14));
        mRecordTV.setText(STRING_RECORD);
        mRecordTV.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        addView(mBalanceTV);
        //addView(mChargeBtn);
        //addView(mRecordTV);
       /* LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.view_parallax, this);
        mBalanceTV = (TextView) findViewById(R.id.tv_balance);
        mBalanceTV.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        mBalanceTV.setTextSize(mBalanceTextSize);
        mBalanceTV.setText("12321321");
        mBalanceTV.setTextColor(getResources().getColor(R.color.black));*/
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureBalanceText();
        //measureChargeBtn();
        //measureRecordText();
        KLog.e("haha", "getHeightByOffset():" + getHeightByOffset());
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), getHeightByOffset());
    }

    private void measureBalanceText() {
        ((TextView) getChildAt(0)).setTextSize(getBalanceTextSize());
    }

    private float getBalanceTextSize() {
        mBalanceTextSize = MAX_BALANCE_TEXT_SIZE - mOffset / EFFECTIVE_MAX_OFFSET * (MAX_BALANCE_TEXT_SIZE - MIN_BALANCE_TEXT_SIZE);
        KLog.e("haha", "mBalanceTextSize:" + mBalanceTextSize);
        return mBalanceTextSize;
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
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);//如果不通过getChild拿到View，不会刷新
        KLog.e("haha", "onLayout:");
        layoutBalanceTV(b - t);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //XXX 应该在这里动态改变的


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
        invalidate();
    }

    private int getHeightByOffset() {
        return MAX_HEIGHT - mOffset;
    }

}
