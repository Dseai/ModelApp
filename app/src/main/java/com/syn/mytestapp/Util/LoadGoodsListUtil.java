package com.syn.mytestapp.Util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Headers;
import com.syn.mytestapp.API.JxnuGoApi;
import com.syn.mytestapp.InternetRequest.NetManageUtil;
import com.syn.mytestapp.db.post.JxnuGoStaticKey;
import com.syn.mytestapp.entity.GoodsListBean;
import com.syn.mytestapp.entity.JsonCodeEntityCallback;
import com.syn.mytestapp.event.EVENT;
import com.syn.mytestapp.event.EventModel;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 孙亚楠 on 2016/8/3.
 */
public class LoadGoodsListUtil {
    public static final String TAG="LoadGoodsListUtil";
    private static String getUserName(Context context){
        SPUtil sp=new SPUtil(context);
        return sp.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERNAME);
    }
    private static String getPassword(Context context){
        SPUtil sp=new SPUtil(context);
        return sp.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.PASSWORD);
    }


    public static void getTagGoodsList(Context context, int tagID){
        final Handler handler = new Handler(Looper.getMainLooper());
        try {
            NetManageUtil.getAuth(JxnuGoApi.BaseTagGoodsUrl+tagID)
                    .addUserName(getUserName(context))
                    .addPassword(getPassword(context))
                    .addTag(TAG)
                    .enqueue(new JsonCodeEntityCallback<GoodsListBean>() {
                        @Override
                        public void onSuccess(GoodsListBean entity, int responseCode, Headers headers) {
                            if (entity != null) {
                                final List<GoodsListBean> list = Arrays.asList(entity);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post(new EventModel<GoodsListBean>(EVENT.JXNUGO_TAG_GOODS_LIST_REFRESH_SUCCESS, list));

                                    }
                                });
                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post(new EventModel<GoodsListBean>(EVENT.JXNUGO_TAG_GOODS_LIST_REFRESH_FAILURE));
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(String error) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    EventBus.getDefault().post(new EventModel<GoodsListBean>(EVENT.JXNUGO_TAG_GOODS_LIST_REFRESH_FAILURE));
                                }
                            });
                        }
                    });
        } catch (IllegalStateException e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new EventModel<GoodsListBean>(EVENT.JXNUGO_TAG_GOODS_LIST_REFRESH_FAILURE));
                }
            });
        }
    }

}
