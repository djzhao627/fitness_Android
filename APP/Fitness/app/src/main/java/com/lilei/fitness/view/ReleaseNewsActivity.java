package com.lilei.fitness.view;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lilei.fitness.R;
import com.lilei.fitness.entity.User;
import com.lilei.fitness.utils.Constants;
import com.lilei.fitness.utils.MyDialogHandler;
import com.lilei.fitness.utils.SharedPreferencesUtils;
import com.lilei.fitness.view.base.BaseActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.define.Define;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by djzhao on 17/05/01.
 */

public class ReleaseNewsActivity extends BaseActivity implements View.OnClickListener {

    private String TITLE_NAME = "新鲜事";
    private View title_back;
    private TextView titleText;

    private Context mContext;

    private EditText title;
    private EditText content;
    private ImageView photo;
    private Button release;

    private ArrayList<Uri> path;

    private File imageFile;

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_add_news);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        this.title_back = $(R.id.title_back);
        this.titleText = $(R.id.titleText);
        this.title = $(R.id.add_news_et_share_title);
        this.content = $(R.id.add_news_et_share_content);
        this.photo = $(R.id.add_news_iv_photo);
        this.release = $(R.id.add_news_btn_release);
    }

    @Override
    protected void initView() {
        mContext = this;
        this.titleText.setText(TITLE_NAME);

        this.title_back.setOnClickListener(this);
        this.photo.setOnClickListener(this);
        this.release.setOnClickListener(this);
        uiFlusHandler = new MyDialogHandler(mContext, "登录中...");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back: {
                this.finish();
            }
            break;
            case R.id.add_news_iv_photo:
                getPhoto();
                break;
            case R.id.add_news_btn_release:
                checkInfo();
                break;
        }
    }

    /**
     *
     */
    private void checkInfo() {
        String titleStr = title.getText().toString();
        String contentStr = content.getText().toString();
        if (TextUtils.isEmpty(titleStr)) {
            DisplayToast("请输入一个标题");
            title.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(contentStr)) {
            DisplayToast("请输入新鲜事");
            content.requestFocus();
            return;
        }
        releaseNews();
    }

    private void releaseNews() {
        String titleStr = title.getText().toString();
        String contentStr = content.getText().toString();

        uiFlusHandler.sendEmptyMessage(DISMISS_LOADING_DIALOG);
        String url;

        /*AsyncHttpClient httpClient = new AsyncHttpClient();

        RequestParams param = new RequestParams();
        try {
            param.put("file", imageFile);
            param.put("content", contentStr);
            param.put("title", titleStr);
            param.put("userId", Constants.USER.getUserId() + "");

            httpClient.post(url, param, new AsyncHttpResponseHandler() {
                @Override
                public void onStart() {
                    uiFlusHandler.sendEmptyMessage(SHOW_LOADING_DIALOG);
                    super.onStart();
                }

                @Override
                public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {
                    uiFlusHandler.sendEmptyMessage(DISMISS_LOADING_DIALOG);
                    DisplayToast("success");
                }

                @Override
                public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable) {
                    DisplayToast("failure");
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/


        if (imageFile != null && imageFile.exists()) {
            url = Constants.BASE_URL + "News?method=releaseNewsWithImage";
            OkHttpUtils
                    .post()
                    .addFile("image", imageFile.getName(), imageFile)
                    .url(url)
                    .id(1)
                    .addHeader("content-Type", "multipart/form-data; boundary=" + UUID.randomUUID().toString())
                    .addParams("title", titleStr)
                    .addParams("content", contentStr)
                    .addParams("userId", Constants.USER.getUserId() + "")
                    .build()
                    .execute(new MyStringCallback());
        } else {
            url = Constants.BASE_URL + "News?method=releaseNewsWithoutImage";
            OkHttpUtils
                    .post()
                    .url(url)
                    .id(1)
                    .addParams("title", titleStr)
                    .addParams("content", contentStr)
                    .addParams("userId", Constants.USER.getUserId() + "")
                    .build()
                    .execute(new MyStringCallback());
        }
    }

    private void getPhoto() {
        FishBun.with(ReleaseNewsActivity.this)
                .setPickerCount(5) //Deprecated
                .setMaxCount(1)
                .setMinCount(1)
                .setPickerSpanCount(4)
                .setActionBarColor(Color.parseColor("#DB4A37"), Color.parseColor("#DB4A37"), false)
                .setActionBarTitleColor(Color.parseColor("#ffffff"))
                .setAlbumSpanCount(2, 4)
                .setButtonInAlbumActivity(false)
                .setCamera(true)
                .setReachLimitAutomaticClose(true)
                .setAllViewTitle("所有图片")
                .setActionBarTitle("图片库")
                .textOnImagesSelectionLimitReached("已选数量受限！")
                .textOnNothingSelected("你还没有选择图片哟~")
                .startAlbum();
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageData) {
        super.onActivityResult(requestCode, resultCode, imageData);
        switch (requestCode) {
            case Define.ALBUM_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
//                    path = imageData.getStringArrayListExtra(Define.INTENT_PATH);

                    //You can get image path(ArrayList<String>) Under version 0.6.2

                    path = imageData.getParcelableArrayListExtra(Define.INTENT_PATH);

                    Uri uri = path.get(0);

                    photo.setImageURI(uri);

                    String[] proj = {MediaStore.Images.Media.DATA};

                    Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);

                    int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                    actualimagecursor.moveToFirst();

                    String img_path = actualimagecursor.getString(actual_image_column_index);

                    imageFile = new File(img_path);

                    //You can get image path(ArrayList<Uri>) Version 0.6.2 or later
                    break;
                }
        }
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onResponse(String response, int id) {
            uiFlusHandler.sendEmptyMessage(DISMISS_LOADING_DIALOG);
            Gson gson = new Gson();
            switch (id) {
                case 1:
                    if (response.contains("success")) {
                        DisplayToast("新鲜事发布成功");
                        finish();
                    } else {
                        DisplayToast("请稍后再试");
                    }
                    break;
                default:
                    DisplayToast("what?");
                    break;
            }
        }

        @Override
        public void onError(Call arg0, Exception arg1, int arg2) {
            DisplayToast("网络链接出错！");
        }
    }
}
