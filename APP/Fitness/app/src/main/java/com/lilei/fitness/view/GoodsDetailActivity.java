package com.lilei.fitness.view;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lilei.fitness.R;
import com.lilei.fitness.entity.Goods;
import com.lilei.fitness.utils.Constants;
import com.lilei.fitness.utils.MyDialogHandler;
import com.lilei.fitness.view.base.BaseActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by djzhao on 20/03/22.
 */

public class GoodsDetailActivity extends BaseActivity implements View.OnClickListener {

    private String TITLE_NAME = "商品详情";
    private View title_back;
    private TextView titleText;

    private ImageView goodsIV;

    private TextView usernameTV;
    private TextView titleTV;
    private TextView stockTV;

    private TextView priceTV;
    private TextView integralTV;

    private LinearLayout commentLL;
    private LinearLayout favorLL;

    private Context mContext;

    private int goodsId;
    private Goods goods;

    private MyDialogHandler uiFlusHandler;

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        goodsId = getIntent().getIntExtra("goodsId", 0);
        setContentView(R.layout.activity_goods_detail);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        this.title_back = $(R.id.title_back);
        this.titleText = $(R.id.titleText);

        goodsIV = $(R.id.goods_detail_image);

        usernameTV = $(R.id.news_detail_username);
        titleTV = $(R.id.detail_title);

        priceTV = $(R.id.goods_detail_price);
        integralTV = $(R.id.goods_detail_integral);
        stockTV = $(R.id.goods_detail_stock);

        commentLL = $(R.id.news_detail_add_comment);
        favorLL = $(R.id.news_detail_add_favor);
    }

    @Override
    protected void initView() {
        mContext = this;
        this.titleText.setText(TITLE_NAME);

        this.title_back.setOnClickListener(this);
        commentLL.setOnClickListener(this);
        favorLL.setOnClickListener(this);

        uiFlusHandler = new MyDialogHandler(mContext, "加载中...");
        refreshData();
    }

    private void refreshData() {
        uiFlusHandler.sendEmptyMessage(SHOW_LOADING_DIALOG);
        String url = Constants.BASE_URL + "Goods?method=getGoodsDetail";
        OkHttpUtils
                .post()
                .url(url)
                .id(1)
                .addParams("id", goodsId + "")
                .build()
                .execute(new MyStringCallback());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            // 积分购买
            case R.id.news_detail_add_favor:
                addNewFavor();
                break;
            // 直接购买
            case R.id.news_detail_add_comment:
                addNewComment();
                break;
        }
    }

    // 直接购买
    private void addNewComment() {
        int stock = goods.getStock();
        if (stock < 1) {
            DisplayToast("库存不足");
            return;
        }
        if (Constants.USER.getWallet() < goods.getPrice()) {
            DisplayToast("余额不足");
            return;
        }
        uiFlusHandler.sendEmptyMessage(SHOW_LOADING_DIALOG);
        String url = Constants.BASE_URL + "Order?method=submitOrder";
        OkHttpUtils
                .post()
                .url(url)
                .id(3)
                .addParams("goodsId", goodsId + "")
                .addParams("userId", Constants.USER.getUserId() + "")
                .addParams("totalPrice", String.valueOf(goods.getPrice()))
                .addParams("payType", "0")
                .build()
                .execute(new MyStringCallback());
    }

    // 积分购买
    private void addNewFavor() {
        int stock = goods.getStock();
        if (stock < 1) {
            DisplayToast("库存不足");
            return;
        }
        if (Constants.USER.getIntegral() < goods.getIntegral()) {
            DisplayToast("积分不足");
            return;
        }
        uiFlusHandler.sendEmptyMessage(SHOW_LOADING_DIALOG);
        String url = Constants.BASE_URL + "Order?method=submitOrder";
        OkHttpUtils
                .post()
                .url(url)
                .id(2)
                .addParams("goodsId", goodsId + "")
                .addParams("userId", Constants.USER.getUserId() + "")
                .addParams("totalPrice", String.valueOf(goods.getIntegral()))
                .addParams("payType", "1")
                .build()
                .execute(new MyStringCallback());
    }

    // 积分购买
    public class MyStringCallback extends StringCallback {
        @Override
        public void onResponse(String response, int id) {
            uiFlusHandler.sendEmptyMessage(DISMISS_LOADING_DIALOG);
            switch (id) {
                case 1:
                    Gson gson = new Gson();
                    try {
                        goods = gson.fromJson(response, Goods.class);
                        if (goods != null) {
                            usernameTV.setText(goods.getName());
                            titleTV.setText(goods.getDescription());
                            priceTV.setText("￥ " + goods.getPrice());
                            integralTV.setText("积分：" + goods.getIntegral());
                            stockTV.setText("库存：" + goods.getStock());
                            String image = goods.getImage();
                            if (image.startsWith("http")) {
                                Glide.with(GoodsDetailActivity.this).load(image).into(goodsIV);
                            } else {
                                Glide.with(GoodsDetailActivity.this).load(Constants.BASE_URL + "Download?method=getNewsImage&imageName=" + image).into(goodsIV);
                            }
                        }

                    } catch (Exception e) {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                case 3:
                    if (response.contains("success")) {
                        DisplayToast("购买成功");
                        if (id == 2) {
                            Constants.USER.setIntegral(Constants.USER.getIntegral() - goods.getIntegral());
                        } else {
                            Constants.USER.setWallet(Constants.USER.getWallet() - goods.getPrice());
                        }
                        finish();
                    } else {
                        DisplayToast(response);
                    }
                    break;
                default:
                    Toast.makeText(mContext, "what！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onError(Call arg0, Exception arg1, int arg2) {
            Toast.makeText(mContext, "网络链接出错！", Toast.LENGTH_SHORT).show();
        }
    }
}
