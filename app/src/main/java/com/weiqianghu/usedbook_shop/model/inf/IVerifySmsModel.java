package com.weiqianghu.usedbook_shop.model.inf;

import android.content.Context;

import cn.bmob.v3.listener.VerifySMSCodeListener;

/**
 * Created by weiqianghu on 2016/2/22.
 */
public interface IVerifySmsModel {
    boolean verifySms(Context context, VerifySMSCodeListener verifySMSCodeListener, String smsCode, String mobileNo);
}
