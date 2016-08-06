package com.syn.mytestapp.fragment;

/**
 * Created by 孙亚楠 on 2016/7/23.
 */
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import com.cundong.recyclerview.EndlessRecyclerOnScrollListener;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.squareup.okhttp.Headers;
import com.syn.mytestapp.API.JxnuGoApi;
import com.syn.mytestapp.InternetRequest.JsonEntityCallback;
import com.syn.mytestapp.InternetRequest.NetManageUtil;
import com.syn.mytestapp.Util.JxnuGoLoginUtil;
import com.syn.mytestapp.Util.LoadGoodsListUtil;
import com.syn.mytestapp.Util.LoadingFooter;
import com.syn.mytestapp.Util.RecyclerViewStateUtils;
import com.syn.mytestapp.Util.SPUtil;
import com.syn.mytestapp.activity.MainActivity;
import com.syn.mytestapp.adapter.GoodsListAdapter;
import com.syn.mytestapp.db.post.JxnuGoStaticKey;
import com.syn.mytestapp.entity.GoodsListBean;
import com.syn.mytestapp.entity.JsonCodeEntityCallback;
import com.syn.mytestapp.entity.OneSimplePost;
import com.syn.mytestapp.event.EVENT;
import com.syn.mytestapp.event.EventModel;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生活用品
 */
public class FragmentSupplies extends BaseListFragment {
    public static final String TAG="CategoryListFragment";
    private static int TOTAL_COUNTER;
    /**每一页展示多少条数据*/
    private static final int REQUEST_COUNT = 16;
    /**已经获取到多少条数据了*/
    private int mCurrentCounter = 0;
    private OneSimplePost goodsModel;
    private ArrayList<OneSimplePost> mList;
    private GoodsListAdapter goodsListAdapter;
    private String nexPage;



    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter = null;
    @Override
    public String getTitle() {
        return "图书音像";
    }
    private static String getUserName(Context context){
        SPUtil sp=new SPUtil(context);
        return sp.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERNAME);
    }
    private static String getPassword(Context context){
        SPUtil sp=new SPUtil(context);
        return sp.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.PASSWORD);
    }
    @Override
    public void onDataRefresh() {
        LoadGoodsListUtil.getTagGoodsList(MainActivity.AppContext,0);
    }

    @Override
    public void bindAdapter() {}




    @Override
    public void initView() {

        layoutManager=new GridLayoutManager(MainActivity.AppContext,2);
        recyclerView.setLayoutManager(layoutManager);
        goodsModel=new OneSimplePost();
        mList=new ArrayList<>();
        setOnLineLayout(JxnuGoLoginUtil.isLogin());
        if(JxnuGoLoginUtil.isLogin()){
//            goodsModel.loadFromCache();
            LoadGoodsListUtil.getTagGoodsList(MainActivity.AppContext,2);
            displayLoading();
        }
        toLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventModel<String>(EVENT.JUMP_TO_JXNUGO_LOGIN));
            }
        });
    }

    private void addItems(ArrayList<OneSimplePost> list) {
        for(OneSimplePost g:list)
            mList.add(g);
        mCurrentCounter += list.size();
    }
    private void notifyDataSetChanged() {
        mHeaderAndFooterRecyclerViewAdapter.notifyDataSetChanged();
    }
    @Override
    public void onEventComing(EventModel eventModel) {
        super.onEventComing(eventModel);

        switch (eventModel.getEventCode()){
            case EVENT.JXNUGO_TAG_GOODS_LIST_REFRESH_SUCCESS:
                initData(eventModel.getDataList());
                hideLoading();
                break;
            case EVENT.JXNUGO_TAG_GOODS_LIST_REFRESH_FAILURE:
                hideLoading();
                break;

        }
    }

    /**
     * LoadNextPage DATA
     * @param tempNextList
     */
    private void loadNextPage(List<GoodsListBean> tempNextList){
        nexPage=tempNextList.get(0).getNext();
        ArrayList<OneSimplePost> tempNextAL=new ArrayList<>();
        for(int i=0;i<tempNextList.get(0).getPosts().length;i++)
            tempNextAL.add(tempNextList.get(0).getPosts()[i]);
        addItems(tempNextAL);
        notifyDataSetChanged();
    }

    /**
     * FrstLoad DATA
     * @param tempList
     */
    private void initData(List<GoodsListBean> tempList) {
        Log.d(TAG,"初始化分类列表");
        mList.clear();
        OneSimplePost[] g=tempList.get(0).getPosts();
        nexPage=tempList.get(0).getNext();
        TOTAL_COUNTER=tempList.get(0).getCount();
        Log.d(TAG,"取得总数为"+TOTAL_COUNTER);
        if(TOTAL_COUNTER==0){
            tip.setText("暂无该类别的二手商品");
            tip.setVisibility(View.VISIBLE);
        }else{
            tip.setVisibility(View.GONE);
        }
        ArrayList<OneSimplePost> tempAL=new ArrayList<>();
        for(int i=0;i<g.length;i++)
            tempAL.add(g[i]);
        addItems(tempAL);
        goodsListAdapter=new GoodsListAdapter(getContext(),mList);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(goodsListAdapter);
        recyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        recyclerView.addOnScrollListener(mOnScrollListener);
        mCurrentCounter=mList.size();
    }


    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerViewStateUtils.setFooterViewState(getActivity(), recyclerView, REQUEST_COUNT, LoadingFooter.State.Loading, null);
            requestData();
        }
    };
    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);

            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(recyclerView);
            if(state == LoadingFooter.State.Loading) {
                Log.d(TAG, "the state is Loading, just wait..");
//                return;
            }

            if (mCurrentCounter < TOTAL_COUNTER) {
                // loading more

                RecyclerViewStateUtils.setFooterViewState(getActivity(), recyclerView, REQUEST_COUNT, LoadingFooter.State.Loading, null);
                requestData();
            } else {
                //the end
                RecyclerViewStateUtils.setFooterViewState(getActivity(), recyclerView, REQUEST_COUNT, LoadingFooter.State.TheEnd, null);
            }
        }
    };


    /**
     * 请求新数据
     */
    private void requestData() {
        final Handler handler = new Handler(Looper.getMainLooper());
//        Log.d(TAG,"请求数据");
        SPUtil spu = new SPUtil(MainActivity.AppContext);
        String userName = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERNAME);
        String password = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.PASSWORD);
//        mHandler.sendEmptyMessage(-1);
//        Log.d(TAG,"正在加载的页面为："+nexPage);
        NetManageUtil.getAuth(nexPage)
                .addUserName(userName)
                .addPassword(password)
                .addTag(TAG)
                .enqueue(new JsonEntityCallback<GoodsListBean>() {
                    @Override
                    public void onFailure(IOException e) {
                        Log.e(TAG,e.toString());
//                        EventBus.getDefault().post(new EventModel<GoodsListBean>(EVENT.GOODS_LIST_NEXTPAGE_REFRESH_FAILURE));
                    }

                    @Override
                    public void onSuccess(GoodsListBean entity, Headers headers) {
                        if (entity != null) {
                            final List<GoodsListBean> list = Arrays.asList(entity);
//                            loadNextPage(list);
                            Log.d(TAG,"当前mCurrentCounter "+mCurrentCounter);
                            Log.d(TAG,"当前TOTAL_COUNTER "+TOTAL_COUNTER);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
//                                    EventBus.getDefault().post(new EventModel<GoodsListBean>(EVENT.GOODS_LIST_NEXTPAGE_REFRESH_SUCCESS, list));
                                    loadNextPage(list);
                                }
                            });
                        } else {
//                            EventBus.getDefault().post(new EventModel<GoodsListBean>(EVENT.GOODS_LIST_NEXTPAGE_REFRESH_FAILURE));
                        }
                    }
                });
    }


}



