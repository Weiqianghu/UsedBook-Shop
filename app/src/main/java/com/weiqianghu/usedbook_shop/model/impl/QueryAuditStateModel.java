package com.weiqianghu.usedbook_shop.model.impl;

import android.content.Context;

import com.weiqianghu.usedbook_shop.model.entity.ShopBean;
import com.weiqianghu.usedbook_shop.model.entity.UserBean;
import com.weiqianghu.usedbook_shop.model.inf.IQueryAuditStateModel;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

/**
 * Created by weiqianghu on 2016/3/3.
 */
public class QueryAuditStateModel implements IQueryAuditStateModel {

    @Override
    public boolean queryAuditState(Context context, UserBean userBean, GetListener getListener) {

        ShopBean shop=userBean.getShop();
        if(shop!=null){
            String objectid=shop.getObjectId();
            BmobQuery<ShopBean> query=new BmobQuery<>();
            query.getObject(context, objectid,getListener);
        }

        return true;
    }

    @Override
    public int queryAuditState( UserBean userBean) {
        ShopBean shop=userBean.getShop();
        if(shop!=null){
            return shop.getVerifyState();
        }
        return -1;
    }
}
