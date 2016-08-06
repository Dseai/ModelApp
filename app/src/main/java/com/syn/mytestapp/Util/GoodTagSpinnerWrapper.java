package com.syn.mytestapp.Util;

import android.content.Context;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.syn.mytestapp.activity.MainActivity;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 孙亚楠 on 2016/8/6.
 */
public class GoodTagSpinnerWrapper {
    private Context mContext = MainActivity.AppContext;
    private OnGoodTagChangedListener listener;
    private int index;
    public String[] tag=new String[]{   //0生活用品 1数码科技 2服饰箱包 3图书音像 4其它

            "生活用品",    //0
            "数码科技",    //1
            "服饰箱包",    //2
            "图书音像",    //3
            "其他"  ,        //4


    };

    public MaterialSpinner build(MaterialSpinner spinner) {
        List<String> tagList = Arrays.asList(tag);
        spinner.setItems(tagList);
        spinner.setSelectedIndex(0);//
        if (listener != null) {
            spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                    listener.onTagChanged(position);
                }
            });
        }
        return spinner;
    }

    public void setOnTagChangedListener(OnGoodTagChangedListener listener) {
        this.listener = listener;

    }

    public void setIndex(int index) {
        this.index = index;
    }
}


