package cn.itsite.amain.yicommunity.main.parking;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import cn.itsite.abase.mvp.view.base.BaseActivity;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.main.parking.view.CarCardFragment;
import cn.itsite.amain.yicommunity.main.parking.view.ParkChargeFragment;
import cn.itsite.amain.yicommunity.qrcode.QRCodeActivity;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 临时停车场模块的容器Activity。
 */
public class TempParkActivity extends BaseActivity {
    private static final String TAG = TempParkActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        if (intent.hasExtra(QRCodeActivity.QRCODE_RESULT)) {
            Bundle bundle = intent.getExtras();
            String url = bundle.getString(QRCodeActivity.QRCODE_RESULT);
            Uri uri = Uri.parse(url);
            String parkID = uri.getQueryParameter(Constants.PARAM_PARKPLACEFID);
            String parkName = uri.getQueryParameter(Constants.PARAM_PARKNAME);
            bundle.putString(Constants.PARAM_PARKPLACEFID, parkID);
            bundle.putString(Constants.PARAM_PARKNAME, parkName);
            loadRootFragment(R.id.fl_main_activity, ParkChargeFragment.newInstance(bundle));
        } else {
            loadRootFragment(R.id.fl_main_activity, CarCardFragment.newInstance());
        }
    }
}
