package cn.itsite.order.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.order.R;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/4/13 0013 8:52
 */

public class InputCommentFragment extends BaseFragment {

    public static final String TAG = InputCommentFragment.class.getSimpleName();
    private Toolbar mToolbar;
    private ImageView mIvBack;
    private RecyclerView mRecyclerView;

    public static InputCommentFragment newInstance() {
        return new InputCommentFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mToolbar = view.findViewById(R.id.toolbar);
        mIvBack = view.findViewById(R.id.iv_back);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initStateBar(mToolbar);
        initData();
        initListener();
    }

    private void initData() {
        mToolbar.setBackgroundColor(_mActivity.getResources().getColor(R.color.white));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        InputCommentRVAdapter mAdapter = new InputCommentRVAdapter();
        mRecyclerView.setAdapter(mAdapter);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("");
        }
        mAdapter.setNewData(data);
    }

    private void initListener() {
    }

}
