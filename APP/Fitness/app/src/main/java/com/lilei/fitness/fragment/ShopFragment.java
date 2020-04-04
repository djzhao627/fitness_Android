package com.lilei.fitness.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lilei.fitness.R;
import com.lilei.fitness.adapter.ShopAdapter;
import com.lilei.fitness.entity.Goods;
import com.lilei.fitness.utils.Constants;
import com.lilei.fitness.view.GoodsDetailActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by djzhao on 20/03/22.
 */

public class ShopFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView foundList;
    private ShopAdapter adapter;
    private Context mContext;

    private List<Goods> mList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shop, null);
        findViewById(v);
        initView();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        reLoadNews();
    }

    private void reLoadNews() {
        String url = Constants.BASE_URL + "Goods?method=getGoodsList";
        OkHttpUtils
                .post()
                .url(url)
                .id(1)
                .build()
                .execute(new MyStringCallback());
    }

    public void findViewById(View v) {
        foundList = (ListView) v.findViewById(R.id.found_list);
    }

    public void initView() {
        mContext = getActivity();
        foundList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), GoodsDetailActivity.class);
        intent.putExtra("goodsId", mList.get(position).getId());
        startActivity(intent);
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onResponse(String response, int id) {
            Gson gson = new Gson();
            try {
                Type type = new TypeToken<ArrayList<Goods>>() {
                }.getType();
                // String json = "[{\"id\": 1,\"title\":\"健身器\",\"image\":\"https://img9.doubanio.com/view/photo/s_ratio_poster/public/p2540924496.webp\",\"description\":\"健身器健身器健身器健身器健身器\",\"stock\":10,\"price\":12.5,\"integral\":20}]";
                mList = gson.fromJson(response, type);
            } catch (Exception e) {
                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                mList = null;
            }
            if (id == 1) {
                if (mList != null && mList.size() > 0) {
                    adapter = new ShopAdapter(mContext, mList);
                    foundList.setAdapter(adapter);
                }
            } else {
                Toast.makeText(mContext, "what！", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(Call arg0, Exception arg1, int arg2) {
            Toast.makeText(mContext, "网络链接出错！", Toast.LENGTH_SHORT).show();
        }
    }
}
