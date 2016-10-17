// IUseBean.aidl
package com.jimmy.aidlservice.aidl;
import com.jimmy.aidlservice.aidl.AccountBean;
// Declare any non-default types here with import statements

interface INoCallBack {//没有回调的
    List<AccountBean> getAccount();
    void addAccount(in AccountBean accountBean);
}
