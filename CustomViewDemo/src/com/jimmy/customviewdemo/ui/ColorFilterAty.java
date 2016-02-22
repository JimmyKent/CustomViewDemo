package com.jimmy.customviewdemo.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jimmy.customviewdemo.R;
import com.jimmy.customviewdemo.utils.BitmapUtils;
import com.jimmy.customviewdemo.utils.PicUtils;
import com.jimmy.customviewdemo.widget.FlowLayout;

public class ColorFilterAty extends Activity {


	private FlowLayout mImgFlowLayout;

	private String mImgPath = "change_pic/a.jpg";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_flow_view);
		mImgFlowLayout = (FlowLayout) findViewById(R.id.flowlayout_img);
		initData();
		initData1();
		initData2();
		initData3();

	}

	private void initData() {
		View view = LayoutInflater.from(this).inflate(R.layout.flowlayout_item_img_text, mImgFlowLayout, false);
		TextView textView = (TextView) view.findViewById(R.id.textview);
		ImageView imgView = (ImageView) view.findViewById(R.id.imageview);
		Bitmap temp = BitmapUtils.getImgFromAssets(this, mImgPath);
		imgView.setImageBitmap(temp);
		textView.setText("原始图片");
		mImgFlowLayout.addView(view);
	}

	private void initData1() {
		View view = LayoutInflater.from(this).inflate(R.layout.flowlayout_item_img_text, mImgFlowLayout, false);
		TextView textView = (TextView) view.findViewById(R.id.textview);
		ImageView imgView = (ImageView) view.findViewById(R.id.imageview);
		Bitmap temp = BitmapUtils.getImgFromAssets(this, mImgPath);
		Bitmap bm = PicUtils.dark(temp);
		temp.recycle();
		imgView.setImageBitmap(bm);
		textView.setText("变灰了");
		mImgFlowLayout.addView(view);
	}

	private void initData2() {
		View view = LayoutInflater.from(this).inflate(R.layout.flowlayout_item_img_text, mImgFlowLayout, false);
		TextView textView = (TextView) view.findViewById(R.id.textview);
		ImageView imgView = (ImageView) view.findViewById(R.id.imageview);
		Bitmap temp = BitmapUtils.getImgFromAssets(this, mImgPath);
		Bitmap bm = PicUtils.change1(temp);
		temp.recycle();
		imgView.setImageBitmap(bm);
		textView.setText("反相效果");
		mImgFlowLayout.addView(view);
	}

	private void initData3() {
		View view = LayoutInflater.from(this).inflate(R.layout.flowlayout_item_img_text, mImgFlowLayout, false);
		TextView textView = (TextView) view.findViewById(R.id.textview);
		ImageView imgView = (ImageView) view.findViewById(R.id.imageview);
		Bitmap temp = BitmapUtils.getImgFromAssets(this, mImgPath);
		Bitmap bm = PicUtils.change2(temp);
		temp.recycle();
		imgView.setImageBitmap(bm);
		textView.setText("红蓝互换");
		mImgFlowLayout.addView(view);
	}


}
