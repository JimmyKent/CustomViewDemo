package com.jimmy.customviewdemo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.jimmy.customviewdemo.R;
import com.jimmy.customviewdemo.widget.FlowLayout;
import com.jimmy.customviewdemo.widget.FlowLayout2;

public class FlowViewAty extends Activity {

	private String[] contents = new String[] {"很专业", "非常棒", "回答很及时", "很满意", "态度超级好", "回答一般", "骚扰", "回答不专业", "不满意",
			"回答时间长"};
	private int[] imgs = new int[] {R.drawable.f018, R.drawable.f019, R.drawable.f020, R.drawable.f018,
			R.drawable.f021, R.drawable.f019, R.drawable.f020, R.drawable.f018, R.drawable.f021, R.drawable.f019,
			R.drawable.f020, R.drawable.f018, R.drawable.f021, R.drawable.f019, R.drawable.f020, R.drawable.f018,
			R.drawable.f021, R.drawable.f019, R.drawable.f020, R.drawable.f018, R.drawable.f021, R.drawable.f019,
			R.drawable.f020, R.drawable.f018, R.drawable.f021, R.drawable.f019, R.drawable.f020, R.drawable.f018,
			R.drawable.f021};

	private FlowLayout2 mTextFlowLayout;
	private FlowLayout mImgFlowLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_flow_view);
		mTextFlowLayout = (FlowLayout2) findViewById(R.id.flowlayout_text);
		mImgFlowLayout = (FlowLayout) findViewById(R.id.flowlayout_img);
		initData();
	}

	public void initData() {
		for (int i = 0; i < contents.length; i++) {
			TextView textView =
					(TextView) LayoutInflater.from(this).inflate(R.layout.flowlayout_item_text, mTextFlowLayout, false);
			textView.setBackgroundResource(R.drawable.bg_review_txt);
			textView.setGravity(Gravity.CENTER);
			textView.setText(contents[i]);
			mTextFlowLayout.addView(textView);
		}

		for (int i = 0; i < imgs.length; i++) {
			ImageView imageView =
					(ImageView) LayoutInflater.from(this).inflate(R.layout.flowlayout_item_img, mImgFlowLayout, false);
			imageView.setImageResource(imgs[i]);
			mImgFlowLayout.addView(imageView);
		}
	}
}
