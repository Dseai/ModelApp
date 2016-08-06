package com.syn.mytestapp.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.qiniu.android.http.ResponseInfo;
import com.squareup.okhttp.Headers;
import com.syn.mytestapp.API.JxnuGoApi;
import com.syn.mytestapp.InternetRequest.NetManageUtil;
import com.syn.mytestapp.activity.MainActivity;
import com.syn.mytestapp.db.post.JxnuGoStaticKey;
import com.syn.mytestapp.entity.GoodsPhotoModel;
import com.syn.mytestapp.entity.PhotokeyBean;
import com.syn.mytestapp.entity.PublishGood;
import com.syn.mytestapp.event.EVENT;
import com.syn.mytestapp.event.EventModel;
import com.syn.mytestapp.support.Load.IUploadService;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 孙亚楠 on 2016/8/6.
 */
public class UploadGoodsUtil {
    public static final String TAG = "UploadGoodsUtil";
    private static Handler handler=new Handler(Looper.getMainLooper());
    public static ByteArrayOutputStream compressImages(String imagePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        float width=bitmap.getWidth();
        float height=bitmap.getHeight();
        while(width>2000||height>2000){
            Log.d(TAG,"上传的图片大小 宽："+width+" 高："+height);
            float scaleWidth=0;
            float scaleHeight=0;
            scaleWidth=(float)((width/2))/width;
            scaleHeight=(float)((height/2))/height;
            Matrix matrix=new Matrix();
            matrix.postScale(scaleWidth,scaleHeight);
            bitmap=Bitmap.createBitmap(bitmap,0,0,(int)width,(int)height,matrix,true);
            width=bitmap.getWidth();
            height=bitmap.getHeight();
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, bos);
        return bos;
    }

    public static void onUploadImages(final Context context, final List<GoodsPhotoModel> photoList) {
        final List<PhotokeyBean> keys = new ArrayList<>();
        IUploadService.OnUploadListener uploadListener = new IUploadService.OnUploadListener() {
            @Override
            public void onCompleted(String key, ResponseInfo info, JSONObject res) {
                if (!info.isOK()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault()
                                    .post(new EventModel<String>(EVENT.GOODS_IMAGES_UPLOAD_FAIL
                                            , ""));
                        }
                    });

                    return;
                }
                PhotokeyBean bean;
                try {
                    bean = new PhotokeyBean(res.getString("key"));
                    keys.add(bean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (keys.size() == photoList.size()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault()
                                    .post(new EventModel<List<PhotokeyBean>>(EVENT.GOODS_IMAGES_UPLOAD_SUCCESS
                                            , keys));
                        }
                    });

                }
            }

            @Override
            public void onProcessing(String key, double percent) {

            }

            @Override
            public boolean onCancelled() {
                return false;
            }
        };

        for (GoodsPhotoModel photoModel : photoList) {
            byte[] bytes = compressImages(photoModel.getPhotoPath()).toByteArray();
            UploadUtil.simpleUploadByBytes(bytes, uploadListener);
        }
    }

    /**
     * 发表帖子
     * @param bean
     * @param context
     */
    public static void onUploadJson(final PublishGood bean, final Context context) {
        SPUtil spu = new SPUtil(MainActivity.AppContext);
        final String userName = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERNAME);
        final String password = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.PASSWORD);
        NetManageUtil.postAuthJson(JxnuGoApi.PublishNewPostUrl)
                .addUserName(userName)
                .addPassword(password)
                .addTag(TAG)
                .addJsonObject(bean)
                .enqueue(new StringCodeCallback() {
                    @Override
                    public void onSuccess(String result, int responseCode, Headers headers) {
                        Log.i(TAG, "" + responseCode);
                        if(responseCode==200)
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    EventBus.getDefault().post(new EventModel<PublishGood>(EVENT.POST_UPLOAD_SUCCESS));
                                }
                            });
                        else{
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    EventBus.getDefault().post(new EventModel<PublishGood>(EVENT.POST_UPLOAD_FAIL));
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.i(TAG, error);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new EventModel<PublishGood>(EVENT.POST_UPLOAD_FAIL));
                            }
                        });
                    }
                });
    }
}


