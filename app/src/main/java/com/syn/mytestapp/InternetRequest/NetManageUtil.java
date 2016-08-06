package com.syn.mytestapp.InternetRequest;

import android.content.Context;
import android.net.ConnectivityManager;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.syn.mytestapp.activity.MainActivity;

/**
 * Created by 孙亚楠 on 2016/7/28.
 */
public class NetManageUtil {
    public static OkHttpClient netClient;
    private static Gson mGson;

    static {
        netClient = new OkHttpClient();
        netClient.setFollowRedirects(false);
        mGson = new Gson();
    }

    private NetManageUtil(){

    }


    /***
     * Build a get request object
     * @return
     */
    public static GetRequest get(String url){
        return new GetRequest(url);
    }

    public static GetAuthRequest getAuth(String url){
        return new GetAuthRequest(url);
    }

    /***
     * Build a post request object
     * @return
     */
    public static PostRequest post(String url){
        return new PostRequest(url);
    }

    public static PostJsonRequest postJson(String url){
        return new PostJsonRequest(url);
    }
    public static PostAuthJsonRequest postAuthJson(String url){
        return new PostAuthJsonRequest(url);
    }

    /***
     * cancel requests by tag
     */

    public static void cancelByTag(String tag){
        netClient.cancel(tag);
    }



    public static boolean isWIFI = false;

    /**
     * 读取网络状态
     * @return
     */
    public static boolean readNetworkState() {

        ConnectivityManager cm = (ConnectivityManager) MainActivity.AppContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null && cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
            isWIFI = (cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI);
            return true;
        } else {
            return false;
        }
    }

}
