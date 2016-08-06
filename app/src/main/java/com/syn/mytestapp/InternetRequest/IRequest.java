package com.syn.mytestapp.InternetRequest;

import java.util.Map;

/**
 * Created by 孙亚楠 on 2016/7/28.
 */
public abstract class IRequest {
    protected Map<String,String> headers = null;
    protected boolean isFirstParams = true;
    protected String url = null;
    protected String tag = null;
    protected String userName;
    protected String password;
    protected String json;
    public abstract IRequest addHeader(String key,String val);
    public abstract IRequest addParams(String key,String val);
    public abstract IRequest addUserName(String userName);
    public abstract IRequest addPassword(String password);
    public abstract IRequest addJsonObject(Object bean);
    public abstract IRequest addTag(String tag);
    public abstract void enqueue(NetCallback callback);
    //public abstract void execute(NetCallback callback);
}
