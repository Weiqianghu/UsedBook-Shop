package com.weiqianghu.usedbook_shop.view.activity;

import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.model.entity.SerializableHandler;
import com.weiqianghu.usedbook_shop.util.Constant;
import com.weiqianghu.usedbook_shop.util.FragmentUtil;
import com.weiqianghu.usedbook_shop.view.fragment.MainFragment;


public class MainActivity extends AppCompatActivity {
    private FragmentManager mFragmentManager;
    private Fragment mFragment;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        mFragment=mFragmentManager.findFragmentByTag(MainFragment.TAG);
        if(mFragment==null){
            mFragment=new MainFragment(toolBarHandler);
        }
        FragmentUtil.addContentNoAnimation(R.id.main_container,mFragment,mFragmentManager,MainFragment.TAG );

        mToolbar= (Toolbar) findViewById(R.id.center_toolbar);
        if(mToolbar!=null) {
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
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };


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
