package com.io.plugindemo.hooktest.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.io.plugindemo.R;
import com.io.plugindemo.hooktest.hook.HookHelper;

public class MainActivity extends AppCompatActivity {
    //activity的mInstrumentation在attach初始化

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            // 在这里进行Hook
            //HookHelper.attachContext();

        } catch (Exception e) {
            e.printStackTrace();
        }
        findViewById(R.id.btn_context_hook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TargetActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // 注意这里使用的ApplicationContext 启动的Activity
                // 因为Activity对象的startActivity使用的并不是ContextImpl的mInstrumentation
                // 而是自己的mInstrumentation, 如果你需要这样, 可以自己Hook
                // 比较简单, 直接替换这个Activity的此字段即可.
                getApplicationContext().startActivity(intent);
            }
        });
        findViewById(R.id.btn_aty_hook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Activity activity = HookHelper.getActivity();
                    Log.e("jgc", "activity:" + activity.getClass().toString());
                    HookHelper.hookActivityInstrumentation(activity);
                    Intent intent = new Intent(MainActivity.this,TargetActivity.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*@Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        try {
            // 在这里进行Hook
            HookHelper.attachContext();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


}
