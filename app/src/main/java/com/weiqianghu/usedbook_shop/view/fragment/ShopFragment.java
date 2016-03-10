package com.weiqianghu.usedbook_shop.view.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.model.entity.SerializableHandler;
import com.weiqianghu.usedbook_shop.util.Constant;
import com.weiqianghu.usedbook_shop.util.FragmentUtil;
import com.weiqianghu.usedbook_shop.view.common.BaseFragment;

public class ShopFragment extends BaseFragment {
    public static final String TAG = ShopFragment.class.getSimpleName();

    private SerializableHandler mHandler;

    public ShopFragment() {
    }

    public ShopFragment(SerializableHandler handler) {
        this.mHandler = handler;
    }

    private Button mAddNewBookBtn;
    private FragmentManager mFragmentManager;
    private Fragment mFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shop;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initView(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Click click = new Click();

        mAddNewBookBtn = (Button) mRootView.findViewById(R.id.btn_add_new_book);
        mAddNewBookBtn.setOnClickListener(click);
    }

    private class Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_add_new_book:
                    gotoAddNewBook();
                    break;
            }
        }
    }

    private void gotoAddNewBook() {
        if (mFragmentManager == null) {
            mFragmentManager = getActivity().getSupportFragmentManager();
        }
        mFragment = mFragmentManager.findFragmentByTag(AddNewBookFragment.TAG);
        if (mFragment == null) {
            mFragment = new AddNewBookFragment();
        }
        Fragment form = mFragmentManager.findFragmentByTag(MainFragment.TAG);
        FragmentUtil.switchContentAddToBackStack(form, mFragment, R.id.main_container, mFragmentManager, AddNewBookFragment.TAG);

        Message message = new Message();
        message.what = Constant.ADD_NEW_BOOK;
        mHandler.sendMessage(message);
    }
}
