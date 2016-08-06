package com.syn.mytestapp.Util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.support.design.widget.Snackbar;
import android.view.View;


import com.syn.mytestapp.activity.MainActivity;

/**
 * Created by 孙亚楠 on 2016/7/27.
 */
public class TextUtil {
    public static boolean isNull(String str){
        if(str == null || str.replaceAll("\\s*", "").equals("")){
            return true;
        }
        return false;
    }
    public static void copyToClipboard(View view, String info) {
        ClipboardManager cm = (ClipboardManager) MainActivity.AppContext.getSystemService(MainActivity.AppContext.CLIPBOARD_SERVICE);
        ClipData cd = ClipData.newPlainText("msg", info);
        cm.setPrimaryClip(cd);
        Snackbar.make(view, "Info copied to Clipboard! You can paste it to an edit area",Snackbar.LENGTH_SHORT).show();
    }


}
