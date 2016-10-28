package com.jimmy.customviewdemo.rx;

/**
 * Created by jinguochong on 16-10-26.
 */
public class User {

    private int mBalance;
    private String mName;

    public int getBalance() {
        return mBalance;
    }

    public void setBalance(int balance) {
        this.mBalance = balance;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    private static User ourInstance = new User();

    public static User getInstance() {
        return ourInstance;
    }

    private User() {
    }
}
