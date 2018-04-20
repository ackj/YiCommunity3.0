package cn.itsite.amain.yicommunity.qrcode;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.utils.ImageUtils;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.App;
import me.yokeyword.fragmentation.SupportFragment;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Author：leguang on 2017/5/2 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class QRCodeFragment extends BaseFragment implements QRCodeView.Delegate {
    private static final String TAG = QRCodeFragment.class.getSimpleName();
    public static final int REQUEST_IMAGE = 0;
    private QRCodeView mQRCodeView;
    private ImageView iv_back;
    private ImageView iv_photo;
    private ImageView iv_flashlight;
    private boolean isFlashlightOpened = false;
    private LinearLayout ll_top_bar;
    public static final String QRCODE_RESULT = "qrcode_result";

    public static QRCodeFragment newInstance() {
        return new QRCodeFragment();
    }

    public static QRCodeFragment newInstance(String roomDir) {
        QRCodeFragment fragment = new QRCodeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("roomDir", roomDir);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan_qrcode, container, false);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }

    private void initView(View view) {
        ll_top_bar = (LinearLayout) view.findViewById(R.id.ll_top_bar);
        initStateBar(ll_top_bar);
        mQRCodeView = (ZXingView) view.findViewById(R.id.zxingview);
        mQRCodeView.setDelegate(this);
        iv_back = (ImageView) view.findViewById(R.id.iv_back_scan_activity);
        iv_photo = (ImageView) view.findViewById(R.id.iv_photo_picker_scan_activity);
        iv_flashlight = (ImageView) view.findViewById(R.id.iv_flashlight_scan_activity);
    }

    private void initData() {
        iv_back.setOnClickListener(v -> _mActivity.finish());
        iv_photo.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_IMAGE);
        });
        iv_flashlight.setOnClickListener(v -> {
            if (isFlashlightOpened) {
                mQRCodeView.closeFlashlight();
            } else {
                mQRCodeView.openFlashlight();
            }
            isFlashlightOpened = !isFlashlightOpened;
        });
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) _mActivity.getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        mQRCodeView.startSpot();
        handleQRCode(result);
    }

    private void handleQRCode(String result) {
        ALog.e("result-->" + result);
        if (!isResult(result)) {
            ALog.e("未识别二维码！");
            DialogHelper.warningSnackbar(getView(), "未识别二维码！");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(QRCODE_RESULT, result);
        setFragmentResult(SupportFragment.RESULT_OK, bundle);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        _mActivity.setResult(SupportFragment.RESULT_OK, intent);
        _mActivity.finish();
    }

    private boolean isResult(String result) {
        if (TextUtils.isEmpty(result)) {
            return false;
        }
        return result.contains("http");
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
        DialogHelper.errorSnackbar(getView(), "打开相机出错！");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 选择系统图片并解析
         */
        if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                final Uri uri = data.getData();

                new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... params) {
                        return QRCodeDecoder.syncDecodeQRCode(ImageUtils.getImageAbsolutePath(App.mContext, uri));
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        if (TextUtils.isEmpty(result)) {
                            Toast.makeText(App.mContext, "未发现二维码", Toast.LENGTH_SHORT).show();
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
    public void onResume() {
        super.onResume();
        mQRCodeView.startCamera();
        mQRCodeView.startSpot();
        mQRCodeView.showScanRect();
    }

    @Override
    public void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
        mQRCodeView.startSpot();
    }

    @Override
    public void onStop() {
        super.onStop();
        mQRCodeView.stopCamera();
        mQRCodeView.closeFlashlight();
        mQRCodeView.stopSpot();
    }

    @Override
    public void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }
}
