package com.weiqianghu.usedbook_shop.view.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.presenter.adapter.FragmentViewPagerAdapter;
import com.weiqianghu.usedbook_shop.view.common.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends BaseFragment {

    public static final String TAG = OrderFragment.class.getSimpleName();

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<Fragment> mViews = new ArrayList<>();
    private FragmentViewPagerAdapter mPagerAdapter;
    private FragmentManager mFragmentManager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_list;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTabLayout = (TabLayout) mRootView.findViewById(R.id.tablayout);
        mViewPager = (ViewPager) mRootView.findViewById(R.id.viewpager);

        Fragment mOrderPayFragment = new OrderPayFragment();
        Fragment mOrderDeliverFragment = new OrderDeliverFragment();
        Fragment mOrderExpressFragment = new OrderExpressFragment();
        Fragment mOrderEvaluateFragment = new OrderEvaluateFragment();
        Fragment mOrderFinishFragment = new OrderFinishFragment();

        mViews.add(mOrderPayFragment);
        mViews.add(mOrderDeliverFragment);
        mViews.add(mOrderExpressFragment);
        mViews.add(mOrderEvaluateFragment);
        mViews.add(mOrderFinishFragment);

        String[] mTitles = {getActivity().getString(R.string.pay),
                getActivity().getString(R.string.deliver), getActivity().getString(R.string.express)
                , getActivity().getString(R.string.evaluate), getActivity().getString(R.string.finish)};


        mFragmentManager = getChildFragmentManager();
        mPagerAdapter = new FragmentViewPagerAdapter(mFragmentManager, mViews, mTitles);

        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d("TabSelectedListener", "onTabUnselected");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d("TabSelectedListener", "onTabReselected");
            }
        });

    }

}
