package com.weiqianghu.usedbook_shop.presenter;

import com.weiqianghu.usedbook_shop.model.entity.UserBean;
import com.weiqianghu.usedbook_shop.model.impl.IsShopModel;
import com.weiqianghu.usedbook_shop.model.inf.IIsShopModel;

/**
 * Created by weiqianghu on 2016/2/27.
 */
public class IsShopPresenter {
    private IIsShopModel mIsShopModel;
    public IsShopPresenter(){
        mIsShopModel=new IsShopModel();
    }

    public boolean isShop(UserBean user){
        return mIsShopModel.isShop(user);
    }
}
