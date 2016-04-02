package com.weiqianghu.usedbook_shop.model.entity;

import java.io.Serializable;

import cn.bmob.newim.bean.BmobIMMessage;

/**
 * Created by weiqianghu on 2016/4/2.
 */
public class ChatMessageModel extends BmobIMMessage implements Serializable {
    private String fromImg;
    private String toImg;

    public String getFromImg() {
        return fromImg;
    }

    public void setFromImg(String fromImg) {
        this.fromImg = fromImg;
    }

    public String getToImg() {
        return toImg;
    }

    public void setToImg(String toImg) {
        this.toImg = toImg;
    }
}
