package com.weiqianghu.usedbook_shop.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.util.FragmentUtil;
import com.weiqianghu.usedbook_shop.view.fragment.SuggestApplyForShopFragment;

public class ApplyForShopActivity extends AppCompatActivity {

    private TextView mTitle;
    private FragmentManager mFragmentManager;
    private Fragment mFragment;

    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for_shop);

        initView();
    }

    private void initView() {
        mTitle = (TextView) findViewById(R.id.title);
        mTitle.setText(R.string.apply_for_shop);
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        mFragment=mFragmentManager.findFragmentByTag(SuggestApplyForShopFragment.TAG);
        if(mFragment==null){
            mFragment=new SuggestApplyForShopFragment();
        }
        FragmentUtil.addContentNoAnimation(R.id.apply_for_shop_container,mFragment,mFragmentManager,SuggestApplyForShopFragment.TAG );

        mToolBar= (Toolbar) findViewById(R.id.toolbar);
        mToolBar.setNavigationIcon(R.mipmap.left);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
