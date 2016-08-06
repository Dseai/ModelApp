package com.syn.mytestapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.syn.mytestapp.activity.MainActivity;
import com.syn.mytestapp.entity.OneSimplePost;
import com.tendcloud.tenddata.TCAgent;

/**
 * Created by 孙亚楠 on 2016/7/28.
 */
public class GoodsDetailActivity extends Activity {
    public static final String TAG = "GoodsDetailActivity";
    private OneSimplePost model;
    private String goodLocation = "地址：";
    private String goodBuyTime = "购买时间：";
    private String goodPrice = "价格：";
    private String goodQuality = "成色：";
    private String goodContact = "联系方式：";
    private TextView tvUserName;
    private TextView tvTime;
    private MenuItem favorite, favorite_select, comment,share;
    private SimpleDraweeView avatarImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TCAgent.onPageStart(MainActivity.AppContext, TAG);
    }


}
