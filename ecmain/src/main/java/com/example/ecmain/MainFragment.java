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
import com.example.ecmain.common.UpdateAppHttpUtils;
import com.example.ecmain.entity.AppUpdateBean;
import com.example.ecmain.mine.view.MineFragment;
import com.google.gson.Gson;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.itsite.abase.cache.SPCache;
import cn.itsite.abase.common.BaseConstants;
import cn.itsite.abase.event.EventECLogout;
import cn.itsite.abase.event.EventLoginSuccess;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.utils.AppUtils;
import cn.itsite.acommon.AudioPlayer;
import cn.itsite.acommon.Constants;
import cn.itsite.acommon.event.RefreshCartRedPointEvent;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
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
        refreshPoint();

        ahbn.setOnTabSelectedListener((position, wasSelected) -> {
            showHideFragment(mFragments[position], mFragments[prePosition]);
            prePosition = position;
            AudioPlayer.getInstance(_mActivity).play(2);
            return true;
        });
        ahbn.setCurrentItem(prePosition);
//        GuideHelper.showHomeGuide(_mActivity);
    }

    private void refreshPoint(){
        int cartNum = (int) SPCache.get(_mActivity, BaseConstants.KEY_CART_NUM, 0);
        ahbn.setNotificationBackgroundColor(getResources().getColor(R.color.base_color));
        ahbn.setNotification(cartNum+"",2);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventLoginSuccess event){
        refreshPoint();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventECLogout eventLogout){
        ahbn.setNotification("",2);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RefreshCartRedPointEvent event) {
        ahbn.setNotification(event.getNumber()+"",2);
    }

    private void updateApp() {
        String random = System.currentTimeMillis() + "";
        String accessKey = Constants.SYS_ACCESS_PREFIX + random + Constants.SYS_ACCESS_KEY;
        ALog.e("random-->" + random);
        ALog.e("accessKey-->" + accessKey);
        ALog.e("getMd5(accessKey)-->" + getMd5(accessKey));

        Map<String, String> params = new HashMap<>();
        params.put("accessKey", getMd5(accessKey));
        params.put("random", random);
        params.put("sc", BuildConfig.SC);
        params.put("appType", Constants.APP_TYPE);

        new UpdateAppManager
                .Builder()
                .setActivity(_mActivity)
                .setHttpManager(new UpdateAppHttpUtils())
                .setPost(true)
                .setParams(params)
                .setUpdateUrl(Constants.APP_UPDATE_URL)
                .hideDialogOnDownloading(false)
                .build()
                .checkNewApp(new UpdateCallback() {
                    @Override
                    protected UpdateAppBean parseJson(String json) {
                        ALog.e("requestAppUpdatae-->" + json);
                        UpdateAppBean updateAppBean = new UpdateAppBean();
                        AppUpdateBean mAppUpdateBean = new Gson().fromJson(json, AppUpdateBean.class);

                        updateAppBean
                                //（必须）是否更新Yes,No
                                .setUpdate(AppUtils.getVersionCode(_mActivity) < mAppUpdateBean.getData().getVersionCode() ? "Yes" : "No")
                                //（必须）新版本号，
                                .setNewVersion(mAppUpdateBean.getData().getVersionName())
                                //（必须）下载地址
                                .setApkFileUrl(mAppUpdateBean.getData().getUrl())
                                //（必须）更新内容
                                .setUpdateLog(mAppUpdateBean.getData().getDescription())
                                //大小，不设置不显示大小，可以不设置
                                .setTargetSize(mAppUpdateBean.getData().getSize())
                                //是否强制更新，可以不设置
                                .setConstraint(mAppUpdateBean.getData().isIsForce());

                        return updateAppBean;
                    }

                    @Override
                    protected void hasNewApp(UpdateAppBean updateApp, UpdateAppManager updateAppManager) {
                        updateAppManager.showDialogFragment();
                    }
                });
    }

    private String getMd5(String str) {
        StringBuffer sb = new StringBuffer();
        try {
            //获取加密方式为md5的算法对象
            MessageDigest instance = MessageDigest.getInstance("MD5");
            byte[] digest = instance.digest(str.getBytes());
            for (byte b : digest) {
                String hexString = Integer.toHexString(b & 0xff);
                if (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                sb = sb.append(hexString);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
