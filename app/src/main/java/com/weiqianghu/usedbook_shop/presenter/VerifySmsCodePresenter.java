package com.weiqianghu.usedbook_shop.presenter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.weiqianghu.usedbook_shop.model.entity.FailureMessageModel;
import com.weiqianghu.usedbook_shop.model.impl.VerifySmsModel;
import com.weiqianghu.usedbook_shop.model.inf.IVerifySmsModel;
import com.weiqianghu.usedbook_shop.util.Constant;
import com.weiqianghu.usedbook_shop.view.view.IVerifySmsCodeView;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.VerifySMSCodeListener;

/**
 * Created by weiqianghu on 2016/2/22.
 */
public class VerifySmsCodePresenter {
    private IVerifySmsModel mVerifySmsModel;
    private IVerifySmsCodeView mVerifySmsCodeView;
    private Handler handler;
    public VerifySmsCodePresenter(IVerifySmsCodeView iVerifySmsCodeView,Handler handler){
        this.mVerifySmsCodeView=iVerifySmsCodeView;
        this.handler=handler;
        mVerifySmsModel=new VerifySmsModel();
    }

    public void verifySmsCode(Context context, String mobileNo, String smsCode){
        VerifySMSCodeListener verifySMSCodeListener =new VerifySMSCodeListener(){

            @Override
            public void done(BmobException e) {
                if(e==null){//短信验证码已验证成功
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

        mVerifySmsModel.verifySms(context,verifySMSCodeListener,smsCode,mobileNo);
    }
}
