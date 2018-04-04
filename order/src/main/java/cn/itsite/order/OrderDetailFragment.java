package cn.itsite.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import cn.itsite.abase.common.BaseConstants;
import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.abase.utils.ScreenUtils;
import cn.itsite.acommon.OperatorBean;
import cn.itsite.order.contract.OrderDetailContract;
import cn.itsite.order.presenter.OrderDetailPresenter;

import static cn.itsite.order.OrderListFragment.TYPE_CANCEL;
import static cn.itsite.order.OrderListFragment.TYPE_DELETE;
import static cn.itsite.order.OrderListFragment.TYPE_LOGISTICS;
import static cn.itsite.order.OrderListFragment.TYPE_PAY;
import static cn.itsite.order.OrderListFragment.TYPE_RECEIPT;

/**
 * Author： Administrator on 2018/2/1 0001.
 * Email： liujia95me@126.com
 */
@Route(path = "/order/orderdetailfragment")
public class OrderDetailFragment extends BaseFragment<OrderDetailContract.Presenter> implements OrderDetailContract.View {

    public static final String TAG = OrderDetailFragment.class.getSimpleName();
    private OrderDetailRVAdapter mAdapter;

    private RelativeLayout mRlToolbar;
    private RecyclerView mRecyclerView;
    private TextView mTvDeliveryType;
    private TextView mTvShopName;
    private TextView mTvCategory;
    private TextView mTvAmount;
    private TextView mTvContactWay;
    private TextView mTvLocation;
    private TextView mTvLeaveWords;
    private TextView mTvOrderNum;
    private TextView mTvOrderTime;

    private String mUid;
    private View mLayoutExpress;
    private TextView mTvExpressPhone;
    private TextView mTvExpressName;
    private ImageView mIvAvator;
    private TextView mTvIdentity;
    private Button mBtn1;
    private Button mBtn2;

    public static OrderDetailFragment newInstance(String uid) {
        OrderDetailFragment fragment = new OrderDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("uid", uid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUid = getArguments().getString("uid");
    }

    @NonNull
    @Override
    protected OrderDetailContract.Presenter createPresenter() {
        return new OrderDetailPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        mRlToolbar = view.findViewById(R.id.rl_toolbar);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mBtn1 = view.findViewById(R.id.btn_1);
        mBtn2 = view.findViewById(R.id.btn_2);
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
        mRlToolbar.setPadding(mRlToolbar.getPaddingLeft(), mRlToolbar.getPaddingTop() + ScreenUtils.getStatusBarHeight(_mActivity), mRlToolbar.getPaddingRight(), mRlToolbar.getPaddingBottom());
    }

    private void initData() {
        mAdapter = new OrderDetailRVAdapter();
        //加header和footer
        View viewHeader = LayoutInflater.from(_mActivity).inflate(R.layout.item_order_detail_header, null);
        mTvDeliveryType = viewHeader.findViewById(R.id.tv_delivery_type);
        mTvShopName = viewHeader.findViewById(R.id.tv_name);
        mTvCategory = viewHeader.findViewById(R.id.tv_category);
        mIvAvator = viewHeader.findViewById(R.id.iv_avator);
        mTvExpressName = viewHeader.findViewById(R.id.tv_express_name);
        mTvExpressPhone = viewHeader.findViewById(R.id.tv_express_phone);
        mLayoutExpress = viewHeader.findViewById(R.id.layout_express);
        mTvIdentity = viewHeader.findViewById(R.id.tv_identity);
        mAdapter.addHeaderView(viewHeader);


        View viewFooter = LayoutInflater.from(_mActivity).inflate(R.layout.item_order_detail_footer, null);
        mTvAmount = viewFooter.findViewById(R.id.tv_amount);
        mTvContactWay = viewFooter.findViewById(R.id.tv_contactway);
        mTvLocation = viewFooter.findViewById(R.id.tv_location);
        mTvLeaveWords = viewFooter.findViewById(R.id.tv_leave_words);
        mTvOrderNum = viewFooter.findViewById(R.id.tv_order_num);
        mTvOrderTime = viewFooter.findViewById(R.id.tv_order_time);
        mAdapter.addFooterView(viewFooter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.getOrderDetail(mUid);
    }

    private void initListener() {
    }


    @Override
    public void responseOrderDetail(OrderDetailBean orderDetailBean) {
        mTvDeliveryType.setText(orderDetailBean.getDeliveryType());
        mTvShopName.setText(orderDetailBean.getShop().getName());
        mTvCategory.setText(orderDetailBean.getCategory());
        mTvAmount.setText(_mActivity.getString(R.string.amount, orderDetailBean.getAmount(), "￥ " + orderDetailBean.getCost()));
        mTvContactWay.setText(_mActivity.getString(R.string.consignee, orderDetailBean.getDelivery().getName(), orderDetailBean.getDelivery().getPhoneNumber()));
        mTvLocation.setText(orderDetailBean.getDelivery().getAddress() + orderDetailBean.getDelivery().getLocation());
        mTvLeaveWords.setText(orderDetailBean.getNote());
        mTvOrderNum.setText(orderDetailBean.getOrderNumber());
        mTvOrderTime.setText(orderDetailBean.getTime());
        mAdapter.setNewData(orderDetailBean.getProducts());

        List<OrderDetailBean.ActionsBean> actions = orderDetailBean.getActions();
        mBtn1.setVisibility(View.INVISIBLE);
        mBtn2.setVisibility(View.INVISIBLE);
        for (int i = 0; i < actions.size(); i++) {
            OrderDetailBean.ActionsBean action = actions.get(i);
            if (i == 0) {
                mBtn2.setText(action.getAction());
                mBtn2.setVisibility(View.VISIBLE);
            } else if (i == 1) {
                mBtn1.setText(action.getAction());
                mBtn1.setVisibility(View.VISIBLE);
            }
        }

        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickAction(orderDetailBean.getUid(), actions.get(1));
            }
        });

        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickAction(orderDetailBean.getUid(), actions.get(0));
            }
        });

        if (orderDetailBean.getDeliveryType().contains("上门")) {
            mLayoutExpress.setVisibility(View.VISIBLE);
            OrderDetailBean.ExpressBean express = orderDetailBean.getExpress();
            Glide.with(_mActivity)
                    .load(express.getImageUrl())
                    .apply(new RequestOptions().error(R.drawable.ic_img_error))
                    .apply(new RequestOptions().placeholder(R.drawable.ic_img_loading))
                    .into(mIvAvator);
            mTvExpressName.setText(express.getName());
            mTvExpressPhone.setText(express.getPhoneNumber());
            mTvIdentity.setText(express.getIdentity());
        } else {
            mLayoutExpress.setVisibility(View.GONE);
        }
    }


    private void clickAction(String orderUid, OrderDetailBean.ActionsBean action) {
        switch (action.getType().toLowerCase()) {
            case TYPE_CANCEL://取消订单
            case TYPE_RECEIPT://确认订单
                //这两种操作只需要无脑传category给后台，以更改该订单的状态即可
                List<OperatorBean> orders = new ArrayList<>();
                OperatorBean order = new OperatorBean();
                order.uid = orderUid;
                order.category = action.getCategory();
                orders.add(order);
                mPresenter.putOrders(orders);
                break;
            case TYPE_DELETE://删除订单
                List<OperatorBean> deleteOrders = new ArrayList<>();
                OperatorBean dOrder = new OperatorBean();
                dOrder.uid = orderUid;
                deleteOrders.add(dOrder);
                mPresenter.deleteOrders(deleteOrders);
                break;
            case TYPE_LOGISTICS://查看物流
                Fragment fragment = (Fragment) ARouter.getInstance().build("/web/webfragment").navigation();
                Bundle bundle = new Bundle();
                bundle.putString(BaseConstants.KEY_LINK, action.getLink());
                bundle.putString(BaseConstants.KEY_TITLE, "查看物流");
                fragment.setArguments(bundle);
                ((BaseFragment) getParentFragment()).start((BaseFragment) fragment);
                break;
            case TYPE_PAY://跳支付
                break;
            default:
        }
    }

    @Override
    public void responseDeleteSuccess(BaseResponse response) {
        DialogHelper.successSnackbar(getView(), response.getMessage());
        pop();
    }

    @Override
    public void responsePutSuccess(BaseResponse response) {
        DialogHelper.successSnackbar(getView(), response.getMessage());
        pop();
    }
}
