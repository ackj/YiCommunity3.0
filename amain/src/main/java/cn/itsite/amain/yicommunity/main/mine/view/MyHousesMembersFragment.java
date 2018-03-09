package cn.itsite.amain.yicommunity.main.mine.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.entity.bean.MyHousesBean;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author: LiuJia on 2017/4/20 9:26.
 * Email: liujia95me@126.com
 */
public class MyHousesMembersFragment extends BaseFragment {
    public static final String TAG = MyHousesMembersFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private PtrFrameLayout ptrFrameLayout;
    private MyHousesMembersRVAdapter mAdapter;
    private MyHousesBean.DataBean.AuthBuildingsBean bean;

    public static MyHousesMembersFragment newInstance(MyHousesBean.DataBean.AuthBuildingsBean bean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.KEY_MEMBER, bean);
        MyHousesMembersFragment fragment = new MyHousesMembersFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            bean = (MyHousesBean.DataBean.AuthBuildingsBean) bundle.getSerializable(Constants.KEY_MEMBER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        recyclerView = ((RecyclerView) view.findViewById(R.id.recyclerView));
        ptrFrameLayout = ((PtrFrameLayout) view.findViewById(R.id.ptrFrameLayout));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText(bean.getAddress());
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }


    private void initData() {
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return false;
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
            }
        });
        //成员头像网格表
        recyclerView.setLayoutManager(new GridLayoutManager(_mActivity, 5));
        mAdapter = new MyHousesMembersRVAdapter();
        recyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(bean.getMembers());
    }
}
