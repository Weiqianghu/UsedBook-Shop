package com.weiqianghu.usedbook_shop.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.util.Constant;
import com.weiqianghu.usedbook_shop.util.FragmentUtil;
import com.weiqianghu.usedbook_shop.view.common.BaseFragment;
import com.weiqianghu.usedbook_shop.view.customview.ClearEditText;


public class AddressSubmitFragment extends BaseFragment {
    public static final String TAG = AddressSubmitFragment.class.getSimpleName();
    private String provinceName;
    private String cityName;
    private String countyName;

    private TextView mRegionTv;
    private ClearEditText mDetailAddressEt;
    private Button mSubmitBtn;

    private String detailAddress;

    private ProgressBar mLoading;

    private FragmentManager mFragmentManager;
    private Fragment mFragment;
    private int layoutId;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_address_submit;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView(savedInstanceState);
        initdata();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mRegionTv = (TextView) mRootView.findViewById(R.id.tv_region);
        mDetailAddressEt = (ClearEditText) mRootView.findViewById(R.id.et_detail_address);
        mSubmitBtn = (Button) mRootView.findViewById(R.id.btn_submit);

        mSubmitBtn.setOnClickListener(new Click());


    }


    private class Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_submit:
                    if (beforeSubmit()) {
                        if (mFragmentManager == null) {
                            mFragmentManager = getActivity().getSupportFragmentManager();
                        }
                        if (layoutId == R.id.main_container) {
                            mFragment = mFragmentManager.findFragmentByTag(EditShopInfoFragment.TAG);
                            if (mFragment == null) {
                                mFragment = new EditShopInfoFragment();
                            }
                        } else if (layoutId == R.id.apply_for_shop_container) {
                            mFragment = mFragmentManager.findFragmentByTag(BasicInfoForApplyForShopFragment.TAG);
                            if (mFragment == null) {
                                mFragment = new BasicInfoForApplyForShopFragment();
                            }
                        }

                        if (mFragment == null) {
                            return;
                        }

                        mFragment.getArguments().putString(Constant.PROVINCE, provinceName);
                        mFragment.getArguments().putString(Constant.CITY, cityName);
                        mFragment.getArguments().putString(Constant.COUNTY, countyName);
                        mFragment.getArguments().putString(Constant.DETAIL_ADDRESS, detailAddress);

                        getActivity().onBackPressed();
                        getActivity().onBackPressed();
                        getActivity().onBackPressed();
                        getActivity().onBackPressed();
                    } else {
                        mSubmitBtn.setClickable(true);
                    }
            }
        }
    }

    void initdata() {
        Bundle bundle = getArguments();
        provinceName = bundle.getString(Constant.PROVINCE);
        cityName = bundle.getString(Constant.CITY);
        countyName = bundle.getString(Constant.COUNTY);
        layoutId = bundle.getInt(Constant.LAYOUT_ID);
        mRegionTv.setText(provinceName + cityName + countyName);
    }

    boolean beforeSubmit() {

        mSubmitBtn.setClickable(false);

        getdata();

        if (detailAddress == null || "".equals(detailAddress)) {
            Toast.makeText(getActivity(), "详细地址不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    void getdata() {
        detailAddress = mDetailAddressEt.getText().toString().trim();
    }

}
