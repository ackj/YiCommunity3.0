package cn.itsite.amain.yicommunity.main.parking.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.itsite.amain.yicommunity.common.Constants;


/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * [我的车卡]的View层。
 * 打开方式：StartApp-->管家-->智慧管家[办理车卡]
 */
public class ApplyCardAdapter extends FragmentPagerAdapter {
    public static final String TAG = ApplyCardAdapter.class.getSimpleName();

    public ApplyCardAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.TYPE, position);
        return ApplyFragment.newInstance(bundle);
    }
}
