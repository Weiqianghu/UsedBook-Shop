package com.weiqianghu.usedbook_shop.model.entity;

import java.util.List;

/**
 * Created by weiqianghu on 2016/3/25.
 */
public class OrderModel {
    private OrderBean orderBean;
    private List<BookImgsBean> bookImgs;


    public OrderBean getOrderBean() {
        return orderBean;
    }

    public void setOrderBean(OrderBean orderBean) {
        this.orderBean = orderBean;
    }

    public List<BookImgsBean> getBookImgs() {
        return bookImgs;
    }

    public void setBookImgs(List<BookImgsBean> bookImgs) {
        this.bookImgs = bookImgs;
    }
}
