package com.syn.mytestapp.entity;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Headers;
import com.syn.mytestapp.API.JxnuGoApi;
import com.syn.mytestapp.InternetRequest.NetManageUtil;
import com.syn.mytestapp.Util.SPUtil;
import com.syn.mytestapp.activity.MainActivity;
import com.syn.mytestapp.db.post.JxnuGoStaticKey;
import com.syn.mytestapp.event.EVENT;
import com.syn.mytestapp.event.EventModel;
import com.syn.mytestapp.support.IModel;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 孙亚楠 on 2016/7/25.
 */
public class OneSimplePost implements IModel<OneSimplePost> {
    private GoodsDAO dao;
    private static final String TAG = "GoodsDAO";

    /**
     * 上传一个例子
     */
    private String author;
    private int author_id;
    private String body;
    //private int commentsCount;
    private String contact;
    private String goodsBuyTime;
    private String goodsLocation;
    private String goodsName;
    private double goodsPrice;
    private String goodsQuality;
    private int goodsTag;
    private int postId;
    private String postUserAvatar;
    private String postNickName;
    private String timestamp;
    private String url;

    private PhotokeyBean[] photos;

    public PhotokeyBean[]  getPhotos() {
        return photos;
    }

   /* public void setPhotos(List<PhotokeyBean> photoKs) {
        this.photos = photoKs;
    }*/

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
/*     评论部分暂时不用

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }
*/
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getGoodsBuyTime() {
        return goodsBuyTime;
    }

    public void setGoodsBuyTime(String goodsBuyTime) {
        this.goodsBuyTime = goodsBuyTime;
    }

    public String getGoodsLocation() {
        return goodsLocation;
    }

    public void setGoodsLocation(String goodsLocation) {
        this.goodsLocation = goodsLocation;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsQuality() {
        return goodsQuality;
    }

    public void setGoodsQuality(String goodsQuality) {
        this.goodsQuality = goodsQuality;
    }

    public int getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(int goodsTag) {
        this.goodsTag = goodsTag;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostUserAvatar() {
        return postUserAvatar;
    }

    public void setPostUserAvatar(String postUserAvatar) {
        this.postUserAvatar = postUserAvatar;
    }

    public String getPostNickName() {
        return postNickName;
    }

    public void setPostNickName(String postNickName) {
        this.postNickName = postNickName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean cacheAll(List<OneSimplePost> list) {
        return false;
    }

    @Override
    public boolean clearCache() {
        return false;
    }

    @Override
    public void loadFromCache() {


                    }

    @Override
    public void loadFromNet() {
    dao.loadFromNet();
    }
}

