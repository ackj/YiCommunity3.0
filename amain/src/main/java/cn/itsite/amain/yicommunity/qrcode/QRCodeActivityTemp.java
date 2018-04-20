package cn.itsite.amain.yicommunity.qrcode;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import cn.itsite.abase.mvp.view.base.BaseActivity;
import cn.itsite.abase.utils.ImageUtils;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import cn.itsite.amain.R;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 */
public class QRCodeActivityTemp extends BaseActivity implements QRCodeView.Delegate {
    private static final String TAG = QRCodeActivityTemp.class.getSimpleName();
    public static final int REQUEST_IMAGE = 0;
    private QRCodeView mQRCodeView;
    private ImageView iv_back;
    private ImageView iv_photo;
    private ImageView iv_flashlight;
    private boolean isFlashlightOpened = false;
    private LinearLayout ll_top_bar;
    public static final String QRCODE_RESULT = "qrcode_result";
    public static final int QRCODE_REQUEST = 0x01;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadRootFragment(R.id.fl_main_activity, QRCodeFragment.newInstance());

//        initView();
//        initData();
    }

    private void initView() {
        ll_top_bar = (LinearLayout) findViewById(R.id.ll_top_bar);
        mQRCodeView = (ZXingView) findViewById(R.id.zxingview);
        mQRCodeView.setDelegate(this);
        iv_back = (ImageView) findViewById(R.id.iv_back_scan_activity);
        iv_photo = (ImageView) findViewById(R.id.iv_photo_picker_scan_activity);
        iv_flashlight = (ImageView) findViewById(R.id.iv_flashlight_scan_activity);
    }

    private void initData() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE);

            }
        });
        iv_flashlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFlashlightOpened) {
                    mQRCodeView.closeFlashlight();
                } else {
                    mQRCodeView.openFlashlight();
                }
                isFlashlightOpened = !isFlashlightOpened;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mQRCodeView != null) {
            mQRCodeView.startCamera();
            mQRCodeView.startSpot();
            mQRCodeView.showScanRect();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mQRCodeView != null) {
            mQRCodeView.startCamera();
            mQRCodeView.startSpot();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mQRCodeView != null) {
            mQRCodeView.stopCamera();
            mQRCodeView.stopSpot();
        }
    }

    @Override
    protected void onDestroy() {
        if (mQRCodeView != null) {
            mQRCodeView.onDestroy();
        }
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        if (mQRCodeView != null) {
            mQRCodeView.startSpot();
        }
        handleQRCode(result);
    }

    private void handleQRCode(String result) {
        Toast.makeText(QRCodeActivityTemp.this, result, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 选择系统图片并解析
         */
        if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                final Uri uri = data.getData();

                new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... params) {
                        return QRCodeDecoder.syncDecodeQRCode(ImageUtils.getImageAbsolutePath(QRCodeActivityTemp.this, uri));
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        if (TextUtils.isEmpty(result)) {
                            Toast.makeText(QRCodeActivityTemp.this, "未发现二维码", Toast.LENGTH_SHORT).show();
                        } else {
                            //解析成功后
                            handleQRCode(result);
                        }
                    }
                }.execute();
            }
        }
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        if (mQRCodeView != null) {
            mQRCodeView.closeFlashlight();
        }
        finish();
    }
}