package cn.itsite.amain.s1.camera;

import android.os.Bundle;

import cn.itsite.abase.mvp.view.base.BaseActivity;
import cn.itsite.amain.R;
import cn.itsite.amain.s1.entity.bean.DeviceListBean;

public class CameraSettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            DeviceListBean.DataBean.SubDevicesBean bean = (DeviceListBean.DataBean.SubDevicesBean) getIntent().getSerializableExtra("bean");
//            loadRootFragment(R.id.fl_main_activity, CameraSettingFragment.newInstance(bean));
        }
    }
}
