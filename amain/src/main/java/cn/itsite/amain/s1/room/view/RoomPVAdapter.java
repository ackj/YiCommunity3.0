package cn.itsite.amain.s1.room.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Author: LiuJia on 2017/4/27 0027 10:13.
 * Email: liujia95me@126.com
 */

public class RoomPVAdapter extends FragmentPagerAdapter {

    private List<SupportFragment> fragments;
    private String[] titles = {"卧室","大厅","厨房"};

    public RoomPVAdapter(List<SupportFragment> fragments, FragmentManager fm){
        this(fm);
        this.fragments = fragments;
    }

    public RoomPVAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
