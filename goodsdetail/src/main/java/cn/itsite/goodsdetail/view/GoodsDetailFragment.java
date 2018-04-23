package cn.itsite.goodsdetail.view;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.flexbox.FlexboxLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.itsite.abase.cache.SPCache;
import cn.itsite.abase.common.BaseConstants;
import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.abase.utils.ScreenUtils;
import cn.itsite.acommon.SkusBean;
import cn.itsite.acommon.SpecificationDialog;
import cn.itsite.acommon.StorePojo;
import cn.itsite.acommon.event.RefreshCartRedPointEvent;
import cn.itsite.acommon.model.ProductsBean;
import cn.itsite.goodsdetail.ProductDetailBean;
import cn.itsite.goodsdetail.R;
import cn.itsite.goodsdetail.contract.ProductContract;
import cn.itsite.goodsdetail.presenter.ProductPresenter;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/4/10 0010 10:10
 */
@Route(path = "/goodsdetail/goodsdetailfragment")
public class GoodsDetailFragment extends BaseFragment<ProductContract.Presenter> implements ProductContract.View, View.OnClickListener {

    public static final String TAG = GoodsDetailFragment.class.getSimpleName();
    private WebView mWebView;
    private Banner mBanner;
    private TextView mTvName;
    private TextView mTvDesc;
    private TextView mTvPrice;
    private FlexboxLayout mFlexboxLayout;
    private View mRlToolbar;
    private ImageView mIvBack;
    private LinearLayout mLlShopCart;
    private TextView mTvBuyItNow;
    private TextView mTvPutShopcart;
    private ProductsBean productsBean;
    private ProductDetailBean mDetailBean;
    private ImageView mIvShopCart;
    private Badge mBadge;

    private String cartUid;

    private SkusBean.SkuBean mSku;
    private int mAmount;
    private TextView mTvGoods;
    private TextView mTvDetail;
    private NestedScrollView mScrollView;
    private ConstraintLayout mConstraintLayout;
    private LinearLayout mLlTabs;
    private TextView mTvTitleShop;
    private LinearLayout mLlComment;
    private RecyclerView mRecyclerView;
    private GoodsCommentRVAdapter mCommentAdapter;
    private LinearLayout mLlCommentLayout;
    private TextView mTvEvaCount;

    public static GoodsDetailFragment newInstance() {
        return new GoodsDetailFragment();
    }

    String uid;

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
        mWebView = view.findViewById(R.id.webview);
        mBanner = view.findViewById(R.id.banner);
        mTvName = view.findViewById(R.id.tv_name);
        mTvDesc = view.findViewById(R.id.tv_desc);
        mTvPrice = view.findViewById(R.id.tv_price);
        mFlexboxLayout = view.findViewById(R.id.flexboxLayout);
        mTvPutShopcart = view.findViewById(R.id.tv_put_shopcart);
        mTvBuyItNow = view.findViewById(R.id.tv_buy_it_now);
        mLlShopCart = view.findViewById(R.id.ll_shopcart);
        mIvBack = view.findViewById(R.id.iv_back);
        mIvShopCart = view.findViewById(R.id.iv_shop_cart);
        mTvGoods = view.findViewById(R.id.tv_goods);
        mTvDetail = view.findViewById(R.id.tv_detail);
        mScrollView = view.findViewById(R.id.scrollView);
        mConstraintLayout = view.findViewById(R.id.cl_goods_layout);
        mTvTitleShop = view.findViewById(R.id.tv_title_shop);
        mLlTabs = view.findViewById(R.id.ll_tab_titles);
        mLlComment = view.findViewById(R.id.ll_comment);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mLlCommentLayout = view.findViewById(R.id.ll_comment_layout);
        mTvEvaCount = view.findViewById(R.id.tv_comment_count);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initStatusBar();
        initListener();
        initWebView();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RefreshCartRedPointEvent event) {
        mBadge.setBadgeNumber(event.getNumber());
    }

    private void initData() {
        mPresenter.getProduct(uid);
        mWebView.setClickable(false);
        mTvGoods.setSelected(true);
        cartUid = "-1";

        mCommentAdapter = new GoodsCommentRVAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setAdapter(mCommentAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);

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
        mTvDetail.setOnClickListener(this);
        mTvGoods.setOnClickListener(this);
        mLlCommentLayout.setOnClickListener(this);
    }

    private void initStatusBar() {
        mRlToolbar.setPadding(mRlToolbar.getPaddingLeft(),
                mRlToolbar.getPaddingTop() + ScreenUtils.getStatusBarHeight(_mActivity),
                mRlToolbar.getPaddingRight(),
                mRlToolbar.getPaddingBottom());

    }

    private void refreshLables(ProductDetailBean bean) {
        for (int i = 0; i < bean.getAttributes().size(); i++) {
            LinearLayout llLable = (LinearLayout) LayoutInflater.from(_mActivity).inflate(R.layout.view_lable, null);
            TextView tvLable = llLable.findViewById(R.id.tv_lable);
            tvLable.setText(bean.getAttributes().get(i).getName());
            mFlexboxLayout.addView(llLable);
        }
    }

    private void refreshBanner(ProductDetailBean bean) {
        List<ProductDetailBean.ImagesBean> images = bean.getImages();
        List<Object> bannerImages = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            bannerImages.add(images.get(i).getImage());
        }
        mBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path)
                        .apply(new RequestOptions().placeholder(R.drawable.ic_img_loading))
                        .apply(new RequestOptions().error(R.drawable.ic_img_error))
                        .into(imageView);
            }
        })
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImages(bannerImages)
                .isAutoPlay(true)
                .start();
    }

    private void initWebView() {
        mWebView.clearCache(true);
        mWebView.clearHistory();
        WebSettings webSettings = mWebView.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //webview自适应屏幕
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, true);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    mTvGoods.setSelected(scrollY <= mConstraintLayout.getBottom());
                    mTvDetail.setSelected(scrollY > mConstraintLayout.getBottom());
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            pop();
        } else if (v.getId() == R.id.ll_comment_layout) {
            start(GoodsCommentFragment.newInstance(mDetailBean.getUid()));
        } else if (v.getId() == R.id.tv_put_shopcart) {
            showSpecificationDialog(false);
        } else if (v.getId() == R.id.tv_buy_it_now) {
            showSpecificationDialog(true);
        } else if (v.getId() == R.id.ll_shopcart) {
            Fragment fragment = (Fragment) ARouter.getInstance().build("/shoppingcart/shoppingcartfragment").navigation();
            start((BaseFragment) fragment);
        } else if (v.getId() == R.id.tv_goods) {
            mScrollView.scrollTo(0, 0);
            mTvGoods.setSelected(true);
            mTvDetail.setSelected(false);
        } else if (v.getId() == R.id.tv_detail) {
            mScrollView.scrollTo(0, mConstraintLayout.getBottom());
            mTvDetail.setSelected(true);
            mTvGoods.setSelected(false);
        }
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

    private void buyItNow() {
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
        Fragment fragment = (Fragment) ARouter
                .getInstance()
                .build("/order/submitorderfragment")
                .withString("from", "detail")
                .withParcelableArrayList("orders", orders)
                .navigation();
        start((BaseFragment) fragment);
    }

    @Override
    public void responseGetProduct(ProductDetailBean bean) {
        String url = bean.getDetail().getUrl();
        refreshBanner(bean);
        refreshLables(bean);
        mTvName.setText(bean.getTitle());
        mTvDesc.setText(bean.getDescription());
        mTvPrice.setText(bean.getPay().getCurrency() + bean.getPay().getCost());
        productsBean = new ProductsBean();
        productsBean.setUid(uid);
        mDetailBean = bean;
        if (TextUtils.isEmpty(url)) {
            mTvTitleShop.setVisibility(View.VISIBLE);
            mLlTabs.setVisibility(View.INVISIBLE);
        } else {
            mWebView.loadUrl(url);
            mTvTitleShop.setVisibility(View.INVISIBLE);
            mLlTabs.setVisibility(View.VISIBLE);
        }
        if (bean.getEvaluates() == null || bean.getEvaluates().size() == 0) {
            //没有评论时将评论相关的布局隐藏
            mLlComment.setVisibility(View.GONE);
        }else{
            mLlComment.setVisibility(View.VISIBLE);
            mCommentAdapter.setNewData(bean.getEvaluates());
            mTvEvaCount.setText("商品评价（"+bean.getEvaCounts()+"）");
        }
    }

    @Override
    public void responsePostSuccess(BaseResponse response) {
        DialogHelper.successSnackbar(getView(), response.getMessage());
        EventBus.getDefault().post(new RefreshCartRedPointEvent(mBadge.getBadgeNumber() + mAmount));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
