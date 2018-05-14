package cn.itsite.login;

import android.os.Bundle;

import cn.itsite.abase.mvp.view.base.BaseActivity;

/**
 * Created by liujia on 11/05/2018.
 */

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_main_activity, LoginFragment.newInstance());
        }
    }

    @Override
    public boolean swipeBackPriority() {
        return true;
    }
}
