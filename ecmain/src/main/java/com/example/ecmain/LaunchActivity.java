package com.example.ecmain;

import android.content.Intent;
import android.os.Bundle;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.itsite.abase.common.BaseConstants;
import cn.itsite.abase.common.RxManager;
import cn.itsite.abase.common.UserHelper;
import cn.itsite.abase.event.EventLoginSuccess;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseActivity;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.login.LoginFragment;
import cn.itsite.login.contract.LoginService;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liujia on 02/05/2018.
 */

public class LaunchActivity extends BaseActivity{

    private RxManager mRxManager = new RxManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserHelper.init();
        mRxManager.add(HttpHelper.getService(LoginService.class)
                .requestCheckToken(LoginService.requestCheckToken, UserHelper.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == BaseConstants.RESPONSE_CODE_SUCCESS) {
                        if (bean.getData().getStatus() == 0) {
                            go2Main();
                        }else{
                            UserHelper.clear();
                            if (savedInstanceState == null) {
                                loadRootFragment(android.R.id.content, LoginFragment.newInstance());
                            }
                        }
                    } else {
                        go2Main();
                    }
                }, throwable -> {
                    ALog.e(throwable);
                    go2Main();
                }));

    }

    private void go2Main() {
        startActivity(new Intent(this, MainActivity.class));
        //此处之所以延迟退出是因为立即退出在小米手机上会有一个退出跳转动画，而我不想要这个垂直退出的跳转动画。
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventLoginSuccess event){
        startActivity(new Intent(this,MainActivity.class));
    }

    @Override
    public boolean swipeBackPriority() {
        return false;
    }
}
