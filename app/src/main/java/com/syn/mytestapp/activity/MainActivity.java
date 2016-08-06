package com.syn.mytestapp.activity;


import android.content.Context;
import android.graphics.Color;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toolbar;

import com.syn.mytestapp.R;
import com.syn.mytestapp.adapter.FragmentAdapter;
import com.syn.mytestapp.fragment.FragmentAll;
import com.syn.mytestapp.fragment.FragmentBooks;
import com.syn.mytestapp.fragment.FragmentClothes;
import com.syn.mytestapp.fragment.FragmentDigtal;
import com.syn.mytestapp.fragment.FragmentOthers;
import com.syn.mytestapp.fragment.FragmentSupplies;
import com.syn.mytestapp.support.CONSTANT;
import com.syn.mytestapp.support.Settings;


import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    private long lastPressTime = 0;
    public static Context AppContext;
    public static final String POST_ID = "POST_ID";
    public static final String POST_COVER = "POST_COVER";

    protected Toolbar mToolbar;
    private Fragment mTradeFragment;

    private FragmentManager mContextFragmentManager;

    private FragmentManager mMainFM;
    private FragmentTransaction mMainFT;

    private ViewPager pager;

    private List<String> titleList;
    private PagerTabStrip tab;
    private List<Fragment> fragList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppContext = getApplicationContext();
      //  mToolbar=(Toolbar)findViewById(R.id.trade_toolbar);

        /**
         * 设置标题
         */
        titleList = new ArrayList();
        titleList.add("全部");
        titleList.add("生活用品");
        titleList.add("数码科技");
        titleList.add("服饰箱包");
        titleList.add("图书音像");
        titleList.add("其它");
        tab = (PagerTabStrip)this.findViewById(R.id.tab);
        /**
         * 为pager title设置一些属性
         */
       this.tab.setBackgroundColor(Color.WHITE);
        this.tab.setTabIndicatorColor(Color.GRAY);
        this.tab.setDrawFullUnderline(false);//不显示下面的线
        this.tab.setTextColor(Color.BLACK);
        tab.setClickable(true);
        this.pager = (ViewPager)this.findViewById(R.id.pager);
        /**
         * 通过fragment创建数据源
         */
       this.fragList = new ArrayList();
        this.fragList.add(new FragmentAll());
        this.fragList.add(new FragmentSupplies());
        this.fragList.add(new FragmentDigtal());
        this.fragList.add(new FragmentClothes());
        this.fragList.add(new FragmentBooks());
        this.fragList.add(new FragmentOthers());

        //创建适配器
        FragmentAdapter adapter=new FragmentAdapter(getSupportFragmentManager(),fragList,titleList);
        this.pager.setAdapter(adapter);

    }

   /* private void setUpToolbar() {
        if (mToolbar != null) {
            mToolbar.setTitle("所有商品");
            setSupportActionBar(mToolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
//设置toolbar上的菜单选项
            // setUpToolbarMenu();
        }
    }*/
    @Override
    public void onBackPressed() {
       if(canExit()){
            super.onBackPressed();
        }
    }

    private boolean canExit(){
        if(Settings.isExitConfirm){
            if(System.currentTimeMillis() - lastPressTime > CONSTANT.exitConfirmTime){
                lastPressTime = System.currentTimeMillis();
                Snackbar.make(getCurrentFocus(), "再按返回键一次推出",Snackbar.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }


}
