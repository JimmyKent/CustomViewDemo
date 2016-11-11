package com.jimmy.customviewdemo.mzrecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jimmy.customviewdemo.R;
import com.jimmy.log.KLog;

import java.util.List;

import flyme.support.v7.widget.MzRecyclerView;
import flyme.support.v7.widget.RecyclerView;

/**
 * Created by jinguochong on 16-11-8.
 */
public class MzAdapter extends MzRecyclerView.Adapter {
    public static final int TYPE_ITEM = 0;
    public static final int TYPE_FOOTER = 1;
    public static final int TYPE_EMPTY = 2;


    private Context mContext;
    private List<String> mList;
    public boolean mHasFooter;

    public MzAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        if (TYPE_EMPTY == viewType) {
            view = inflater.inflate(R.layout.item_empty, parent, false);
            return new EmptyViewHolder(view);
        } else if (TYPE_FOOTER == viewType) {
            view = inflater.inflate(R.layout.item_footer, parent, false);
            return new FooterViewHolder(view);
        }
        view = inflater.inflate(R.layout.item_tv, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).tv.setText(mList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        //XXX 这个是不是动态的？
        int count = 0;
        if (mHasFooter) {
            count++;
        }
        if (mList != null) {
            count += mList.size();
        }
        KLog.e("jgc", "getItemCount:" + count);
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.size() == 0 && position == 0) {
            return TYPE_EMPTY;
        }
        if (position == mList.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tv;

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

}
