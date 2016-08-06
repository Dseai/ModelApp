package com.syn.mytestapp.InternetRequest;

import com.google.gson.Gson;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

/**
 * Created by 孙亚楠 on 2016/7/28.
 */
public abstract class JsonEntityCallback<T> extends NetCallback{
    public abstract void onSuccess(T entity, Headers headers);
    @Override
    public void onResponse(Response response) throws IOException {
        T entity = new Gson().fromJson(response.body().string(),((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        onSuccess(entity,response.headers());
    }
}

