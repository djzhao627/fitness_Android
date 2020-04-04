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
import com.lilei.fitness.entity.UserOrderVO;
import com.lilei.fitness.utils.Constants;

import java.util.List;

/**
 * Created by djzhao on 20/04/04.
 */

public class OrdersListAdapter extends BaseAdapter {

    private Context mContext;

    private LayoutInflater inflater;

    private List<UserOrderVO> mList;

    public OrdersListAdapter(Context mContext, List<UserOrderVO> mList) {
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
            convertView = inflater.inflate(R.layout.item_orders, null);
            viewHolder = new ViewHolder();
            viewHolder.goodsName = (TextView) convertView.findViewById(R.id.item_normal_title);
            viewHolder.totalPrice = (TextView) convertView.findViewById(R.id.item_normal_username);
            viewHolder.status = (TextView) convertView.findViewById(R.id.item_orders_status);
            viewHolder.goodsImage = (ImageView) convertView.findViewById(R.id.item_goods_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // fill data
        UserOrderVO detail = mList.get(position);
        viewHolder.goodsName.setText(detail.getGoodsName());
        if (detail.getPayType() == 0) {
            viewHolder.totalPrice.setText("￥ " + detail.getTotalPrice());
        } else {
            viewHolder.totalPrice.setText("积分：" + detail.getTotalPrice());
        }
        String image = detail.getGoodsImage();
        if (image != null) {
            if (image.startsWith("http")) {
                Glide.with(mContext).load(image).into(viewHolder.goodsImage);
            } else {
                Glide.with(mContext).load(Constants.BASE_URL + "Download?method=getNewsImage&imageName=" + image).into(viewHolder.goodsImage);
            }
        }
        viewHolder.status.setText(detail.getStatus() == 0 ? "已下单" : "已发货");
        return convertView;
    }

    private class ViewHolder {
        public TextView goodsName;
        public TextView totalPrice;
        public TextView status;
        public ImageView goodsImage;
    }
}
