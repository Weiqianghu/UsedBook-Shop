package com.weiqianghu.usedbook_shop.model.inf;

import android.content.Context;

import com.weiqianghu.usedbook_shop.model.entity.UserBean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.GetListener;

/**
 * Created by weiqianghu on 2016/3/3.
 */
public interface IQueryAuditStateModel<T extends BmobObject> {
    boolean queryAuditState(Context context, UserBean userBean,GetListener<T> getListener);
    int queryAuditState( UserBean userBean);
}
