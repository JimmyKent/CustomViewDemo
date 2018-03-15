package com.jimmy.customviewdemo.ui;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jimmy.customviewdemo.R;
import com.jimmy.customviewdemo.widget.FlowLayout;
import com.jimmy.customviewdemo.widget.PorterDuffView2;

public class PorterDuffAty2 extends Activity{
	
	private FlowLayout mImgFlowLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_flow_view);
		mImgFlowLayout = (FlowLayout) findViewById(R.id.flowlayout_img);
		initData(PorterDuff.Mode.ADD);
		initData(PorterDuff.Mode.CLEAR);
		initData(PorterDuff.Mode.DARKEN);
		initData(PorterDuff.Mode.DST);
		initData(PorterDuff.Mode.DST_ATOP);
		initData(PorterDuff.Mode.DST_IN);
		initData(PorterDuff.Mode.DST_OUT);
		initData(PorterDuff.Mode.DST_OVER);
		initData(PorterDuff.Mode.LIGHTEN);
		initData(PorterDuff.Mode.MULTIPLY);
		initData(PorterDuff.Mode.OVERLAY);
		initData(PorterDuff.Mode.SCREEN);
		initData(PorterDuff.Mode.SRC);
		initData(PorterDuff.Mode.SRC_ATOP);
		initData(PorterDuff.Mode.SRC_IN);
		initData(PorterDuff.Mode.SRC_OUT);
		initData(PorterDuff.Mode.SRC_OVER);
		initData(PorterDuff.Mode.XOR);
		initData(PorterDuff.Mode.XOR);
		initData(PorterDuff.Mode.XOR);
		initData(PorterDuff.Mode.XOR);
		initData(PorterDuff.Mode.XOR);
		
	}

	
	private void initData(PorterDuff.Mode mode) {
		View view = LayoutInflater.from(this).inflate(R.layout.flowlayout_item_porterduff2, mImgFlowLayout, false);
		TextView textView = (TextView) view.findViewById(R.id.textview);
		PorterDuffView2 pdv = (PorterDuffView2) view.findViewById(R.id.porterduffview);
		pdv.setMode(mode);
		textView.setText(mode.toString());
		mImgFlowLayout.addView(view);
	}
	
	
	
}
