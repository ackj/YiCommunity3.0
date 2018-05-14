package cn.itsite.goodshome.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import cn.itsite.abase.cache.SPCache;
import cn.itsite.abase.common.UserHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.acommon.data.GoodsParams;
import cn.itsite.acommon.data.bean.DeliveryBean;
import cn.itsite.acommon.event.EventSelectedDelivery;
import cn.itsite.acommon.event.SwitchStoreEvent;
import cn.itsite.adialog.dialogfragment.BaseDialogFragment;
import cn.itsite.goodshome.R;
import cn.itsite.goodshome.contract.StoreContract;
import cn.itsite.goodshome.model.ShopBean;
import cn.itsite.goodshome.presenter.StorePresenter;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by liujia on 03/05/2018.
 * 针对于独立商城APP（宅易购）的首页
 */

public class StoreHomeECFragment extends BaseFragment<StoreContract.Presenter> implements StoreContract.View {
    public static final String TAG = StoreHomeECFragment.class.getSimpleName();
    //    private TabLayout mTabLayout;
    private LinearLayout mLlToolbar;
    private ImageView mIvShopCart;
    private FloatingActionButton mFabSearch;

    private String[] shopTypes = {"smartHome", "shop"};

    private GoodsParams mParams = new GoodsParams();
    private StoreHomeVPAdapter mAdapter;
    private DeliveryBean mDeliveryBean;
    private ImageView mIvBack;
    private boolean isSmartHome;
    private TextView mTvTitle;

    public static StoreHomeECFragment newInstance(boolean isSmartHome) {
        StoreHomeECFragment fragment = new StoreHomeECFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isSmartHome",isSmartHome);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isSmartHome = getArguments().getBoolean("isSmartHome");
    }

    @NonNull
    @Override
    protected StoreContract.Presenter createPresenter() {
        return new StorePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_home_ec, container, false);
//        mTabLayout = view.findViewById(R.id.tabLayout);
        mLlToolbar = view.findViewById(R.id.ll_toolbar);
        mTvTitle = view.findViewById(R.id.tv_title);
        mIvShopCart = view.findViewById(R.id.iv_shop_cart);
        mFabSearch = view.findViewById(R.id.fab_search);
        mIvBack = view.findViewById(R.id.iv_back);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initStateBar(mLlToolbar);
        initData();
        initListener();
        EventBus.getDefault().register(this);
    }

    private void initData() {
        mTvTitle.setText(isSmartHome?"智能商城":"便利商店");
        loadRootFragment(R.id.fl_content,StoreFragment.newInstance(shopTypes[isSmartHome?0:1]));

        mAdapter = new StoreHomeVPAdapter(getChildFragmentManager());

    }

    public void showStoreDialog(List<ShopBean> list) {
        new BaseDialogFragment()
                .setLayoutId(R.layout.dialog_store)
                .setConvertListener((holder, dialog) -> {
                    RecyclerView recyclerView = holder.getView(R.id.recyclerView);
                    StoreDialogRVAdapter adapter = new StoreDialogRVAdapter();
                    recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
                    recyclerView.setAdapter(adapter);
                    adapter.setNewData(list);
                    adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
                            String shopUid = adapter.getData().get(position).getUid();
                            SPCache.put(_mActivity, UserHelper.SHOP_ID, shopUid);
                            SPCache.put(_mActivity, UserHelper.DELIVERY, mDeliveryBean.getLocation() + mDeliveryBean.getAddress());
                            EventBus.getDefault().post(new SwitchStoreEvent(shopUid, mDeliveryBean));
                            dialog.dismiss();
                        }
                    });
                })
                .setDimAmount(0.3f)
                .setGravity(Gravity.BOTTOM)
                .show(getChildFragmentManager());
    }

    private void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.finish();
            }
        });
        mIvShopCart.setOnClickListener(v -> {
            Fragment fragment = (Fragment) ARouter.getInstance()
                    .build("/shoppingcart/shoppingcartfragment")
                    .navigation();
            ((SupportFragment)getParentFragment()).start((BaseFragment) fragment);
        });
        mFabSearch.setOnClickListener(v -> {
            Fragment fragment = (Fragment) ARouter.getInstance()
                    .build("/goodssearch/searchgoodsfragment")
                    .navigation();
            Bundle bundle = new Bundle();
            String shopType = shopTypes[isSmartHome?0:1];
            bundle.putString("shopType", shopType);
            if (shopType.equals("shop")) {
                bundle.putString("shopUid", (String) SPCache.get(_mActivity, UserHelper.SHOP_ID, ""));
            }
            fragment.setArguments(bundle);
            ((SupportFragment)getParentFragment()).start((BaseFragment) fragment);
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventSelectedDelivery event){
        if(!isSmartHome){
            mDeliveryBean = event.deliveryBean;
            mParams.latitude = mDeliveryBean.getLatitude();
            mParams.longitude = mDeliveryBean.getLongitude();
            mParams.shoptype = shopTypes[1];
            mPresenter.getStore(mParams);
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            mDeliveryBean = (DeliveryBean) data.getSerializable("delivery");
            mParams.latitude = mDeliveryBean.getLatitude();
            mParams.longitude = mDeliveryBean.getLongitude();
            mParams.shoptype = shopTypes[1];
            mPresenter.getStore(mParams);
        }
    }

    @Override
    public void responseGetStore(List<ShopBean> list) {
        if (list == null || list.isEmpty()) {
            showHintDialog();
        } else {
            showStoreDialog(list);
        }
    }

    private void showHintDialog() {
        new BaseDialogFragment()
                .setLayoutId(R.layout.dialog_hint)
                .setConvertListener((holder, dialog) -> {
                    holder.setText(R.id.tv_content, "抱歉，该地址附近暂无便利店，请重新选择~")
                            .setVisible(R.id.btn_cancel, false)
                            .setOnClickListener(R.id.btn_comfirm, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Fragment addressFragment = (Fragment) ARouter.getInstance().build("/delivery/selectshoppingaddressfragment").withBoolean("fromShop",true).navigation();
                                    ((SupportFragment)getParentFragment()).startForResult((BaseFragment) addressFragment, 100);
                                    dialog.dismiss();
                                }
                            });
                })
                .setDimAmount(0.3f)
                .setMargin(40)
                .setGravity(Gravity.CENTER)
                .show(getChildFragmentManager());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
