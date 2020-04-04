package com.lilei.fitness.view;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.lilei.fitness.R;
import com.lilei.fitness.utils.Constants;
import com.lilei.fitness.utils.MyDialogHandler;
import com.lilei.fitness.view.base.BaseActivity;

/**
 * Created by djzhao on 20/04/04.
 */

public class CalorieActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private final int[] MIMIAN = {0, 1420, 1520, 932, 1134, 1524, 1424, 1524, 1120};
    private final int[] DOUZHI = {0, 295, 780, 175, 1912, 340};
    private final int[] MEAT = {0, 783, 781, 185, 1402, 525, 537, 382, 441, 722, 373, 840, 840, 440, 463, 440, 1063, 391, 1290, 631, 575, 560};
    private final int[] DRINK = {0, 285, 177, 140};
    private final int[] SHUCAI = {0, 66, 96, 96, 1000, 113, 1265, 753, 150, 398, 1112, 72, 76, 88, 76, 1600, 88, 54, 100, 80, 100, 2504, 1328};

    private String TITLE_NAME = "卡路里计算";
    private View title_back;
    private TextView titleText;

    private Context mContext;
    private RadioGroup sexGroup;
    private RadioButton maleRadio;
    private RadioButton femaleRadio;

    private Spinner mimianS;
    private Spinner douzhiS;
    private Spinner meatS;
    private Spinner drinkS;
    private Spinner shucaiS;

    private EditText weight;
    private EditText age;

    private TextView metabolizeResult;
    private TextView weightResult;
    private TextView formula;

    private boolean isFormulaExpand = false;

    private Button update;

    private String weightStr;
    private String ageStr;
    private String sex;

    private MyDialogHandler uiFlusHandler;

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_calorie);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        this.title_back = $(R.id.title_back);
        this.titleText = $(R.id.titleText);
        sexGroup = $(R.id.homepage_radio_sex);
        update = $(R.id.homepage_btn_update);
        weight = $(R.id.homepage_et_weight);
        formula = $(R.id.formula);
        age = $(R.id.homepage_et_age);
        metabolizeResult = $(R.id.metabolize_amount_text);
        weightResult = $(R.id.metabolize_weight_text);

        mimianS = $(R.id.select_mimain);
        douzhiS = $(R.id.select_douzhi);
        meatS = $(R.id.select_meat);
        drinkS = $(R.id.select_drink);
        shucaiS = $(R.id.select_shucai);

        maleRadio = $(R.id.homepage_rd_male);
        femaleRadio = $(R.id.homepage_rd_female);
    }

    @Override
    protected void initView() {
        mContext = this;
        this.titleText.setText(TITLE_NAME);

        this.title_back.setOnClickListener(this);
        update.setOnClickListener(this);
        formula.setOnClickListener(this);
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
            case R.id.formula:
                if (isFormulaExpand) {
                    formula.setText(R.string.calorie_formula_detail_show);
                    formula.setGravity(Gravity.CENTER);
                } else {
                    formula.setText(R.string.calorie_formula_detail);
                    formula.setGravity(Gravity.START);
                }
                isFormulaExpand = !isFormulaExpand;
                break;
            default:
                break;
        }
    }

    private void echo() {
        weight.setText(Constants.USER.getWeight() + "");
        age.setText(Constants.USER.getAge() + "");
        if (Constants.USER.getSex().equals("女")) {
            femaleRadio.setChecked(true);
        } else {
            maleRadio.setChecked(true);
        }
    }

    private void checkInfo() {
        weightStr = weight.getText().toString().trim();
        ageStr = age.getText().toString().trim();
        sex = "男";
        if (sexGroup.getCheckedRadioButtonId() == R.id.homepage_rd_female) {
            sex = "女";
        }

        if (TextUtils.isEmpty(weightStr) || TextUtils.isEmpty(ageStr)) {
            DisplayToast("不可留空！");
            return;
        }

        update();

    }

    private void update() {
        double need = 0;
        int age = Integer.parseInt(ageStr);
        double weight = Double.parseDouble(weightStr);
        try {
            if ("男".equals(sex)) {
                if (age >= 10 && age <= 17) {
                    need = (17.5 * weight + 651) * 1.78;
                } else if (age >= 18 && age <= 29) {
                    need = (15.3 * weight + 679) * 1.78;
                } else if (age >= 30 && age <= 59) {
                    need = (11.6 * weight + 879) * 1.78;
                } else {
                    need = (13.5 * weight + 487) * 1.78;
                }
            } else {
                if (age >= 10 && age <= 17) {
                    need = (12.2 * weight + 746) * 1.64;
                } else if (age >= 18 && age <= 29) {
                    need = (14.7 * weight + 496) * 1.64;
                } else if (age >= 30 && age <= 59) {
                    need = (8.7 * weight + 829) * 1.64;
                } else {
                    need = (10.5 * weight + 596) * 1.64;
                }
            }
        } catch (NumberFormatException e) {
            DisplayToast("计算出错");
        }
        this.metabolizeResult.setText(String.valueOf((int) need));

        int select = 0;
        select = MIMIAN[mimianS.getSelectedItemPosition()] +
                DOUZHI[douzhiS.getSelectedItemPosition()] +
                MEAT[meatS.getSelectedItemPosition()] +
                DRINK[drinkS.getSelectedItemPosition()] +
                SHUCAI[shucaiS.getSelectedItemPosition()];

        this.weightResult.setText(String.valueOf(select));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (view.getId()) {
            case R.id.select_mimain:
                break;
            case R.id.select_douzhi:
                break;
            case R.id.select_meat:
                break;
            case R.id.select_drink:
                break;
            case R.id.select_shucai:
                break;
        }
    }
}

