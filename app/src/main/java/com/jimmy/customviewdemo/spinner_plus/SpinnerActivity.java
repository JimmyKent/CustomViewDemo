package com.jimmy.customviewdemo.spinner_plus;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jimmy.customviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinguochong on 15/03/2018.
 */

public class SpinnerActivity extends Activity {

    private TextView accountTV;
    private ImageView arrowIV;
    private PopupWindow accountListPpw;
    private View accountBtn;

    private List<String> mAccountInfos = new ArrayList<>();

    private int index;
    private String mCurrentLoginName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
        accountTV = findViewById(R.id.tv_account);
        arrowIV = findViewById(R.id.iv_arrow);
        accountBtn = findViewById(R.id.spinner_head);


        for (int i = 0; i < 5; i++) {
            mAccountInfos.add("account name " + i);
        }

        accountListPpw = new PopupWindow();
        accountListPpw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                arrowIV.setImageResource(R.drawable.arrow_down);
            }
        });

        accountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrowIV.setImageResource(R.drawable.arrow_up);
                refreshPpw();

            }
        });
    }

    private void refreshPpw() {
        if (accountListPpw == null) {
            return;
        }
        if (accountListPpw.isShowing()) {
            accountListPpw.dismiss();
        }
        View accountListView = LayoutInflater.from(this).inflate(R.layout.account_list, null, true);

        RecyclerView rv = accountListView.findViewById(R.id.rv_account);
        rv.setAdapter(new AccountAdapter(this, mAccountInfos));
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        accountListPpw.setContentView(accountListView);
        accountListPpw.setWidth((int) getResources().getDimension(R.dimen.small_window_width));
        int ppwHeight = mAccountInfos.size() < 3
                ? (int) getResources().getDimension(R.dimen.height_item_multi_account) * mAccountInfos.size()
                : (int) getResources().getDimension(R.dimen.height_item_multi_account) * 3;
        accountListPpw.setHeight(ppwHeight);
        accountListPpw.setOutsideTouchable(true);
        accountListPpw.setTouchable(true);
        accountListPpw.setFocusable(true);
        accountListPpw.setContentView(accountListView);
        accountListPpw.setBackgroundDrawable(new BitmapDrawable());
        accountListPpw.showAsDropDown(accountBtn);
    }

    private void doDelete(int position) {
        if (index == position) {
            mCurrentLoginName = "";
            accountTV.setText(mCurrentLoginName);
        }
        //list.remove(pos);
        //notifyDataSetChanged();

        mAccountInfos.remove(position);
        refreshPpw();
    }

    private class AccountAdapter extends RecyclerView.Adapter {

        private Context context;
        private List<String> list;

        AccountAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_drop_down_account, parent, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ItemViewHolder) {
                String item = list.get(position);
                ((ItemViewHolder) holder).nameTV.setText(item);

                final int pos = position;
                ((ItemViewHolder) holder).deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doDelete(pos);
                    }
                });
                ((ItemViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        index = pos;
                        mCurrentLoginName = mAccountInfos.get(index);
                        accountTV.setText(mCurrentLoginName);
                        if (accountListPpw != null) {
                            accountListPpw.dismiss();
                        }
                    }
                });
            }

        }

        @Override
        public int getItemCount() {
            return list.size();
        }


        private class ItemViewHolder extends RecyclerView.ViewHolder {

            private View itemView;
            private TextView nameTV;
            private View deleteBtn;

            private ItemViewHolder(View view) {
                super(view);
                itemView = view;
                nameTV = (TextView) view.findViewById(R.id.tv_account);
                deleteBtn = view.findViewById(R.id.btn_delete);
            }
        }
    }

}
