package com.syn.mytestapp.Util;

import com.syn.mytestapp.support.Load.IUploadService;

/**
 * Created by 孙亚楠 on 2016/8/6.
 */
public class SimpleUploadService extends IUploadService {
    private static SimpleUploadService instance;

    private SimpleUploadService() {
        setUploadManager();
    }

    public static SimpleUploadService getInstance() {
        if (instance == null) {
            synchronized (SimpleUploadService.class) {
                if (instance == null) {
                    instance = new SimpleUploadService();
                }
            }
        }
        return instance;
    }

    @Override
    public void setUploadManager() {
        this.manager = UploadConfig.getInstance()
                .getDefaultManager();
    }
}

