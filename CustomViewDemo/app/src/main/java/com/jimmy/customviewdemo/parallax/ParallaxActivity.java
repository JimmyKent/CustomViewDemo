package com.jimmy.customviewdemo.parallax;

import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.jimmy.customviewdemo.R;
import com.jimmy.customviewdemo.adapter.MyRecyclerAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ParallaxActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private NestedScrollView scrollView;
    private AppBarLayout mAppBarLayout;
    private TextView mBalanceShowTV;
    private RecyclerView mRecyclerView;

    private List<String> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallax3);
        //scrollView = (NestedScrollView) findViewById(R.id.screen_view);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.main_appbar);
        mBalanceShowTV = (TextView) findViewById(R.id.tv_balance_title);

        Typeface typeFace = Typeface.createFromFile("/system/fonts/DINPro-Regular.otf");
        mBalanceShowTV.setTypeface(typeFace);


        mAppBarLayout.addOnOffsetChangedListener(this);
        /*scrollView.setSmoothScrollingEnabled(true);

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, final int scrollY, int oldScrollX, int oldScrollY) {
            }
        });*/
        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));//设置RecyclerView布局管理器为2列垂直排布
        MyRecyclerAdapter adapter = new MyRecyclerAdapter(this, lists);
        mRecyclerView.setAdapter(adapter);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(null);

    }

    private void initData() {
        lists = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            lists.add("" + i);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScrollY = appBarLayout.getTotalScrollRange();
        //Log.e("hehe", "maxScrollY:" + maxScrollY);
        //Log.e("hehe", "verticalOffset:" + verticalOffset);
        if (-verticalOffset == maxScrollY) {
            mBalanceShowTV.setVisibility(View.VISIBLE);
        } else {
            mBalanceShowTV.setVisibility(View.GONE);
        }

    }
}
