package com.weiqianghu.usedbook_shop.presenter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;

import com.weiqianghu.usedbook_shop.model.entity.BookBean;
import com.weiqianghu.usedbook_shop.model.entity.ShopBean;
import com.weiqianghu.usedbook_shop.model.entity.UserBean;
import com.weiqianghu.usedbook_shop.model.impl.QueryModel;
import com.weiqianghu.usedbook_shop.model.inf.IQueryModel;
import com.weiqianghu.usedbook_shop.util.Constant;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;

/**
 * Created by weiqianghu on 2016/3/12.
 */
public class QueryBooksPresenter extends CommonPresenter {
    private IQueryModel<BookBean> mQueryModel;

    public QueryBooksPresenter(Handler handler) {
        super(handler);
        mQueryModel = new QueryModel<BookBean>();
    }

    public void queryBooks(Context context, int start, int step) {
        FindListener<BookBean> findListener = new FindListener<BookBean>() {
            @Override
            public void onSuccess(List list) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Constant.LIST, (ArrayList<? extends Parcelable>) list);
                handleSuccess(bundle);
            }

            @Override
            public void onError(int i, String s) {
                handleFailureMessage(i, s);
            }
        };

        UserBean currentUser = BmobUser.getCurrentUser(context, UserBean.class);
        ShopBean shop = null;
        if (currentUser != null) {
            shop = currentUser.getShop();
        }

        BmobQuery<BookBean> query = new BmobQuery<>();
        query.addWhereEqualTo("shop", shop);
        query.setLimit(step);
        query.setSkip(start);
        query.order("-createdAt");
        mQueryModel.query(context, query, findListener);
    }


    public void queryBooks(Context context, String bookObjectId) {
        GetListener<BookBean> getListener = new GetListener<BookBean>() {

            @Override
            public void onFailure(int i, String s) {
                handleFailureMessage(i, s);
            }

            @Override
            public void onSuccess(BookBean bookBean) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constant.DATA, bookBean);
                handleSuccess(bundle);
            }
        };

        BmobQuery<BookBean> query = new BmobQuery<>();
        query.include("shop");
        mQueryModel.query(context, query, getListener, bookObjectId);
    }

    public void queryBooks(Context context, int limit) {
        FindListener<BookBean> findListener = new FindListener<BookBean>() {
            @Override
            public void onSuccess(List list) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Constant.LIST, (ArrayList<? extends Parcelable>) list);
                handleSuccess(bundle);
            }

            @Override
            public void onError(int i, String s) {
                handleFailureMessage(i, s);
            }
        };

        UserBean currentUser = BmobUser.getCurrentUser(context, UserBean.class);
        ShopBean shop = null;
        if (currentUser != null) {
            shop = currentUser.getShop();
        }

        BmobQuery<BookBean> query = new BmobQuery<>();
        query.addWhereEqualTo("shop", shop);
        query.setLimit(limit);
        query.order("-salesVolume");
        mQueryModel.query(context, query, findListener);
    }

    public void queryBooks(Context context) {
        FindListener<BookBean> findListener = new FindListener<BookBean>() {
            @Override
            public void onSuccess(List list) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Constant.LIST, (ArrayList<? extends Parcelable>) list);
                handleSuccess(bundle);
            }

            @Override
            public void onError(int i, String s) {
                handleFailureMessage(i, s);
            }
        };

        UserBean currentUser = BmobUser.getCurrentUser(context, UserBean.class);
        ShopBean shop = null;
        if (currentUser != null) {
            shop = currentUser.getShop();
        }

        BmobQuery<BookBean> query = new BmobQuery<>();
        query.addWhereEqualTo("shop", shop);
        query.order("-price");
        mQueryModel.query(context, query, findListener);
    }
}
