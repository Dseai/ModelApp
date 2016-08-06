package com.syn.mytestapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.syn.mytestapp.API.JxnuGoApi;
import com.syn.mytestapp.R;
import com.syn.mytestapp.activity.GoodsDetailActivity;
import com.syn.mytestapp.entity.OneSimplePost;

import com.syn.mytestapp.Util.DisplayUtil;
import com.syn.mytestapp.Util.TimeUtil;
import com.syn.mytestapp.activity.MainActivity;
import com.syn.mytestapp.event.EVENT;
import com.syn.mytestapp.event.EventModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by 孙亚楠 on 2016/7/27.
 */
public class GoodsListAdapter extends RecyclerView.Adapter {
public static final String TAG="GoodsListAdapter";
private LayoutInflater mLayoutInflater;
private List<OneSimplePost> mList;
private Context mContext;
public GoodsListAdapter(Context context, List<OneSimplePost> mList){
        this.mContext=context;
        mLayoutInflater = LayoutInflater.from(context);
        this.mList=mList;
        }
@Override
public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(mLayoutInflater.inflate(R.layout.card_goods_list2, parent, false));
        }
    @Override
public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
final OneSimplePost model=mList.get(position);
        VH vh=(VH)holder;

        vh.time.setText(TimeUtil.getJxnuGoPassTime(model.getTimestamp()));
        Log.d(TAG,model.getTimestamp());
        RecyclerView.LayoutParams lp=(RecyclerView.LayoutParams)vh.itemView.getLayoutParams();
        lp.width= (int)(DisplayUtil.getScreenWidth(MainActivity.AppContext)*0.5);
        if(position%2==0){
        Log.d("当前的position为","--"+position);
        Log.d("对应名称为：","--"+model.getGoodsName());
        lp.rightMargin=0;
        lp.leftMargin=(int)(DisplayUtil.getScreenWidth(MainActivity.AppContext)*0.02);
        }else{
        lp.leftMargin=(int)(DisplayUtil.getScreenWidth(MainActivity.AppContext)*0.02);
        lp.rightMargin=(int)(DisplayUtil.getScreenWidth(MainActivity.AppContext)*0.02);
        }
        vh.goodsCard.setLayoutParams(lp);
        vh.goodName.setText(model.getGoodsName());
        vh.goodPrice.setText(model.getGoodsPrice()+"");
        if(model.getPhotos()!=null&&model.getPhotos().length>0){
        vh.goodFirstImg.setImageURI(Uri.parse(JxnuGoApi.BasePicUrl+ model.getPhotos()[0].getKey()+"?imageMogr2/thumbnail/2000x2000"));
        }
        else{
        vh.goodFirstImg.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        EventBus.getDefault().postSticky(new EventModel<OneSimplePost>(EVENT.GOODS_DETAIL_INTENT,model));
        Intent intent=new Intent();
        intent.setClass(mContext, GoodsDetailActivity.class);
        mContext.startActivity(intent);
        }
        });
        }

@Override
public int getItemCount() {
        return mList.size();
        }


static class VH extends RecyclerView.ViewHolder {
    View itemView;
    TextView time;
    TextView goodName;
    TextView goodPrice;
    SimpleDraweeView goodFirstImg;
    CardView goodsCard;
    public VH(View itemView) {
        super(itemView);
        this.itemView = itemView;
        time=(TextView)itemView.findViewById(R.id.goods_time);
        goodName=(TextView)itemView.findViewById(R.id.goods_title);
        goodPrice=(TextView)itemView.findViewById(R.id.goods_price);
        goodFirstImg=(SimpleDraweeView)itemView.findViewById(R.id.goods_image);
        goodsCard=(CardView) itemView.findViewById(R.id.card_view);
    }
}

}
