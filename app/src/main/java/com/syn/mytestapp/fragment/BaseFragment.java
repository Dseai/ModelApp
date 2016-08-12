package com.syn.mytestapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syn.mytestapp.R;
import com.syn.mytestapp.event.EventModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by 孙亚楠 on 2016/7/29.
 */
public abstract class BaseFragment extends Fragment {

    protected View parentView = null;

    private void loadConfig(){

    }

    protected abstract void init();
    public abstract String getTitle();
    public abstract void onEventComing(EventModel eventModel);

    protected abstract int getLayoutID();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = View.inflate(getContext(), R.layout.layout_common_list,null);
        //init();
        loadConfig();
        return parentView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEventMainThread(EventModel eventModel){
        if(eventModel != null){
            onEventComing(eventModel);
        }
    }
}

