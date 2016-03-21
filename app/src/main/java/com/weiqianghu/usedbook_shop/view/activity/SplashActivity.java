package com.weiqianghu.usedbook_shop.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.model.entity.UserBean;
import com.weiqianghu.usedbook_shop.model.impl.IsLoginModel;
import com.weiqianghu.usedbook_shop.presenter.IsLoginPresenter;
import com.weiqianghu.usedbook_shop.presenter.IsShopPresenter;
import com.weiqianghu.usedbook_shop.presenter.QueryAuditStatePresenter;
import com.weiqianghu.usedbook_shop.util.CallBackHandler;
import com.weiqianghu.usedbook_shop.util.Constant;
import com.weiqianghu.usedbook_shop.util.FileUtil;
import com.weiqianghu.usedbook_shop.util.ImgUtil;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class SplashActivity extends AppCompatActivity {

    private IsLoginPresenter mIsLoginPresenter;
    private IsShopPresenter mIsShopPresenter;
    private QueryAuditStatePresenter mQueryAuditStatePresenter;

    private int auditState = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Bmob.initialize(this, "0efc92162139629c26767e7eaf7a4510");

        mIsLoginPresenter = new IsLoginPresenter();
        mIsShopPresenter = new IsShopPresenter();
        mQueryAuditStatePresenter = new QueryAuditStatePresenter(queryAuditStateHanler);

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

        Intent intent = null;

        public void run() {

            if (!mIsLoginPresenter.isLogin(SplashActivity.this)) {
                intent = new Intent(SplashActivity.this, LoginAndRegisterActivity.class);
                startActivity(intent);
                finish();
            } else if (!mIsShopPresenter.isShop(BmobUser.getCurrentUser(SplashActivity.this, UserBean.class))) {
                intent = new Intent(SplashActivity.this, ApplyForShopActivity.class);
                startActivity(intent);
                finish();
            } else {
                UserBean currentUser = BmobUser.getCurrentUser(SplashActivity.this, UserBean.class);
                if (currentUser != null) {
                    mQueryAuditStatePresenter.queryAuditState(SplashActivity.this, currentUser);
                }
            }
        }
    }

    CallBackHandler queryAuditStateHanler = new CallBackHandler() {
        public void handleSuccessMessage(Message msg) {
            switch (msg.what) {
                case Constant.SUCCESS:
                    auditState = msg.getData().getInt(Constant.AUDIT_STATE);
                    Intent intent = null;
                    if ((auditState != -1 && auditState != Constant.AUDIT_STATE1)) {
                        intent = new Intent(SplashActivity.this, AuditActivity.class);
                        intent.putExtra(Constant.AUDIT_STATE, auditState);
                    } else if (auditState == -1 && (auditState = mQueryAuditStatePresenter.queryAuditState(BmobUser.getCurrentUser(SplashActivity.this, UserBean.class))) != Constant.AUDIT_STATE1) {
                        intent = new Intent(SplashActivity.this, AuditActivity.class);
                        intent.putExtra(Constant.AUDIT_STATE, auditState);
                    } else {
                        intent = new Intent(SplashActivity.this, MainActivity.class);
                    }
                    startActivity(intent);
                    finish();
                    break;
            }
        }

        public void handleFailureMessage(String msg) {
            Toast.makeText(SplashActivity.this, msg, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SplashActivity.this, LoginAndRegisterActivity.class);
            startActivity(intent);
            finish();
        }
    };
}
