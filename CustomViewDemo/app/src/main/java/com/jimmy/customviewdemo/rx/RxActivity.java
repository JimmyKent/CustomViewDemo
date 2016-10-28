package com.jimmy.customviewdemo.rx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jimmy.customviewdemo.R;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * 场景：
 * 一个单例
 * <p>
 * 点击按钮A 修改 值
 * <p>
 * 显示在B上
 * <p>
 * 在onCreate的时候订阅；
 * 判断单例是否为空；
 * 点击修改单例值
 */
public class RxActivity extends AppCompatActivity {

    private final String tag = "RxActivity";

    private TextView mTV;
    private Button mAddSubscribeBtn;
    private Button mDistributeBtn;
    private Observable<String> mObservable;

    private Subscriber<String> subscriber1;
    private Subscriber<String> subscriber2;

    private List<Subscriber<String>> subscriberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);


        mTV = (TextView) findViewById(R.id.text);
        mAddSubscribeBtn = (Button) findViewById(R.id.btn_add_subscribe);
        mDistributeBtn = (Button) findViewById(R.id.btn_distribute);

        mAddSubscribeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscriber2 = new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        mTV.setText("2");
                    }
                };
            }
        });

        mDistributeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mObservable.subscribe(subscriber1);
                mObservable.subscribe(subscriber2);
            }
        });

        subscriberList = new ArrayList<>();

        Observable.OnSubscribe<String> onSubscriber1 = new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("1");
                subscriber.onCompleted();
            }
        };

        subscriber1 = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                mTV.setText(s);
            }
        };

        mObservable = Observable.create(onSubscriber1);


    }
}
