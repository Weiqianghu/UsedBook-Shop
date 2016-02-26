package com.weiqianghu.usedbook_shop.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.util.FileUtil;
import com.weiqianghu.usedbook_shop.util.ImgUtil;

import java.io.File;

import cn.bmob.v3.Bmob;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Bmob.initialize(this, "0efc92162139629c26767e7eaf7a4510");

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

        public void run() {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
