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
import com.weiqianghu.usedbook_shop.model.entity.ProvinceModel;
import com.weiqianghu.usedbook_shop.presenter.adapter.CommonAdapter;
import com.weiqianghu.usedbook_shop.util.Constant;
import com.weiqianghu.usedbook_shop.util.FragmentUtil;
import com.weiqianghu.usedbook_shop.view.ViewHolder;
import com.weiqianghu.usedbook_shop.view.common.BaseFragment;

import java.util.List;


public class CityAddressFragment extends BaseFragment {

    public static final String TAG = CityAddressFragment.class.getSimpleName();

    private ProvinceModel province;
    private String provinceName;
    private List<CityModel> citys;

    private TextView mProvinceTv;
    private ListView mCityLv;

    private FragmentManager mFragmentManager;
    private Fragment mCountyAddressFragment;
    private int layoutId;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_city_address;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView(savedInstanceState);
        initdata();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mProvinceTv = (TextView) mRootView.findViewById(R.id.tv_province);
        mCityLv = (ListView) mRootView.findViewById(R.id.lv_city);
        mCityLv.setOnItemClickListener(itemClick);

    }

    void initdata() {
        Bundle bundle = getArguments();
        province = (ProvinceModel) bundle.getSerializable(Constant.PROVINCE);
        provinceName = province.getName();
        citys = province.getCity();
        layoutId = bundle.getInt(Constant.LAYOUT_ID);

        mProvinceTv.setText(provinceName);
        mCityLv.setAdapter(new CommonAdapter<CityModel>(getActivity(), citys, R.layout.item_address_name) {
            @Override
            public void convert(ViewHolder helper, CityModel item) {
                helper.setText(R.id.tv_name, item.getName());
            }
        });
    }


    AdapterView.OnItemClickListener itemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectCounty(position);
        }
    };

    private void selectCounty(int position) {
        if (mFragmentManager == null) {
            mFragmentManager = getActivity().getSupportFragmentManager();
        }
        mCountyAddressFragment = mFragmentManager.findFragmentByTag(CountyAddressFragment.TAG);
        if (mCountyAddressFragment == null) {
            mCountyAddressFragment = new CountyAddressFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.CITY, citys.get(position));
        bundle.putString(Constant.PROVINCE, provinceName);
        bundle.putInt(Constant.LAYOUT_ID, layoutId);
        mCountyAddressFragment.setArguments(bundle);

        Fragment from = mFragmentManager.findFragmentByTag(CityAddressFragment.TAG);
        FragmentUtil.switchContentAddToBackStack(from, mCountyAddressFragment, layoutId, mFragmentManager, CountyAddressFragment.TAG);
    }

}
