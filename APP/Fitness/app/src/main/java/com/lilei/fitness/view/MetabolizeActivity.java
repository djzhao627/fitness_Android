package com.lilei.fitness.view;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lilei.fitness.R;
import com.lilei.fitness.utils.Constants;
import com.lilei.fitness.utils.MyDialogHandler;
import com.lilei.fitness.view.base.BaseActivity;

/**
 * Created by djzhao on 20/03/29.
 */

public class MetabolizeActivity extends BaseActivity implements View.OnClickListener {

    private String TITLE_NAME = "基础代谢率";
    private View title_back;
    private TextView titleText;

    private Context mContext;
    private RadioGroup sexGroup;
    private RadioButton maleRadio;
    private RadioButton femaleRadio;

    private EditText height;
    private EditText weight;
    private EditText age;

    private TextView metabolizeResult;
    private TextView weightResult;

    private Button update;

    private String heightStr;
    private String weightStr;
    private String ageStr;
    private String sex;

    private MyDialogHandler uiFlusHandler;

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_metabolize);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        this.title_back = $(R.id.title_back);
        this.titleText = $(R.id.titleText);
        sexGroup = $(R.id.homepage_radio_sex);
        update = $(R.id.homepage_btn_update);
        height = $(R.id.homepage_et_height);
        weight = $(R.id.homepage_et_weight);
        age = $(R.id.homepage_et_age);
        metabolizeResult = $(R.id.metabolize_amount_text);
        weightResult = $(R.id.metabolize_weight_text);

        maleRadio = $(R.id.homepage_rd_male);
        femaleRadio = $(R.id.homepage_rd_female);
    }

    @Override
    protected void initView() {
        mContext = this;
        this.titleText.setText(TITLE_NAME);

        this.title_back.setOnClickListener(this);
        update.setOnClickListener(this);
        uiFlusHandler = new MyDialogHandler(mContext, "更新中...");
        echo();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back: {
                this.finish();
            }
            break;
            case R.id.homepage_btn_update:
                checkInfo();
                break;
        }
    }

    private void echo() {
        height.setText(Constants.USER.getHeight() + "");
        weight.setText(Constants.USER.getWeight() + "");
        age.setText(Constants.USER.getAge() + "");
        if (Constants.USER.getSex().equals("女")) {
            femaleRadio.setChecked(true);
        } else {
            maleRadio.setChecked(true);
        }
    }

    private void checkInfo() {
        heightStr = height.getText().toString().trim();
        weightStr = weight.getText().toString().trim();
        ageStr = age.getText().toString().trim();
        sex = "男";
        if (sexGroup.getCheckedRadioButtonId() == R.id.homepage_rd_female) {
            sex = "女";
        }

        if (TextUtils.isEmpty(heightStr) || TextUtils.isEmpty(weightStr) || TextUtils.isEmpty(ageStr)) {
            DisplayToast("不可留空！");
            return;
        }

        update();

    }

    private void update() {
        int result = 0;
        double weight = 0;
        try {
            if ("男".equals(sex)) {
                result = (int) (67 + 13.73 * Double.parseDouble(weightStr) + 5 * Double.parseDouble(heightStr) - 6.9 * Integer.parseInt(ageStr));
                weight = (Double.parseDouble(heightStr) - 80) * 0.7;
            } else {
                result = (int) (661 + 9.6 * Double.parseDouble(weightStr) + 1.72 * Double.parseDouble(heightStr) - 4.7 * Integer.parseInt(ageStr));
                weight = (Double.parseDouble(heightStr) - 70) * 0.6;
            }
        } catch (NumberFormatException e) {
            DisplayToast("计算出错");
        }
        this.metabolizeResult.setText(String.valueOf(result));
        this.weightResult.setText(String.valueOf(weight));
    }
}

