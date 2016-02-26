package com.weiqianghu.usedbook_shop.view.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.presenter.adapter.MainPagerAdapter;
import com.weiqianghu.usedbook_shop.view.common.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends BaseFragment {
    public static final String TAG=MainFragment.class.getSimpleName();

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<Fragment> mViews = new ArrayList<>();
    private MainPagerAdapter mPagerAdapter;

    private FragmentManager mFragmentManager;

    private int count=0;

    private Toolbar mToolbar;



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
        mTabLayout= (TabLayout) mRootView.findViewById(R.id.tl_main);
        mViewPager= (ViewPager) mRootView.findViewById(R.id.vp_main);

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Fragment mShopFragment=new ShopFragment();
        Fragment mOrderFragment=new OrderFragment();
        Fragment mMineFragment=new MineFragment();

        mViews.add(mShopFragment);
        mViews.add(mOrderFragment);
        mViews.add(mMineFragment);

        String[] mTitles={getActivity().getString(R.string.shop),
                getActivity().getString(R.string.order),getActivity().getString(R.string.mine)};

        mFragmentManager=getChildFragmentManager();
        mPagerAdapter=new MainPagerAdapter(mFragmentManager,mViews,mTitles);

        mViewPager.setAdapter(mPagerAdapter);
       mTabLayout.setupWithViewPager(mViewPager);

        mRootView.setFocusable(true);
        mRootView.setFocusableInTouchMode(true);
        mRootView.setOnKeyListener(new View.OnKeyListener(){

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_BACK){
                    exit(v);
                    return true;
                }
                return false;
            }
        });

        mToolbar= (Toolbar) mRootView.findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.main_page);
        mToolbar.inflateMenu(R.menu.main);
        mToolbar.setOnMenuItemClickListener(onMenuItemClick);
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.ab_message:
                    msg += "ab_message";
                    break;
                case R.id.ab_search:
                    msg += "ab_search";
                    break;
                case R.id.action_settings:
                    msg += "action_settings";
                    break;
                case R.id.action_about:
                    msg += "action_about";
                    break;
            }

            if(!msg.equals("")) {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };


    public void exit(View v) {
        if(count<1){
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
        } else{
            getActivity().finish();
        }
    }
}
