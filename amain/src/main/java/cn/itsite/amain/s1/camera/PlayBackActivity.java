package cn.itsite.amain.s1.camera;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.p2p.core.BasePlayBackActivity;
import com.p2p.core.P2PHandler;
import com.p2p.core.P2PView;

import cn.itsite.amain.R;

public class PlayBackActivity extends BasePlayBackActivity {
    public static final String TAG = PlayBackActivity.class.getSimpleName();
    public static String P2P_ACCEPT = "com.XXX.P2P_ACCEPT";
    public static String P2P_READY = "com.XXX.P2P_READY";
    public static String P2P_REJECT = "com.XXX.P2P_REJECT";
    private RelativeLayout rlP2pview;
    private View viewBlack;
    private LinearLayout llTalkback;
    private String filename;
    private int position;
    private String deviceId;
    private String devicePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_back);
        filename = getIntent().getStringExtra("filename");
        position = getIntent().getIntExtra("position", 0);
        deviceId = getIntent().getStringExtra("deviceId");
        devicePwd = getIntent().getStringExtra("devicePwd");

        initView();
        initp2pView();
        regFilter();
    }

    private void initView() {
        rlP2pview = ((RelativeLayout) findViewById(R.id.rl_p2pview));
        viewBlack = ((View) findViewById(R.id.view_black));
        llTalkback = ((LinearLayout) findViewById(R.id.ll_talkback));
        llTalkback.setOnClickListener(v -> play());
    }

    public void initp2pView() {
        pView = (P2PView) findViewById(R.id.pview);
        //7是设备类型(技威定义的)
        //LAYOUTTYPE_TOGGEDER 录像回放连接命令和P2P_ACCEPT、P2P_READY、P2P_REJECT等命令在同一界面
        //LAYOUTTYPE_SEPARATION 录像回放连接命令和P2P_ACCEPT、P2P_READY、P2P_REJECT等命令不在同一界面
        this.initP2PView(7, P2PView.LAYOUTTYPE_TOGGEDER);
        pView.halfScreen();
    }

    @Override
    protected void onCaptureScreenResult(boolean isSuccess, int prePoint) {

    }

    @Override
    protected void onVideoPTS(long videoPTS) {

    }

    @Override
    protected void onP2PViewSingleTap() {

    }


    public void regFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(P2P_REJECT);
        filter.addAction(P2P_ACCEPT);
        filter.addAction(P2P_READY);
        registerReceiver(mReceiver, filter);
    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(P2P_ACCEPT)) {
                int[] type = intent.getIntArrayExtra("type");
                P2PView.type = type[0];
                P2PView.scale = type[1];
//                txText.append("\n 监控数据接收");
                Log.e("dxsTest", "监控数据接收:" + deviceId);
                P2PHandler.getInstance().openAudioAndStartPlaying(2);//打开音频并准备播放，calllType与call时type一致
            } else if (intent.getAction().equals(P2P_READY)) {
//                txText.append("\n 监控准备,开始监控");
                Log.e("dxsTest", "监控准备,开始监控" + deviceId);
                pView.sendStartBrod();
                viewBlack.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewBlack.setVisibility(View.GONE);
                    }
                }, 2000);
            } else if (intent.getAction().equals(P2P_REJECT)) {
//                txText.append("\n 监控挂断");
            }
        }
    };

    public void play() {
        //录像回放连接
        P2PHandler.getInstance().playbackConnect(deviceId,
                devicePwd, filename, position, 0, 0, 896, 896, 0);
    }

    @Override
    public int getActivityInfo() {
        return 33;
    }

    @Override
    protected void onGoBack() {

    }

    @Override
    protected void onGoFront() {

    }

    @Override
    protected void onExit() {

    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        P2PHandler.getInstance().finish();
        super.onDestroy();
    }
}
