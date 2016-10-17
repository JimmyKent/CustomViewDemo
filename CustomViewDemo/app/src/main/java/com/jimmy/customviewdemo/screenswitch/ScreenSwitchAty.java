package com.jimmy.customviewdemo.screenswitch;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jimmy.customviewdemo.R;
import com.jimmy.log.KLog;

public class ScreenSwitchAty extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_switch);
        findViewById(R.id.btn_a_permission).setOnClickListener(this);
        findViewById(R.id.btn_many_permission).setOnClickListener(this);

        //getSupportFragmentManager().beginTransaction().add(R.id.parent_fragment, new ParentFragment(), "ParentFragment").commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_a_permission:

                break;
            case R.id.btn_many_permission:

                break;
            case R.id.btn_a_permission_toast:
                break;
            case R.id.btn_many_permission_toast:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        KLog.e("jimmy","ScreenSwitchAty onPause");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        KLog.e("jimmy","ScreenSwitchAty onConfigurationChanged");
        //setContentView(R.layout.activity_screen_switch);
    }
}
