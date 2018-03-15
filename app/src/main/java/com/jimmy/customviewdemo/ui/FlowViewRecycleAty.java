package com.jimmy.customviewdemo.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.jimmy.customviewdemo.R;
import com.jimmy.customviewdemo.adapter.MyRecyclerAdapter;

/** 
 * 使用recycleview实现瀑布流
 * @Author Spartacus26
 * @Since 2016-2-29 下午4:41:36 
 */
public class FlowViewRecycleAty extends Activity {
	private RecyclerView mRecyclerView;
	private List<String> lists;
	private MyRecyclerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_flow_view_recycle);
		initData();
		//set recycleview
		mRecyclerView = (RecyclerView) findViewById(R.id.recycleview);
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		//mRecyclerView.addItemDecoration();//设置分割线
		mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));//设置RecyclerView布局管理器为2列垂直排布
		adapter = new MyRecyclerAdapter(this, lists);
		mRecyclerView.setAdapter(adapter);
		adapter.setOnClickListener(new MyRecyclerAdapter.OnItemClickListener() {
			@Override
			public void ItemClickListener(View view, int postion) {
				Toast.makeText(FlowViewRecycleAty.this, "点击了：" + postion, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void ItemLongClickListener(View view, int postion) {
				//长按删除
				lists.remove(postion);
				adapter.notifyItemRemoved(postion);
			}
		});
	}

	private void initData() {
		lists = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			lists.add("" + i);
		}
	}

}
