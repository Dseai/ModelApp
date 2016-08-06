package com.syn.mytestapp.activity;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.syn.mytestapp.R;
import com.syn.mytestapp.Util.GoodTagSpinnerWrapper;
import com.syn.mytestapp.Util.OnGoodTagChangedListener;
import com.syn.mytestapp.Util.SPUtil;
import com.syn.mytestapp.Util.TextUtil;
import com.syn.mytestapp.Util.UploadGoodsUtil;
import com.syn.mytestapp.adapter.ChoosePicAdapter;
import com.syn.mytestapp.db.post.JxnuGoStaticKey;
import com.syn.mytestapp.entity.GoodsPhotoModel;
import com.syn.mytestapp.entity.PhotokeyBean;
import com.syn.mytestapp.entity.PublishGood;
import com.syn.mytestapp.event.EVENT;
import com.syn.mytestapp.event.EventModel;
import com.syn.mytestapp.support.FrescoImageLoader;
import com.syn.mytestapp.support.Settings;
import com.tendcloud.tenddata.TCAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.galleryfinal.widget.HorizontalListView;

/**
 * Created by 孙亚楠 on 2016/8/5.
 */
public class PubishGoodsActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "PublishGoodsActivity";
    private static final String title = "上传物品";
    private final int REQUEST_CODE_GALLERY = 1001;
    private int goodTag = 4;//默认其它
    private List<GoodsPhotoModel> mPhotoList;
    private ArrayList<String> photoKeys = new ArrayList<>();
    private ChoosePicAdapter adapter;
    private HorizontalListView picListView;
    private AppCompatButton addPicButton;
    private TextView finishText;
    private EditText goodNameET;
    private EditText yearET;
    private EditText monthET;
    private EditText dayET;
    private EditText priceET;
    private EditText amountET;
    private EditText positionET;
    private EditText contactET;
    private EditText discribtionET;
    private EditText qualityET;
    private AppCompatButton doneBackButton;
    private LinearLayout uploadingLayout,doneLayout;
    private ScrollView infoScrollview;
    private MenuItem sendMenuItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        TCAgent.onPageStart(MainActivity.AppContext, TAG);
        setContentView(R.layout.activity_jxnugo_new_goods);
        initView();
        bindAdapter();
        addTagSpinner();//添加spinner下拉列表框

    }

    private void addTagSpinner() {
        GoodTagSpinnerWrapper spinnerWrapper = new GoodTagSpinnerWrapper();
        spinnerWrapper.setOnTagChangedListener(new OnGoodTagChangedListener() {
            @Override
            public void onTagChanged(int tag) {
                goodTag = tag;
            }
        });
        spinnerWrapper.build((MaterialSpinner) findViewById(R.id.spinner));
    }


    private void initView() {
        picListView = (HorizontalListView) findViewById(R.id.lv_photo);
        addPicButton = (AppCompatButton) findViewById(R.id.addPic);
        goodNameET=(EditText)findViewById(R.id.title);
        yearET=(EditText)findViewById(R.id.year);
        monthET=(EditText)findViewById(R.id.month);
        dayET=(EditText)findViewById(R.id.day);
        priceET=(EditText)findViewById(R.id.price);
        amountET=(EditText)findViewById(R.id.count);
        positionET=(EditText)findViewById(R.id.pos);
        contactET=(EditText)findViewById(R.id.contact);
        discribtionET=(EditText)findViewById(R.id.description);
        qualityET=(EditText)findViewById(R.id.quality);
        infoScrollview=(ScrollView)findViewById(R.id.content);
        uploadingLayout=(LinearLayout)findViewById(R.id.uploading);
        doneLayout=(LinearLayout)findViewById(R.id.doneUploading);
        doneBackButton=(AppCompatButton)findViewById(R.id.doneBackButton);
        addPicButton.setOnClickListener(this);
        doneBackButton.setOnClickListener(this);
    }

    private void bindAdapter() {
        mPhotoList = new ArrayList<>();
        adapter = new ChoosePicAdapter(PubishGoodsActivity.this, mPhotoList);
        picListView.setAdapter(adapter);
    }

    private void callImageSelector() {
       ThemeConfig themeConfig = setImageSelectorTheme();
        FunctionConfig.Builder functionConfigBuilder = new FunctionConfig.Builder();
        cn.finalteam.galleryfinal.ImageLoader imageLoader;
        imageLoader = new FrescoImageLoader(PubishGoodsActivity.this);
        functionConfigBuilder.setMutiSelectMaxSize(6);
        if(mPhotoList.size()>0){
            List <PhotoInfo> tempPhotolist=new ArrayList<>();
            for(int i=0;i<mPhotoList.size();i++){
                PhotoInfo p=new PhotoInfo();
                p.setHeight(mPhotoList.get(i).getHeight());
                p.setWidth(mPhotoList.get(i).getWidth());
                p.setPhotoId(mPhotoList.get(i).getPhotoId());
                p.setPhotoPath(mPhotoList.get(i).getPhotoPath());
                tempPhotolist.add(p);
            }
            functionConfigBuilder.setSelected(tempPhotolist);
        }
        functionConfigBuilder.setEnableEdit(false);
        functionConfigBuilder.setEnableCamera(true);
        functionConfigBuilder.setEnablePreview(true);

        final FunctionConfig functionConfig = functionConfigBuilder.build();
        File fileDir = new File("/sdcard/DCIM/syn的App/");
        CoreConfig coreConfig = new CoreConfig.Builder(PubishGoodsActivity.this, imageLoader, themeConfig)
                .setFunctionConfig(functionConfig)
                .setTakePhotoFolder(fileDir)
                .setNoAnimcation(true)
                .build();
        GalleryFinal.init(coreConfig);
        GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
        initFresco();
    }

    /**
     * 判斷是否輸入完成了必要信息
     * 必要信息：商品图片，商品名称，价格，联系方式，基本的描述
     * @return
     */
    private boolean completeInput(){
        if(mPhotoList.size()>0&&!TextUtil.isNull(goodNameET.getText().toString())
                &&!TextUtil.isNull(contactET.getText().toString())
                &&!TextUtil.isNull(priceET.getText().toString())
                &&!TextUtil.isNull(discribtionET.getText().toString()))return true;
        else        return false;
    }

    /**
     * 设置多图选择器的主题
     * @return
     */
    private ThemeConfig setImageSelectorTheme() {
        ThemeConfig.Builder thBuilder = new ThemeConfig.Builder();
        ThemeConfig theme = null;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            TypedArray array = getTheme().obtainStyledAttributes(new int[]{
                    android.R.attr.colorPrimary, android.R.attr.colorPrimaryDark
            });
            theme = thBuilder.setTitleBarBgColor(array.getColor(0, 0x000000))
                    .setFabNornalColor(array.getColor(0, 0x000000))
                    .setFabPressedColor(array.getColor(array.getIndex(1), 0x000000))
                    .setCheckSelectedColor(array.getColor(0, 0x000000))
                    .setCropControlColor(array.getColor(0, 0x000000)).build();
            array.recycle();
        } else {
            theme = theme.DARK;
        }
        return theme;
    }


    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                mPhotoList.clear();
                for (int i = 0; i < resultList.size(); i++) {
                    mPhotoList.add(new GoodsPhotoModel(resultList.get(i).getPhotoId(),
                            resultList.get(i).getPhotoPath(),
                            resultList.get(i).getWidth(),
                            resultList.get(i).getHeight()));
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    };



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addPic:     //添加图片
                callImageSelector();     //调用图片选择函数
                break;
            case R.id.doneBackButton:    //完成上传，即上传成功
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new EventModel<Void>(EVENT.FINISH_GOODS_SEND));
                    }
                });

                this.finish();
                break;
        }
    }


    private void initFresco() {
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setBitmapsConfig(Bitmap.Config.ARGB_8888)
                .build();
        Fresco.initialize(this, config);
    }
/**
 * 编辑菜单
 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_newgoods, menu);
        sendMenuItem=menu.findItem(R.id.menu_new_goods_done);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_new_goods_done:
                if(!completeInput()){
                    Snackbar.make(getCurrentFocus(), "请上传",Snackbar.LENGTH_SHORT).show();
                }
                else{
                    sendMenuItem.setVisible(false);
                    setLayoutVisible(1);//设置显示内容上传中
                    if(mPhotoList.size()>0)
                        UploadGoodsUtil.onUploadImages(PubishGoodsActivity.this, mPhotoList);
                    else
                        uploadData(null);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void uploadData(ArrayList<PhotokeyBean> keys) {
        if(keys==null){
            keys=new ArrayList<>();
        }
        SPUtil spu = new SPUtil(MainActivity.AppContext);
        int userId = spu.getIntSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERID);
        int amount=amountET.getText().toString().equals("")?1:Integer.parseInt(amountET.getText().toString());
        final PublishGood bean = new PublishGood(
                userId+""
                ,discribtionET.getText().toString()
                , goodNameET.getText().toString()
                , amount
                , Float.parseFloat(priceET.getText().toString())
                , positionET.getText().toString()
                , qualityET.getText().toString()
                , yearET.getText().toString() + "-"
                + monthET.getText().toString() + "-" +
                dayET.getText().toString()
                , Integer.parseInt(String.valueOf(goodTag))
                , contactET.getText().toString()
                , keys,
                Settings.getJxnugoAuthToken());

        UploadGoodsUtil.onUploadJson(bean, this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void uploadPost(EventModel eventModel) {
        switch (eventModel.getEventCode()) {
            case EVENT.GOODS_IMAGES_UPLOAD_SUCCESS:
                ArrayList<PhotokeyBean> keys = (ArrayList<PhotokeyBean>) eventModel.getData();
                uploadData(keys);
                break;
            case EVENT.GOODS_IMAGES_UPLOAD_FAIL:
                break;
            case EVENT.POST_UPLOAD_SUCCESS:
//                DisplayUtil.Snack(getCurrentFocus(), "upload success");
                setLayoutVisible(2);
                break;
            case EVENT.POST_UPLOAD_FAIL:
                setLayoutVisible(2);
                finishText=(TextView)findViewById(R.id.finishtext);
                finishText.setText("服务器出错，发布失败");

                break;
        }
    }

    /**
     * 设置界面显示模式
     * 参数为模式
     * 0表示待发送界面
     * 1表示发送中的界面
     * 2表示发送完成界面
     * @param mode
     */
    private void setLayoutVisible(int mode){
        switch (mode){
            case 0:
                infoScrollview.setVisibility(View.VISIBLE);
                uploadingLayout.setVisibility(View.GONE);
                doneLayout.setVisibility(View.GONE);
                break;
            case 1:
                infoScrollview.setVisibility(View.GONE);
                uploadingLayout.setVisibility(View.VISIBLE);
                doneLayout.setVisibility(View.GONE);
                break;
            case 2:
                infoScrollview.setVisibility(View.GONE);
                uploadingLayout.setVisibility(View.GONE);
                doneLayout.setVisibility(View.VISIBLE);
                break;
        }
    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        TCAgent.onPageEnd(MainActivity.AppContext, TAG);
        super.onDestroy();
    }

}



