package com.jimmy.customviewdemo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.jimmy.customviewdemo.R;
import com.jimmy.customviewdemo.widget4.MultiCircleView2;

public class MultiCircleAty extends Activity {

	private MultiCircleView2 mMultiCircleView;
	private int i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_multicircle);
		mMultiCircleView = (MultiCircleView2) findViewById(R.id.multi_circle);
		findViewById(R.id.btn_change_text).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mMultiCircleView.setCenterText("Jimmy"+(i++));
			}
		});
	}
}
