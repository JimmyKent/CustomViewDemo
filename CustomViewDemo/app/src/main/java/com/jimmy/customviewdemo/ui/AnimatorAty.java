package com.jimmy.customviewdemo.ui;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.PathInterpolator;
import android.widget.Button;

import com.jimmy.customviewdemo.R;

public class AnimatorAty extends Activity {

    private Button mBtn;

    private WindowManager mWinManager;
    private View mPopLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_animator);

        /*mBtn = (Button) findViewById(R.id.button);


        ObjectAnimator animator = ObjectAnimator.ofFloat(mBtn, "translationY",
                0, 1200);
        animator.setDuration(2500);//动画时间
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            animator.setInterpolator(new PathInterpolator(0, 0, 0.2f, 1));//动画插值
        }
        animator.setStartDelay(1000);
        animator.start();


        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mBtn, "translationY",
                1200, 0);
        animator2.setDuration(2500);//动画时间
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            animator2.setInterpolator(new PathInterpolator(0, 0, 0.2f, 1));//动画插值
        }
        animator2.setStartDelay(3500);
        animator2.start();*/


        mWinManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mPopLayout = LayoutInflater.from(this).inflate(R.layout.flowlayout_item_text, null);

        int flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                20 * 3,
                WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL,
                flags,
                PixelFormat.RGBA_8888);

        params.gravity = Gravity.TOP;
        params.x = 0;
        params.y = -10;
        mWinManager.addView(mPopLayout, params);


    }
}
