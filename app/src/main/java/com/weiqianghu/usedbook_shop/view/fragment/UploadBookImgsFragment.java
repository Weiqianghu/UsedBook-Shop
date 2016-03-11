package com.weiqianghu.usedbook_shop.view.fragment;


import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.model.entity.BookBean;
import com.weiqianghu.usedbook_shop.model.entity.BookImgsBean;
import com.weiqianghu.usedbook_shop.model.entity.ImgModel;
import com.weiqianghu.usedbook_shop.presenter.SavePresenter;
import com.weiqianghu.usedbook_shop.presenter.UploadBatchPresenter;
import com.weiqianghu.usedbook_shop.util.CallBackHandler;
import com.weiqianghu.usedbook_shop.util.Constant;
import com.weiqianghu.usedbook_shop.util.ImgUtil;
import com.weiqianghu.usedbook_shop.view.common.BaseFragment;
import com.weiqianghu.usedbook_shop.view.view.ISaveView;

import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class UploadBookImgsFragment extends BaseFragment implements ISaveView {
    public static final String TAG = UploadBookImgsFragment.class.getSimpleName();

    private ImageView mImageView1;
    private ImageView mImageView2;
    private ImageView mImageView3;

    private Button mSubmitBtn;

    private BookBean book;

    private String imgPath1 = "";
    private String imgPath2 = "";
    private String imgPath3 = "";

    private SavePresenter mSavePresenter;
    private UploadBatchPresenter mUploadBatchPresenter;
    private List<ImgModel> imgUrls;

    private boolean isImg1;
    private boolean isImg2;
    private boolean isImg3;

    private int times = 1;

    private ProgressBar mLoading;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_upload_book_imgs;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView(savedInstanceState);
        initData();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Click click = new Click();

        mImageView1 = (ImageView) mRootView.findViewById(R.id.iv_1);
        mImageView2 = (ImageView) mRootView.findViewById(R.id.iv_2);
        mImageView3 = (ImageView) mRootView.findViewById(R.id.iv_3);
        mSubmitBtn = (Button) mRootView.findViewById(R.id.btn_submit);

        mImageView1.setOnClickListener(click);
        mImageView2.setOnClickListener(click);
        mImageView3.setOnClickListener(click);
        mSubmitBtn.setOnClickListener(click);

        mSavePresenter = new SavePresenter(this, saveHandler);
        mUploadBatchPresenter = new UploadBatchPresenter(uploadHandler);

        mLoading= (ProgressBar) mRootView.findViewById(R.id.pb_loading);
    }

    public void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            book = (BookBean) bundle.getSerializable(Constant.BOOK);
        }
    }


    CallBackHandler saveHandler = new CallBackHandler() {
        public void handleSuccessMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    if (times < 3) {
                        times++;
                    } else {
                        times = 0;
                        mLoading.setVisibility(View.INVISIBLE);
                        mSubmitBtn.setClickable(true);
                        Toast.makeText(getActivity(), "上架成功", Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                        getActivity().onBackPressed();
                    }
                    break;
            }
        }

        public void handleFailureMessage(String msg) {
            mSubmitBtn.setClickable(true);
            mLoading.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    };

    CallBackHandler uploadHandler = new CallBackHandler() {
        public void handleSuccessMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    mSubmitBtn.setClickable(true);
                    imgUrls = msg.getData().getParcelableArrayList(Constant.FILE);
                    for (int i = 0, length = imgUrls.size(); i < length; i++) {
                        BookImgsBean bookImgsBean = new BookImgsBean();
                        bookImgsBean.setImg(imgUrls.get(i).getImgUrl());
                        bookImgsBean.setBook(book);
                        mSavePresenter.save(getActivity(), bookImgsBean);
                    }
                    break;
            }
        }

        public void handleFailureMessage(String msg) {
            mSubmitBtn.setClickable(true);
            mLoading.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    };

    private class Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_submit:
                    submit();
                    break;
                case R.id.iv_1:
                    ImgUtil.selectSingleImgForFragment(UploadBookImgsFragment.this, Constant.IMG1);
                    break;
                case R.id.iv_2:
                    ImgUtil.selectSingleImgForFragment(UploadBookImgsFragment.this, Constant.IMG2);
                    break;
                case R.id.iv_3:
                    ImgUtil.selectSingleImgForFragment(UploadBookImgsFragment.this, Constant.IMG3);
                    break;
            }
        }
    }

    private void submit() {
        if (!isImg1 || !isImg2 || !isImg3) {
            Toast.makeText(getActivity(), "请选择图片", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] files = new String[3];
        files[0] = imgPath1;
        files[1] = imgPath2;
        files[2] = imgPath3;

        mUploadBatchPresenter.uploadBatch(getActivity(), files);
        mSubmitBtn.setClickable(false);
        mLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(null==data){
            return;
        }
        List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
        String smallPath = "";
        switch (requestCode) {
            case Constant.IMG1:
                smallPath = ImgUtil.getSmallImgPath(path.get(0), mImageView1.getWidth(), mImageView1.getHeight());
                mImageView1.setImageBitmap(BitmapFactory.decodeFile(smallPath));
                imgPath1 = smallPath;
                isImg1 = true;
                break;
            case Constant.IMG2:
                smallPath = ImgUtil.getSmallImgPath(path.get(0), mImageView2.getWidth(), mImageView2.getHeight());
                mImageView2.setImageBitmap(BitmapFactory.decodeFile(smallPath));
                imgPath2 = smallPath;
                isImg2 = true;
                break;
            case Constant.IMG3:
                smallPath = ImgUtil.getSmallImgPath(path.get(0), mImageView3.getWidth(), mImageView3.getHeight());
                mImageView3.setImageBitmap(BitmapFactory.decodeFile(smallPath));
                imgPath3 = smallPath;
                isImg3 = true;
                break;
        }
    }
}
