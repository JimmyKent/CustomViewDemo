package com.jimmy.customviewdemo.mzrecycler;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jimmy.customviewdemo.R;

import java.util.ArrayList;
import java.util.List;

import flyme.support.v7.widget.LinearLayoutManager;
import flyme.support.v7.widget.MzRecyclerView;
import flyme.support.v7.widget.RecyclerView;

@SuppressWarnings("ALL")
public class MzRecyclerActivity extends AppCompatActivity {
    private MzRecyclerView mzRecyclerView;
    private MzAdapter mzAdapter;
    private List<String> lists;
    private LinearLayoutManager mLayoutManager;

    private int index;
    private boolean mLoading;
    private boolean mHasMore;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mz_recycler);
        lists = new ArrayList<>();
        mzRecyclerView = (MzRecyclerView) findViewById(R.id.mz_rv);
        mzAdapter = new MzAdapter(this, lists);
        mzRecyclerView.setAdapter(mzAdapter);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mzRecyclerView.setLayoutManager(mLayoutManager);

        mzRecyclerView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                if (!mLoading && lists.size() == 0) {
                    loadMore();
                }
            }


            @Override
            public void onViewDetachedFromWindow(View v) {

            }
        });

        mzRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (lists.size() > 0 && mHasMore && mLayoutManager.findLastCompletelyVisibleItemPosition() >= lists.size() - 1) {

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mLoading = false;
                            // adapter.notifyItemRemoved(adapter.getItemCount());
                            loadMore();
                        }
                    }, 2000);
                }
            }


        });
        mzRecyclerView.setItemAnimator(null);
    }


    private void loadMore() {
        index++;
        if (index <= 3) {
            mHasMore = true;
            for (int i = 0; i < 10; i++) {
                String str = "str" + i;
                lists.add(str);
            }
        } else {
            mHasMore = false;
            Toast.makeText(this, "已经没有新的了", Toast.LENGTH_SHORT).show();
        }
        mzAdapter.notifyItemRemoved(mzAdapter.getItemCount());
    }
}
