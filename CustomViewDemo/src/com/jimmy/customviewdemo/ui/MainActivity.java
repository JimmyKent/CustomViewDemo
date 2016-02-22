package com.jimmy.customviewdemo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.jimmy.customviewdemo.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.btn_large_img).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, LargeImgAty.class);
				startActivity(intent);
			}
		});

		findViewById(R.id.btn_measure_once).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, MeasureOnceAty.class);
				startActivity(intent);
			}
		});

		findViewById(R.id.btn_flow_view).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, FlowViewAty.class);
				startActivity(intent);
			}
		});
		findViewById(R.id.btn_aige_1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, MovingCircleAty.class);
				startActivity(intent);
			}
		});
	}
}
