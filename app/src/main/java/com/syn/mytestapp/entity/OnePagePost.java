package com.syn.mytestapp.entity;

import java.util.List;

/**
 * Created by root on 16-5-19.
 */
public class OnePagePost {
    /**
     * count : 100
     * next :
     * prev :
     * posts : [{"author":"http://127.0.0.1:5000/api/user/2","body":"Pellentesque viverra pede ac diam. Morbi sem mauris, laoreet ut, rhoncus aliquet, pulvinar sed, nisl.","commentsCount":0,"contact":"1144728202","goodBuyTime":"2016-04-26","goodLocation":"78 Anhalt Parkway","goodName":"Rental & Leasing Services","goodPrice":1239.12,"goodQuality":"9成新","goodTag":4,"photos":null,"postId":1,"postUserAvator":"http://7xrkww.com1.z0.glb.clouddn.com/84BE7838-E41C-4E60-A1B8-CA95DBEE326B.png","postUserName":"nicole71","timestamp":"2016-05-07 00:00:00","url":"http://127.0.0.1:5000/api/posts/1"}]
     */

    private int count;
    private String next;
    private String prev;
    /**
     * author : http://127.0.0.1:5000/api/user/2
     * body : Pellentesque viverra pede ac diam. Morbi sem mauris, laoreet ut, rhoncus aliquet, pulvinar sed, nisl.
     * commentsCount : 0
     * contact : 1144728202
     * goodsBuyTime : 2016-04-26
     * goodsLocation : 78 Anhalt Parkway
     * goodsName : Rental & Leasing Services
     * goodsPrice : 1239.12
     * goodsQuality : 9成新
     * goodsTag : 4
     * photos : null
     * postId : 1
     * postUserAvatar : http://7xrkww.com1.z0.glb.clouddn.com/84BE7838-E41C-4E60-A1B8-CA95DBEE326B.png
     * postNickName : nicole71
     * timestamp : 2016-05-07 00:00:00
     * url : http://127.0.0.1:5000/api/posts/1
     */

    private List<OneSimplePost> posts;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public List<OneSimplePost> getPosts() {
        return posts;
    }

    public void setPosts(List<OneSimplePost> posts) {
        this.posts = posts;
    }
}
