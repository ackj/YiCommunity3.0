package cn.itsite.amain.yicommunity.qrcode;

import android.os.Bundle;

import cn.itsite.abase.mvp.view.base.BaseActivity;
import cn.itsite.amain.R;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 */
public class QRCodeActivity extends BaseActivity {
    private static final String TAG = QRCodeActivity.class.getSimpleName();
    public static final String QRCODE_RESULT = "qrcode_result";
    public static final int QRCODE_REQUEST = 0x01;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadRootFragment(R.id.fl_main_activity, QRCodeFragment.newInstance());
    }
}