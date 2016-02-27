package com.weiqianghu.usedbook_shop.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.weiqianghu.usedbook_shop.R;
import com.weiqianghu.usedbook_shop.view.fragment.LoginFragment;


public class LoginAndRegisterActivity extends AppCompatActivity {

    private TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_register);

        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.login_and_register_container,new LoginFragment());
        ft.commit();

        mTitle= (TextView)findViewById(R.id.title);
        mTitle.setText(R.string.register_login);
    }
}
