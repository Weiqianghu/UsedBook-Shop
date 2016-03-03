package com.weiqianghu.usedbook_shop.view.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.model.entity.ShopBean;
import com.weiqianghu.usedbook_shop.model.entity.UserBean;
import com.weiqianghu.usedbook_shop.presenter.SavePresenter;
import com.weiqianghu.usedbook_shop.presenter.UpdatePresenter;
import com.weiqianghu.usedbook_shop.presenter.UploadFileByPathPresenter;
import com.weiqianghu.usedbook_shop.util.CallBackHandler;
import com.weiqianghu.usedbook_shop.util.Constant;
import com.weiqianghu.usedbook_shop.util.FileUtil;
import com.weiqianghu.usedbook_shop.util.ImgUtil;
import com.weiqianghu.usedbook_shop.view.ISaveView;
import com.weiqianghu.usedbook_shop.view.activity.AuditActivity;
import com.weiqianghu.usedbook_shop.view.common.BaseFragment;
import com.weiqianghu.usedbook_shop.view.customview.ClearEditText;
import com.weiqianghu.usedbook_shop.view.view.IUpdateView;
import com.weiqianghu.usedbook_shop.view.view.IUploadFileByPathView;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class PrivateInfoForApplyForShopFragment extends BaseFragment implements IUploadFileByPathView,ISaveView,IUpdateView{

    public static String Tag = PrivateInfoForApplyForShopFragment.class.getSimpleName();

    private ClearEditText mIdEt;
    private ImageView mIdFrontIv;
    private ImageView mIdBackIv;
    private Button mSubmitBtn;

    private ShopBean mShop;
    private String id;
    private String idFrontImg;
    private String idBackImg;

    private boolean clickFront = false;
    private boolean clickBack = false;

    private UploadFileByPathPresenter mUploadFileByPathPresenter;
    private ProgressBar mLoading;
    private SavePresenter mSavePresenter;
    private UpdatePresenter<UserBean> mUpdatePresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_private_info_for_apply_for_shop;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView(savedInstanceState);
        initData();
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mShop = (ShopBean) bundle.getSerializable(Constant.SHOP);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Click click = new Click();

        mIdEt = (ClearEditText) mRootView.findViewById(R.id.et_id);
        mIdFrontIv = (ImageView) mRootView.findViewById(R.id.iv_id_front);
        mIdBackIv = (ImageView) mRootView.findViewById(R.id.iv_id_back);
        mSubmitBtn = (Button) mRootView.findViewById(R.id.btn_submit);
        mSubmitBtn.setOnClickListener(click);
        mIdFrontIv.setOnClickListener(click);
        mIdBackIv.setOnClickListener(click);

        mLoading = (ProgressBar) mRootView.findViewById(R.id.pb_loading);
        mUploadFileByPathPresenter = new UploadFileByPathPresenter(this, uploadIdFrontImgHandler);
        mSavePresenter=new SavePresenter(this,saveHandler);
        mUpdatePresenter=new UpdatePresenter<>(this,updateHandler);
    }

    CallBackHandler uploadIdFrontImgHandler = new CallBackHandler() {
        public void handleSuccessMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    Bundle bundle = msg.getData();
                    BmobFile file = (BmobFile) bundle.getSerializable(Constant.FILE);
                    String fileUrl = file.getUrl();
                    mShop.setIdFrontImg(fileUrl);
                    mUploadFileByPathPresenter = new UploadFileByPathPresenter(PrivateInfoForApplyForShopFragment.this, uploadIdBackImgHandler);
                    mUploadFileByPathPresenter.uploadFileByPath(getActivity(),idBackImg);
                    break;
            }
        }

        public void handleFailureMessage(String msg) {
            mLoading.setVisibility(View.INVISIBLE);
            mSubmitBtn.setClickable(true);
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    };

    CallBackHandler uploadIdBackImgHandler = new CallBackHandler() {
        public void handleSuccessMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    Bundle bundle = msg.getData();
                    BmobFile file = (BmobFile) bundle.getSerializable(Constant.FILE);
                    String fileUrl = file.getUrl();
                    mShop.setIdBackImg(fileUrl);
                    mSavePresenter.save(getActivity(),mShop);
                    break;
            }
        }

        public void handleFailureMessage(String msg) {
            mLoading.setVisibility(View.INVISIBLE);
            mSubmitBtn.setClickable(true);
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    };

    CallBackHandler saveHandler = new CallBackHandler() {
        public void handleSuccessMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    UserBean userBean=new UserBean();
                    UserBean user= BmobUser.getCurrentUser(getActivity(),UserBean.class);
                    userBean.setShop(mShop);
                    userBean.setShop(true);
                    mUpdatePresenter.update(getActivity(),userBean,user.getObjectId());
                    break;
            }
        }

        public void handleFailureMessage(String msg) {
            mLoading.setVisibility(View.INVISIBLE);
            mSubmitBtn.setClickable(true);
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    };

    CallBackHandler updateHandler = new CallBackHandler() {
        public void handleSuccessMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    Intent intent=new Intent(getActivity(), AuditActivity.class);
                    intent.putExtra(Constant.AUDIT_STATE,Constant.AUDIT_STATE0);
                    startActivity(intent);
                    getActivity().finish();
                    break;
            }
        }

        public void handleFailureMessage(String msg) {
            mLoading.setVisibility(View.INVISIBLE);
            mSubmitBtn.setClickable(true);
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    };

    private class Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_submit:
                    if (beforeSubmit()) {
                        mLoading.setVisibility(View.VISIBLE);
                        mSubmitBtn.setClickable(false);
                        mUploadFileByPathPresenter.uploadFileByPath(getActivity(),idFrontImg);
                    }
                    break;
                case R.id.iv_id_front:
                    clickFront = true;
                    ImgUtil.selectSingleImg(getActivity(), Constant.REQUEST_ID_FRONT);
                    break;
                case R.id.iv_id_back:
                    clickBack = true;
                    ImgUtil.selectSingleImg(getActivity(), Constant.REQUEST_ID_BACK);
                    break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Bundle bundle = getArguments();
        if (bundle != null) {
            if (clickFront) {
                idFrontImg = bundle.getString(Constant.REQUEST_ID_FRONT_STR);
                setImg(mIdFrontIv, idFrontImg);
                clickFront = false;
            }
            if (clickBack) {
                idBackImg = bundle.getString(Constant.REQUEST_ID_BACK_STR);
                setImg(mIdBackIv, idBackImg);
                clickBack = false;
            }
        }
    }

    private void setImg(ImageView imageView, String path) {
        if (imageView == null || path == null || path.length() < 1) {
            return;
        }
        Uri uri = FileUtil.getUriByPath(path);
        if (uri != null) {
            imageView.setImageURI(uri);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    private boolean beforeSubmit() {
        id = mIdEt.getText().toString().trim();
        if (id == null || "".equals(id)) {
            Toast.makeText(getActivity(), "身份证号码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (idFrontImg == null || "".equals(idFrontImg)) {
            Toast.makeText(getActivity(), "请上传身份证正面照", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (idBackImg == null || "".equals(idBackImg)) {
            Toast.makeText(getActivity(), "请上传身份证背面照", Toast.LENGTH_SHORT).show();
            return false;
        }
        mShop.setIdNumber(id);
        return true;
    }

}
