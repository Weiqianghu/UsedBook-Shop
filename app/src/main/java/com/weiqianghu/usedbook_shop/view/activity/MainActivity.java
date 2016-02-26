package com.weiqianghu.usedbook_shop.view.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.util.FragmentUtil;
import com.weiqianghu.usedbook_shop.view.fragment.MainFragment;


public class MainActivity extends AppCompatActivity {
    private FragmentManager mFragmentManager;
    private Fragment mFragment;

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
            mFragment=new MainFragment();
        }
        FragmentUtil.addContentNoAnimation(R.id.main_container,mFragment,mFragmentManager,MainFragment.TAG );
    }
}
