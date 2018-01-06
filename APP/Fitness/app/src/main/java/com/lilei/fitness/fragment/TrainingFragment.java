package com.lilei.fitness.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lilei.fitness.R;
import com.lilei.fitness.view.VideoPlayer;

/**
 * Created by djzhao on 17/04/30.
 */

public class TrainingFragment extends Fragment implements View.OnClickListener {

    private LinearLayout baseTraning;
    private LinearLayout enhanceTraning;
    private LinearLayout acmeTraning;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_train, null);
        findViewById(v);
        initView();

        return v;
    }

    public void findViewById(View v) {
        baseTraning = (LinearLayout) v.findViewById(R.id.train_base);
        enhanceTraning = (LinearLayout) v.findViewById(R.id.train_enhance);
        acmeTraning = (LinearLayout) v.findViewById(R.id.train_acme);
    }

    public void initView() {
        baseTraning.setOnClickListener(this);
        enhanceTraning.setOnClickListener(this);
        acmeTraning.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), VideoPlayer.class);
        switch (v.getId()) {
            case R.id.train_base:
                intent.putExtra("tag", 1);
                startActivity(intent);
                break;
            case R.id.train_enhance:
                intent.putExtra("tag", 2);
                startActivity(intent);
                break;
            case R.id.train_acme:
                intent.putExtra("tag", 3);
                startActivity(intent);
                break;
        }
    }
}
