package com.weiqianghu.usedbook_shop.presenter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.weiqianghu.usedbook_shop.model.entity.ShopBean;
import com.weiqianghu.usedbook_shop.model.entity.UserBean;
import com.weiqianghu.usedbook_shop.model.impl.QueryAuditStateModel;
import com.weiqianghu.usedbook_shop.model.inf.IQueryAuditStateModel;
import com.weiqianghu.usedbook_shop.util.Constant;

import cn.bmob.v3.listener.GetListener;

/**
 * Created by weiqianghu on 2016/3/3.
 */
public class QueryAuditStatePresenter extends CommonPresenter {
    private IQueryAuditStateModel mQueryAuditStateModel;


    public QueryAuditStatePresenter(Handler handler) {
        super(handler);
        mQueryAuditStateModel = new QueryAuditStateModel();
    }

    public int queryAuditState(UserBean userBean) {
        return mQueryAuditStateModel.queryAuditState(userBean);
    }

    public void queryAuditState(Context context, UserBean userBean) {
        GetListener<ShopBean> getListener = new GetListener<ShopBean>() {
            @Override
            public void onSuccess(ShopBean shopBean) {
                Bundle bundle=new Bundle();
                bundle.putInt(Constant.AUDIT_STATE,shopBean.getVerifyState());
                handleSuccess(bundle);
            }

            @Override
            public void onFailure(int i, String s) {
                handleFailureMessage(i, s);
            }
        };
        mQueryAuditStateModel.queryAuditState(context,userBean,getListener);
    }
}
