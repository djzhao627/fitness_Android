package com.lilei.fitness.view;

import okhttp3.Call;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lilei.fitness.R;
import com.lilei.fitness.entity.User;
import com.lilei.fitness.utils.AppManager;
import com.lilei.fitness.utils.Constants;
import com.lilei.fitness.utils.MyDialogHandler;
import com.lilei.fitness.utils.SharedPreferencesUtils;
import com.lilei.fitness.view.base.BaseActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

public class RegisterActivity extends BaseActivity implements OnClickListener {
    private String TITLE_NAME = "注册";
    private View title_back;
    private TextView titleText;

    private Context mContext;
    private EditText et_username;
    private EditText et_password;
    private EditText et_repassword;
    private EditText et_height;
    private EditText et_weight;
    private Button register_login;
    private RadioGroup radio_sex;

    private MyDialogHandler uiFlusHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViewById();
        initView();
    }

    @Override
    protected void initView() {
        mContext = this;
        this.title_back.setOnClickListener(this);
        this.titleText.setText(TITLE_NAME);
        this.register_login.setOnClickListener(this);

        uiFlusHandler = new MyDialogHandler(mContext, "正在注册...");

    }

    @Override
    protected void findViewById() {
        this.title_back = $(R.id.title_back);
        this.titleText = $(R.id.titleText);

        et_username = $(R.id.reg_et_username);
        et_password = $(R.id.reg_et_password);
        et_repassword = $(R.id.reg_et_repassword);
        et_height = $(R.id.reg_et_height);
        et_weight = $(R.id.reg_et_weight);

        this.radio_sex = (RadioGroup) findViewById(R.id.radio_sex);
        this.register_login = (Button) findViewById(R.id.reg_btn_register);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back: {
                this.finish();
            }
            break;
            case R.id.reg_btn_register:
                register();
                break;
        }
    }

    private void register() {

        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String repassword = et_repassword.getText().toString().trim();
        String height = et_height.getText().toString().trim();
        String weight = et_weight.getText().toString().trim();
        String sex = "女";
        if (radio_sex.getCheckedRadioButtonId() == R.id.reg_rd_male) {
            sex = "男";
        }
        //d.判断用户名密码是否为空，不为空请求服务器（省略，默认请求成功）i
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword) || TextUtils.isEmpty(height) || TextUtils.isEmpty(weight)) {
            DisplayToast("信息不能为空");
            return;
        }
        // 判断两次密码
        if (!password.equals(repassword)) {
            DisplayToast("两次密码输入不一致");
            return;
        }
        uiFlusHandler.sendEmptyMessage(SHOW_LOADING_DIALOG);
        // 服务端验证
        String url = Constants.BASE_URL + "User?method=register";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("username", username)
                .addParams("password", password)
                .addParams("sex", sex)
                .addParams("height", height)
                .addParams("weight", weight)
                .id(1)
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {

        @Override
        public void onResponse(String response, int id) {
            Gson gson = new Gson();
            switch (id) {
                case 1:
                    uiFlusHandler.sendEmptyMessage(DISMISS_LOADING_DIALOG);
                    User user = null;
                    try {
                        user = gson.fromJson(response, User.class);
                    } catch (JsonSyntaxException e) {
                        user = null;
                    }
                    if (user == null) {
                        DisplayToast(response);
                        return;
                    } else {
                        // 存储用户
                        Constants.USER = user;
                        boolean result = SharedPreferencesUtils.saveUserInfo(mContext, user);
                        if (result) {
                            Toast.makeText(mContext, "注册成功，已经自动登录！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "用户名密码保存失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    openActivity(MainActivity.class);
                    AppManager.getInstance().killAllActivity();
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
