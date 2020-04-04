package com.lilei.fitness.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lilei.fitness.R;
import com.lilei.fitness.entity.Goods;
import com.lilei.fitness.utils.Constants;

import java.util.List;

/**
 * Created by djzhao on 20/03/22.
 */

public class ShopAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<Goods> mList;

    public ShopAdapter(Context mContext, List<Goods> mList) {
        this.inflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mList = mList;
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
    ViewHolder holder;
    if (convertView == null) {
        convertView = inflater.inflate(R.layout.item_shop_goods, null);
        holder = new ViewHolder();
        holder.image = (ImageView) convertView.findViewById(R.id.item_shop_image_iv);
        holder.title = (TextView) convertView.findViewById(R.id.item_shop_title_tv);
        holder.price = (TextView) convertView.findViewById(R.id.item_shop_price_tv);
        holder.integral = (TextView) convertView.findViewById(R.id.item_shop_integral_tv);
        convertView.setTag(holder);
    } else {
        holder = (ViewHolder) convertView.getTag();
    }

    // fill data
    Goods goods = mList.get(position);
    holder.title.setText(goods.getName());
    holder.price.setText("￥" + goods.getPrice());
    holder.integral.setText("积分: " + goods.getIntegral());
    String image = goods.getImage();
    if (image.startsWith("http")) {
        Glide.with(mContext).load(image).into(holder.image);
    } else {
        Glide.with(mContext).load(Constants.BASE_URL + "Download?method=getNewsImage&imageName=" + image).into(holder.image);
    }

    return convertView;
}

    private class  ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView price;
        public TextView integral;
    }
}
