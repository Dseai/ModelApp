package com.syn.mytestapp.entity;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Headers;
import com.syn.mytestapp.API.JxnuGoApi;
import com.syn.mytestapp.Interface.DAO;
import com.syn.mytestapp.InternetRequest.NetManageUtil;
import com.syn.mytestapp.Util.SPUtil;
import com.syn.mytestapp.activity.MainActivity;
import com.syn.mytestapp.db.post.JxnuGoStaticKey;
import com.syn.mytestapp.event.EVENT;
import com.syn.mytestapp.event.EventModel;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 孙亚楠 on 2016/7/29.
 */
public class GoodsDAO implements DAO<OneSimplePost> {
    private static final String TAG = "GoodsDAO";

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
        loadFromNet();
    }

    @Override
    public void loadFromNet() {
        SPUtil spu = new SPUtil(MainActivity.AppContext);
        String userName = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERNAME);
        String password = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.PASSWORD);
        final Handler handler = new Handler(Looper.getMainLooper());
        try {
            NetManageUtil.getAuth(JxnuGoApi.AllPostUrl)
                    .addUserName(userName)
                    .addPassword(password)
                    .addTag(TAG)
                    .enqueue(new JsonCodeEntityCallback<GoodsListBean>() {
                        @Override
                        public void onSuccess(GoodsListBean entity, int responseCode, Headers headers) {
                            if (entity != null) {
                                final List<GoodsListBean> list = Arrays.asList(entity);
//                                Log.d(TAG, "取得的条数" + entity.getCount());
//                                Log.d(TAG, "下一页" + entity.getNext());
//                                Log.d(TAG, "实际条数" + entity.getPosts().length);
//                                Log.d(TAG, "获取到的第一条信息" + entity.getPosts()[0].getBody());
//                                Log.d(TAG, "商品模型列表大小" + list.size());
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post(new EventModel<GoodsListBean>(EVENT.GOODS_LIST_REFRESH_SUCCESS, list));

                                    }
                                });
                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post(new EventModel<GoodsListBean>(EVENT.GOODS_LIST_REFRESH_FAILURE));
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(String error) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    EventBus.getDefault().post(new EventModel<GoodsListBean>(EVENT.GOODS_LIST_REFRESH_FAILURE));
                                }
                            });
                        }
                    });
        } catch (IllegalStateException e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new EventModel<GoodsListBean>(EVENT.GOODS_LIST_REFRESH_FAILURE));
                }
            });
        }
    }
}
