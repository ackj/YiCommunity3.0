package cn.itsite.amain.yicommunity.main.door;

import android.os.Bundle;

import cn.itsite.abase.mvp.view.base.BaseActivity;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.main.door.view.AppointOpenDoorFragment;
import cn.itsite.amain.yicommunity.main.door.view.OpenDoorRecordFragment;
import cn.itsite.amain.yicommunity.main.door.view.PasswordOpenDoorFragment;
import cn.itsite.amain.yicommunity.main.door.view.QuickOpenDoorFragment;

/**
 * [智慧门禁]的父容器
 */
public class DoorActivity extends BaseActivity {
    private static final String TAG = DoorActivity.class.getSimpleName();
    int intFromTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取上一个Activity传递过来的数据。
        initData();
        if (savedInstanceState == null) {
            switch (intFromTo) {
                case Constants.SET_OPEN_DOOR:
                    loadRootFragment(R.id.fl_main_activity, QuickOpenDoorFragment.newInstance());
                    break;
                case Constants.APPOINT_OPEN_DOOR:
                    loadRootFragment(R.id.fl_main_activity, AppointOpenDoorFragment.newInstance());
                    break;
                case Constants.PASSWORD_OPEN_DOOR:
                    loadRootFragment(R.id.fl_main_activity, PasswordOpenDoorFragment.newInstance());
                    break;
                case Constants.OPEN_RECORD:
                    loadRootFragment(R.id.fl_main_activity, OpenDoorRecordFragment.newInstance());
                    break;
                default:
                    break;
            }
        }
    }

    private void initData() {
        intFromTo = getIntent().getIntExtra(Constants.KEY_FROM_TO, 0);
    }
}
