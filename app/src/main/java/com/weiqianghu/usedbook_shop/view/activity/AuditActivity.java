package com.weiqianghu.usedbook_shop.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.util.Constant;
import com.weiqianghu.usedbook_shop.util.FragmentUtil;
import com.weiqianghu.usedbook_shop.view.fragment.AuditFailureFragment;
import com.weiqianghu.usedbook_shop.view.fragment.AuditingFragment;

public class AuditActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit);

        initView();
    }

    private void initView() {

        Intent intent = getIntent();
        int auditState = intent.getIntExtra(Constant.AUDIT_STATE, 0);

        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        Bundle bundle=new Bundle();
        switch (auditState) {
            case Constant.AUDIT_STATE0:
                mFragment = mFragmentManager.findFragmentByTag(AuditingFragment.TAG);
                if (mFragment == null) {
                    mFragment = new AuditingFragment();
                }
                FragmentUtil.addContentNoAnimation(R.id.audit_container, mFragment, mFragmentManager, AuditingFragment.TAG);
                break;
            case Constant.AUDIT_STATE2:
                mFragment = mFragmentManager.findFragmentByTag(AuditFailureFragment.TAG);
                if (mFragment == null) {
                    mFragment = new AuditFailureFragment();
                }
                bundle.putInt(Constant.AUDIT_STATE,Constant.AUDIT_STATE2);
                mFragment.setArguments(bundle);
                FragmentUtil.addContentNoAnimation(R.id.audit_container, mFragment, mFragmentManager, AuditFailureFragment.TAG);
                break;
            case Constant.AUDIT_STATE3:
                mFragment = mFragmentManager.findFragmentByTag(AuditFailureFragment.TAG);
                if (mFragment == null) {
                    mFragment = new AuditFailureFragment();
                }
                bundle.putInt(Constant.AUDIT_STATE,Constant.AUDIT_STATE3);
                mFragment.setArguments(bundle);
                FragmentUtil.addContentNoAnimation(R.id.audit_container, mFragment, mFragmentManager, AuditFailureFragment.TAG);
                break;
        }
    }

}
