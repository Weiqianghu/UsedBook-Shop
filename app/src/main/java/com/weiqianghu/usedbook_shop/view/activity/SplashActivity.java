package com.weiqianghu.usedbook_shop.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.model.entity.UserBean;
import com.weiqianghu.usedbook_shop.model.impl.IsLoginModel;
import com.weiqianghu.usedbook_shop.presenter.IsLoginPresenter;
import com.weiqianghu.usedbook_shop.presenter.IsShopPresenter;
import com.weiqianghu.usedbook_shop.util.FileUtil;
import com.weiqianghu.usedbook_shop.util.ImgUtil;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class SplashActivity extends AppCompatActivity {

    private IsLoginPresenter mIsLoginPresenter;
    private IsShopPresenter mIsShopPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Bmob.initialize(this, "0efc92162139629c26767e7eaf7a4510");

        mIsLoginPresenter=new IsLoginPresenter();
        mIsShopPresenter=new IsShopPresenter();
        //删除上传文件时生成的临时文件
        new Thread() {
            public void run() {
                ImgUtil.deleteAllTempFiles(new File(FileUtil.getCachePath() + "/tempImg/"));
            }
        }.start();

        Handler x = new Handler();
        x.postDelayed(new splashHandler(), 3000);
    }

    class splashHandler implements Runnable {

        Intent intent=null;
        public void run() {
            if(!mIsLoginPresenter.isLogin(SplashActivity.this)){
                intent = new Intent(SplashActivity.this, LoginAndRegisterActivity.class);
            }else if(!mIsShopPresenter.isShop(BmobUser.getCurrentUser(SplashActivity.this, UserBean.class))){
                intent = new Intent(SplashActivity.this, ApplyForShopActivity.class);
            }else {
                intent= new Intent(SplashActivity.this, MainActivity.class);
            }
            startActivity(intent);
            finish();
        }
    }
}
