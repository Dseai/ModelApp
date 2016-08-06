package com.syn.mytestapp.Util;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.EditText;

import com.squareup.okhttp.Headers;
import com.syn.mytestapp.API.JxnuGoApi;
import com.syn.mytestapp.InternetRequest.NetManageUtil;
import com.syn.mytestapp.activity.MainActivity;
import com.syn.mytestapp.db.post.JxnuGoStaticKey;
import com.syn.mytestapp.entity.JsonCodeEntityCallback;
import com.syn.mytestapp.event.EVENT;
import com.syn.mytestapp.event.EventModel;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by 孙亚楠 on 2016/7/29.
 */

/**
 * 参考自师大+，登录模块
 *
 */
public class JxnuGoLoginUtil {
    public static final String TAG = "JxnuGoLoginUtil";

    public static String getUserName() {
        return userName;
    }

    public static String getPassWord() {
        return passWord;
    }

    private static String userName;
    private static String passWord;

    public static String getUserAvatar() {
        return userAvatar;
    }

    public static void setUserAvatar(String userAvatar) {
        SPUtil mysp = new SPUtil(MainActivity.AppContext);
        mysp.putStringSP(JxnuGoStaticKey.SP_FILE_NAME,JxnuGoStaticKey.USER_AVATAR,userAvatar);
        JxnuGoLoginUtil.userAvatar = userAvatar;
    }

    private static String userAvatar;

    public static String getToken() {
        return token;
    }

    private static String token;

    public static String getUsername(EditText editText) {
        return editText.getText().toString();
    }

    public static String getPassword(EditText editText) {
        return editText.getText().toString();
    }

    public static void onLogin(final EditText usernameText, final EditText passwordText) {
        if (TextUtil.isNull(getUsername(usernameText)) || TextUtil.isNull(getPassword(passwordText))) {
            return;
        }
        //there has something inputed into the username and password edittext,and then verify it by net
        else {
//            Log.d(TAG, "开始请求网络");
            final Handler handler = new Handler(Looper.getMainLooper());
            NetManageUtil.getAuth(JxnuGoApi.LoginUrl)
                    .addTag(TAG)
                    .addUserName(getUsername(usernameText))
                    .addPassword(getPassword(passwordText))
                    .enqueue(new JsonCodeEntityCallback<JxnuGoLoginBean>() {
                        @Override
                        public void onSuccess(final JxnuGoLoginBean entity, int responseCode, Headers headers) {
                            Log.d(TAG, "返回数据成功");
                            Log.d(TAG, "状态码" + responseCode);
                            if (!TextUtil.isNull(entity.getToken())) {
                                Log.d("JXNUGOLOD","SUCCESS");

                                token = entity.getToken();
                                final int userId=entity.getUserId();
//                                Log.d(TAG,"userId:"+userId);
                                userAvatar=entity.getUserAvatar();
                                saveToSP(userAvatar,token, getUsername(usernameText), getPassword(passwordText),entity.getUserId());

                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        //Log.d("JXNUGOLOAD", entity.getToken()+entity.getMessage()+entity.getError());
//                                        EventBus.getDefault().post(new EventModel<Integer>(EVENT.JUMP_TO_JXNUGO_USERINFO,userId));
                                        EventBus.getDefault().post(new EventModel<String>(EVENT.JXNUGO_LOGIN_SUCCESS));
                                    }
                                });
                            }
                            else{
                                Log.d(TAG,"错误信息："+entity.getError()+"\n"+entity.getMessage());
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post(new EventModel<String>(EVENT.JXNUGO_LOGIN_FAILURE));
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(String error) {
                            Log.d(TAG, "登录失败" + error);
                            Log.d(TAG,"账户信息"+getUsername(usernameText)+getPassword(passwordText));
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    EventBus.getDefault().post(new EventModel<String>(EVENT.JXNUGO_LOGIN_FAILURE_SEVER_ERROR));
                                }
                            });
                        }
                    });
        }
    }

    private static void saveToSP(String userAvatar,String token, String userName, String passWord,int userId) {
        SPUtil mysp = new SPUtil(MainActivity.AppContext);
        mysp.putStringSP(JxnuGoStaticKey.SP_FILE_NAME,JxnuGoStaticKey.USER_AVATAR,userAvatar);
        mysp.putStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.TOKEN, token);
        mysp.putStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERNAME, userName);
        mysp.putStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.PASSWORD, passWord);
        mysp.putIntSP(JxnuGoStaticKey.SP_FILE_NAME,JxnuGoStaticKey.USERID,userId);

    }

    /**
     * 这个方法是根据当前cookie是否存在来判断当前是否处于登陆状态【查询本地sp即可】
     *
     * @return
     */

    public static boolean isLogin() {
        SPUtil sp = new SPUtil(MainActivity.AppContext);
        userName=sp.getStringSP(JxnuGoStaticKey.SP_FILE_NAME,JxnuGoStaticKey.USERNAME);
        if (TextUtil.isNull(userName) == false) {
            Log.d(TAG,"Jxnugo已登录");
            passWord=sp.getStringSP(JxnuGoStaticKey.SP_FILE_NAME,JxnuGoStaticKey.PASSWORD);
            userAvatar=sp.getStringSP(JxnuGoStaticKey.SP_FILE_NAME,JxnuGoStaticKey.USER_AVATAR);
            return true;
        }
        return false;
    }

    public static void clearInfo(){
        SPUtil sp=new SPUtil(MainActivity.AppContext);
        sp.clearSP(JxnuGoStaticKey.SP_FILE_NAME);
        userName=null;
        passWord=null;
    }

}
