package com.lilei.fitness.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lilei.fitness.R;
import com.lilei.fitness.entity.User;
import com.lilei.fitness.utils.Constants;
import com.lilei.fitness.utils.MyDialogHandler;
import com.lilei.fitness.utils.SharedPreferencesUtils;
import com.lilei.fitness.view.base.BaseActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.aigestudio.datepicker.bizs.calendars.DPCManager;
import cn.aigestudio.datepicker.bizs.decors.DPDecor;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;
import okhttp3.Call;

/**
 * Created by djzhao on 17/05/01.
 */

public class DateCheckActivity extends BaseActivity implements View.OnClickListener {

    private String TITLE_NAME = "每日打卡";
    private View title_back;
    private TextView titleText;

    private Context mContext;
    private DatePicker picker;
    private Button btnPick;

    private MyDialogHandler uiFlusHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 全屏
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_date_check);
        findViewById();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // getDailyCheck();
        echoChecked();
    }

    /**
     * 获取签到记录
     */
    private void getDailyCheck() {
        uiFlusHandler.sendEmptyMessage(SHOW_LOADING_DIALOG);
        String url = Constants.BASE_URL + "DailyCheck?method=getCheckedList";
        OkHttpUtils
                .post()
                .url(url)
                .id(2)
                .addParams("userId", Constants.USER.getUserId() + "")
                .build()
                .execute(new MyStringCallback());
    }

    @Override
    protected void findViewById() {
        this.title_back = $(R.id.title_back);
        this.titleText = $(R.id.titleText);

        picker = (DatePicker) findViewById(R.id.date_date_picker);
        btnPick = (Button) findViewById(R.id.date_btn_check);
    }

    @Override
    protected void initView() {
        mContext = this;
        this.title_back.setOnClickListener(this);
        this.titleText.setText(TITLE_NAME);
        btnPick.setOnClickListener(this);
        uiFlusHandler = new MyDialogHandler(mContext, "刷新数据...");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                this.finish();
                break;
            case R.id.date_btn_check:
                todayCheck();
                break;
        }
    }

    /**
     * 今日打卡
     */
    private void todayCheck() {
        /* 日期弹窗选择
        final AlertDialog dialog = new AlertDialog.Builder(DateCheckActivity.this).create();
        dialog.show();
        DatePicker picker = new DatePicker(DateCheckActivity.this);
        picker.setDate(2015, 10);
        picker.setMode(DPMode.SINGLE);
        picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                Toast.makeText(DateCheckActivity.this, date, Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setContentView(picker, params);
        dialog.getWindow().setGravity(Gravity.CENTER);
        */
        uiFlusHandler.setTip("正在打卡...");
        uiFlusHandler.sendEmptyMessage(SHOW_LOADING_DIALOG);
        String url = Constants.BASE_URL + "DailyCheck?method=check";
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
            uiFlusHandler.sendEmptyMessage(DISMISS_LOADING_DIALOG);
            switch (id) {
                case 1:
                    if (response.contains("success")) {
                        DisplayToast("今日打卡成功");
                    } else {
                        DisplayToast(response);
                    }
                    break;
                case 2:
                    if (response.contains("error")) {
                        DisplayToast("暂时无法获取数据");
                    } else {
                        String[] dates = response.split(",");
                        for (String s: dates) {
//                            dailyCheckedList.add(s);
                        }
                    }
                    break;
            }
        }

        @Override
        public void onError(Call arg0, Exception arg1, int arg2) {
            uiFlusHandler.sendEmptyMessage(DISMISS_LOADING_DIALOG);
            DisplayToast("网络链接出错！");
        }
    }

    /**
     * 已经打卡数据展示
     */
    public void echoChecked() {
/*
        // 默认多选模式
        DatePicker picker = (DatePicker) findViewById(R.id.main_dp);
        picker.setDate(2015, 7);
        picker.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
            @Override
            public void onDateSelected(List<String> date) {
                String result = "";
                Iterator iterator = date.iterator();
                while (iterator.hasNext()) {
                    result += iterator.next();
                    if (iterator.hasNext()) {
                        result += "\n";
                    }
                }
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
            }
        });

         自定义背景绘制示例 Example of custom date's background
        List<String> tmp = new ArrayList<>();
        tmp.add("2015-7-1");
        tmp.add("2015-7-8");
        tmp.add("2015-7-16");
        DPCManager.getInstance().setDecorBG(tmp);

        DatePicker picker = (DatePicker) findViewById(R.id.main_dp);
        picker.setDate(2015, 7);
        picker.setDPDecor(new DPDecor() {
            @Override
            public void drawDecorBG(Canvas canvas, Rect rect, Paint paint) {
                paint.setColor(Color.RED);
                canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2F, paint);
            }
        });
        picker.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
            @Override
            public void onDateSelected(List<String> date) {
                String result = "";
                Iterator iterator = date.iterator();
                while (iterator.hasNext()) {
                    result += iterator.next();
                    if (iterator.hasNext()) {
                        result += "\n";
                    }
                }
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
            }
        });
        */

        // 自定义前景装饰物绘制示例 Example of custom date's foreground decor
        // 绘制倒左边
        /*List<String> tmpTL = new ArrayList<>();
        tmpTL.add("2015-10-5");
        tmpTL.add("2015-10-6");
        tmpTL.add("2015-10-7");
        tmpTL.add("2015-10-8");
        tmpTL.add("2015-10-9");
        tmpTL.add("2015-10-10");

        DPCManager.getInstance().setDecorTL(tmpTL);*/

        // 绘制到右边
        /*List<String> dailyCheckedList = new ArrayList<String>();
        dailyCheckedList.add("2017-5-1");
        dailyCheckedList.add("2016-10-11");
        dailyCheckedList.add("2015-10-12");
        dailyCheckedList.add("2017-10-13");
        dailyCheckedList.add("2015-10-14");
        dailyCheckedList.add("2014-10-15");
        dailyCheckedList.add("2017-10-16");
        DPCManager.getInstance().setDecorTR(dailyCheckedList);*/
        DPCManager.getInstance().setDecorTR(Constants.DAILYCHECKEDLIST);

        Calendar today = Calendar.getInstance();

        picker.setDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH) + 1);
        picker.setFestivalDisplay(false);
        picker.setTodayDisplay(true);
        picker.setHolidayDisplay(false);
        picker.setDeferredDisplay(false);
        picker.setMode(DPMode.NONE);
        picker.setDPDecor(new DPDecor() {
            @Override
            public void drawDecorTL(Canvas canvas, Rect rect, Paint paint, String data) {
                super.drawDecorTL(canvas, rect, paint, data);
                switch (data) {
                    case "2015-10-5":
                    case "2015-10-7":
                    case "2015-10-9":
                    case "2015-10-11":
                        paint.setColor(Color.GREEN);
                        // canvas.drawRect(rect, paint);
                        BitmapDrawable bmpDraw = (BitmapDrawable) getResources().getDrawable(R.drawable.icon_location_checked);
                        Bitmap bmp = bmpDraw.getBitmap();
                        canvas.drawBitmap(bmp, 10, 10, paint);
                        break;
                    default:
                        paint.setColor(Color.RED);
                        canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2, paint);
                        break;
                }
            }

            @Override
            public void drawDecorTR(Canvas canvas, Rect rect, Paint paint, String data) {
                super.drawDecorTR(canvas, rect, paint, data);
                /*
                switch (data) {
                    case "2015-10-10":
                    case "2015-10-11":
                    case "2015-10-12":
                        paint.setColor(Color.RED);
                        canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2, paint);
                        break;
                    default:
                        paint.setColor(Color.YELLOW);
                        canvas.drawRect(rect, paint);
                        break;
                }*/
                paint.setColor(Color.RED);
                canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2, paint);
            }
        });
//        picker.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
//            @Override
//            public void onDateSelected(List<String> date) {
//                String result = "";
//                Iterator iterator = date.iterator();
//                while (iterator.hasNext()) {
//                    result += iterator.next();
//                    if (iterator.hasNext()) {
//                        result += "\n";
//                    }
//                }
//                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
//            }
//        });
    }
}
