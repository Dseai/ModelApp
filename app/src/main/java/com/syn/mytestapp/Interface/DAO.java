package com.syn.mytestapp.Interface;

import java.util.List;

/**
 * Created by 孙亚楠 on 2016/7/29.
 */
public interface DAO<M> {



    /**
     * 将数据写入到数据库中
     * @param list  数据源 需要写入的数据
     * @return
     */
    boolean cacheAll(List<M> list);

    /**
     * 清除缓存
     * @return
     */
    boolean clearCache();


    /***
     * 从数据库中获取数据
     * 如果获取到数据则添加到list中　发送成功消息   没有获取到数据则发送失败消息(EventBus)
     */
    void loadFromCache();

    /**
     * 从网络拉取数据
     * 如果拉取成功则将拉取的数据添加到list中发送成功消息 并缓存数据  失败则发送失败消息(EventBus)
     */
    void loadFromNet();
}
