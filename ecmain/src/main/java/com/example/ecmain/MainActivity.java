package com.example.ecmain;

import android.os.Bundle;

import cn.itsite.abase.mvp.view.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            loadRootFragment(android.R.id.content, MainFragment.newInstance());
        }
    }

    @Override
    public boolean swipeBackPriority() {
        return false;
    }
}
