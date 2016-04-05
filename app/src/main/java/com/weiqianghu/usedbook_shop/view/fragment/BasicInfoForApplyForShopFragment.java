package com.weiqianghu.usedbook_shop.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.model.entity.ShopBean;
import com.weiqianghu.usedbook_shop.util.Constant;
import com.weiqianghu.usedbook_shop.util.FragmentUtil;
import com.weiqianghu.usedbook_shop.util.InputUtil;
import com.weiqianghu.usedbook_shop.view.common.BaseFragment;
import com.weiqianghu.usedbook_shop.view.customview.ClearEditText;

public class BasicInfoForApplyForShopFragment extends BaseFragment {

    public static String TAG = BasicInfoForApplyForShopFragment.class.getSimpleName();

    private View mAddressView;
    private FragmentManager mFragmentManager;
    private Fragment mAddressFragment;
    private Fragment mPrivateInfoFragment;

    private ShopBean mShop;

    private ClearEditText mShopNameEt;
    private ClearEditText mContactsEt;
    private ClearEditText mContactNumberEt;
    private TextView mAddressTv;
    private Button mNextBtn;

    private String shopName;
    private String contacts;
    private String contactNumber;
    private String address;


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

        mAddressTv = (TextView) mRootView.findViewById(R.id.tv_address);
        mShopNameEt = (ClearEditText) mRootView.findViewById(R.id.et_shop_name);
        mContactsEt = (ClearEditText) mRootView.findViewById(R.id.et_contacts);
        mContactNumberEt = (ClearEditText) mRootView.findViewById(R.id.et_contact_number);
        mNextBtn = (Button) mRootView.findViewById(R.id.btn_next);
        mNextBtn.setOnClickListener(click);
        mShop = new ShopBean();
    }

    private class Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.lt_address:
                    if (mFragmentManager == null) {
                        mFragmentManager = getActivity().getSupportFragmentManager();
                    }
                    mAddressFragment = mFragmentManager.findFragmentByTag(ProvinceAddressFragment.TAG);
                    if (null == mAddressFragment) {
                        mAddressFragment = new ProvinceAddressFragment();
                    }
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constant.LAYOUT_ID, R.id.apply_for_shop_container);
                    mAddressFragment.setArguments(bundle);
                    Fragment from = mFragmentManager.findFragmentByTag(BasicInfoForApplyForShopFragment.TAG);
                    FragmentUtil.switchContentAddToBackStack(from, mAddressFragment,
                            R.id.apply_for_shop_container, mFragmentManager, ProvinceAddressFragment.TAG);
                    break;
                case R.id.btn_next:
                    submit();
                    break;
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setAddress();
        }
    }

    private void setAddress() {
        Bundle bundle = getArguments();
        String province = bundle.getString(Constant.PROVINCE);
        String city = bundle.getString(Constant.CITY);
        String county = bundle.getString(Constant.COUNTY);
        String detailAddress = bundle.getString(Constant.DETAIL_ADDRESS);

        if (province != null && city != null && county != null && detailAddress != null) {
            String address = new StringBuffer().append(province).append(city).append(county).append(detailAddress).toString();
            mShop.setProvince(province);
            mShop.setCity(city);
            mShop.setCounty(county);
            mShop.setDetailAddress(detailAddress);

            mAddressTv.setText(address);
        }
    }

    boolean beforeSubmit() {
        getInput();
        if (shopName == null || shopName.length() < 1) {
            Toast.makeText(getActivity(), "店名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (contacts == null || contacts.length() < 1) {
            Toast.makeText(getActivity(), "联系人不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (contactNumber == null || contactNumber.length() < 1) {
            Toast.makeText(getActivity(), "联系电话不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!InputUtil.verifyMobileNo(contactNumber)) {
            Toast.makeText(getActivity(), "电话号码不合法，请核对后再次提交", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (address == null || address.length() < 1 || address.equals("地址")) {
            Toast.makeText(getActivity(), "地址不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        mShop.setShopName(shopName);
        mShop.setContacts(contacts);
        mShop.setContactNumber(contactNumber);
        return true;
    }

    void getInput() {
        shopName = mShopNameEt.getText().toString().trim();
        contacts = mContactsEt.getText().toString().trim();
        contactNumber = mContactNumberEt.getText().toString().trim();
        address = mAddressTv.getText().toString().trim();
    }

    void submit() {
        if (beforeSubmit()) {
            if (mFragmentManager == null) {
                mFragmentManager = getActivity().getSupportFragmentManager();
            }
            mPrivateInfoFragment = mFragmentManager.findFragmentByTag(PrivateInfoForApplyForShopFragment.Tag);
            if (mPrivateInfoFragment == null) {
                mPrivateInfoFragment = new PrivateInfoForApplyForShopFragment();
            }
            Fragment from = mFragmentManager.findFragmentByTag(BasicInfoForApplyForShopFragment.TAG);

            if (mPrivateInfoFragment.getArguments() == null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.SHOP, mShop);
                mPrivateInfoFragment.setArguments(bundle);
            }

            FragmentUtil.switchContentAddToBackStack(from, mPrivateInfoFragment,
                    R.id.apply_for_shop_container, mFragmentManager, PrivateInfoForApplyForShopFragment.Tag);
        }
    }
}
