package com.weiqianghu.usedbook_shop.model.impl;

import android.content.Context;


import com.weiqianghu.usedbook_shop.model.entity.UserBean;
import com.weiqianghu.usedbook_shop.model.inf.IIsLoginModel;

import cn.bmob.v3.BmobUser;

/**
 * Created by 胡伟强 on 2016/1/28.
 */
public class IsLoginModel implements IIsLoginModel {

    @Override
    public boolean isLogin(Context context) {
        UserBean userBean=  BmobUser.getCurrentUser(context,UserBean.class);
        return userBean!=null;
    }
}
