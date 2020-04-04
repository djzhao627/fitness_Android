package com.lilei.fitness.entity;

import java.util.Date;

/**
 * 用户订单数据实体类
 *
 * @author djzhao
 * @date 20/03/27 10:59
 * @email djzhao627@gmail.com
 */
public class UserOrderVO {
    /**
     * 主键
     */
    private int id;

    /**
     * 下单的用户ID
     */
    private int userId;

    /**
     * 用户姓名
     */
    private String username;

    /**
     * 关联的商品ID
     */
    private String goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品图片
     */
    private String goodsImage;

    /**
     * 订单总价
     */
    private Double totalPrice;

    /**
     * 支付方式 0余额 1积分
     */
    private int payType;

    /**
     * 下单时间
     */
    private Date createTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态 0已下单1已发货
     */
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
