package com.syn.mytestapp.Util;

import com.squareup.okhttp.Headers;
import com.syn.mytestapp.API.JxnuGoApi;
import com.syn.mytestapp.InternetRequest.JsonEntityCallback;
import com.syn.mytestapp.InternetRequest.NetManageUtil;
import com.syn.mytestapp.entity.QiniuUptokenBean;
import com.syn.mytestapp.support.Load.IUploadService;

import java.io.File;
import java.io.IOException;

/**
 * Created by 孙亚楠 on 2016/8/6.
 */
public class UploadUtil {
    private static SimpleUploadService service;

    static {
        service = SimpleUploadService.getInstance();
    }

    public static void simpleUploadByPath(final String path, IUploadService.OnUploadListener uploadListener) {
        service.setOnUploadListener(uploadListener);
        NetManageUtil.get(JxnuGoApi.QiniuToken)
                .enqueue(new JsonEntityCallback<QiniuUptokenBean>() {
                    @Override
                    public void onFailure(IOException e) {

                    }

                    @Override
                    public void onSuccess(QiniuUptokenBean entity, Headers headers) {
                        service.putDataByPath(path, null, entity.getUptoken());
                    }
                });
    }

    public static void simpleUploadByBytes(final byte[] bytes, IUploadService.OnUploadListener uploadListener) {
        service.setOnUploadListener(uploadListener);
        NetManageUtil.get(JxnuGoApi.QiniuToken)
                .enqueue(new JsonEntityCallback<QiniuUptokenBean>() {
                    @Override
                    public void onFailure(IOException e) {

                    }

                    @Override
                    public void onSuccess(QiniuUptokenBean entity, Headers headers) {
                        service.putDataByBytes(bytes, null, entity.getUptoken());
                    }
                });
    }

    public static void simpleUploadByFile(final File file, IUploadService.OnUploadListener uploadListener) {
        service.setOnUploadListener(uploadListener);
        NetManageUtil.get(JxnuGoApi.QiniuToken)
                .enqueue(new JsonEntityCallback<QiniuUptokenBean>() {
                    @Override
                    public void onFailure(IOException e) {

                    }

                    @Override
                    public void onSuccess(QiniuUptokenBean entity, Headers headers) {
                        service.putDataByFile(file, null, entity.getUptoken());
                    }
                });
    }
}

