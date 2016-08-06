package com.syn.mytestapp.InternetRequest;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by 孙亚楠 on 2016/7/28.
 */
public abstract class NetCallback implements Callback {

    public abstract void onFailure(IOException e);
    @Override
    public void onFailure(Request request, IOException e) {
        onFailure(e);
    }

    @Override
    public void onResponse(Response response) throws IOException {

    }

}
