package com.weiqianghu.usedbook_shop.presenter;

import android.content.Context;

import com.weiqianghu.usedbook_shop.model.impl.IsLoginModel;
import com.weiqianghu.usedbook_shop.model.inf.IIsLoginModel;


/**
 * Created by 胡伟强 on 2016/1/28.
 */
public class IsLoginPresenter {
    private IIsLoginModel model;

    public IsLoginPresenter(){
        model=new IsLoginModel();
    }

    public boolean isLogin(Context context){
        return model.isLogin(context);
    }
}
