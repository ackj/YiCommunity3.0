package cn.itsite.goodsdetail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

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

import java.util.ArrayList;
import java.util.List;

import cn.itsite.abase.BaseApp;
import cn.itsite.abase.cache.SPCache;
import cn.itsite.abase.common.BaseConstants;
import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.abase.utils.ScreenUtils;
import cn.itsite.acommon.DefaultTransformer;
import cn.itsite.acommon.SkusBean;
import cn.itsite.acommon.SpecificationDialog;
import cn.itsite.acommon.StorePojo;
import cn.itsite.acommon.VerticalViewPager;
import cn.itsite.acommon.event.RefreshCartRedPointEvent;
import cn.itsite.acommon.model.ProductsBean;
import cn.itsite.goodsdetail.contract.ProductContract;
import cn.itsite.goodsdetail.presenter.ProductPresenter;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

import static android.view.View.OVER_SCROLL_NEVER;

/**
 * Author： Administrator on 2018/2/6 0006.
 * Email： liujia95me@126.com
 */
@Route(path = "/goodsdetail/goodsdetailfragment")
public class GoodsDetailFragment extends BaseFragment<ProductContract.Presenter> implements ProductContract.View, View.OnClickListener {

    public static final String TAG = GoodsDetailFragment.class.getSimpleName();

    private RelativeLayout mRlToolbar;
    private TextView mTvPutShopcart;
    private TextView mTvBuyItNow;
    private LinearLayout mLlShopCart;
    private VerticalViewPager mViewPager;
    private MagicIndicator mMagicIndicator;
    private TextView mTvTitleShop;
    private ImageView mIvBack;
    private ProductDetailBean mDetailBean;
    private ImageView mIvShopCart;
    private Badge mBadge;

    public static GoodsDetailFragment newInstance() {
        return new GoodsDetailFragment();
    }

    String uid;
    private String cartUid;
    private ProductsBean productsBean;

    private SkusBean.SkuBean mSku;
    private int mAmount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uid = getArguments().getString("uid");
    }

    @NonNull
    @Override
    protected ProductContract.Presenter createPresenter() {
        return new ProductPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods_detail, container, false);
        mRlToolbar = view.findViewById(R.id.rl_toolbar);
        mTvPutShopcart = view.findViewById(R.id.tv_put_shopcart);
        mTvBuyItNow = view.findViewById(R.id.tv_buy_it_now);
        mLlShopCart = view.findViewById(R.id.ll_shopcart);
        mViewPager = view.findViewById(R.id.ultra_viewpager);
        mMagicIndicator = view.findViewById(R.id.magicIndicator);
        mTvTitleShop = view.findViewById(R.id.tv_title_shop);
        mIvBack = view.findViewById(R.id.iv_back);
        mIvShopCart = view.findViewById(R.id.iv_shop_cart);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initStatusBar();
        initData();
        initListener();
        EventBus.getDefault().register(this);
    }

    private void initStatusBar() {
        mRlToolbar.setPadding(mRlToolbar.getPaddingLeft(),
                mRlToolbar.getPaddingTop() + ScreenUtils.getStatusBarHeight(_mActivity),
                mRlToolbar.getPaddingRight(),
                mRlToolbar.getPaddingBottom());
    }

    private void initData() {
//        mUltraViewPager.setScrollMode(UltraViewPager.ScrollMode.VERTICAL);
        mViewPager.setPageTransformer(true, new DefaultTransformer());
        mViewPager.setOverScrollMode(OVER_SCROLL_NEVER);
        cartUid = "-1";
        mPresenter.getProduct(uid);


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

    private void initListener() {
        mIvBack.setOnClickListener(this);
        mTvBuyItNow.setOnClickListener(this);
        mLlShopCart.setOnClickListener(this);
        mTvPutShopcart.setOnClickListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RefreshCartRedPointEvent event) {
        mBadge.setBadgeNumber(event.getNumber());
    }

    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(_mActivity);
        commonNavigator.setScrollPivotX(0.65f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            private String[] mTitles = BaseApp.mContext.getResources().getStringArray(R.array.goods_detail_tabs);

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
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
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


    private void showSpecificationDialog(boolean isBuyItNow) {
        if (productsBean == null) {
            return;
        }
        String normalImage = null;
        if (mDetailBean.getImages() != null && mDetailBean.getImages().size() > 0) {
            normalImage = mDetailBean.getImages().get(0).getImage();
        }
        SpecificationDialog dialog = new SpecificationDialog(_mActivity, uid, normalImage);
        dialog.setSkuListener(new SpecificationDialog.OnSkusListener() {
            @Override
            public void clickComfirm(SkusBean.SkuBean sku, int amount, SpecificationDialog dialog) {
                mSku = sku;
                mAmount = amount;
                productsBean.setAmount(amount + "");
                if (sku != null) {
                    productsBean.setSku(sku.getUid());
                }
                if (isBuyItNow) {//立即购买
                    buyItNow();
                } else {//加到购物车
                    mPresenter.postProduct(cartUid, productsBean);
                }
                dialog.dismiss();
            }
        });
        dialog.show(getChildFragmentManager());
    }

    @Override
    public void responseGetProduct(ProductDetailBean bean) {
        mDetailBean = bean;
        if (TextUtils.isEmpty(bean.getDetail().getUrl())) {
            mMagicIndicator.setVisibility(View.INVISIBLE);
            mTvTitleShop.setVisibility(View.VISIBLE);
        } else {
            initMagicIndicator();
            mMagicIndicator.setVisibility(View.VISIBLE);
            mTvTitleShop.setVisibility(View.INVISIBLE);
        }
        productsBean = new ProductsBean();
        productsBean.setUid(uid);

        GoodsDetailVPAdapter adapter = new GoodsDetailVPAdapter(getChildFragmentManager(), bean);
        mViewPager.setAdapter(adapter);
    }

    @Override
    public void responsePostSuccess(BaseResponse response) {
        DialogHelper.successSnackbar(getView(), response.getMessage());
        EventBus.getDefault().post(new RefreshCartRedPointEvent(mBadge.getBadgeNumber() + mAmount));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            pop();
        } else if (v.getId() == R.id.tv_put_shopcart) {
            showSpecificationDialog(false);
        } else if (v.getId() == R.id.tv_buy_it_now) {
            showSpecificationDialog(true);
        } else if (v.getId() == R.id.ll_shopcart) {
            Fragment fragment = (Fragment) ARouter.getInstance().build("/shoppingcart/shoppingcartfragment").navigation();

            start((BaseFragment) fragment);
        }
    }

    private void buyItNow() {
        Fragment fragment = (Fragment) ARouter.getInstance().build("/order/submitorderfragment").navigation();
        StorePojo storePojo = new StorePojo();
        StorePojo.ShopBean shopBean = new StorePojo.ShopBean();
        shopBean.setName(mDetailBean.getShop().getName());
        shopBean.setServiceType(mDetailBean.getShop().getServiceType());
        shopBean.setType(mDetailBean.getShop().getType());
        shopBean.setUid(mDetailBean.getShop().getUid());
        storePojo.setShop(shopBean);

        List<StorePojo.ProductsBean> productsBeans = new ArrayList<>();
        storePojo.setProducts(productsBeans);
        StorePojo.ProductsBean productsBean = new StorePojo.ProductsBean();
        productsBean.setDescription(mDetailBean.getDescription());
        productsBean.setIcon(mDetailBean.getImages().get(0).getImage());
        StorePojo.ProductsBean.PayBean payBean = new StorePojo.ProductsBean.PayBean();
        payBean.setCurrency(mDetailBean.getPay().getCurrency());
        payBean.setPrice(mDetailBean.getPay().getPrice());
        productsBean.setPay(payBean);
        productsBean.setTitle(mDetailBean.getTitle());
        productsBean.setCount(mAmount);
        productsBean.setUid(mDetailBean.getUid());
        if (mSku != null) {
            productsBean.setSkuID(mSku.getUid());
            productsBean.setSku(mSku.getSku());
        }
        productsBeans.add(productsBean);
        ArrayList<StorePojo> orders = new ArrayList<>();
        orders.add(storePojo);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("orders", orders);
        fragment.setArguments(bundle);
        start((BaseFragment) fragment);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}
