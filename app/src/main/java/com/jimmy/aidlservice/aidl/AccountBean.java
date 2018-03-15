package com.jimmy.aidlservice.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jinguochong on 16-8-31.
 */
public class AccountBean implements Parcelable{

    public String name;
    public String pwd;

    public AccountBean(){}

    public AccountBean(Parcel in) {
        name = in.readString();
        pwd = in.readString();
    }

    public static final Creator<AccountBean> CREATOR = new Creator<AccountBean>() {
        @Override
        public AccountBean createFromParcel(Parcel in) {
            return new AccountBean(in);
        }

        @Override
        public AccountBean[] newArray(int size) {
            return new AccountBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(pwd);
    }

    @Override
    public String toString() {
        return "AccountBean{" +
                "name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
