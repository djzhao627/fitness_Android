package com.lilei.fitness.widgets;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/**
 * Created by djzhao on 17/05/02.
 */

public class LinearLayoutForListView extends LinearLayout {

    private BaseAdapter adapter;
    private OnClickListener onClickListener = null;

    public LinearLayoutForListView(Context context) {
        super(context);
    }

    public void bindLinearLayout() {
        int count = adapter.getCount();
        this.removeAllViews();
        for (int i = 0; i < count; i++) {
            View v = adapter.getView(i, null, null);
            v.setOnClickListener(this.onClickListener);
            addView(v, i);
        }
        Log.v("countTAG", "" + count);
    }

    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
    }
}
