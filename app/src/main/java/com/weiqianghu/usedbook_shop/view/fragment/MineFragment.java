package com.weiqianghu.usedbook_shop.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.model.entity.UserBean;
import com.weiqianghu.usedbook_shop.presenter.EditUserPresenter;
import com.weiqianghu.usedbook_shop.presenter.QueryUserPresenter;
import com.weiqianghu.usedbook_shop.presenter.UploadFileByPathPresenter;
import com.weiqianghu.usedbook_shop.util.CallBackHandler;
import com.weiqianghu.usedbook_shop.util.Constant;
import com.weiqianghu.usedbook_shop.util.FileUtil;
import com.weiqianghu.usedbook_shop.util.FragmentUtil;
import com.weiqianghu.usedbook_shop.util.ImgUtil;
import com.weiqianghu.usedbook_shop.util.SelectImgUtil;
import com.weiqianghu.usedbook_shop.view.activity.MessageListActivity;
import com.weiqianghu.usedbook_shop.view.common.BaseFragment;
import com.weiqianghu.usedbook_shop.view.view.IEditUserView;
import com.weiqianghu.usedbook_shop.view.view.IUploadFileByPathView;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;


public class MineFragment extends BaseFragment implements IUploadFileByPathView, IEditUserView {
    public static final String TAG = MineFragment.class.getSimpleName();

    private Context mContext;
    private QueryUserPresenter mQueryUserPresenter;
    private UserBean currentUser = new UserBean();
    private SimpleDraweeView mUserImg;
    private TextView mShopNameTv;

    private View mEditShopInfo;
    private View mStatistics;
    private View mSettings;
    private View mMessage;
    private View mAbout;

    private FragmentManager mFragmentManager;

    private UploadFileByPathPresenter mUploadFileByPathPresenter;
    private EditUserPresenter mEditUserPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView(savedInstanceState);
        initData();
    }

    private void updateView() {
        if (currentUser.getImg() != null) {
            Uri uri = Uri.parse(currentUser.getImg());
            mUserImg.setImageURI(uri);
        }
        if (currentUser.getShop() != null) {
            mShopNameTv.setText(currentUser.getShop().getShopName());
        }
    }

    private void initData() {
        mContext = getActivity();
        UserBean user = BmobUser.getCurrentUser(mContext, UserBean.class);
        mQueryUserPresenter.queryUser(mContext, user.getObjectId());
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Click click = new Click();

        mQueryUserPresenter = new QueryUserPresenter(queryUserHandler);
        mUserImg = (SimpleDraweeView) mRootView.findViewById(R.id.iv_user_img);
        mUserImg.setOnClickListener(click);
        mShopNameTv = (TextView) mRootView.findViewById(R.id.tv_shop_name);
        mEditShopInfo = mRootView.findViewById(R.id.edit_shop_info);
        mEditShopInfo.setOnClickListener(click);
        mUploadFileByPathPresenter = new UploadFileByPathPresenter(this, uploadFileHandler);
        mEditUserPresenter = new EditUserPresenter(this, editUserHanler);

        mStatistics = mRootView.findViewById(R.id.statistics);
        mStatistics.setOnClickListener(click);

        mSettings = mRootView.findViewById(R.id.settings);
        mSettings.setOnClickListener(click);
        mMessage = mRootView.findViewById(R.id.message);
        mMessage.setOnClickListener(click);
        mAbout = mRootView.findViewById(R.id.about);
        mAbout.setOnClickListener(click);
    }

    CallBackHandler queryUserHandler = new CallBackHandler() {
        @Override
        public void handleSuccessMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    Bundle bundle = msg.getData();
                    if (bundle != null) {
                        currentUser = bundle.getParcelable(Constant.DATA);
                        updateView();
                    }
            }
        }

        @Override
        public void handleFailureMessage(String msg) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    };

    private class Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.edit_shop_info:
                    gotoEditShopInfo();
                    break;
                case R.id.iv_user_img:
                    SelectImgUtil.selectImg(MineFragment.this, MultiImageSelectorActivity.MODE_SINGLE, 1);
                    break;
                case R.id.settings:
                    gotoSettings();
                    break;
                case R.id.statistics:
                    gotoStatistics();
                    break;
                case R.id.message:
                    gotoMessageList();
                    break;
                case R.id.about:
                    gotoAbout();
                    break;
            }
        }
    }

    private void gotoAbout() {
        if (mFragmentManager == null) {
            mFragmentManager = getActivity().getSupportFragmentManager();
        }
        Fragment to = mFragmentManager.findFragmentByTag(AboutFragment.TAG);
        if (to == null) {
            to = new AboutFragment();
        }
        Fragment from = mFragmentManager.findFragmentByTag(MainFragment.TAG);
        FragmentUtil.switchContentAddToBackStack(from, to, R.id.main_container, mFragmentManager, AboutFragment.TAG);
    }

    private void gotoMessageList() {
        Intent intent = new Intent(mContext, MessageListActivity.class);
        startActivity(intent);
    }

    private void gotoEditShopInfo() {
        if (mFragmentManager == null) {
            mFragmentManager = getActivity().getSupportFragmentManager();
        }
        Fragment to = mFragmentManager.findFragmentByTag(EditShopInfoFragment.TAG);
        if (to == null) {
            to = new EditShopInfoFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.DATA, currentUser);
        to.setArguments(bundle);

        Fragment from = mFragmentManager.findFragmentByTag(MainFragment.TAG);
        FragmentUtil.switchContentAddToBackStack(from, to, R.id.main_container, mFragmentManager, EditShopInfoFragment.TAG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_IMAGE) {
            if (resultCode == getActivity().RESULT_OK) {
                // 获取返回的图片列表
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if (path.size() > 0) {
                    if (mUserImg == null) {
                        mUserImg = (SimpleDraweeView) mRootView.findViewById(R.id.iv_user_img);
                    }
                    String smallImgPath = ImgUtil.getSmallImgPath(path.get(0), mUserImg.getWidth(), mUserImg.getHeight());
                    updateImg(smallImgPath);
                    mUploadFileByPathPresenter.uploadFileByPath(mContext, smallImgPath);
                }
            }
        }
    }

    private void updateImg(String smallImgPath) {
        if (smallImgPath != null) {
            Uri uri = FileUtil.getUriByPath(smallImgPath);
            if (uri != null) {
                mUserImg.setImageURI(uri);
            }
        }
    }

    CallBackHandler uploadFileHandler = new CallBackHandler() {
        public void handleSuccessMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    Bundle bundle = msg.getData();
                    BmobFile file = (BmobFile) bundle.getSerializable(Constant.FILE);
                    String fileUrl = file.getUrl();

                    UserBean userBean = new UserBean();
                    userBean.setImg(fileUrl);
                    userBean.setSex(currentUser.isSex());
                    userBean.setAge(currentUser.getAge());
                    userBean.setShop(currentUser.isShop());

                    mEditUserPresenter.updateUser(mContext, userBean);
                    break;
            }
        }

        public void handleFailureMessage(String msg) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
    };

    CallBackHandler editUserHanler = new CallBackHandler() {
        public void handleFailureMessage(String msg) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
    };

    private void gotoSettings() {
        if (mFragmentManager == null) {
            mFragmentManager = getActivity().getSupportFragmentManager();
        }
        Fragment mFragment = mFragmentManager.findFragmentByTag(SeetingsFragment.TAG);
        if (mFragment == null) {
            mFragment = new SeetingsFragment();
        }

        Fragment from = mFragmentManager.findFragmentByTag(MainFragment.TAG);
        FragmentUtil.switchContentAddToBackStack(from, mFragment, R.id.main_container, mFragmentManager, SeetingsFragment.TAG);
    }

    private void gotoStatistics() {
        if (mFragmentManager == null) {
            mFragmentManager = getActivity().getSupportFragmentManager();
        }
        Fragment mFragment = mFragmentManager.findFragmentByTag(StatisticsFragment.TAG);
        if (mFragment == null) {
            mFragment = new StatisticsFragment();
        }

        Fragment from = mFragmentManager.findFragmentByTag(MainFragment.TAG);
        FragmentUtil.switchContentAddToBackStack(from, mFragment, R.id.main_container, mFragmentManager, StatisticsFragment.TAG);
    }

}
