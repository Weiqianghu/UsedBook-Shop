package com.weiqianghu.usedbook_shop.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.util.Constant;
import com.weiqianghu.usedbook_shop.util.FragmentUtil;
import com.weiqianghu.usedbook_shop.view.common.BaseFragment;

public class SuggestApplyForShopFragment extends BaseFragment {
    public static String TAG = SuggestApplyForShopFragment.class.getName();

    private FragmentManager mFragmentManager;
    private Fragment mFragment;

    private Button mBtn;



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_suggest_apply_for_shop;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mBtn = (Button) mRootView.findViewById(R.id.btn_suggest_apply_for_shop);
        mBtn.setOnClickListener(click);
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_suggest_apply_for_shop:
                    if (mFragmentManager == null) {
                        mFragmentManager = getActivity().getSupportFragmentManager();
                    }
                    mFragment = mFragmentManager.findFragmentByTag(BasicInfoForApplyForShopFragment.TAG);
                    if (mFragment == null) {
                        mFragment = new BasicInfoForApplyForShopFragment();
                    }

                    if (mFragment.getArguments() == null) {
                        Bundle bundle = new Bundle();
                        mFragment.setArguments(bundle);
                    }

                    Fragment from = mFragmentManager.findFragmentByTag(SuggestApplyForShopFragment.TAG);
                    FragmentUtil.switchContentAddToBackStack(from, mFragment,
                            R.id.apply_for_shop_container, mFragmentManager, BasicInfoForApplyForShopFragment.TAG);
                    break;
            }
        }
    };
}
