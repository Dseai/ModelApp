package com.syn.mytestapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.syn.mytestapp.R;
import com.syn.mytestapp.entity.GoodsPhotoModel;
import com.syn.mytestapp.support.FrescoImageLoader;

import cn.finalteam.galleryfinal.widget.GFImageView;
import cn.finalteam.toolsfinal.DeviceUtils;

import java.util.List;

/**
 * Created by 孙亚楠 on 2016/8/5.
 */
public class ChoosePicAdapter extends BaseAdapter {

    private List<GoodsPhotoModel> mList;
    private LayoutInflater mInflater;
    private int mScreenWidth;
    private Context mContext;

    public ChoosePicAdapter(Activity activity, List<GoodsPhotoModel> list){
        this.mList = list;
        this.mContext=activity;
        this.mInflater = LayoutInflater.from(activity);
        this.mScreenWidth = DeviceUtils.getScreenPix(activity).widthPixels;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GFImageView iv=(GFImageView)mInflater.inflate(R.layout.card_choose_photolist, null);
        setHeight(iv);
        FrescoImageLoader fl=new FrescoImageLoader(mContext);
        fl.displayImage((Activity)mContext,
                mList.get(position).getPhotoPath(),
                iv,
                ContextCompat.getDrawable(mContext, R.drawable.ic_gf_default_photo),
                100,
                100);
        return iv;

    }
    private void setHeight(final View convertView) {
        int height = mScreenWidth / 3 - 8;
        convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
    }
}
