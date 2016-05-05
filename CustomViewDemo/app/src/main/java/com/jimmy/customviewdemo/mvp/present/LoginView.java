package com.jimmy.customviewdemo.mvp.present;

/**
 * Created by Administrator on 2016/4/7.
 */
public interface LoginView {
    void showProgress();

    void hideProgress();

    void toast(String msg);

}
