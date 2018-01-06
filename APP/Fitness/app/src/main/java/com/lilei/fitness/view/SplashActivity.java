package com.lilei.fitness.view;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.lilei.fitness.R;
import com.lilei.fitness.utils.Constants;
import com.lilei.fitness.utils.SharedPreferencesUtils;
import com.lilei.fitness.view.base.BaseActivity;
import com.lilei.fitness.view.test.VideoPlayer;

import java.util.Map;

public class SplashActivity extends BaseActivity implements View.OnClickListener {

    private Button goFitness;
    private ImageView bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        goFitness = $(R.id.btn_go_fitness);
        bg = $(R.id.splash_bg);
    }

    private void changeBackgroundImage() {
        switch ((int)(System.currentTimeMillis() % 8)) {
            case 0:
                bg.setImageResource(R.drawable.start1);
                break;
            case 1:
                bg.setImageResource(R.drawable.start2);
                break;
            case 2:
                bg.setImageResource(R.drawable.start3);
                break;
            case 3:
                bg.setImageResource(R.drawable.start4);
                break;
            case 4:
                bg.setImageResource(R.drawable.start5);
                break;
            case 5:
                bg.setImageResource(R.drawable.start6);
                break;
            case 6:
                bg.setImageResource(R.drawable.start7);
                break;
            case 7:
                bg.setImageResource(R.drawable.start8);
                break;
        }
    }

    @Override
    protected void initView() {
        goFitness.setOnClickListener(this);
        changeBackgroundImage();
        getConfig();
        jumpToLogin();
    }

    private void getConfig() {
        Map<String, String> ipConfig = SharedPreferencesUtils.getIPConfig(this);
        if (TextUtils.isEmpty(ipConfig.get("ip")) || TextUtils.isEmpty(ipConfig.get("port"))) {
            return;
        } else {
            Constants.BASE_URL = "http://" + ipConfig.get("ip") + ":" + ipConfig.get("port") + "/FitnessServer/";
        }
    }

    private void jumpToLogin() {
        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                openActivity(LoginActivity.class);
//                openActivity(MainActivity.class);
                finish();
            }
        }.start();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_go_fitness:
                openActivity(LoginActivity.class);
                this.finish();
                break;
        }
    }

}
