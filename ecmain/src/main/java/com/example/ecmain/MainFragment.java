package com.example.ecmain;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.ecmain.mine.view.MineFragment;

import java.util.ArrayList;

import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.acommon.AudioPlayer;
import cn.itsite.goodshome.view.StoreHomeECFragment;
import cn.itsite.shoppingcart.ShoppingCartECFragment;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Author： Administrator on 2018/2/1 0001.
 * Email： liujia95me@126.com
 */

public class MainFragment extends BaseFragment  {
    private static final String TAG = MainFragment.class.getSimpleName();
    private static final long WAIT_TIME = 2000L;// 再点一次退出程序时间设置
    private long TOUCH_TIME = 0;
    private AHBottomNavigation ahbn;
    private ArrayList<AHBottomNavigationItem> bottomItems = new ArrayList<>();
    private int prePosition = 0;
    private SupportFragment[] mFragments = new SupportFragment[4];

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ahbn = ((AHBottomNavigation) view.findViewById(R.id.ahbn_main_fragment));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            mFragments[0] = StoreHomeECFragment.newInstance(true);
            mFragments[1] = StoreHomeECFragment.newInstance(false);
            mFragments[2] = ShoppingCartECFragment.newInstance();
            mFragments[3] = MineFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_container_main_fragment, 0, mFragments[0], mFragments[1], mFragments[2], mFragments[3]);
        } else {
            mFragments[0] = findChildFragment(StoreHomeECFragment.class);
            mFragments[1] = findChildFragment(StoreHomeECFragment.class);
            mFragments[2] = findChildFragment(ShoppingCartECFragment.class);
            mFragments[3] = findChildFragment(MineFragment.class);
            prePosition = savedInstanceState.getInt("prePosition");
        }
        initData();
    }

    private void initData() {
        updateApp();//检测App的更新。
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_discover, R.drawable.tab_zhinengray_78px, R.color.white);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_shop, R.drawable.tab_bianligray_78px, R.color.white);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_cart, R.drawable.ic_tab_shoppingcart_78px, R.color.white);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.tab_mine, R.drawable.ic_tab_mine_78px, R.color.white);
        bottomItems.add(item1);
        bottomItems.add(item2);
        bottomItems.add(item3);
        bottomItems.add(item4);
        ahbn.addItems(bottomItems);
        ahbn.setDefaultBackgroundColor(ContextCompat.getColor(App.mContext, R.color.white));
        ahbn.setBehaviorTranslationEnabled(false);
        ahbn.setColored(true);
        ahbn.setForceTint(false);
        ahbn.setAccentColor(ContextCompat.getColor(App.mContext, R.color.base_color));
        ahbn.setInactiveColor(ContextCompat.getColor(App.mContext, R.color.base_gray));
        ahbn.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        ahbn.setOnTabSelectedListener((position, wasSelected) -> {
            showHideFragment(mFragments[position], mFragments[prePosition]);
            prePosition = position;
            AudioPlayer.getInstance(_mActivity).play(2);

//            if (wasSelected) {
//                switch (position) {
//                    case 0:
//                        ((HomeFragment) mFragments[0]).go2TopAndRefresh();
//                        break;
//                    case 1:
//                        ((StoreHomeFragment) mFragments[1]).go2TopAndRefresh(null);
//                        break;
//                    case 2:
//                        ((SocialityFragment) mFragments[2]).go2TopAndRefresh();
//                        break;
//                    case 3:
//                        break;
//                    default:
//                        break;
//                }
//            }
            return true;
        });
        ahbn.setCurrentItem(prePosition);
//        GuideHelper.showHomeGuide(_mActivity);
    }

    private void updateApp() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("prePosition", prePosition);
    }

    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            //退到桌面，而不是退出应用，让用户以为退出应用，尽量保活。
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        } else {
            TOUCH_TIME = System.currentTimeMillis();
        }
        return true;
    }
}
