package cn.itsite.goodshome;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.itsite.abase.mvp.view.base.BaseActivity;
import cn.itsite.goodshome.view.StoreHomeFragment;

@Route(path = "/goodshome/main")
public class MainActivity extends BaseActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            loadRootFragment(android.R.id.content, StoreHomeFragment.newInstance());
        }
    }
}
