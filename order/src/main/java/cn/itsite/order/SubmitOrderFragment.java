package cn.itsite.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.ArrayList;
import java.util.List;

import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.utils.ScreenUtils;
import cn.itsite.acommon.StorePojo;

/**
 * Author： Administrator on 2018/2/1 0001.
 * Email： liujia95me@126.com
 */
@Route(path = "/order/submitorderfragment")
public class SubmitOrderFragment extends BaseFragment {
    public static final String TAG = SubmitOrderFragment.class.getSimpleName();
    private RelativeLayout mLlToolbar;
    private RecyclerView mRecyclerView;
    private SubmitOrderRVAdapter mAdapter;
    private ArrayList<StorePojo> mOrders;
    private TextView mTvTotalPrice;
    private TextView mTvAmount;

    public static SubmitOrderFragment newInstance() {
        return new SubmitOrderFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOrders = getArguments().getParcelableArrayList("orders");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_submit_order, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mLlToolbar = view.findViewById(R.id.rl_toolbar);
        mTvAmount = view.findViewById(R.id.tv_amount);
        mTvTotalPrice = view.findViewById(R.id.tv_total_price);

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

        List<SubmitOrderBean> data = new ArrayList<>();
        float totalPrice = 0;
        int amount = 0;
        String currency = "";
        for (int i = 0; i < mOrders.size(); i++) {
            SubmitOrderBean store = new SubmitOrderBean();
            store.setItemType(SubmitOrderBean.TYPE_STORE_TITLE);
            store.setShopBean(mOrders.get(i).getShop());
            List<StorePojo.ProductsBean> products = mOrders.get(i).getProducts();
            data.add(store);
            for (int i1 = 0; i1 < products.size(); i1++) {
                StorePojo.ProductsBean productsBean = products.get(i1);
                SubmitOrderBean product = new SubmitOrderBean();
                product.setItemType(SubmitOrderBean.TYPE_STORE_GOODS);
                product.setProductsBean(productsBean);
                data.add(product);
                amount += productsBean.getCount();
                totalPrice += Float.valueOf(productsBean.getPay().getPrice()) * productsBean.getCount();
                if (TextUtils.isEmpty(currency)) {
                    currency = productsBean.getPay().getCurrency();
                }
            }
            SubmitOrderBean info = new SubmitOrderBean();
            info.setItemType(SubmitOrderBean.TYPE_ORDER_INFO);
            data.add(info);
        }
        mTvAmount.setText(amount + "件");
        mTvTotalPrice.setText(currency + totalPrice);
        mAdapter.setNewData(data);
    }

    private void initListener() {

    }

//    @Override
//    public void responseGetOrder(List<OrderBean> data) {
//        List<SubmitOrderBean> list = new ArrayList<>();
//        for (int i = 0; i < data.size(); i++) {
//            OrderBean bean = data.get(i);
//            SubmitOrderBean titleBean = new SubmitOrderBean();
//            titleBean.setItemType(SubmitOrderBean.TYPE_STORE_TITLE);
//            titleBean.setOrderInfoBean(bean);
//            list.add(titleBean);
//            for (int j = 0; j < bean.getProducts().size(); j++) {
//                SubmitOrderBean productBean = new SubmitOrderBean();
//                productBean.setItemType(SubmitOrderBean.TYPE_STORE_GOODS);
////                productBean.setProductsBean(bean.getProducts().get(j));
//                list.add(productBean);
//            }
//            SubmitOrderBean orderInfoBean = new SubmitOrderBean();
//            orderInfoBean.setItemType(SubmitOrderBean.TYPE_ORDER_INFO);
//            orderInfoBean.setOrderInfoBean(bean);
//            list.add(orderInfoBean);
//        }
//        mAdapter.setNewData(list);
//    }
}
