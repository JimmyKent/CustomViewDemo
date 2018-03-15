package com.jimmy.customviewdemo.ui;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.jimmy.customviewdemo.R;
import com.jimmy.customviewdemo.widget.LargeImageView;

public class LargeImgAty extends Activity {

	private LargeImageView mLargeImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_large_img);
		mLargeImageView = (LargeImageView) findViewById(R.id.id_largetImageview);
		mLargeImageView.getHeight();
		
		try {
			InputStream inputStream = getAssets().open("qm.jpg");
			mLargeImageView.setInputStream(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		Log.d("LargeImgAty", "view height:"+mLargeImageView.getHeight());
	}
}
