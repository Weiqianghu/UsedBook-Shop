package com.weiqianghu.usedbook_shop.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.util.AuditUtil;
import com.weiqianghu.usedbook_shop.util.Constant;
import com.weiqianghu.usedbook_shop.view.activity.ApplyForShopActivity;
import com.weiqianghu.usedbook_shop.view.activity.LoginAndRegisterActivity;
import com.weiqianghu.usedbook_shop.view.common.BaseFragment;

public class AuditFailureFragment extends BaseFragment {

    public static String TAG = AuditingFragment.class.getSimpleName();

    private TextView mFailureReasonTv;
    private TextView mTitle;
    private TextView mLoginOtherAccount;
    private TextView mRepeatSubmitInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_audit_failure;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mFailureReasonTv = (TextView) mRootView.findViewById(R.id.tv_failure_reason);
        mTitle = (TextView) mRootView.findViewById(R.id.title);
        mTitle.setText(R.string.audit_failure);

        Click click=new Click();

        mLoginOtherAccount= (TextView) mRootView.findViewById(R.id.tv_login_other_account);
        mLoginOtherAccount.setOnClickListener(click);
        mRepeatSubmitInfo= (TextView) mRootView.findViewById(R.id.tv_repeat_submit_info);
        mRepeatSubmitInfo.setOnClickListener(click);
    }

    private void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (null != bundle) {
            int auditState = bundle.getInt(Constant.AUDIT_STATE);
            mFailureReasonTv.setText(AuditUtil.getStrByFailureCode(auditState));
        }
    }

    private class Click implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()){
                case R.id.tv_login_other_account:
                    intent=new Intent(getActivity(), LoginAndRegisterActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tv_repeat_submit_info:
                    intent=new Intent(getActivity(), ApplyForShopActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }


}
