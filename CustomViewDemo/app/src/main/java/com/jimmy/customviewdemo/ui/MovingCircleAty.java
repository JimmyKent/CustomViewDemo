package com.jimmy.customviewdemo.ui;

import android.app.Activity;
import android.os.Bundle;

import com.jimmy.customviewdemo.R;
import com.jimmy.customviewdemo.widget.MovingCircleView;

public class MovingCircleAty extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_moving_circle);
		MovingCircleView circle = (MovingCircleView) findViewById(R.id.moving_circle);
		//new Thread(circle).start();

	}


}
