package com.syn.mytestapp.support;

import java.util.List;


public interface IModel<M extends IModel> {


    /**
     * 将数据写入到数据库中
     * @param list  数据源 需要写入的数据
     * @return
     */
    boolean cacheAll(List<M> list);
    boolean clearCache();
    void loadFromCache();
    void loadFromNet();

}
