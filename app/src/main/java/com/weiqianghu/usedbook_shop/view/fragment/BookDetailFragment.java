package com.weiqianghu.usedbook_shop.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.view.common.BaseFragment;


public class BookDetailFragment extends BaseFragment {
    public static final String TAG = BookDetailFragment.class.getSimpleName();

    private Toolbar mToolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book_detail;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mToolbar = (Toolbar) getActivity().findViewById(R.id.center_toolbar);
        mToolbar.setTitle("详情");
    }

}
