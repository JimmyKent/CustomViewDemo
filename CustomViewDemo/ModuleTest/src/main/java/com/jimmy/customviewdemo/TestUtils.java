package com.jimmy.customviewdemo;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by jinguochong on 16-5-31.
 */
public class TestUtils {
    public static void  showMsg(Context context){
        Toast.makeText(context,"print msg",Toast.LENGTH_LONG).show();
    }
}
