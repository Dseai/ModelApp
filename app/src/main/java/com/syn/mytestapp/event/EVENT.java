package com.syn.mytestapp.event;

/**
 * Created by 孙亚楠 on 2016/7/28.
 */
public class EVENT {
    //完成商品发布
    public static final int FINISH_GOODS_SEND = 95;
    //商品详情跳转
    public static final int GOODS_DETAIL_INTENT = 67;
    //商品列表加载code
    public static final int GOODS_LIST_REFRESH_SUCCESS = 65;
    public static final int GOODS_LIST_REFRESH_FAILURE = 66;
    public static final int JUMP_TO_JXNUGO_LOGIN = 2014;
    //JXNUGO登录的状态码
    public static final int JXNUGO_LOGIN_SUCCESS = 83;
    public static final int JXNUGO_LOGIN_FAILURE = 84;
    //JXNUGO登录补充
    public static final int JXNUGO_LOGIN_FAILURE_SEVER_ERROR = 105;
    //JXNUGO根据标签获取货物列表相关状态码
    public static final int JXNUGO_TAG_GOODS_LIST_REFRESH_SUCCESS = 98;
    public static final int JXNUGO_TAG_GOODS_LIST_REFRESH_FAILURE = 99;
    //上传图片
    public static final int GOODS_IMAGES_UPLOAD_SUCCESS = 70;
    public static final int GOODS_IMAGES_UPLOAD_FAIL = 71;
    //上传帖子
    public static final int POST_UPLOAD_SUCCESS = 72;
    public static final int POST_UPLOAD_FAIL = 73;

}
