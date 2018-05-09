package cn.itsite.goodshome.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import cn.itsite.abase.BaseApp;
import cn.itsite.abase.cache.SPCache;
import cn.itsite.abase.common.BaseConstants;
import cn.itsite.abase.common.UserHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.utils.ScreenUtils;
import cn.itsite.acommon.data.GoodsParams;
import cn.itsite.acommon.data.bean.DeliveryBean;
import cn.itsite.acommon.event.EventSelectedDelivery;
import cn.itsite.acommon.event.RefreshCartRedPointEvent;
import cn.itsite.acommon.event.SwitchStoreEvent;
import cn.itsite.adialog.dialogfragment.BaseDialogFragment;
import cn.itsite.goodshome.R;
import cn.itsite.goodshome.contract.StoreContract;
import cn.itsite.goodshome.model.ShopBean;
import cn.itsite.goodshome.presenter.StorePresenter;
import me.yokeyword.fragmentation.SupportFragment;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * Author： Administrator on 2018/1/30 0030.
 * Email： liujia95me@126.com
 */
public class StoreHomeFragment extends BaseFragment<StoreContract.Presenter> implements StoreContract.View {
    public static final String TAG = StoreHomeFragment.class.getSimpleName();
    //    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private LinearLayout mLlToolbar;
    private ImageView mIvShopCart;
    private FloatingActionButton mFabSearch;
    private MagicIndicator mMagicIndicator;

    private String[] shopTypes = {"smartHome", "shop"};

    private GoodsParams mParams = new GoodsParams();
    private StoreHomeVPAdapter mAdapter;
    private DeliveryBean mDeliveryBean;
    private ImageView mIvBack;
    private Badge mBadge;

    public static StoreHomeFragment newInstance() {
        return new StoreHomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    protected StoreContract.Presenter createPresenter() {
        return new StorePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_home, container, false);
//        mTabLayout = view.findViewById(R.id.tabLayout);
        mViewPager = view.findViewById(R.id.viewPager);
        mLlToolbar = view.findViewById(R.id.ll_toolbar);
        mIvShopCart = view.findViewById(R.id.iv_shop_cart);
        mFabSearch = view.findViewById(R.id.fab_search);
        mMagicIndicator = view.findViewById(R.id.magicIndicator);
        mIvBack = view.findViewById(R.id.iv_back);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initStatusBar();
        initMagicIndicator();
        initData();
        initListener();
        EventBus.getDefault().register(this);
    }

    private void initStatusBar() {
        mLlToolbar.setPadding(mLlToolbar.getPaddingLeft(), mLlToolbar.getPaddingTop() + ScreenUtils.getStatusBarHeight(_mActivity), mLlToolbar.getPaddingRight(), mLlToolbar.getPaddingBottom());
    }

    private void initData() {
        mAdapter = new StoreHomeVPAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
//        mTabLayout.setupWithViewPager(mViewPager);

        //购物车上的小红点数字
        mBadge = new QBadgeView(_mActivity)
                .bindTarget(mIvShopCart)
                .setBadgeTextSize(10, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setBadgeBackgroundColor(0xA0FF0000)
                .setBadgeTextColor(0x99FFFFFF);

        //读取购物车数量缓存
        int cartNum = (int) SPCache.get(_mActivity, BaseConstants.KEY_CART_NUM, 0);
        mBadge.setBadgeNumber(cartNum);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RefreshCartRedPointEvent event) {
        mBadge.setBadgeNumber(event.getNumber());
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
            start((BaseFragment) fragment);
        });
        mFabSearch.setOnClickListener(v -> {
            Fragment fragment = (Fragment) ARouter.getInstance()
                    .build("/goodssearch/searchgoodsfragment")
                    .navigation();
            Bundle bundle = new Bundle();
            String shopType = shopTypes[mViewPager.getCurrentItem()];
            bundle.putString("shopType", shopType);
            if (shopType.equals("shop")) {
                bundle.putString("shopUid", (String) SPCache.get(_mActivity, UserHelper.SHOP_ID, ""));
            }
            fragment.setArguments(bundle);
            ((SupportFragment)getParentFragment()).start((BaseFragment) fragment);
        });
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            mDeliveryBean = (DeliveryBean) data.getSerializable("delivery");
            mParams.latitude = mDeliveryBean.getLatitude();
            mParams.longitude = mDeliveryBean.getLongitude();
            mPresenter.getStore(mParams);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventSelectedDelivery event){
        mDeliveryBean = event.deliveryBean;
        mParams.latitude = mDeliveryBean.getLatitude();
        mParams.longitude = mDeliveryBean.getLongitude();
        mPresenter.getStore(mParams);
    }


    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(_mActivity);
        commonNavigator.setScrollPivotX(0.65f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            private String[] mTitles = BaseApp.mContext.getResources().getStringArray(R.array.store_home_tabs);

            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(mTitles[index]);
                simplePagerTitleView.setNormalColor(_mActivity.getResources().getColor(R.color.base_black));
                simplePagerTitleView.setSelectedColor(_mActivity.getResources().getColor(R.color.base_color));
                simplePagerTitleView.setOnClickListener(v -> mViewPager.setCurrentItem(index));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 3));
                indicator.setLineWidth(UIUtil.dip2px(context, 28));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setYOffset(UIUtil.dip2px(context, 4));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(_mActivity.getResources().getColor(R.color.base_color));
                return indicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(getResources().getDrawable(R.drawable.simple_splitter_2));
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
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
                                    startForResult((BaseFragment) addressFragment, 100);
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
