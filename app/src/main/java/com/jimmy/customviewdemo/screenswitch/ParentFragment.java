package com.jimmy.customviewdemo.screenswitch;

import android.Manifest;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.jimmy.customviewdemo.R;
import com.jimmy.log.KLog;
import com.jimmy.permission.AndPermission;
import com.jimmy.permission.IPermissionCallback;

/**
 * Created by jinguochong on 16-9-23.
 *
 */
public class ParentFragment extends Fragment {

    private static final String TAG = "jimmy ParentFragment";
    private static final int REQUEST_CALENDAR = 101;
    private View mRootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);//在最外层设置，内层不能设置
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRootView =  view.findViewById(R.id.parent_root);

        getFragmentManager().beginTransaction().add(R.id.ll_parent_content, new SubFragment(), "SubFragment").commit();

        final int sub = R.id.ll_parent_content;
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = new SubFragment();
        ft.add(sub, fragment, "SubFragment");
        ft.commitAllowingStateLoss();

        view.findViewById(R.id.btn_request_single).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mRootView.getLayoutParams();
        lp.width  = (int) getResources().getDimension(R.dimen.big_window_width);
        lp.height  = (int) getResources().getDimension(R.dimen.big_window_height);
    }




}
