package com.jimmy.customviewdemo.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.jimmy.customviewdemo.R;

import java.util.List;

/**
 *  RecyclerView基类adapter
 * Created by Teaphy on 2015/10/26.
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE = -1;

    Context context;
    List<String> lists;

    public MyAdapter(Context context, List<String> lists) {
        this.context = context;
        this.lists = lists;
    }

    /**
     *  创建ViewHolder
     * @param viewGroup
     * @param viewType
     * @return
     */
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);

        if (VIEW_TYPE == viewType) {
            view = inflater.inflate(R.layout.item_empty, viewGroup, false);

            return new MyEmptyHolder(view);
        }

        view = inflater.inflate(R.layout.item_tv, viewGroup, false);

        return new MyHolder(view);
    }

    /**
     *  将数据绑定到ViewHolder上
     * @param viewHolder
     * @param position
     */
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof MyHolder) {
            ((MyHolder) viewHolder).tv_test.setText(lists.get(position));
        }
    }

    /**
     *  获取总的条目数量
     * @return
     */
    public int getItemCount() {
        return lists.size() > 0 ? lists.size() : 1;
    }

    /**
     *  获取条目 View填充的类型
     *  默认返回0
     * @param position
     * @return
     */
    public int getItemViewType(int position) {
        if (lists.size() <= 0) {
            return VIEW_TYPE;
        }
        return super.getItemViewType(position);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_test;

        public MyHolder(View itemView) {
            super(itemView);

            tv_test = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }

    public class MyEmptyHolder extends RecyclerView.ViewHolder{
        TextView tv_empty;

        public MyEmptyHolder(View itemView) {
            super(itemView);

            tv_empty = (TextView) itemView.findViewById(R.id.tv_empty);
        }
    }
}
