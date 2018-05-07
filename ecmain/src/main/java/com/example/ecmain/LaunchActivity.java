package com.example.ecmain;

import android.content.Intent;
import android.os.Bundle;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.itsite.abase.event.EventLoginSuccess;
import cn.itsite.abase.mvp.view.base.BaseActivity;
import cn.itsite.login.LoginFragment;

/**
 * Created by liujia on 02/05/2018.
 */

public class LaunchActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            loadRootFragment(android.R.id.content, LoginFragment.newInstance());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventLoginSuccess event){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    public boolean swipeBackPriority() {
        return false;
    }
}
