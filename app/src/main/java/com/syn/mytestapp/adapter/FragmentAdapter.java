package com.syn.mytestapp.adapter;

/**
 * Created by 孙亚楠 on 2016/7/23.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;


import java.util.List;

public class FragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragList;
    private List<String> titleList;

    private Context mContext;


    private boolean showLoadMore = false;

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragList, List<String> titleList) {
        super(fm);
        this.fragList = fragList;
        this.titleList = titleList;
    }




    public Fragment getItem(int arg0) {

        return fragList.get(arg0);
    }

    public int getCount() {

        return fragList.size();
    }

    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    public Object instantiateItem(ViewGroup arg0, int arg1) {
        return super.instantiateItem(arg0, arg1);
    }

}
