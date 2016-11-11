package com.jimmy.customviewdemo.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jimmy.customviewdemo.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {

    private RecyclerView rv_test;

    private List<String> lists;
    private MyAdapter mMyAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        rv_test = (RecyclerView) findViewById(R.id.rv_test);

        lists = new ArrayList<>();

        mMyAdapter = new MyAdapter(this, lists);

        rv_test.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        rv_test.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        rv_test.setAdapter(mMyAdapter);
    }

    public void addData(View view) {
        for (int i = 0; i < 10; i++) {
            lists.add("Test_" + i);
        }

        mMyAdapter.notifyDataSetChanged();
    }

    public void refreshData(View view) {
        lists.clear();

        mMyAdapter.notifyDataSetChanged();
    }

}
