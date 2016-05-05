package com.jimmy.customviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.jimmy.customviewdemo.mvp.MvpAty;
import com.jimmy.customviewdemo.ui.*;
import com.jimmy.customviewdemo.ui.movie_seat.MovieSeatAty;
import com.jimmy.customviewdemo.ui.movie_seat.MovieSelectSeatActivity1;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        findViewById(R.id.btn_large_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LargeImgAty.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_measure_once).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MeasureOnceAty.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_flow_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FlowViewAty.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_flow_view_recycleview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FlowViewRecycleAty.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_aige_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MovingCircleAty.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_aige_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ColorFilterAty.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_aige_2_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AvoidXAty.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_porterduff).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PorterDuffAty.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_porterduff2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PorterDuffAty2.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_eraser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EraserAty.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_multicircle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MultiCircleAty.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_touch_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TouchEventAty.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_view_log).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewLogAty.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_mvp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MvpAty.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_gif).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GifAty.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_movie_seat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MovieSelectSeatActivity1
                Intent intent = new Intent(MainActivity.this, MovieSeatAty.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
