package cn.itsite.amain.yicommunity.main.house;

import android.os.Bundle;

import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseActivity;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.main.house.view.AddHouseFragment;
import cn.itsite.amain.yicommunity.main.house.view.MemberPermissionFragment;

/**
 * [房屋]模块的父容器。
 */
public class HouseActivity extends BaseActivity {
    private static final String TAG = HouseActivity.class.getSimpleName();
    private int intFromTo;
    private String fid;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);

        //获取上一个Activity传递过来的数据。
        initData();
        if (savedInstanceState == null) {
            switch (intFromTo) {
                case Constants.HOUSE_RIGHTS:
                    loadRootFragment(R.id.fl_house_activity, MemberPermissionFragment.newInstance(fid, address));
                    ALog.e("Constants.HOUSE_RIGHTS::" + Constants.HOUSE_RIGHTS);
                    break;
                case Constants.ADD_HOUSE:
                    loadRootFragment(R.id.fl_house_activity, AddHouseFragment.newInstance(address));
                    ALog.e("Constants.ADD_HOUSE::" + Constants.ADD_HOUSE);
                    break;
                default:
                    break;
            }
        }
    }

    private void initData() {
        intFromTo = getIntent().getIntExtra(Constants.KEY_FROM_TO, 0);
        fid = getIntent().getStringExtra(Constants.KEY_FID);
        address = getIntent().getStringExtra(Constants.KEY_ADDRESS);
        ALog.e(intFromTo);
        ALog.e(fid);
    }
}
