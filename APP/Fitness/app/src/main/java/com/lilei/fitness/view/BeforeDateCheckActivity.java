package com.lilei.fitness.view;

import android.os.Bundle;
import android.os.SystemClock;

import com.lilei.fitness.R;
import com.lilei.fitness.utils.Constants;
import com.lilei.fitness.view.base.BaseActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by djzhao on 17/05/04.
 */

public class BeforeDateCheckActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_before_date_check);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {

    }

    @Override
    protected void initView() {
        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                getCheckedList();
            }
        }.start();
    }

    private void getCheckedList() {
        String url = Constants.BASE_URL + "DailyCheck?method=getCheckedList";
        OkHttpUtils
                .post()
                .url(url)
                .id(1)
                .addParams("userId", Constants.USER.getUserId() + "")
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onResponse(String response, int id) {
            SystemClock.sleep(1000);
            switch (id) {
                case 1:
                    if (response.contains("error")) {
                        DisplayToast("暂时无法获取数据");
                        finish();
                    } else {
                        String[] dates = response.split(",");
                        if (Constants.DAILYCHECKEDLIST == null) {
                            Constants.DAILYCHECKEDLIST = new ArrayList<String>();
                        } else {
                            Constants.DAILYCHECKEDLIST.clear();
                        }
                        for (String s: dates) {
                            String[] split = s.split("-");
                            s = split[0] + "-" + removeHeadingZero(split[1]) + "-" + removeHeadingZero(split[2]);
                            Constants.DAILYCHECKEDLIST.add(s);
                        }
                        openActivity(DateCheckActivity.class);
                        finish();
                    }
                    break;
            }
        }

        /**
         * 去除头部的0
         * @param str
         * @return
         */
        public String removeHeadingZero(String str) {
            if (str.startsWith("0")) {
                return str.substring(1);
            } else {
                return str;
            }
        }

        @Override
        public void onError(Call arg0, Exception arg1, int arg2) {
            uiFlusHandler.sendEmptyMessage(DISMISS_LOADING_DIALOG);
            DisplayToast("网络链接出错！");
        }
    }
}
