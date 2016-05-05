package com.jimmy.customviewdemo.mvp.present;

/**
 * Created by Administrator on 2016/4/7.
 */
public class UserPresenterImpl implements  UserPresenter{
    private LoginView loginView;

    public UserPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void validate(String name, String pwd) {
        if (loginView != null) {
            loginView.showProgress();
        }
        loginView.toast("登录成功");
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }
}
