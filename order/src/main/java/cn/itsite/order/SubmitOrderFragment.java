package cn.itsite.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.abase.utils.ScreenUtils;
import cn.itsite.abase.utils.ToastUtils;
import cn.itsite.acommon.DeliveryBean;
import cn.itsite.acommon.OperatorBean;
import cn.itsite.acommon.StorePojo;
import cn.itsite.adialog.dialogfragment.BaseDialogFragment;
import cn.itsite.order.contract.SubmitOrderContract;
import cn.itsite.order.presenter.SubmitOrderPresenter;

/**
 * Author： Administrator on 2018/2/1 0001.
 * Email： liujia95me@126.com
 */
@Route(path = "/order/submitorderfragment")
public class SubmitOrderFragment extends BaseFragment<SubmitOrderContract.Presenter> implements SubmitOrderContract.View {
    public static final String TAG = SubmitOrderFragment.class.getSimpleName();
    private RelativeLayout mLlToolbar;
    private RecyclerView mRecyclerView;
    private SubmitOrderRVAdapter mAdapter;
    private ArrayList<StorePojo> mOrders;
    private TextView mTvTotalPrice;
    private TextView mTvAmount;

    private SubmitOrderBean mCurrentEditInfo;
    private DeliveryBean mDefaultDelivery;
    private TextView mTvSubmit;

    public static SubmitOrderFragment newInstance() {
        return new SubmitOrderFragment();
    }

    @NonNull
    @Override
    protected SubmitOrderContract.Presenter createPresenter() {
        return new SubmitOrderPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOrders = getArguments().getParcelableArrayList("orders");
        ALog.e(TAG, "orders:" + mOrders);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_submit_order, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mLlToolbar = view.findViewById(R.id.rl_toolbar);
        mTvAmount = view.findViewById(R.id.tv_amount);
        mTvTotalPrice = view.findViewById(R.id.tv_total_price);
        mTvSubmit = view.findViewById(R.id.tv_submit);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initStatusBar();
        initData();
        initListener();
    }

    private void initStatusBar() {
        mLlToolbar.setPadding(mLlToolbar.getPaddingLeft(), mLlToolbar.getPaddingTop() + ScreenUtils.getStatusBarHeight(_mActivity), mLlToolbar.getPaddingRight(), mLlToolbar.getPaddingBottom());
    }

    private void initData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mAdapter = new SubmitOrderRVAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.getAddress();
    }

    private void loadData() {
        List<SubmitOrderBean> data = new ArrayList<>();
        float allTotalPrice = 0;
        int allAmount = 0;
        String currency = "";
        for (int i = 0; i < mOrders.size(); i++) {
            SubmitOrderBean store = new SubmitOrderBean();
            store.setItemType(SubmitOrderBean.TYPE_STORE_TITLE);
            store.setShopBean(mOrders.get(i).getShop());
            List<StorePojo.ProductsBean> products = mOrders.get(i).getProducts();
            data.add(store);
            int amount = 0;
            float totalPrice = 0;
            for (int i1 = 0; i1 < products.size(); i1++) {
                StorePojo.ProductsBean productsBean = products.get(i1);
                SubmitOrderBean product = new SubmitOrderBean();
                product.setItemType(SubmitOrderBean.TYPE_STORE_GOODS);
                product.setProductsBean(productsBean);
                data.add(product);
                allAmount += productsBean.getCount();
                allTotalPrice += Float.valueOf(productsBean.getPay().getPrice()) * productsBean.getCount();
                amount += productsBean.getCount();
                totalPrice += Float.valueOf(productsBean.getPay().getPrice()) * productsBean.getCount();
                if (TextUtils.isEmpty(currency)) {
                    currency = productsBean.getPay().getCurrency();
                }
            }
            SubmitOrderBean info = new SubmitOrderBean();
            info.setItemType(SubmitOrderBean.TYPE_ORDER_INFO);
            info.setTotalPrice(totalPrice);
            info.setAmount(amount);
            info.setCurrency(currency);
            info.setDeliveryBean(mDefaultDelivery);
            data.add(info);
        }
        mTvAmount.setText(allAmount + "件");
        mTvTotalPrice.setText(currency + allTotalPrice);
        mAdapter.setNewData(data);
    }

    private void initListener() {
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.ll_delivery) {
                    Fragment addressFragment = (Fragment) ARouter.getInstance().build("/delivery/selectshoppingaddressfragment").navigation();
                    startForResult((BaseFragment) addressFragment, 100);
                    mCurrentEditInfo = mAdapter.getItem(position);
                } else if (view.getId() == R.id.tv_leave_message) {
                    mCurrentEditInfo = mAdapter.getItem(position);
                    showInputDialog();
                }
            }
        });
        mTvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrder();
            }
        });
    }

    private void submitOrder() {
        List<OperatorBean> data = new ArrayList<>();
        OperatorBean operatorBean = new OperatorBean();
        for (int i = 0; i < mAdapter.getData().size(); i++) {
            SubmitOrderBean submitOrderBean = mAdapter.getData().get(i);
            if (submitOrderBean.getItemType() == SubmitOrderBean.TYPE_STORE_TITLE) {
                operatorBean = new OperatorBean();
                operatorBean.products = new ArrayList<>();
            } else if (submitOrderBean.getItemType() == SubmitOrderBean.TYPE_STORE_GOODS) {
                OperatorBean.Product product = new OperatorBean.Product();
                product.amount = submitOrderBean.getProductsBean().getCount() + "";
                product.sku = submitOrderBean.getProductsBean().getSkuID();
                product.uid = submitOrderBean.getProductsBean().getUid();
                operatorBean.products.add(product);
            } else if (submitOrderBean.getItemType() == SubmitOrderBean.TYPE_ORDER_INFO) {
                operatorBean.note = submitOrderBean.getLeaveMessage() + "";
                operatorBean.uid = submitOrderBean.getDeliveryBean().getUid();
                data.add(operatorBean);
            }
        }
        if (data.size() > 0) {
            mPresenter.postOrders(data);
        }
    }

    private void showInputDialog() {
        new BaseDialogFragment()
                .setLayoutId(R.layout.dialog_input)
                .setConvertListener((holder, dialog) -> {
                    EditText etInput = holder.getView(R.id.et_input);
                    etInput.setText(mCurrentEditInfo.getLeaveMessage() + " ");
                    etInput.setSelection(etInput.getText().toString().length());
                    holder
                            .setOnClickListener(R.id.btn_cancel, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            })
                            .setOnClickListener(R.id.btn_comfirm, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String input = etInput.getText().toString().trim();
                                    if (TextUtils.isEmpty(input)) {
                                        ToastUtils.showToast(_mActivity, "请输入留言内容");
                                    } else {
                                        mCurrentEditInfo.setLeaveMessage(input);
                                        mAdapter.notifyDataSetChanged();
                                        dialog.dismiss();
                                    }
                                }
                            });
                })
                .setDimAmount(0.3f)
                .setMargin(40)
                .setGravity(Gravity.CENTER)
                .show(getChildFragmentManager());
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            DeliveryBean addressBean = (DeliveryBean) data.getSerializable("delivery");
            mCurrentEditInfo.setDeliveryBean(addressBean);
            mAdapter.notifyDataSetChanged();
        } else if (resultCode == RESULT_OK && requestCode == 101) {
            mPresenter.getAddress();
        }
    }

    @Override
    public void responseGetAddress(List<DeliveryBean> data) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).isIsDefault()) {
                mDefaultDelivery = data.get(i);
                break;
            }
        }
        if (data.size() >= 1 && mDefaultDelivery == null) {
            mDefaultDelivery = data.get(0);
        }

        if (mDefaultDelivery == null) {
            showHintDialog();
        } else {
            loadData();
        }
    }

    @Override
    public void responsePostOrdersSuccess(BaseResponse response) {
        DialogHelper.successSnackbar(getView(), response.getMessage());
    }

    private void showHintDialog() {
        new BaseDialogFragment()
                .setLayoutId(R.layout.dialog_hint)
                .setConvertListener((holder, dialog) -> {
                    holder.setText(R.id.tv_content, "您还没有地址，请先新增一个默认地址")
                            .setVisible(R.id.btn_cancel, false)
                            .setOnClickListener(R.id.btn_comfirm, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Fragment addressFragment = (Fragment) ARouter.getInstance().build("/delivery/selectshoppingaddressfragment").navigation();
                                    startForResult((BaseFragment) addressFragment, 101);
                                    dialog.dismiss();
                                }
                            });
                })
                .setDimAmount(0.3f)
                .setMargin(40)
                .setGravity(Gravity.CENTER)
                .show(getChildFragmentManager());
    }
}
