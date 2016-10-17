package com.jimmy.customviewdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jimmy.customviewdemo.andpermission.AndPermissionAty;
import com.jimmy.customviewdemo.mvp.MvpAty;
import com.jimmy.customviewdemo.screenswitch.ScreenSwitchAty;
import com.jimmy.customviewdemo.ui.AidlAty;
import com.jimmy.customviewdemo.ui.AvoidXAty;
import com.jimmy.customviewdemo.ui.ColorFilterAty;
import com.jimmy.customviewdemo.ui.ConstraintLayoutAty;
import com.jimmy.customviewdemo.ui.EraserAty;
import com.jimmy.customviewdemo.ui.FlowViewAty;
import com.jimmy.customviewdemo.ui.FlowViewRecycleAty;
import com.jimmy.customviewdemo.ui.GifAty;
import com.jimmy.customviewdemo.ui.LargeImgAty;
import com.jimmy.customviewdemo.ui.MeasureOnceAty;
import com.jimmy.customviewdemo.ui.MovingCircleAty;
import com.jimmy.customviewdemo.ui.MultiCircleAty;
import com.jimmy.customviewdemo.ui.PorterDuffAty;
import com.jimmy.customviewdemo.ui.PorterDuffAty2;
import com.jimmy.customviewdemo.ui.TextAty;
import com.jimmy.customviewdemo.ui.TouchEventAty;
import com.jimmy.customviewdemo.ui.ViewLogAty;
import com.jimmy.customviewdemo.ui.lockview.LockAty;
import com.jimmy.log.KLog;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String abiStr = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            StringBuffer sb = new StringBuffer();
            for (String abi : Build.SUPPORTED_ABIS) {
                sb.append(abi + ",");
            }
            abiStr = "CPU_ABI:" + Build.CPU_ABI + ",CPU_ABI2:" + Build.CPU_ABI2 + ",SUPPORTED_ABIS:" +sb;
        }
        Toast.makeText(this, abiStr, Toast.LENGTH_SHORT).show();
        KLog.e("jimmy", abiStr);
        findViewById(R.id.btn_large_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log();
                Intent intent = new Intent(MainActivity.this, LargeImgAty.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_measure_once).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MeasureOnceAty.class);
                startActivity(intent);

                overridePendingTransition(R.anim.flip_vertical_in,
                        R.anim.flip_vertical_out);

            }
        });

        findViewById(R.id.btn_flow_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FlowViewAty.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_flow_view_recycleview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FlowViewRecycleAty.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_aige_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MovingCircleAty.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_aige_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ColorFilterAty.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_aige_2_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AvoidXAty.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_porterduff).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PorterDuffAty.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_porterduff2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PorterDuffAty2.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_eraser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EraserAty.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_multicircle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MultiCircleAty.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_touch_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TouchEventAty.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_view_log).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewLogAty.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_mvp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MvpAty.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_gif).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GifAty.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_lock_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MovieSelectSeatActivity1
                Intent intent = new Intent(MainActivity.this, LockAty.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_anim_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ConstraintLayoutAty.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_aidl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AidlAty.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TextAty.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_permission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AndPermissionAty.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_screen_switch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScreenSwitchAty.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        KLog.e("jimmy","MainAty onConfigurationChanged");
    }

    private void log() {
        KLog.v();
        KLog.d();
        KLog.i();
        KLog.w();
        KLog.e();
        KLog.a();
    }
}
