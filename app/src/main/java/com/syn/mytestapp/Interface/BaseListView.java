package com.syn.mytestapp.Interface;

/**
 * Created by 孙亚楠 on 2016/7/29.
 */
public interface BaseListView extends BaseView<BaseListView> {
    boolean trySetupRefreshLayout();
    void onDataRefresh();
    void bindAdapter();
    void addHeader();
}
