package cn.itsite.amain.yicommunity.main.mypublish;

import android.os.Bundle;

import cn.itsite.abase.mvp.view.base.BaseActivity;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.main.mypublish.view.MyPublishFragment;


/**
 * 我的发布的父容器
 */
public class MyPublishActivity extends BaseActivity {
    private static final String TAG = MyPublishActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_main_activity, MyPublishFragment.newInstance());
        }
    }
}
