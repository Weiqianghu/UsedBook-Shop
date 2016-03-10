package com.weiqianghu.usedbook_shop.presenter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;

import com.bmob.btp.callback.UploadBatchListener;
import com.weiqianghu.usedbook_shop.model.entity.ImgModel;
import com.weiqianghu.usedbook_shop.model.impl.UploadBatchModel;
import com.weiqianghu.usedbook_shop.model.inf.IUploadBatchModel;
import com.weiqianghu.usedbook_shop.util.Constant;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by weiqianghu on 2016/3/10.
 */
public class UploadBatchPresenter extends CommonPresenter {
    private IUploadBatchModel mUploadBatchModel;
    private Handler mHandler;

    public UploadBatchPresenter(Handler handler) {
        super(handler);
        mUploadBatchModel = new UploadBatchModel();
    }

    public void uploadBatch(Context context, String[] files) {
        UploadBatchListener uploadBatchListener = new UploadBatchListener() {
            @Override
            public void onSuccess(boolean b, String[] strings, String[] strings1, BmobFile[] bmobFiles) {
                if (b) {
                    List<ImgModel> imgUrlList = new ArrayList<>();
                    for (int i = 0, length = bmobFiles.length; i < length; i++) {
                        ImgModel imgModel = new ImgModel();
                        imgModel.setImgUrl(bmobFiles[i].getUrl());
                        imgUrlList.add(imgModel);
                    }
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(Constant.FILE, (ArrayList<? extends Parcelable>) imgUrlList);
                    handleSuccess(bundle);
                }
            }

            @Override
            public void onProgress(int i, int i1, int i2, int i3) {
                Log.i("bmob", "onProgress :" + i + "---" + i1 + "---" + i2 + "----" + i3);
            }

            @Override
            public void onError(int i, String s) {
                handleFailureMessage(i, s);
            }
        };
        mUploadBatchModel.uploadBatch(context, files, uploadBatchListener);
    }
}
