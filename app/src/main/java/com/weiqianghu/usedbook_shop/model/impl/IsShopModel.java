package com.weiqianghu.usedbook_shop.model.impl;

import com.weiqianghu.usedbook_shop.model.entity.UserBean;
import com.weiqianghu.usedbook_shop.model.inf.IIsShopModel;

/**
 * Created by weiqianghu on 2016/2/27.
 */
public class IsShopModel implements IIsShopModel {
    @Override
    public boolean isShop(UserBean user) {
        if(user==null){
            return false;
        }
        return user.isShop();
    }
}
