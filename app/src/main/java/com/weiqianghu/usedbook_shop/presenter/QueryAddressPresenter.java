package com.weiqianghu.usedbook_shop.presenter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.weiqianghu.usedbook_shop.model.entity.AddressBean;
import com.weiqianghu.usedbook_shop.model.entity.UserBean;
import com.weiqianghu.usedbook_shop.model.impl.QueryModel;
import com.weiqianghu.usedbook_shop.model.inf.IQueryModel;
import com.weiqianghu.usedbook_shop.util.Constant;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

/**
 * Created by weiqianghu on 2016/3/26.
 */
public class QueryAddressPresenter extends CommonPresenter {
    private IQueryModel<AddressBean> mQueryModel;

    public QueryAddressPresenter(Handler handler) {
        super(handler);
        mQueryModel = new QueryModel<AddressBean>();
    }

    public void QueryAddress(Context context, String objectId) {
        GetListener<AddressBean> getListener = new GetListener<AddressBean>() {

            @Override
            public void onFailure(int i, String s) {
                handleFailureMessage(i, s);
            }

            @Override
            public void onSuccess(AddressBean addressBean) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constant.DATA, addressBean);
                handleSuccess(bundle);
            }
        };

        BmobQuery<AddressBean> query = new BmobQuery<>();
        mQueryModel.query(context, query, getListener, objectId);
    }
}
