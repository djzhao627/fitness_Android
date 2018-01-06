package com.lilei.fitness.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lilei.fitness.R;
import com.lilei.fitness.entity.NewsListItem;

import java.util.List;

/**
 * Created by djzhao on 17/05/04.
 */

public class NormalListAdapter extends BaseAdapter {

    private Context mContext;

    private LayoutInflater inflater;

    private List<NewsListItem> mList;

    public NormalListAdapter(Context mContext, List<NewsListItem> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_normal, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.item_normal_title);
            viewHolder.username = (TextView) convertView.findViewById(R.id.item_normal_username);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // fill data
        NewsListItem detail = mList.get(position);
        viewHolder.title.setText(detail.getTitle());
        viewHolder.username.setText(detail.getUsername());
        return convertView;
    }

    private class ViewHolder {
        public TextView title;
        public TextView username;
    }
}
