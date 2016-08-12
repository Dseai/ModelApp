package com.syn.mytestapp.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.syn.mytestapp.R;
import com.syn.mytestapp.adapter.TabFragmentAdapter;
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



public class MainActivity extends  BaseActivity implements Toolbar.OnMenuItemClickListener {
    private long lastPressTime = 0;
    public static Context AppContext;
    protected Toolbar mToolbar;

    private ViewPager pager;
    private TabLayout tabLayout;

    private List<Fragment> fragList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        AppContext=getApplicationContext();
        initView();
    }
    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        initToolbar();
        initTabLayout();

    }
    private void initTabLayout() {
        pager = findView(R.id.viewPager);
        tabLayout = findView(R.id.tabs);

        /**
         * 为tab添加标题
         */
        List<String> tabList = new ArrayList<>();
        tabList.add("全部");
        tabList.add("生活用品");
        tabList.add("数码科技");
        tabList.add("服饰箱包");
        tabList.add("图书音像");
        tabList.add("其它");

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//设置tab模式，当前为系统默认模式
        //此处代码设置无效，不知道为啥？？？xml属性是可以的
//        tabLayout.setTabTextColors(android.R.color.white, android.R.color.holo_red_dark);//设置TabLayout两种状态
        tabLayout.addTab(tabLayout.newTab().setText(tabList.get(0)));//添加tab选项卡
        tabLayout.addTab(tabLayout.newTab().setText(tabList.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(tabList.get(2)));
        tabLayout.addTab(tabLayout.newTab().setText("Tab4"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab5"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab6"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab7"));

        /**
         * 初始化fragment
         */
        this.fragList = new ArrayList();
        this.fragList.add(new FragmentAll());
        this.fragList.add(new FragmentSupplies());
        this.fragList.add(new FragmentDigtal());
        this.fragList.add(new FragmentClothes());
        this.fragList.add(new FragmentBooks());
        this.fragList.add(new FragmentOthers());
        TabFragmentAdapter fragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), fragList, tabList);
        pager.setAdapter(fragmentAdapter);//给ViewPager设置适配器
        tabLayout.setupWithViewPager(pager);//将TabLayout和ViewPager关联起来。
        tabLayout.setTabsFromPagerAdapter(fragmentAdapter);//给Tabs设置适配器

    }
    private void initToolbar() {
        mToolbar = findView(R.id.toolbar);
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            mToolbar.setTitle("高仿淘二手");
            mToolbar.setSubtitle("SYN");
            mToolbar.setLogo(R.mipmap.ic_launcher);
        mToolbar.setOnMenuItemClickListener(this);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_goods:
                startActivity(new Intent(MainActivity.this, PubishGoodsActivity.class));
                break;
            case R.id.action_share:
                Toast.makeText(this, "用户信息查看以及编辑按钮", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }



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
                Snackbar.make(getCurrentFocus(), "再按返回键一次退出",Snackbar.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

}
