package cn.itsite.amain.s1.net;

import android.os.Bundle;

import cn.itsite.abase.mvp.view.base.BaseActivity;
import cn.itsite.amain.R;
import cn.itsite.amain.s1.net.view.NetFragment;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 配网模块。
 */

public class NetActivity extends BaseActivity {
    private static final String TAG = NetActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_main_activity, NetFragment.newInstance());
        }
    }

    @Override
    public boolean swipeBackPriority() {
        return false;
    }
}
