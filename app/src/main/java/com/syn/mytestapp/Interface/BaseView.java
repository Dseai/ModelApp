package com.syn.mytestapp.Interface;

/**
 * Created by 孙亚楠 on 2016/7/29.
 */
public interface BaseView<V> extends IView<V> {
    void displayLoading();
    void hideLoading();
    void displayNetworkError();
}
