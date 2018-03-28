package cn.itsite.amain.s1.more.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.s1.event.EventDeviceNameChanged;
import cn.itsite.amain.s1.host.view.HostSettingsFragment;
import cn.itsite.amain.s1.net.view.SetWifiFragment;
import cn.itsite.abase.common.UserHelper;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Author: LiuJia on 2017/9/25 0025 18:24.
 * Email: liujia95me@126.com
 */

public class More2Fragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = More2Fragment.class.getSimpleName();
    private TextView tvName;
    private TextView tvDeviceNo;
    private LinearLayout llWifiSetting;
    private LinearLayout llHostManager;
    private LinearLayout llRoomManager;

    public static SupportFragment newInstance() {
        return new More2Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_2, container, false);
        tvName = ((TextView) view.findViewById(R.id.tv_name));
        tvDeviceNo = ((TextView) view.findViewById(R.id.tv_device_no));
        llWifiSetting = ((LinearLayout) view.findViewById(R.id.ll_wifi_setting));
        llHostManager = ((LinearLayout) view.findViewById(R.id.ll_host_manager));
        llRoomManager = ((LinearLayout) view.findViewById(R.id.ll_room_manager));
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initListener();
    }

    private void initListener() {
        llWifiSetting.setOnClickListener(this);
        llHostManager.setOnClickListener(this);
        llRoomManager.setOnClickListener(this);
    }

    private void initData() {
        tvName.setText(UserHelper.deviceName);
        tvDeviceNo.setText(UserHelper.deviceSn);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventAddDevice(EventDeviceNameChanged event) {
        tvName.setText(UserHelper.deviceName);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ll_wifi_setting) {
            ((SupportActivity) _mActivity).start(SetWifiFragment.newInstance());

        } else if (i == R.id.ll_host_manager) {
            ((SupportActivity) _mActivity).start(HostSettingsFragment.newInstance());

        } else if (i == R.id.ll_room_manager) {
            ((SupportActivity) _mActivity).start(RoomManagerFragment.newInstance());

        }
    }
}
