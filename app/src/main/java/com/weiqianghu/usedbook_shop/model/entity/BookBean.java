package com.weiqianghu.usedbook_shop.model.entity;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by weiqianghu on 2016/3/10.
 */
public class BookBean extends BmobObject implements Serializable{
    private String bookName;
    private String isbn;
    private double price;
    private String press;//出版社
    private String author;
    private String category;
    private boolean isSell;
    private int salesVolume;//销量
    private int stock;//库存
    private double percent;
    private String percentDescribe;
    private ShopBean shop;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isSell() {
        return isSell;
    }

    public void setSell(boolean sell) {
        isSell = sell;
    }

    public int getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(int salesVolume) {
        this.salesVolume = salesVolume;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public String getPercentDescribe() {
        return percentDescribe;
    }

    public void setPercentDescribe(String percentDescribe) {
        this.percentDescribe = percentDescribe;
    }

    public ShopBean getShop() {
        return shop;
    }

    public void setShop(ShopBean shop) {
        this.shop = shop;
    }
}
