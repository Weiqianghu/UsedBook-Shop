package com.weiqianghu.usedbook_shop.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.view.common.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends BaseFragment {
    public static final String TAG=OrderFragment.class.getSimpleName();

    private TextView mEmptyTv;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mEmptyTv.setText(R.string.order_empty);
    }

}
