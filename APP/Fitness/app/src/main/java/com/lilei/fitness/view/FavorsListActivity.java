package com.lilei.fitness.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lilei.fitness.R;
import com.lilei.fitness.adapter.NormalListAdapter;
import com.lilei.fitness.entity.NewsListItem;
import com.lilei.fitness.utils.Constants;
import com.lilei.fitness.utils.MyDialogHandler;
import com.lilei.fitness.view.base.BaseActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by djzhao on 17/05/04.
 */

public class FavorsListActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private String TITLE_NAME = "我的收藏";
    private View title_back;
    private TextView titleText;

    private Context mContext;

    private NormalListAdapter adapter;
    private List<NewsListItem> mList;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_normal_list);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        this.title_back = $(R.id.title_back);
        this.titleText = $(R.id.titleText);
        this.mListView = $(R.id.normal_list_lv);
    }

    @Override
    protected void initView() {
        mContext = this;
        this.titleText.setText(TITLE_NAME);
        uiFlusHandler = new MyDialogHandler(mContext, "加载中...");

        mListView.setOnItemClickListener(this);
        this.title_back.setOnClickListener(this);

        getComments();
    }

    /**
     * 获取评论
     */
    private void getComments() {
        uiFlusHandler.sendEmptyMessage(SHOW_LOADING_DIALOG);

        String url = Constants.BASE_URL + "Favor?method=getFavorsList";
        OkHttpUtils
                .post()
                .url(url)
                .id(1)
                .addParams("userId", Constants.USER.getUserId() + "")
                .build()
                .execute(new MyStringCallback());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onResponse(String response, int id) {
            Gson gson = new Gson();
            switch (id) {
                case 1:
                    uiFlusHandler.sendEmptyMessage(DISMISS_LOADING_DIALOG);
                    Type type = new TypeToken<ArrayList<NewsListItem>>() {
                    }.getType();
                    mList = gson.fromJson(response, type);
                    if (mList == null || mList.size() == 0) {
                        DisplayToast("暂无数据");
                        return;
                    } else {
                        // 存储用户
                        adapter = new NormalListAdapter(mContext, mList);
                        mListView.setAdapter(adapter);
                        // mListView.notify();
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
