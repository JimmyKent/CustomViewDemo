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
		findViewById(R.id.btn_flow_view_recycleview).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, FlowViewRecycleAty.class);
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
		findViewById(R.id.btn_aige_2).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ColorFilterAty.class);
				startActivity(intent);
			}
		});

		findViewById(R.id.btn_aige_2_2).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, AvoidXAty.class);
				startActivity(intent);
			}
		});
		findViewById(R.id.btn_porterduff).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, PorterDuffAty.class);
				startActivity(intent);
			}
		});
		findViewById(R.id.btn_porterduff2).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, PorterDuffAty2.class);
				startActivity(intent);
			}
		});
		findViewById(R.id.btn_eraser).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, EraserAty.class);
				startActivity(intent);
			}
		});
		findViewById(R.id.btn_multicircle).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, MultiCircleAty.class);
				startActivity(intent);
			}
		});
	}
}
