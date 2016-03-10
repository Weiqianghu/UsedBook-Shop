package com.weiqianghu.usedbook_shop.model.inf;

import android.content.Context;

import com.bmob.btp.callback.UploadBatchListener;
import com.weiqianghu.usedbook_shop.util.Constant;

/**
 * Created by weiqianghu on 2016/3/10.
 */
public interface IUploadBatchModel {
    boolean uploadBatch(Context context, String[] files, UploadBatchListener uploadBatchListener);
}
