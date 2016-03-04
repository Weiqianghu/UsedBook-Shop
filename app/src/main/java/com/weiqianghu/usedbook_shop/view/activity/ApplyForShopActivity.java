package com.weiqianghu.usedbook_shop.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.util.Constant;
import com.weiqianghu.usedbook_shop.util.FragmentUtil;
import com.weiqianghu.usedbook_shop.util.ImgUtil;
import com.weiqianghu.usedbook_shop.view.fragment.PrivateInfoForApplyForShopFragment;
import com.weiqianghu.usedbook_shop.view.fragment.SuggestApplyForShopFragment;

import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class ApplyForShopActivity extends AppCompatActivity {

    private TextView mTitle;
    private FragmentManager mFragmentManager;
    private Fragment mPrivateInfoFragment;
    private Fragment mFragment;

    private Toolbar mToolBar;

    private ImageView mIdFrontIv;
    private ImageView mIdBackIv;


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
        mFragment = mFragmentManager.findFragmentByTag(SuggestApplyForShopFragment.TAG);
        if (mFragment == null) {
            mFragment = new SuggestApplyForShopFragment();
        }
        FragmentUtil.addContentNoAnimation(R.id.apply_for_shop_container, mFragment, mFragmentManager, SuggestApplyForShopFragment.TAG);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.REQUEST_ID_FRONT) {
            if (resultCode == RESULT_OK) {
                // 获取返回的图片列表
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                // 处理你自己的逻辑 ....

                if (mFragmentManager == null) {
                    mFragmentManager = getSupportFragmentManager();
                }
                mPrivateInfoFragment = mFragmentManager.findFragmentByTag(PrivateInfoForApplyForShopFragment.Tag);
                if (mPrivateInfoFragment == null) {
                    mPrivateInfoFragment = new PrivateInfoForApplyForShopFragment();
                }
                if (mIdFrontIv == null) {
                    mIdFrontIv = (ImageView) findViewById(R.id.iv_id_front);
                }
                String smallPath = ImgUtil.getSmallImgPath(path.get(0), mIdFrontIv.getWidth(), mIdFrontIv.getHeight());
                mPrivateInfoFragment.getArguments().putString(Constant.REQUEST_ID_FRONT_STR, smallPath);
            }
        }
        if (requestCode == Constant.REQUEST_ID_BACK) {
            if (resultCode == RESULT_OK) {
                // 获取返回的图片列表
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                // 处理你自己的逻辑 ....

                if (mFragmentManager == null) {
                    mFragmentManager = getSupportFragmentManager();
                }
                mPrivateInfoFragment = mFragmentManager.findFragmentByTag(PrivateInfoForApplyForShopFragment.Tag);
                if (mPrivateInfoFragment == null) {
                    mPrivateInfoFragment = new PrivateInfoForApplyForShopFragment();
                }
                if (mIdBackIv == null) {
                    mIdBackIv = (ImageView) findViewById(R.id.iv_id_back);
                }
                String smallPath = ImgUtil.getSmallImgPath(path.get(0), mIdBackIv.getWidth(), mIdBackIv.getHeight());
                mPrivateInfoFragment.getArguments().putString(Constant.REQUEST_ID_BACK_STR, smallPath);
            }
        }
    }
}
