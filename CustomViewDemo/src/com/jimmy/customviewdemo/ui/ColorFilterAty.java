package com.jimmy.customviewdemo.ui;

import android.app.Activity;
import android.graphics.AvoidXfermode;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jimmy.customviewdemo.R;
import com.jimmy.customviewdemo.utils.BitmapUtils;
import com.jimmy.customviewdemo.utils.PicUtils;
import com.jimmy.customviewdemo.utils.ScreenUtils;
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
		initData4();
		initData5();
		initData6();
		initData7();

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
	
	private void initData4() {
		View view = LayoutInflater.from(this).inflate(R.layout.flowlayout_item_img_text, mImgFlowLayout, false);
		TextView textView = (TextView) view.findViewById(R.id.textview);
		ImageView imgView = (ImageView) view.findViewById(R.id.imageview);
		Bitmap temp = BitmapUtils.getImgFromAssets(this, mImgPath);
		Bitmap bm = PicUtils.change3(temp);
		temp.recycle();
		imgView.setImageBitmap(bm);
		textView.setText("饱和度对比度加强");
		mImgFlowLayout.addView(view);
	}
	
	private void initData5() {
		View view = LayoutInflater.from(this).inflate(R.layout.flowlayout_item_img_text, mImgFlowLayout, false);
		TextView textView = (TextView) view.findViewById(R.id.textview);
		ImageView imgView = (ImageView) view.findViewById(R.id.imageview);
		Bitmap temp = BitmapUtils.getImgFromAssets(this, mImgPath);
		Bitmap bm = PicUtils.removeGreen(temp);
		temp.recycle();
		imgView.setImageBitmap(bm);
		textView.setText("去掉绿色");
		mImgFlowLayout.addView(view);
	}
	
	private void initData6() {
		View view = LayoutInflater.from(this).inflate(R.layout.flowlayout_item_img_text, mImgFlowLayout, false);
		TextView textView = (TextView) view.findViewById(R.id.textview);
		ImageView imgView = (ImageView) view.findViewById(R.id.imageview);
		Bitmap temp = BitmapUtils.getImgFromAssets(this, mImgPath);
		Bitmap bm = PicUtils.red(temp);
		temp.recycle();
		imgView.setImageBitmap(bm);
		textView.setText("变暗变红");
		mImgFlowLayout.addView(view);
	}
	
	private void initData7() {
		View view = LayoutInflater.from(this).inflate(R.layout.flowlayout_item_img_text, mImgFlowLayout, false);
		TextView textView = (TextView) view.findViewById(R.id.textview);
		ImageView imgView = (ImageView) view.findViewById(R.id.imageview);
		Bitmap temp = BitmapUtils.getImgFromAssets(this, mImgPath);
		Bitmap bm = PicUtils.avoidXfermodeTarget(temp);
		temp.recycle();
		imgView.setImageBitmap(bm);
		textView.setText("1）AvoidXfermode  指定了一个颜色和容差，强制Paint避免在它上面绘图(或者只在它上面绘图)。");
		mImgFlowLayout.addView(view);
	}

}
