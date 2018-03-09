package cn.itsite.amain.s1.camera;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.p2p.core.P2PHandler;

import java.util.Arrays;
import java.util.Date;

import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.widget.PtrHTFrameLayout;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author: LiuJia on 2017/9/19 0019 10:26.
 * Email: liujia95me@126.com
 */

public class CameraFileRecordFragment extends BaseFragment {
    public static final String TAG = CameraFileRecordFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private PtrHTFrameLayout ptrFrameLayout;
    public static final String RECORDFILES = "com.yoosee.RECORDFILES";
    private CameraFileRecordRVAdapter adapter;
    private String id;
    private String pwd;

    public static CameraFileRecordFragment newInstance(String id, String pwd) {
        CameraFileRecordFragment fragment = new CameraFileRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("pwd", pwd);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
            pwd = bundle.getString("pwd");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        recyclerView = ((RecyclerView) view.findViewById(R.id.recyclerView));
        ptrFrameLayout = ((PtrHTFrameLayout) view.findViewById(R.id.ptrFrameLayout));
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        regFilter();
        initListener();
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("录像列表");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    private void initData() {
        ptrFrameLayout.setEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        adapter = new CameraFileRecordRVAdapter();
        recyclerView.setAdapter(adapter);
        pwd = P2PHandler.getInstance().EntryPassword(pwd);
        Date startDate = new Date(0);
        Date endDate = new Date(System.currentTimeMillis());
        showLoading();
        //获取录像列表
        P2PHandler.getInstance().getRecordFiles(id, pwd, startDate, endDate);
    }

    private void initListener() {
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            Intent intent = new Intent(_mActivity, PlayBackActivity.class);
            intent.putExtra("filename", adapter.getItem(position));
            intent.putExtra("position", position);
            intent.putExtra("deviceId", id);
            intent.putExtra("devicePwd", pwd);
            _mActivity.startActivity(intent);
        });
    }

    private void regFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(RECORDFILES);
        _mActivity.registerReceiver(receiver, filter);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(RECORDFILES)) {
                //获取到的录像文件名字
                // 该数组中的文件名按照录像时间从近到远开始排序 且 每次获取到的nams的长度不大于64
                String[] names = (String[]) intent.getCharSequenceArrayExtra("recordList");
                dismissLoading();
                ALog.e(TAG, "names:" + names.length);
                adapter.setNewData(Arrays.asList(names));
//                byte option = intent.getByteExtra("option1", (byte) -1);
//                if (option == 82) {
//                } else {
//                    if (names.length > 0) {
//                        updateAdapter(names);
//                    }
//                }
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        _mActivity.unregisterReceiver(receiver);
    }
}
