package com.weiqianghu.usedbook_shop.model.impl;

import android.content.Context;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadBatchListener;
import com.weiqianghu.usedbook_shop.model.inf.IUploadBatchModel;

/**
 * Created by weiqianghu on 2016/3/10.
 */
public class UploadBatchModel implements IUploadBatchModel {
    @Override
    public boolean uploadBatch(Context context, String[] files, UploadBatchListener uploadBatchListener) {
        BmobProFile.getInstance(context).uploadBatch(files, uploadBatchListener);
        return true;
    }
}
