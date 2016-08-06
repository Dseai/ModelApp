package com.syn.mytestapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.syn.mytestapp.support.IModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 孙亚楠 on 2016/7/29.
 */
public abstract class BaseListAdapter<M extends IModel,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected List<M> mItems;
    protected Context mContext;
    protected abstract void updateView();
    public BaseListAdapter(Context mContext,M model) {
        this.mContext = mContext;
    }
    public BaseListAdapter(Context mContext,List<M> modelList) {
        this.mContext = mContext;
    }
    @Override
    public int getItemCount() {
        if (mItems == null) return 0;
        return mItems.size();
    }

    protected M getItem(int position){
        return mItems.get(position);
    }
    public void newList(List<M> list){
        if(list == null){
            return;
        }
        if(mItems == null){
            mItems = new ArrayList<>();
        }else {
            mItems.clear();
        }

        mItems.addAll(list);
        updateView();
    }


}

