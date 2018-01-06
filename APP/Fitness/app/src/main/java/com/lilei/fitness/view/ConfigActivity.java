package com.lilei.fitness.view;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lilei.fitness.R;
import com.lilei.fitness.utils.Constants;
import com.lilei.fitness.utils.SharedPreferencesUtils;
import com.lilei.fitness.view.base.BaseActivity;

import java.util.Map;

/**
 * Created by djzhao on 17/05/01.
 */

public class ConfigActivity extends BaseActivity implements View.OnClickListener {

    private String TITLE_NAME = "配置";
    private View title_back;
    private TextView titleText;

    private Button saveButton;
    private EditText ipEditText;
    private EditText portEditText;

    private Context mContext;

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_config);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        this.title_back = $(R.id.title_back);
        this.titleText = $(R.id.titleText);

        saveButton = $(R.id.config_btn_save);
        ipEditText = $(R.id.config_et_ip);
        portEditText = $(R.id.config_et_port);
    }

    @Override
    protected void initView() {
        mContext = this;
        this.titleText.setText(TITLE_NAME);
        this.title_back.setOnClickListener(this);
        saveButton.setOnClickListener(this);

        // 回显
        echo();
    }

    /**
     * 数据回显
     */
    private void echo() {
        Map<String, String> map = SharedPreferencesUtils.getIPConfig(mContext);
        ipEditText.setText(map.get("ip"));
        portEditText.setText(map.get("port"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back: {
                this.finish();
            }
            break;
            case R.id.config_btn_save:
                saveConfig();
                break;
        }
    }

    private void saveConfig() {
        String ip = ipEditText.getText().toString().trim();
        String port = portEditText.getText().toString().trim();
        if (TextUtils.isEmpty(ip) || TextUtils.isEmpty(port)) {
            DisplayToast("请勿留空！");
            return;
        }
        SharedPreferencesUtils.saveIPConfig(mContext, ip, port);
        Constants.BASE_URL = "http://" + ip + ":" + port + "/FitnessServer/";
        DisplayToast(Constants.BASE_URL);
        this.finish();
    }
}
