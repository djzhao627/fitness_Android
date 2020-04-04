package com.lilei.fitness.entity;

/**
 * Created by djzhao on 20/03/22.
 */

public class Goods {
    private int id;
    private String name;
    private String image;
    private String description;
    private int stock;
    private double price;
    private int integral;

    // [{"id": 1,"title":"健身器","imageUrl":"https://img9.doubanio.com/view/photo/s_ratio_poster/public/p2540924496.webp","description":"健身器健身器健身器健身器健身器","stock":10,"price":12.5,"integral":20}]

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }
}
