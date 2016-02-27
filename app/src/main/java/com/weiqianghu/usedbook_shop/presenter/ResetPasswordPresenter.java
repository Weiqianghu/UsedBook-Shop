package com.weiqianghu.usedbook_shop.presenter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


import com.weiqianghu.usedbook_shop.model.entity.FailureMessageModel;
import com.weiqianghu.usedbook_shop.model.impl.ResetPasswordModel;
import com.weiqianghu.usedbook_shop.model.inf.IResetPasswordModel;
import com.weiqianghu.usedbook_shop.util.Constant;
import com.weiqianghu.usedbook_shop.view.view.IResetPasswordView;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.ResetPasswordByCodeListener;

/**
 * Created by weiqianghu on 2016/2/22.
 */
public class ResetPasswordPresenter {
    private IResetPasswordModel mResetPasswordModel;
    private IResetPasswordView mResetPasswordView;
    private Handler handler;

    public ResetPasswordPresenter(IResetPasswordView iResetPasswordView, Handler handler){
        this.mResetPasswordView=iResetPasswordView;
        this.handler=handler;
        mResetPasswordModel=new ResetPasswordModel();
    }

    public void resetPassword(Context context,String smsCode,String password){
        ResetPasswordByCodeListener resetPasswordByCodeListener=new ResetPasswordByCodeListener(){

            @Override
            public void done(BmobException e) {
                if(e==null){
                    Message message=new Message();
                    message.what= Constant.SUCCESS;
                    handler.sendMessage(message);
                }else{
                    Message message=new Message();
                    message.what= Constant.FAILURE;

                    FailureMessageModel failureMessageModel =new FailureMessageModel();
                    failureMessageModel.setMsgCode(e.getErrorCode());
                    failureMessageModel.setMsg(e.getLocalizedMessage());

                    Bundle bundle=new Bundle();
                    bundle.putSerializable(Constant.FAILURE_MESSAGE, failureMessageModel);

                    message.setData(bundle);
                    handler.sendMessage(message);
                }
            }
        };
        mResetPasswordModel.resetPwd(context,resetPasswordByCodeListener,smsCode,password);
    }
}
