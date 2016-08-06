package com.syn.mytestapp.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 孙亚楠 on 2016/8/5.
 */
public class PublishGood {
    private String userId;
    private String describe;
    private String goodsName;
    private int goodsNum;
    private float goodsPrice;
    private String goodsLocation;
    private String goodsQuality;
    private String goodsBuyTime;
    private int goodsTag;
    private String contact;
    private String auth_token;
    private List<PhotokeyBean> photos;
    public PublishGood(String userId, String body, String goodsName, int goodsNum, float goodsPrice
            , String goodsLocation, String goodsQuality, String goodsBuyTime
            , int goodsTag, String contact, List<PhotokeyBean> photos,String auth_token) {
        this.userId=userId;
        this.describe = body;
        this.goodsName = goodsName;
        this.goodsNum = goodsNum;
        this.goodsPrice = goodsPrice;
        this.goodsLocation = goodsLocation;
        this.goodsQuality = goodsQuality;
        this.goodsBuyTime = goodsBuyTime;
        this.goodsTag = goodsTag;
        this.contact = contact;
        this.photos = photos;
        this.auth_token=auth_token;
    }


}
