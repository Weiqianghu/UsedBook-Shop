package com.weiqianghu.usedbook_shop.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.model.entity.CityModel;
import com.weiqianghu.usedbook_shop.presenter.adapter.CommonAdapter;
import com.weiqianghu.usedbook_shop.util.Constant;
import com.weiqianghu.usedbook_shop.util.FragmentUtil;
import com.weiqianghu.usedbook_shop.view.ViewHolder;
import com.weiqianghu.usedbook_shop.view.common.BaseFragment;

import java.util.List;


public class CountyAddressFragment extends BaseFragment {

    public static final String TAG = CountyAddressFragment.class.getSimpleName();

    private String provinceName;
    private CityModel city;
    private String cityName;
    private List<String> countys;

    private TextView mProvinceTv;
    private TextView mCityTv;
    private ListView mCountyLv;

    private FragmentManager mFragmentManager;
    private Fragment mAddressSubmitFragment;
    private int layoutId;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_county_address;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView(savedInstanceState);
        initdata();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        mProvinceTv = (TextView) mRootView.findViewById(R.id.tv_province);
        mCityTv = (TextView) mRootView.findViewById(R.id.tv_city);
        mCountyLv = (ListView) mRootView.findViewById(R.id.lv_county);
        mCountyLv.setOnItemClickListener(itemClick);
    }

    void initdata() {
        Bundle bundle = getArguments();
        provinceName = bundle.getString(Constant.PROVINCE);
        layoutId = bundle.getInt(Constant.LAYOUT_ID);
        city = (CityModel) bundle.getSerializable(Constant.CITY);
        cityName = city.getName();
        countys = city.getArea();

        mProvinceTv.setText(provinceName);
        mCityTv.setText(cityName);

        mCountyLv.setAdapter(new CommonAdapter<String>(getActivity(), countys, R.layout.item_address_name) {
            @Override
            public void convert(ViewHolder helper, String item) {
                helper.setText(R.id.tv_name, item);
            }
        });
    }

    AdapterView.OnItemClickListener itemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            addressSubmit(position);
        }
    };

    private void addressSubmit(int position) {
        if (mFragmentManager == null) {
            mFragmentManager = getActivity().getSupportFragmentManager();
        }
        mAddressSubmitFragment = mFragmentManager.findFragmentByTag(AddressSubmitFragment.TAG);
        if (mAddressSubmitFragment == null) {
            mAddressSubmitFragment = new AddressSubmitFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROVINCE, provinceName);
        bundle.putSerializable(Constant.CITY, cityName);
        bundle.putString(Constant.COUNTY, countys.get(position));
        bundle.putInt(Constant.LAYOUT_ID, layoutId);
        mAddressSubmitFragment.setArguments(bundle);

        Fragment from = mFragmentManager.findFragmentByTag(CountyAddressFragment.TAG);
        FragmentUtil.switchContentAddToBackStack(from, mAddressSubmitFragment, layoutId, mFragmentManager, AddressSubmitFragment.TAG);
    }

}
