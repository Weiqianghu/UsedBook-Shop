package com.weiqianghu.usedbook_shop.presenter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.weiqianghu.usedbook_shop.model.entity.UserBean;
import com.weiqianghu.usedbook_shop.model.impl.QueryModel;
import com.weiqianghu.usedbook_shop.model.inf.IQueryModel;
import com.weiqianghu.usedbook_shop.util.Constant;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

/**
 * Created by weiqianghu on 2016/3/26.
 */
public class QueryUserPresenter extends CommonPresenter {
    private IQueryModel<UserBean> mQueryModel;

    public QueryUserPresenter(Handler handler) {
        super(handler);
        mQueryModel = new QueryModel<UserBean>();
    }

    public void QueryUser(Context context, String objectId) {
        GetListener<UserBean> getListener = new GetListener<UserBean>() {

            @Override
            public void onFailure(int i, String s) {
                handleFailureMessage(i, s);
            }

            @Override
            public void onSuccess(UserBean userBean) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constant.DATA, userBean);
                handleSuccess(bundle);
            }
        };

        BmobQuery<UserBean> query = new BmobQuery<>();
        mQueryModel.query(context, query, getListener, objectId);
    }
}
