package com.weiqianghu.usedbook_shop.view.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.model.entity.SerializableHandler;
import com.weiqianghu.usedbook_shop.model.entity.UserBean;
import com.weiqianghu.usedbook_shop.presenter.messagehandler.ChatMessageHandler;
import com.weiqianghu.usedbook_shop.util.Constant;
import com.weiqianghu.usedbook_shop.util.FragmentUtil;
import com.weiqianghu.usedbook_shop.util.ServiceUtil;
import com.weiqianghu.usedbook_shop.view.fragment.MainFragment;
import com.weiqianghu.usedbook_shop.view.fragment.SeetingsFragment;
import com.weiqianghu.usedbook_shop.view.service.OrderRealTimeService;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

public class MainActivity extends AppCompatActivity {
    private FragmentManager mFragmentManager;
    private Fragment mFragment;

    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBmobIm();
        initView();
    }

    private void initBmobIm() {
        UserBean user = BmobUser.getCurrentUser(MainActivity.this, UserBean.class);
        BmobIM.connect(user.getObjectId(), new ConnectListener() {
            @Override
            public void done(String uid, BmobException e) {
                if (e == null) {
                    Log.i("im", "connect success");
                } else {
                    Log.e("im", e.getErrorCode() + "/" + e.getMessage());
                }
            }
        });
    }

    private void initView() {
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        mFragment = mFragmentManager.findFragmentByTag(MainFragment.TAG);
        if (mFragment == null) {
            mFragment = new MainFragment(toolBarHandler);
        }
        FragmentUtil.addContentNoAnimation(R.id.main_container, mFragment, mFragmentManager, MainFragment.TAG);

        mToolbar = (Toolbar) findViewById(R.id.center_toolbar);
        if (mToolbar != null) {
            mToolbar.setTitle(R.string.main_page);
            mToolbar.inflateMenu(R.menu.main);
            mToolbar.setOnMenuItemClickListener(onMenuItemClick);
        }
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.ab_message:
                    gotoMessageList();
                    break;
                case R.id.ab_search:
                    msg += "ab_search";
                    break;
                case R.id.action_settings:
                    gotoSettings();
                    break;
                case R.id.action_about:
                    msg += "action_about";
                    break;
            }

            if (!msg.equals("")) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

    private void gotoMessageList() {
        Intent intent = new Intent(MainActivity.this, MessageListActivity.class);
        startActivity(intent);
    }

    private void gotoSettings() {
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        mFragment = mFragmentManager.findFragmentByTag(SeetingsFragment.TAG);
        if (mFragment == null) {
            mFragment = new SeetingsFragment();
        }
        Fragment from = mFragmentManager.findFragmentByTag(MainFragment.TAG);
        FragmentUtil.switchContentAddToBackStack(from, mFragment, R.id.main_container, mFragmentManager, SeetingsFragment.TAG);
    }


    private SerializableHandler toolBarHandler = new SerializableHandler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case Constant.TAB_SHOP:
                    mToolbar.setTitle(R.string.shop);
                    break;
                case Constant.TAB_ORDER:
                    mToolbar.setTitle(R.string.order);
                    break;
                case Constant.TAB_MINE:
                    mToolbar.setTitle(R.string.mine);
                    break;
                case Constant.ADD_NEW_BOOK:
                    mToolbar.setTitle(R.string.add_new_book);
                    break;
            }
        }
    };
}
