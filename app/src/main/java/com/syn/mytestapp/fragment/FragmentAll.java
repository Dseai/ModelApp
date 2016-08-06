package com.syn.mytestapp.fragment;

/**
 * Created by 孙亚楠 on 2016/7/23.
 */


import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;


import android.widget.Toast;

import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.squareup.okhttp.Headers;
import com.syn.mytestapp.InternetRequest.JsonEntityCallback;

import com.syn.mytestapp.Util.JxnuGoLoginUtil;
import com.syn.mytestapp.entity.GoodsListBean;
import com.syn.mytestapp.entity.OneSimplePost;

import com.syn.mytestapp.Util.LoadingFooter;
import com.syn.mytestapp.InternetRequest.NetManageUtil;
import com.syn.mytestapp.Util.RecyclerViewStateUtils;
import com.syn.mytestapp.activity.MainActivity;
import com.syn.mytestapp.adapter.GoodsListAdapter;
import com.syn.mytestapp.Util.SPUtil;
import com.syn.mytestapp.db.post.JxnuGoStaticKey;
import com.syn.mytestapp.event.EVENT;
import com.syn.mytestapp.event.EventModel;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;




/**
 *全部
 */
public class FragmentAll extends BaseListFragment{
    public static final String TAG="FragmentAll";
    private static int TOTAL_COUNTER;
    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;
    //每一页展示多少数据
    private static final int REQUEST_COUNT = 16;
    //已经获取到多少条数据了
    private int mCurrentCounter = 0;

    private OneSimplePost oneSimplePost;
    private ArrayList<OneSimplePost> mlist;
    private View view;
    private GoodsListAdapter goodsListAdapter;

    private String nextPage;

    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter = null;
    @Override
    public String getTitle() {
        return "所有商品";
    }
    @Override
    public void onDataRefresh() {
        oneSimplePost.loadFromNet();
    }

    @Override
    public void bindAdapter() {}


    @Override
    public void addHeader() {}
    @Override
    public void initView(){

        layoutManager=new GridLayoutManager(MainActivity.AppContext,2);
        recyclerView.setLayoutManager(layoutManager);
        oneSimplePost=new OneSimplePost();
        mlist=new ArrayList<>();
        setOnLineLayout(JxnuGoLoginUtil.isLogin());
        if(JxnuGoLoginUtil.isLogin()){
            oneSimplePost.loadFromCache();
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
            mlist.add(g);
        mCurrentCounter += list.size();
    }
    private void notifyDataSetChanged() {
        mHeaderAndFooterRecyclerViewAdapter.notifyDataSetChanged();
    }
    private void loadNextPage(List<GoodsListBean> tempNextList){
        nextPage=tempNextList.get(0).getNext();
        ArrayList<OneSimplePost> tempNextAL=new ArrayList<>();
        for(int i=0;i<tempNextList.get(0).getPosts().length;i++)
            tempNextAL.add(tempNextList.get(0).getPosts()[i]);
        addItems(tempNextAL);
        notifyDataSetChanged();
    }
    @Override
    public void onEventComing(EventModel eventModel) {
        super.onEventComing(eventModel);
        switch (eventModel.getEventCode()){
            case EVENT.GOODS_LIST_REFRESH_SUCCESS:
                initData(eventModel.getDataList());

                hideLoading();
                break;
            case EVENT.GOODS_LIST_REFRESH_FAILURE:
                hideLoading();
                displayNetworkError();
                break;
            case EVENT.FINISH_GOODS_SEND:
                oneSimplePost.loadFromCache();
                displayLoading();
                break;
//            case EVENT.GOODS_LIST_NEXTPAGE_REFRESH_SUCCESS:
//                loadNextPage(eventModel.getDataList());
//                hideLoading();
//                break;
//            case EVENT.GOODS_LIST_NEXTPAGE_REFRESH_FAILURE:
//                hideLoading();
//                break;
        }
    }
    private void initData(List<GoodsListBean> tempList) {
        mlist.clear();
        OneSimplePost[] g=tempList.get(0).getPosts();
        nextPage=tempList.get(0).getNext();
        TOTAL_COUNTER=tempList.get(0).getCount();
       // Log.d(TAG,"取得信息数为："+TOTAL_COUNTER);
        if(TOTAL_COUNTER==0){
           Toast.makeText(MainActivity.AppContext,"暂无商品",Toast.LENGTH_SHORT).show();
        }
        ArrayList<OneSimplePost> tempAL=new ArrayList<>();
        for(int i=0;i<g.length;i++)
            tempAL.add(g[i]);
        addItems(tempAL);
        goodsListAdapter=new GoodsListAdapter(getContext(),mlist);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(goodsListAdapter);
        recyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        recyclerView.addOnScrollListener(mOnScrollListener);
        mCurrentCounter=mlist.size();
    }
    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerViewStateUtils.setFooterViewState(getActivity(), recyclerView, REQUEST_COUNT, LoadingFooter.State.Loading, null);
            requestData();
        }
    };
    private com.cundong.recyclerview.EndlessRecyclerOnScrollListener mOnScrollListener = new com.cundong.recyclerview.EndlessRecyclerOnScrollListener() {

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
        SPUtil spu = new SPUtil(MainActivity.AppContext);
        String userName = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERNAME);
        String password = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.PASSWORD);
        NetManageUtil.getAuth(nextPage)
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
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    loadNextPage(list);
                                }
                            });
                        }
                    }
                });
    }


}








