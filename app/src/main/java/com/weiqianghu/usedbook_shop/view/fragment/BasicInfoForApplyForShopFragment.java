package com.weiqianghu.usedbook_shop.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.util.Constant;
import com.weiqianghu.usedbook_shop.util.FragmentUtil;
import com.weiqianghu.usedbook_shop.view.common.BaseFragment;

public class BasicInfoForApplyForShopFragment extends BaseFragment {

    public static String TAG = BasicInfoForApplyForShopFragment.class.getSimpleName();

    private View mAddressView;
    private FragmentManager mFragmentManager;
    private Fragment mFragment;

    private TextView mAddressTv;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_basic_info_for_apply_for_shop;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Click click = new Click();

        mAddressView = mRootView.findViewById(R.id.lt_address);
        mAddressView.setOnClickListener(click);

        mAddressTv= (TextView) mRootView.findViewById(R.id.tv_address);
    }

    private class Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.lt_address:
                    if (mFragmentManager == null) {
                        mFragmentManager = getActivity().getSupportFragmentManager();
                    }
                    mFragment = mFragmentManager.findFragmentByTag(ProvinceAddressFragment.TAG);
                    if (null == mFragment) {
                        mFragment = new ProvinceAddressFragment();
                    }
                    Fragment from = mFragmentManager.findFragmentByTag(BasicInfoForApplyForShopFragment.TAG);
                    FragmentUtil.switchContentAddToBackStack(from, mFragment,
                            R.id.apply_for_shop_container, mFragmentManager, ProvinceAddressFragment.TAG);
                    break;
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            setAddress();
        }
    }

    private void setAddress(){
        Bundle bundle=getArguments();
        String address=bundle.getString(Constant.ADDRESS);
        if(address!=null){
            mAddressTv.setText(address);
        }
    }
}
