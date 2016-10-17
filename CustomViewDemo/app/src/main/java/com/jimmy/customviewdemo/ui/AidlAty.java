package com.jimmy.customviewdemo.ui;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jimmy.aidlservice.aidl.AccountBean;
import com.jimmy.aidlservice.aidl.ICallBack;
import com.jimmy.aidlservice.aidl.INoCallBack;
import com.jimmy.aidlservice.aidl.IResponse;
import com.jimmy.customviewdemo.R;

import java.util.List;

public class AidlAty extends AppCompatActivity {

    public final static String ACTION_BIND_SERVICE = "com.jimmy.aidlservice.NoCallBackService";
    public final static String ACTION_BIND_CALLBACK_SERVICE = "com.jimmy.aidlservice.CallBackService";

    public static final String KEY_NAME = "key_name";
    public static final String KEY_PWD = "key_pwd";

    private INoCallBack mINoCallBack;
    private ICallBack mICallBack;
    private Bundle data = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_aidl);
        //Android5.0中service的intent一定要显性声明
        Intent mIntent = new Intent();
        mIntent.setAction(ACTION_BIND_SERVICE);
        final Intent explitictIntent = new Intent(getExplicitIntent(this, mIntent));
        bindService(explitictIntent, mNoCallBackConnection, BIND_AUTO_CREATE);

        Intent intent = new Intent();
        intent.setAction(ACTION_BIND_CALLBACK_SERVICE);
        final Intent eIntent = new Intent(getExplicitIntent(this, intent));
        bindService(eIntent, mCallBackConnection, BIND_AUTO_CREATE);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AccountBean account = new AccountBean();
                    account.name = "jimmy";
                    account.pwd = "pwd";
                    mINoCallBack.addAccount(account);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<AccountBean> accounts = mINoCallBack.getAccount();
                    for (AccountBean bean : accounts) {
                        Toast.makeText(AidlAty.this, bean.toString(), Toast.LENGTH_SHORT).show();
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (data == null) {
                    data = new Bundle();
                    data.putString(KEY_NAME, "jimmy");
                    data.putString(KEY_PWD, "pwd");
                } else {
                    data = null;
                }
                try {
                    mICallBack.login(data, mResponse);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private IResponse.Stub mResponse = new IResponse.Stub() {
        @Override
        public void success() throws RemoteException {
            Log.e("kent","success");
            Toast.makeText(AidlAty.this, "login success.", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(int code, String msg) throws RemoteException {
            Toast.makeText(AidlAty.this, "login fail.", Toast.LENGTH_SHORT).show();
        }
    };


    private ServiceConnection mNoCallBackConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mINoCallBack = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //通过服务端onBind方法返回的binder对象得到IMyService的实例，得到实例就可以调用它的方法了
            mINoCallBack = INoCallBack.Stub.asInterface(service);
            try {
                AccountBean student = mINoCallBack.getAccount().get(0);
                showDialog(student.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    };

    private ServiceConnection mCallBackConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mICallBack = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //通过服务端onBind方法返回的binder对象得到IMyService的实例，得到实例就可以调用它的方法了
            mICallBack = ICallBack.Stub.asInterface(service);
        }
    };

    public void showDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("jimmy")
                .setMessage(message)
                .setPositiveButton("确定", null)
                .show();
    }


    public static Intent getExplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);
        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }
        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);
        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);
        // Set the component to be explicit
        explicitIntent.setComponent(component);
        return explicitIntent;
    }


    @Override
    protected void onDestroy() {
        if (mINoCallBack != null) {
            unbindService(mNoCallBackConnection);
        }
        if (mICallBack != null) {
            unbindService(mCallBackConnection);
        }
        super.onDestroy();
    }

}