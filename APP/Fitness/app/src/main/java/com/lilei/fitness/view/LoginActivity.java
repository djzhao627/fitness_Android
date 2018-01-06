package com.lilei.fitness.view;

import java.util.Map;

import okhttp3.Call;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends BaseActivity implements OnClickListener {
    private EditText et_username;
    private EditText et_password;

    private Button bt_login;
    private Button bt_register;
    private Context mContext;

    private MyDialogHandler uiFlusHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;

        findViewById();
        initView();
    }

    private void login() {
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        //d.判断用户名密码是否为空，不为空请求服务器（省略，默认请求成功）
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(mContext, "不可留空", Toast.LENGTH_SHORT).show();
            return;
        }
        // 服务端验证
        checkUser();
        // openActivity(MainActivity.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_bt_login:
                login();
                break;
            case R.id.login_bt_register:
                openActivity(RegisterActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            openActivity(ConfigActivity.class);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void findViewById() {
        et_username = $(R.id.login_et_username);
        et_password = $(R.id.login_et_password);

        bt_login = $(R.id.login_bt_login);
        bt_register = $(R.id.login_bt_register);
    }

    @Override
    protected void initView() {
        bt_login.setOnClickListener(this);
        bt_register.setOnClickListener(this);
        echo();
        uiFlusHandler = new MyDialogHandler(mContext, "登录中...");
    }

/**
 * 回显
 */
private void echo() {
    Map<String, String> map = SharedPreferencesUtils.getUserInfo(mContext);//获取用户名密码
    if (map != null) {
        String username = map.get("username");
        String password = map.get("password");
        et_username.setText(username);
        et_password.setText(password);
    }
}

    private void checkUser() {
        uiFlusHandler.sendEmptyMessage(SHOW_LOADING_DIALOG);

        String url = Constants.BASE_URL + "User?method=login";
        OkHttpUtils
                .post()
                .url(url)
                .id(1)
                .addParams("username", et_username.getText().toString().trim())
                .addParams("password", et_password.getText().toString().trim())
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
                    User user = gson.fromJson(response, User.class);
                    if (user.getUserId() == 0) {
                        DisplayToast("用户名或者密码错误");
                        return;
                    } else {
                        // 存储用户
                        Constants.USER = user;
                        boolean result = SharedPreferencesUtils.saveUserInfo(mContext, user);
                        if (result) {
                            Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "用户存储失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    openActivity(MainActivity.class);
                    finish();
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
