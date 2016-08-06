package com.syn.mytestapp.Util;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Response;
import com.syn.mytestapp.InternetRequest.NetCallback;

import java.io.IOException;

/**
 * Created by 孙亚楠 on 2016/8/6.
 */
public abstract class StringCodeCallback extends NetCallback {
    public abstract void onSuccess(String result, int responseCode,Headers headers);
    public abstract void onFailure(String error);
    @Override
    public void onResponse(final Response response) throws IOException {
        onSuccess(response.body().string(),response.code(),response.headers());
    }

    @Override
    public void onFailure(IOException e) {
        onFailure(e.toString());

    }
}
