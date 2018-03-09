package cn.itsite.amain.s1.main.home.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

import cn.itsite.abase.common.ActivityHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.s1.App;
import cn.itsite.amain.s1.more.view.More2Fragment;
import cn.itsite.amain.s1.net.NetActivity;
import cn.itsite.amain.s1.net.view.SetWifiFragment;
import cn.itsite.amain.s1.room.view.RoomDeviceListFragment;
import cn.itsite.amain.s1.scene.view.SceneFragment;
import cn.itsite.amain.s1.security.view.SecurityFragment;
import me.yokeyword.fragmentation.SupportFragment;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Author: LiuJia on 2017/4/27 0027 11:12.
 * Email: liujia95me@126.com
 */

public class MainFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks {
    public static final String TAG = MainFragment.class.getSimpleName();
    public static final long WAIT_TIME = 2000L;// 再点一次退出程序时间设置
    public static final int CAMERA_LOCATION = 100;
    public long TOUCH_TIME = 0;
    private AHBottomNavigation ahbn;
    private SupportFragment[] fragments = new SupportFragment[5];
    private int bottomNavigationPreposition;
    private static final String KEY_CURR_POSITION = "bottomNavigationPreposition";

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ahbn = (AHBottomNavigation) view.findViewById(R.id.ahbn_main_fragment);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            fragments[0] = SecurityFragment.newInstance();
            fragments[1] = RoomDeviceListFragment.newInstance();
            fragments[2] = SceneFragment.newInstance();
//            fragments[3] = DeviceLogsFragment.newInstance();
            fragments[3] = More2Fragment.newInstance();
            loadMultipleRootFragment(R.id.fl_container_main_fragment, 0, fragments[0], fragments[1], fragments[2], fragments[3]);
        } else {
            fragments[0] = findChildFragment(SecurityFragment.class);
            fragments[1] = findChildFragment(RoomDeviceListFragment.class);
            fragments[2] = findChildFragment(SceneFragment.class);
//            fragments[3] = findChildFragment(DeviceLogsFragment.class);
            fragments[3] = findChildFragment(More2Fragment.class);
            bottomNavigationPreposition = savedInstanceState.getInt(KEY_CURR_POSITION);
        }
        initData();
        requiresPermissions();
    }


    private void initData() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.security, R.drawable.ic_navigationsecurity_black_78px, R.color.white);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.room, R.drawable.ic_navigationroom_black_78px, R.color.white);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.scene, R.drawable.ic_navigationscenes_black_78px, R.color.white);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem(R.string.more, R.drawable.ic_navigationmore_black_78px, R.color.white);
        List<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
        bottomNavigationItems.add(item5);
        ahbn.addItems(bottomNavigationItems);
        ahbn.setDefaultBackgroundColor(getResources().getColor(R.color.white));
        ahbn.setBehaviorTranslationEnabled(false);
        ahbn.setColored(true);
        ahbn.setForceTint(false);
        ahbn.setAccentColor(getResources().getColor(R.color.base_color));
        ahbn.setInactiveColor(getResources().getColor(R.color.black));
        ahbn.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        ahbn.setOnTabSelectedListener((position, wasSelected) -> {
            showHideFragment(fragments[position], fragments[bottomNavigationPreposition]);
            bottomNavigationPreposition = position;
            return true;
        });
        ahbn.setCurrentItem(bottomNavigationPreposition);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURR_POSITION, bottomNavigationPreposition);
    }

    @AfterPermissionGranted(CAMERA_LOCATION)
    private void requiresPermissions() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(_mActivity, perms)) {

        } else {
            EasyPermissions.requestPermissions(this, "需要定位权限", CAMERA_LOCATION, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        //这里需要重新设置Rationale和title，否则默认是英文格式
        new AppSettingsDialog.Builder(this)
                .setRationale("没有该权限，此应用程序可能无法正常工作。打开应用设置屏幕以修改应用权限")
                .setTitle("必需权限")
                .build()
                .show();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        WifiManager wifiManager = (WifiManager) App.mContext.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getSSID().contains(SetWifiFragment.WIFI_NAME)) {
            startActivity(new Intent(_mActivity, NetActivity.class));
        } else {
            ActivityHelper.getInstance().finishActivity(NetActivity.class);
        }
    }
}
