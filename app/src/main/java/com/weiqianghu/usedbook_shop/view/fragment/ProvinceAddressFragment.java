package com.weiqianghu.usedbook_shop.view.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.model.entity.ProvinceModel;
import com.weiqianghu.usedbook_shop.presenter.adapter.CommonAdapter;
import com.weiqianghu.usedbook_shop.util.Constant;
import com.weiqianghu.usedbook_shop.util.FileUtil;
import com.weiqianghu.usedbook_shop.util.FragmentUtil;
import com.weiqianghu.usedbook_shop.util.ThreadPool;
import com.weiqianghu.usedbook_shop.view.ViewHolder;
import com.weiqianghu.usedbook_shop.view.common.BaseFragment;

import java.lang.reflect.Type;
import java.util.List;


public class ProvinceAddressFragment extends BaseFragment {

    public static final String TAG=ProvinceAddressFragment.class.getSimpleName();
    private List<ProvinceModel> provinceModelList=null;

    private ListView mProvinceLv;
    private CommonAdapter<ProvinceModel> mAdapter;

    private FragmentManager mFragmentManager;
    private Fragment mCityAddressFragment;

    private ProgressBar mLoading;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_province_address;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        new GetDataAsyncTask().executeOnExecutor(ThreadPool.getThreadPool());

        mProvinceLv= (ListView) mRootView.findViewById(R.id.lv_province);
        mProvinceLv.setOnItemClickListener(itemClick);

        mLoading = (ProgressBar) mRootView.findViewById(R.id.pb_loading);
        mLoading.setVisibility(View.VISIBLE);
    }


    class GetDataAsyncTask extends AsyncTask<Void,Void,List<ProvinceModel>>{


        @Override
        protected List<ProvinceModel> doInBackground(Void... params) {
            return getProvincesByJSON();
        }

        @Override
        protected void onPostExecute(List<ProvinceModel> provinceModels) {
            mLoading.setVisibility(View.GONE);
            provinceModelList=provinceModels;
            super.onPostExecute(provinceModels);
            mProvinceLv.setAdapter(mAdapter=new CommonAdapter<ProvinceModel>(getActivity(),provinceModels,R.layout.item_address_name) {
                @Override
                public void convert(ViewHolder helper, ProvinceModel item) {
                    helper.setText(R.id.tv_name,item.getName());
                }
            });
        }
    }

    AdapterView.OnItemClickListener itemClick=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectCity(position);
        }
    };

    private void selectCity(int position) {
        if(mFragmentManager==null){
            mFragmentManager=getActivity().getSupportFragmentManager();
        }
        mCityAddressFragment=mFragmentManager.findFragmentByTag(CityAddressFragment.TAG);
        if(mCityAddressFragment==null){
            mCityAddressFragment=new CityAddressFragment();
        }

        Bundle bundle=new Bundle();
        bundle.putSerializable(Constant.PROVINCE,provinceModelList.get(position));
        mCityAddressFragment.setArguments(bundle);

        Fragment from=mFragmentManager.findFragmentByTag(ProvinceAddressFragment.TAG);
        FragmentUtil.switchContentAddToBackStack(from,mCityAddressFragment,R.id.apply_for_shop_container,mFragmentManager,CityAddressFragment.TAG);
    }

    private List<ProvinceModel> getProvincesByJSON(){
        List<ProvinceModel> provinceModelList;
        Gson g=new Gson();
        Type lt=new TypeToken<List<ProvinceModel>>(){}.getType();

        String json= FileUtil.getStrFromRaw(getResources().openRawResource(R.raw.address));
        provinceModelList=g.fromJson(json,lt);
        return provinceModelList;
    }

}
