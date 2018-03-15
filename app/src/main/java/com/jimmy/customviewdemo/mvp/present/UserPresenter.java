package com.jimmy.customviewdemo.mvp.present;

/**
 * Created by Administrator on 2016/4/7.
 */
public interface UserPresenter {

    void validate(String name, String pwd);

    void onDestroy();
}
