package cn.itsite.shoppingcart;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daimajia.swipe.SwipeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.abase.utils.ScreenUtils;
import cn.itsite.acommon.GoodsCounterView;
import cn.itsite.acommon.GoodsParams;
import cn.itsite.acommon.OperateBean;
import cn.itsite.acommon.SkusBean;
import cn.itsite.acommon.SpecificationDialog;
import cn.itsite.acommon.StorePojo;
import cn.itsite.acommon.event.RefreshCartEvent;
import cn.itsite.acommon.event.RefreshCartRedPointEvent;
import cn.itsite.acommon.model.ProductsBean;
import cn.itsite.adialog.dialogfragment.BaseDialogFragment;
import cn.itsite.shoppingcart.contract.CartContract;
import cn.itsite.shoppingcart.presenter.CartPresenter;
import cn.itsite.statemanager.BaseViewHolder;
import cn.itsite.statemanager.StateLayout;
import cn.itsite.statemanager.StateListener;
import cn.itsite.statemanager.StateManager;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Author： Administrator on 2018/1/31 0031.
 * Email： liujia95me@126.com
 */
@Route(path = "/shoppingcart/shoppingcartfragment")
public class ShoppingCartFragment extends BaseFragment<CartContract.Presenter> implements CartContract.View, View.OnClickListener {

    public static final String TAG = ShoppingCartFragment.class.getSimpleName();

    private RelativeLayout mRlToolbar;
    private RecyclerView mRecyclerView;
    private ShoppingCartRVAdapter mAdapter;
    private CheckBox mCbSelectAll;
    private TextView mTvSubmit;
    private TextView mTvTotalSum;
    private TextView mTvEdit;
    private TextView mTvAnchor;//锚，无需在意这个view
    //--------------------------
    private boolean isEditModel;//是编辑模式吗
    //    private GoodsCounterView mCurrentCounterView;//当前计数的view
    List<StoreBean> mDatas = new ArrayList<>();

    StoreBean emptyBean = new StoreBean();//空页面对应的bean

    private String cartUid = "-1";

    private GoodsParams mGoodsParams = new GoodsParams();
    private ImageView mIvArrowLeft;

    private StorePojo.ProductsBean mOperationProduct;
    private int mOptionAmount;
    private SwipeLayout mOperationSwipeLayout;
    private PtrFrameLayout mPtrFrameLayout;
    private StateManager mStateManager;

    public static ShoppingCartFragment newInstance() {
        return new ShoppingCartFragment();
    }

    @NonNull
    @Override
    protected CartContract.Presenter createPresenter() {
        return new CartPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRlToolbar = view.findViewById(R.id.rl_toolbar);
        mCbSelectAll = view.findViewById(R.id.cb_select_all);
        mTvSubmit = view.findViewById(R.id.tv_submit);
        mTvTotalSum = view.findViewById(R.id.tv_total_sum);
        mTvEdit = view.findViewById(R.id.tv_edit);
        mTvAnchor = view.findViewById(R.id.anchor_1);
        mIvArrowLeft = view.findViewById(R.id.iv_arrow_left);
        mPtrFrameLayout = view.findViewById(R.id.ptrFrameLayout);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initStatusBar();
        initStateManager();
        initData();
        initListener();
        initPtrFrameLayout(mPtrFrameLayout, mRecyclerView);
    }

    private void initStatusBar() {
        mRlToolbar.setPadding(mRlToolbar.getPaddingLeft(), mRlToolbar.getPaddingTop() + ScreenUtils.getStatusBarHeight(_mActivity), mRlToolbar.getPaddingRight(), mRlToolbar.getPaddingBottom());
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getCarts(cartUid);
    }

    private void initData() {
        emptyBean.setItemType(StoreBean.TYPE_EMPTY);
        emptyBean.setSpanSize(2);

        mRecyclerView.setLayoutManager(new GridLayoutManager(_mActivity, 2));
        mAdapter = new ShoppingCartRVAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return mDatas.get(position).getSpanSize();
            }
        });
        mAdapter.setNewData(mDatas);
    }

    private void initStateManager() {
        mStateManager = StateManager.builder(_mActivity)
                .setContent(mRecyclerView)
                .setEmptyView(R.layout.state_empty_layout)
                .setEmptyImage(R.drawable.ic_prompt_order_01)
                .setErrorOnClickListener(v -> mPtrFrameLayout.autoRefresh())
                .setConvertListener(new StateListener.ConvertListener() {
                    @Override
                    public void convert(BaseViewHolder holder, StateLayout stateLayout) {
                        holder.setVisible(R.id.bt_empty_state, false);
                    }
                })
                .setEmptyText("当前购物车暂无商品，再去逛逛~~")
                .build();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RefreshCartEvent event) {
        mPresenter.getCarts(cartUid);
    }

    private void initListener() {
        mTvSubmit.setOnClickListener(this);
        mTvEdit.setOnClickListener(this);
        mIvArrowLeft.setOnClickListener(this);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                StoreBean item = mAdapter.getItem(position);
                switch (item.getItemType()) {
                    case StoreBean.TYPE_STORE_TITLE:
                        break;
                    case StoreBean.TYPE_STORE_GOODS:
                        if (view.getId() == R.id.tv_specification) {
                            GoodsCounterView goodsCounterView = ((View) view.getParent()).findViewById(R.id.goodsCounterView);
                            StorePojo.ProductsBean productsBean = item.getProductsBean();
                            showSpecificationDialog(productsBean, view, goodsCounterView);
                        } else if (view.getId() == R.id.tv_confirm) {
                            GoodsCounterView goodsCounterView = ((View) view.getParent()).findViewById(R.id.goodsCounterView);
                            mOperationSwipeLayout = (SwipeLayout) view.getParent().getParent();
                            mOperationProduct = item.getProductsBean();
                            mOptionAmount = goodsCounterView.getCounter();
                            ProductsBean productsBean = new ProductsBean();
                            productsBean.setAmount(goodsCounterView.getCounter() + "");
                            TextView tvSpecification = ((View) view.getParent()).findViewById(R.id.tv_specification);
                            List<String> skus = (List<String>) tvSpecification.getTag(R.id.tag_skus);
                            if (item.getProductsBean().getSkuID() != null) {
                                if (skus == null) {
                                    List<String> skuList = new ArrayList<>();
                                    skuList.add(item.getProductsBean().getSkuID());
                                    productsBean.setSkus(skuList);
                                } else {
                                    productsBean.setSkus(skus);
                                }
                            }
                            productsBean.setUid(item.getProductsBean().getUid());
                            mPresenter.putProduct(cartUid, productsBean);
                        } else if (view.getId() == R.id.iv_edit) {
                            mOperationSwipeLayout = (SwipeLayout) view.getParent().getParent();
                            mOperationSwipeLayout.open();
                        }
                        break;
                    case StoreBean.TYPE_RECOMMEND_TITLE:
                        break;
                    case StoreBean.TYPE_RECOMMEND_GOODS:
                        Fragment goodsDetailFragment = (Fragment) ARouter
                                .getInstance()
                                .build("/goodsdetail/goodsdetailfragment")
                                .withString("uid", item.getRecommendGoodsBean().getUid())
                                .navigation();
                        start((BaseFragment) goodsDetailFragment);
                        break;
                    case StoreBean.TYPE_EMPTY:
                        pop();
                        break;
                    default:
                }
            }
        });

        mCbSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                List<StoreBean> data = mAdapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    StoreBean bean = data.get(i);
                    bean.setChecked(isChecked);
                }
                mAdapter.notifyDataSetChanged();
                computePrice();
            }
        });

        mAdapter.setOnCheckedChangedListener(new ShoppingCartRVAdapter.OnCheckedChangedListener() {
            @Override
            public void onStoreCheckedChanged(int position, boolean isChecked) {
                checkStoreGoods(position, isChecked);
                computePrice();
            }

            @Override
            public void onGoodsCheckedChanged(int position, boolean isChecked) {
                mAdapter.getData().get(position).setChecked(isChecked);
                refreshChecked();
                computePrice();
            }
        });
    }

    //刷新勾选的逻辑：有商品的商铺必须勾选，反之亦然
    private void refreshChecked() {
        List<StoreBean> data = mAdapter.getData();
        boolean hasChecked = false;
        StoreBean store = null;
        for (int i = 0; i < data.size(); i++) {
            StoreBean storeBean = data.get(i);
            if (storeBean.getItemType() == StoreBean.TYPE_STORE_TITLE || i == data.size() - 1) {
                if (!hasChecked && store != null) {
                    store.setChecked(false);
                }
                hasChecked = false;
                store = storeBean;
            } else if (storeBean.getItemType() == StoreBean.TYPE_STORE_GOODS && storeBean.isChecked()) {
                hasChecked = true;
                store.setChecked(true);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    //计算总额
    private void computePrice() {
        List<StoreBean> data = mDatas;
        float amountPrice = 0;
        String currency = "";
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getItemType() == StoreBean.TYPE_STORE_GOODS && data.get(i).isChecked()) {
                StorePojo.ProductsBean productsBean = data.get(i).getProductsBean();
                amountPrice += Float.valueOf(productsBean.getPay().getPrice()) * productsBean.getCount();
                if (TextUtils.isEmpty(currency)) {
                    currency = productsBean.getPay().getCurrency();
                }
            }
        }
        mTvTotalSum.setText(currency + amountPrice);
    }

    private void computeCount() {
        computeCount(mDatas);
    }

    private void computeCount(List<StoreBean> data) {
        int number = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getItemType() == StoreBean.TYPE_STORE_GOODS) {
                StorePojo.ProductsBean productsBean = data.get(i).getProductsBean();
                number += productsBean.getCount();
            }
        }
        EventBus.getDefault().post(new RefreshCartRedPointEvent(number));
    }

    private void showHintDialog() {
        new BaseDialogFragment()
                .setLayoutId(R.layout.dialog_hint)
                .setConvertListener((holder, dialog) -> {
                    holder.setText(R.id.tv_content, "您确定删除选中的商品？")
                            .setText(R.id.btn_cancel, "取消")
                            .setText(R.id.btn_comfirm, "确定")
                            .setOnClickListener(R.id.btn_cancel, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            })
                            .setOnClickListener(R.id.btn_comfirm, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    deleteProducts();
                                    dialog.dismiss();
                                }
                            });
                })
                .setMargin(40)
                .setDimAmount(0.3f)
                .setGravity(Gravity.CENTER)
                .show(getChildFragmentManager());
    }

    private void deleteProducts() {
        List<StoreBean> data = mAdapter.getData();
        List<OperateBean> deleteOperateBeans = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getItemType() == StoreBean.TYPE_STORE_GOODS && data.get(i).isChecked()) {
                StorePojo.ProductsBean productsBean = data.get(i).getProductsBean();
                OperateBean OperateBean = new OperateBean();
                OperateBean.sku = productsBean.getSkuID();
                OperateBean.uid = productsBean.getUid();
                deleteOperateBeans.add(OperateBean);
            }
        }
        if (deleteOperateBeans.size() > 0) {
            mPresenter.deleteProduct(cartUid, deleteOperateBeans);
        } else {
            DialogHelper.warningSnackbar(getView(), "请勾选要删除的商品");
        }
    }

    //刷新选中的商城商品
    private void checkStoreGoods(int position, boolean isChecked) {
        StoreBean bean = mAdapter.getData().get(position);
        for (int i = position; i <= bean.getGoodsCount() + position; i++) {
            mDatas.get(i).setChecked(isChecked);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void responseDeleteSuccess(BaseResponse response) {
        DialogHelper.successSnackbar(getView(), response.getMessage());
        mPresenter.getCarts(cartUid);
    }

    @Override
    public void responsePostSuccess(BaseResponse response) {
        DialogHelper.successSnackbar(getView(), response.getMessage());
        computeCount();
    }

    @Override
    public void responsePutSuccess(BaseResponse response) {
        DialogHelper.successSnackbar(getView(), response.getMessage());
        if (mOperationSwipeLayout != null) {
            mOperationSwipeLayout.close();
            mOperationProduct.setCount(mOptionAmount);
            computePrice();
            computeCount();
        }
    }

    @Override
    public void responseGetCartsSuccess(List<StoreBean> data) {
        mPtrFrameLayout.refreshComplete();
        if (data == null || data.isEmpty()) {
            mDatas.clear();
            mDatas.add(emptyBean);
        } else {
            mDatas = data;
        }
        computeCount(data);
        computePrice();
        //查推荐
        mPresenter.getRecommendGoods(mGoodsParams);
    }

    @Override
    public void responseRecommendGoodsSuccess(List<StoreBean> data) {
        mDatas.addAll(data);
        mAdapter.setNewData(mDatas);
        if (mDatas.size() == 0) {
            mStateManager.showEmpty();
        } else {
            mStateManager.showContent();
        }
    }

    private void switchEditModel() {
        if (isEditModel) {
            mTvEdit.setText("编辑");
            mTvEdit.setTextColor(_mActivity.getResources().getColor(R.color.base_black));
            mTvSubmit.setText("结算");
            mTvSubmit.setBackgroundColor(_mActivity.getResources().getColor(R.color.warn));
            mTvAnchor.setVisibility(View.VISIBLE);
            mTvTotalSum.setVisibility(View.VISIBLE);
        } else {
            mTvEdit.setText("完成");
            mTvEdit.setTextColor(_mActivity.getResources().getColor(R.color.warn));
            mTvSubmit.setText("删除");
            mTvSubmit.setBackgroundColor(_mActivity.getResources().getColor(R.color.error));
            mTvAnchor.setVisibility(View.GONE);
            mTvTotalSum.setVisibility(View.GONE);
        }
        isEditModel = !isEditModel;
    }

    private void clickSubmit() {
        if (isEditModel) {
            //删除
            showHintDialog();
        } else {
            //结算
            submitOrders();

        }
    }

    private void submitOrders() {
        //1、检查是否有勾选商品
        //2、把勾选的商品以及其所属的商铺用集合包装好
        //3、将数据传递过去生成订单
        List<StoreBean> data = mAdapter.getData();
        ArrayList<StorePojo> resultData = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            StoreBean storeBean = data.get(i);
            if (storeBean.getItemType() == StoreBean.TYPE_STORE_TITLE && storeBean.isChecked()) {
                StorePojo storePojo = new StorePojo();
                List<StorePojo.ProductsBean> products = new ArrayList<>();
                storePojo.setProducts(products);
                storePojo.setShop(storeBean.getShopBean());
                resultData.add(storePojo);
            } else if (storeBean.getItemType() == StoreBean.TYPE_STORE_GOODS && storeBean.isChecked()) {
                resultData.get(resultData.size() - 1).getProducts().add(storeBean.getProductsBean());
            }
        }

        if (resultData.size() > 0) {
            Fragment fragment = (Fragment) ARouter.getInstance().build("/order/submitorderfragment").navigation();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("orders", resultData);
            bundle.putString("from", "cart");
            fragment.setArguments(bundle);
            start((BaseFragment) fragment);
        }

    }

    private void showSpecificationDialog(StorePojo.ProductsBean product, View view, GoodsCounterView goodsCounterView) {
        SpecificationDialog dialog = new SpecificationDialog(_mActivity, product.getUid(), product.getIcon(), goodsCounterView.getCounter(), product.getSkuID());
        dialog.setSkuListener(new SpecificationDialog.OnSkusListener() {
            @Override
            public void clickComfirm(SkusBean.SkuBean sku, int amount, SpecificationDialog dialog) {
                if (sku != null) {
                    if (!product.getSkuID().equals(sku.getUid())) {
                        List<String> skus = new ArrayList<>();
                        skus.add(product.getSkuID());
                        skus.add(sku.getUid());
                        product.setSkuID(sku.getUid());
                        product.setSku(sku.getSku());
                        view.setTag(R.id.tag_skus, skus);
                    }
                    ((TextView) view).setText(sku.getSku());
                }
                goodsCounterView.setCounter(amount);
                dialog.dismiss();
            }
        });
        dialog.show(getChildFragmentManager());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_edit) {
            switchEditModel();
        } else if (v.getId() == R.id.tv_submit) {
            clickSubmit();
        } else if (v.getId() == R.id.iv_arrow_left) {
            pop();
        }
    }

    @Override
    public void error(String errorMessage) {
        super.error(errorMessage);
        mPtrFrameLayout.refreshComplete();
        mStateManager.showError();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
