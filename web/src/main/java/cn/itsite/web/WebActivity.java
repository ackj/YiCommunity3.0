package cn.itsite.web;

import android.os.Bundle;

import cn.itsite.abase.mvp.view.base.BaseActivity;

/**
 * Author：leguang on 2017/4/12 0009 15:49
 * Email：langmanleguang@qq.com
 * <p>
 * 负责项目中的web部分。
 */

public class WebActivity extends BaseActivity {
    public static final String TAG = WebActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (savedInstanceState == null) {
            loadRootFragment(android.R.id.content, WebFragment.newInstance(bundle));
        }
    }
}
