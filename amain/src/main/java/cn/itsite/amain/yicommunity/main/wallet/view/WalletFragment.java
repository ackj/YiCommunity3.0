package cn.itsite.amain.yicommunity.main.wallet.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.amain.R;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/4/13 0013 10:51
 */

public class WalletFragment extends BaseFragment {

    public static final String TAG = WalletFragment.class.getSimpleName();
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private WalletRVAdapter mAdapter;
    private PtrFrameLayout mPtrFrameLayout;

    public static WalletFragment newInstance() {
        return new WalletFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mToolbar = view.findViewById(R.id.toolbar);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mPtrFrameLayout = view.findViewById(R.id.ptrFrameLayout);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initStateBar(mToolbar);
        initData();
        initListener();
        initPtrFrameLayout(mPtrFrameLayout,mRecyclerView);
    }

    private void initData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mAdapter = new WalletRVAdapter();

        View viewHeader = LayoutInflater.from(_mActivity).inflate(R.layout.item_wallet_header,null);
        mAdapter.addHeaderView(viewHeader);

        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("");
        }
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(data);

    }

    private void initListener() {

    }

}
