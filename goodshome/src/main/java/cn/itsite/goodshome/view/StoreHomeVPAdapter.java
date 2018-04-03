package cn.itsite.goodshome.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.itsite.abase.BaseApp;
import cn.itsite.goodshome.R;

/**
 * Author： Administrator on 2018/1/30 0030.
 * Email： liujia95me@126.com
 */

public class StoreHomeVPAdapter extends FragmentPagerAdapter {

    private String[] mTitles = BaseApp.mContext.getResources().getStringArray(R.array.store_home_tabs);

    public StoreHomeVPAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return position == 0 ? StoreFragment.newInstance("smartHome") : StoreFragment.newInstance("shop");
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public int getCount() {
        return 2;
    }
}
