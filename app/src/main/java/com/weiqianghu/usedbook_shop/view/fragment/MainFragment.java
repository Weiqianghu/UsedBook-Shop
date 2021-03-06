package com.weiqianghu.usedbook_shop.view.fragment;


import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.model.entity.SerializableHandler;
import com.weiqianghu.usedbook_shop.presenter.adapter.FragmentViewPagerAdapter;
import com.weiqianghu.usedbook_shop.util.Constant;
import com.weiqianghu.usedbook_shop.view.common.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends BaseFragment {
    public static final String TAG = MainFragment.class.getSimpleName();

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<Fragment> mViews = new ArrayList<>();
    private FragmentViewPagerAdapter mPagerAdapter;

    private FragmentManager mFragmentManager;

    private int count = 0;

    private Toolbar mToolBar;

    public MainFragment() {
    }



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

        initView(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolBar= (Toolbar) getActivity().findViewById(R.id.center_toolbar);

        mTabLayout = (TabLayout) mRootView.findViewById(R.id.tl_main);
        mViewPager = (ViewPager) mRootView.findViewById(R.id.vp_main);

        Fragment mShopFragment = new ShopFragment();
        Fragment mOrderFragment = new OrderDeliverFragment();
        Fragment mMineFragment = new MineFragment();

        mViews.add(mShopFragment);
        mViews.add(mOrderFragment);
        mViews.add(mMineFragment);

        String[] mTitles = {getActivity().getString(R.string.shop),
                getActivity().getString(R.string.order), getActivity().getString(R.string.mine)};

        mFragmentManager = getChildFragmentManager();
        mPagerAdapter = new FragmentViewPagerAdapter(mFragmentManager, mViews, mTitles);

        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        mToolBar.setTitle(R.string.shop);
                        break;
                    case 1:
                        mToolBar.setTitle(R.string.order);
                        break;
                    case 2:
                        mToolBar.setTitle(R.string.mine);
                        break;
                }
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

        mRootView.setFocusable(true);
        mRootView.setFocusableInTouchMode(true);
        mRootView.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    exit(v);
                    return true;
                }
                return false;
            }
        });

    }


    public void exit(View v) {
        if (count < 1) {
            Toast.makeText(getActivity(), "再按一次退出", Toast.LENGTH_SHORT).show();
        }
        if (count < 2) {
            count++;
            new Thread() {
                public void run() {
                    try {
                        sleep(3000);
                        count = 0;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } else {
            getActivity().finish();
        }
    }
}
