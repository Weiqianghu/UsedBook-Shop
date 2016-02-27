package com.weiqianghu.usedbook_shop.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.model.entity.FailureMessageModel;
import com.weiqianghu.usedbook_shop.model.entity.UserBean;
import com.weiqianghu.usedbook_shop.presenter.IsShopPresenter;
import com.weiqianghu.usedbook_shop.presenter.LoginPresenter;
import com.weiqianghu.usedbook_shop.util.Constant;
import com.weiqianghu.usedbook_shop.view.activity.ApplyForShopActivity;
import com.weiqianghu.usedbook_shop.view.activity.LoginAndRegisterActivity;
import com.weiqianghu.usedbook_shop.view.activity.MainActivity;
import com.weiqianghu.usedbook_shop.view.activity.SplashActivity;
import com.weiqianghu.usedbook_shop.view.common.BaseFragment;
import com.weiqianghu.usedbook_shop.view.customview.ClearEditText;
import com.weiqianghu.usedbook_shop.view.view.ILoginView;

import cn.bmob.v3.BmobUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment implements ILoginView {

    private TextView mGotoRegisterTV;
    private TextView mForgetPasswordTV;
    private Button mLoginBtn;

    private FragmentManager mFragmentManager;

    private LoginPresenter mLoginPresenter;

    private ProgressBar mLoading;
    private ClearEditText mUsernameEt;
    private ClearEditText mPasswordEt;
    private String username;
    private String password;

    private IsShopPresenter mIsShopPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView(savedInstanceState);
    }

    protected void initView(Bundle savedInstanceState) {

        mLoginBtn = (Button) mRootView.findViewById(R.id.btn_login);
        mLoginBtn.setOnClickListener(new Click());

        mGotoRegisterTV = (TextView) mRootView.findViewById(R.id.tv_goto_register);
        mGotoRegisterTV.setOnClickListener(new Click());

        mForgetPasswordTV = (TextView) mRootView.findViewById(R.id.tv_forget_password);
        mForgetPasswordTV.setOnClickListener(new Click());

        mLoading = (ProgressBar) mRootView.findViewById(R.id.pb_loading);
        mUsernameEt = (ClearEditText) mRootView.findViewById(R.id.et_username);
        mPasswordEt = (ClearEditText) mRootView.findViewById(R.id.et_password);
        mIsShopPresenter=new IsShopPresenter();
    }

    public Handler loginHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    mLoading.setVisibility(View.INVISIBLE);
                    mLoginBtn.setClickable(true);
                    Toast.makeText(getActivity(), "登陆成功", Toast.LENGTH_SHORT).show();
                    if(mIsShopPresenter.isShop(BmobUser.getCurrentUser(getActivity(),UserBean.class))){
                        Intent intent=new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(getActivity(), ApplyForShopActivity.class);
                        startActivity(intent);
                    }
                    getActivity().finish();
                    break;
                case Constant.FAILURE:
                    Bundle bundle = msg.getData();
                    FailureMessageModel failureMessageModel = (FailureMessageModel) bundle.getSerializable(Constant.FAILURE_MESSAGE);
                    String failureMsg = failureMessageModel.getMsg();
                    mLoading.setVisibility(View.INVISIBLE);
                    mLoginBtn.setClickable(true);
                    Toast.makeText(getActivity(), failureMsg, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private class Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_goto_register:
                    gotoRegister();
                    break;
                case R.id.btn_login:
                    if (mLoginPresenter == null) {
                        mLoginPresenter = new LoginPresenter(LoginFragment.this, loginHandler);
                    }
                    if (beforeLogin()) {
                        mLoading.setVisibility(View.VISIBLE);
                        mLoginPresenter.login(getActivity(), username, password);
                    }
                    break;
                case R.id.tv_forget_password:
                    forgetPassword();
                    break;
            }
        }
    }

    private boolean beforeLogin() {
        username = mUsernameEt.getText().toString().trim();
        password = mPasswordEt.getText().toString().trim();
        mLoginBtn.setClickable(false);
        if (username == null || username.length() < 1) {
            Toast.makeText(getActivity(), "用户名不能为空", Toast.LENGTH_SHORT).show();
            mLoginBtn.setClickable(true);
            return false;
        }
        if (password == null || password.length() < 1) {
            Toast.makeText(getActivity(), "密码不能为空", Toast.LENGTH_SHORT).show();
            mLoginBtn.setClickable(true);
            return false;
        }
        return true;
    }

    private void forgetPassword() {
        if (mFragmentManager == null) {
            mFragmentManager = getActivity().getSupportFragmentManager();
        }
        FragmentTransaction ft = mFragmentManager.beginTransaction();

        ft.setCustomAnimations(
                R.anim.push_left_in,
                R.anim.push_left_out,
                R.anim.push_right_in,
                R.anim.push_right_out);

        ft.replace(R.id.login_and_register_container, new ForgetPasswordkFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    private void gotoRegister() {
        if (mFragmentManager == null) {
            mFragmentManager = getActivity().getSupportFragmentManager();
        }
        FragmentTransaction ft = mFragmentManager.beginTransaction();

        ft.setCustomAnimations(
                R.anim.push_left_in,
                R.anim.push_left_out,
                R.anim.push_right_in,
                R.anim.push_right_out);

        ft.replace(R.id.login_and_register_container, new RegisterFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

}
