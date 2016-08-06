package com.syn.mytestapp.entity;

import com.syn.mytestapp.entity.OneSimplePost;

/**
 * Created by 孙亚楠 on 2016/7/27.
 */
public class GoodsListBean {
    private int count;
    private String next;
    private String prev;
    private OneSimplePost[] posts;

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public OneSimplePost[] getPosts() {
        return posts;
    }

    public void setPosts(OneSimplePost[] posts) {
        this.posts = posts;
    }



}
