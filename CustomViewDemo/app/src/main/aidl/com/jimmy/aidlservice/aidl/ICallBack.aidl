// IUseAidl.aidl
package com.jimmy.aidlservice.aidl;

import com.jimmy.aidlservice.aidl.IResponse;
import android.os.Bundle;

interface ICallBack {
    void login(in Bundle data, IResponse response);
}
