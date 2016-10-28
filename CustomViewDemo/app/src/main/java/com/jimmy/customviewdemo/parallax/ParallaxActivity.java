package com.jimmy.customviewdemo.parallax;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.jimmy.customviewdemo.R;

public class ParallaxActivity extends AppCompatActivity {

    private ParallaxView2 mParallaxView;
    private NestedScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallax);
        mParallaxView = (ParallaxView2) findViewById(R.id.parallaxView);
        mParallaxView.setBalance("￥300");
        scrollView = (NestedScrollView) findViewById(R.id.screen_view);

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, final int scrollY, int oldScrollX, int oldScrollY) {
                Log.e("haha", "scrollY:" + scrollY + ",oldScrollY:" + oldScrollY);

                //mParallaxView.setFocusable(false);
                //scrollView.setFocusable(false);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mParallaxView.setOffset(scrollY);
                    }
                }, 200);
            }
        });

    }


    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });
}
