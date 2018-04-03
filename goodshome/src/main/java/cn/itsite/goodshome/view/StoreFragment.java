package cn.itsite.goodshome.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youth.banner.Banner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.itsite.abase.cache.SPCache;
import cn.itsite.abase.common.UserHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.acommon.GoodsParams;
import cn.itsite.acommon.event.SwitchStoreEvent;
import cn.itsite.goodshome.R;
import cn.itsite.goodshome.contract.HomeContract;
import cn.itsite.goodshome.model.StoreItemGridBean;
import cn.itsite.goodshome.presenter.HomePresenter;
import cn.itsite.statemanager.BaseViewHolder;
import cn.itsite.statemanager.StateLayout;
import cn.itsite.statemanager.StateListener;
import cn.itsite.statemanager.StateManager;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Author： Administrator on 2018/1/30 0030.
 * Email： liujia95me@126.com
 */

public class StoreFragment extends BaseFragment<HomeContract.Presenter> implements HomeContract.View {
    public static final String TAG = StoreFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private StoreRVAdapter mAdatper;
    private LinearLayout mLlLocation;
    private List<StoreItemGridBean> mDatas;
    private Banner mBanner;
    private List<Object> mBannerImages;
    private List<String> mBannerTitles;
    private GoodsParams mParmas = new GoodsParams();
    private StateManager mStateManager;
    private PtrFrameLayout mPtrFrameLayout;

    public static StoreFragment newInstance(String shopType) {
        StoreFragment fragment = new StoreFragment();
        Bundle bundle = new Bundle();
        bundle.putString("shopType", shopType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParmas.shoptype = getArguments().getString("shopType");
    }

    @NonNull
    @Override
    protected HomeContract.Presenter createPresenter() {
        return new HomePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mPtrFrameLayout = view.findViewById(R.id.ptrFrameLayout);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initStateManager();
        initData();
        initListener();
        initPtrFrameLayout(mPtrFrameLayout, mRecyclerView);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        refresh();
    }

    private void refresh() {
        if ("shop".equals(mParmas.shoptype)) {
            mParmas.shopUid = (String) SPCache.get(_mActivity, UserHelper.SHOP_ID, null);
            if (TextUtils.isEmpty(mParmas.shopUid)) {
                mStateManager.showEmpty();
            }
        }
        mPresenter.getHome(mParmas);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SwitchStoreEvent event) {
        refresh();
    }

    private void initStateManager() {
        mStateManager = StateManager.builder(_mActivity)
                .setContent(mRecyclerView)
                .setEmptyView(R.layout.state_empty_layout)
                .setEmptyImage(R.drawable.ic_prompt_dingwei_01)
                .setErrorOnClickListener(v -> mPtrFrameLayout.autoRefresh())
                .setConvertListener(new StateListener.ConvertListener() {
                    @Override
                    public void convert(BaseViewHolder holder, StateLayout stateLayout) {
                        holder.setText(R.id.bt_empty_state, "切换地址");
                    }
                })
                .setEmptyText("当前暂无商品，请切换地址试试吧！")
                .build();
    }

    private void initData() {
        mAdatper = new StoreRVAdapter();
        mRecyclerView.setLayoutManager(new GridLayoutManager(_mActivity, 2));
        mRecyclerView.setAdapter(mAdatper);

        mDatas = new ArrayList<>();
        mAdatper.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return mDatas.get(position).getSpanSize();
            }
        });
    }

    private void initListener() {
        mAdatper.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                StoreItemGridBean item = mAdatper.getItem(position);
                switch (item.getItemType()) {
                    case StoreItemGridBean.TYPE_BANNER:
                        if (view.getId() == R.id.ll_location) {
                            Fragment addressFragment = (Fragment) ARouter.getInstance().build("/delivery/selectshoppingaddressfragment").navigation();
                            ((StoreHomeFragment) getParentFragment()).startForResult((BaseFragment) addressFragment, 100);
                        }
                        break;
                    default:
                }
            }
        });

        mAdatper.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                StoreItemGridBean item = mAdatper.getItem(position);
                switch (item.getItemType()) {
                    case StoreItemGridBean.TYPE_BANNER:
                        break;
                    case StoreItemGridBean.TYPE_MORE:
                        Fragment fragment = (Fragment) ARouter.getInstance().build("/classify/classifyfragment").navigation();
                        Bundle bundle = new Bundle();
                        bundle.putString("shopType", mParmas.shoptype);
                        bundle.putString("shopUid", mParmas.shopUid);
                        fragment.setArguments(bundle);
                        ((StoreHomeFragment) getParentFragment()).start((BaseFragment) fragment);
                        break;
                    case StoreItemGridBean.TYPE_RECOMMEND:
                    case StoreItemGridBean.TYPE_GOODS:
                        Fragment goodsDetailFragment = (Fragment) ARouter.getInstance().build("/goodsdetail/goodsdetailfragment").navigation();
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("uid", item.getProductsBean().getUid());
                        goodsDetailFragment.setArguments(bundle2);
                        ((StoreHomeFragment) getParentFragment()).start((BaseFragment) goodsDetailFragment);
                        break;
                    default:
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void responseGetHome(List<StoreItemGridBean> data) {
        mDatas = data;
        mPtrFrameLayout.refreshComplete();
        if (data == null || data.isEmpty()) {
            mStateManager.showEmpty();
        } else {
            mStateManager.showContent();
        }
        mAdatper.setNewData(mDatas);
    }
}
