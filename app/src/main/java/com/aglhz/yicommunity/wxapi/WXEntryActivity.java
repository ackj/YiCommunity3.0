package com.aglhz.yicommunity.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import cn.itsite.abase.log.ALog;
import cn.itsite.amain.R;
import cn.itsite.acommon.UserHelper;

import static cn.itsite.apayment.payment.pay.wechat.WeChatAppPay.WECHAT_PAY_RESULT_ACTION;
import static cn.itsite.apayment.payment.pay.wechat.WeChatAppPay.WECHAT_PAY_RESULT_EXTRA;


/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/4/12 0009 17:39
 * 微信支付工具。
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    public static final String TAG = WXEntryActivity.class.getName();
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wx_pay);

        api = WXAPIFactory.createWXAPI(this, UserHelper.WXAPPID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq request) {
        if (request instanceof PayReq) {
            ALog.e("extData--" + ((PayReq) request).extData);
        }
        ALog.e(TAG, "request-->" + request.openId);
        ALog.e(TAG, "request-->" + request.getType());
        ALog.e(TAG, "request-->" + request.transaction);
    }

    @Override
    public void onResp(BaseResp response) {
        sendPayResultBroadcast(response.errCode);

//        String extData = "";
//        if (response instanceof PayResp) {
//            extData = ((PayResp) response).extData;
//        }
        ALog.e(TAG, "response-->" + response.errStr);
        ALog.e(TAG, "response-->" + response.openId);
        ALog.e(TAG, "response-->" + response.transaction);
        ALog.e(TAG, "response-->" + response.getType());
        ALog.e(TAG, "response-->" + response.checkArgs());
//        ((Vibrator) App.mContext.getSystemService(VIBRATOR_SERVICE)).vibrate(500);
//        if (response.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//            EventBus.getDefault().post(new EventPay(response.errCode, "", extData));
//            finish();
//        }
    }

    /**
     * 发送微信支付结果的本地广播
     * 本地广播比全局广播效率高，更安全
     * <p>
     * 接收者请参考：
     * http://www.cnblogs.com/trinea/archive/2012/11/09/2763182.html
     *
     * @param resultCode
     */
    private void sendPayResultBroadcast(int resultCode) {
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        Intent payResult = new Intent();
        payResult.setAction(WECHAT_PAY_RESULT_ACTION);
        payResult.putExtra(WECHAT_PAY_RESULT_EXTRA, resultCode);
        broadcastManager.sendBroadcast(payResult);
        finish();
    }
}