package com.jimmy.customviewdemo.mvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jimmy.customviewdemo.R;
import com.jimmy.customviewdemo.mvp.present.LoginView;
import com.jimmy.customviewdemo.mvp.present.UserPresenter;
import com.jimmy.customviewdemo.mvp.present.UserPresenterImpl;
//http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2016/0425/4178.html
public class MvpAty extends AppCompatActivity implements LoginView, View.OnClickListener {

    private EditText mUserET, mPwdET;
    private UserPresenter mUserPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp_aty);
        mUserET = (EditText) findViewById(R.id.et_name);
        mPwdET = (EditText) findViewById(R.id.et_pwd);
        findViewById(R.id.button).setOnClickListener(this);
        mUserPresenter = new UserPresenterImpl(this);
    }

    @Override
    public void onClick(View v) {
        mUserPresenter.validate(mUserET.getText().toString().trim(), mPwdET.getText().toString().trim());
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        mUserPresenter.onDestroy();
        super.onDestroy();
    }
}
