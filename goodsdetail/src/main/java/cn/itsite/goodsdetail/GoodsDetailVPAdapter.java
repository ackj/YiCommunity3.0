package cn.itsite.goodsdetail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/21 0021 14:36
 */

public class GoodsDetailVPAdapter extends FragmentPagerAdapter {
    public static final String TAG = GoodsDetailVPAdapter.class.getSimpleName();

    private ProductDetailBean bean;

    public GoodsDetailVPAdapter(FragmentManager fm, ProductDetailBean bean) {
        super(fm);
        this.bean = bean;
    }

    @Override
    public Fragment getItem(int position) {
        return position == 0 ? GoodsFragment.newInstance(bean) : DetailFragment.newInstance(bean.getDetail().getUrl());
    }

    @Override
    public int getCount() {
        return TextUtils.isEmpty(bean.getDetail().getUrl()) ? 1 : 2;
    }
}
