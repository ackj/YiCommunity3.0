package cn.itsite.amain.yicommunity.main.propery.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.abase.common.UserHelper;
import cn.itsite.amain.yicommunity.entity.bean.PropertyPayBean;
import cn.itsite.amain.yicommunity.entity.bean.PropertyPayDetailBean;
import cn.itsite.amain.yicommunity.event.EventCommunity;
import cn.itsite.amain.yicommunity.main.propery.contract.PropertyPayContract;
import cn.itsite.amain.yicommunity.main.propery.presenter.PropertyPayPresenter;
import cn.itsite.apayment.payment.PayParams;
import cn.itsite.apayment.payment.Payment;
import cn.itsite.apayment.payment.PaymentListener;
import cn.itsite.apayment.payment.network.NetworkClient;
import cn.itsite.apayment.payment.network.PayService;
import cn.itsite.apayment.payment.pay.IPayable;
import cn.itsite.apayment.payment.pay.Pay;
import cn.itsite.statemanager.StateManager;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by leguang on 2017/4/29 0029.
 * Email：langmanleguang@qq.com
 */

public class PropertyPayListFragment extends BaseFragment<PropertyPayContract.Presenter> implements PropertyPayContract.View {
    public static final String TAG = PropertyPayListFragment.class.getSimpleName();
    RecyclerView recyclerView;
    PtrFrameLayout ptrFrameLayout;
    TextView tvSum;
    Button btPay;
    LinearLayout ll;
    private int payState;
    private PropertyPayRVAdapter mAdapter;
    private Params params = Params.getInstance();
    private String[] payTypes = {Constants.ALIPAY, Constants.WXPAY};
    private StateManager mStateManager;
    private Payment payment;

    public static PropertyPayListFragment newInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.KEY_PAY_STATE, position);

        PropertyPayListFragment fragment = new PropertyPayListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            payState = bundle.getInt(Constants.KEY_PAY_STATE);
        }
    }

    @NonNull
    @Override
    protected PropertyPayContract.Presenter createPresenter() {
        return new PropertyPayPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_property_pay_list, container, false);
        tvSum = ((TextView) view.findViewById(R.id.tv_sum_property_pay_list_fragment));
        btPay = ((Button) view.findViewById(R.id.bt_pay_property_pay_list_fragment));
        recyclerView = ((RecyclerView) view.findViewById(R.id.recyclerView));
        ptrFrameLayout = ((PtrFrameLayout) view.findViewById(R.id.ptrFrameLayout));
        ll = ((LinearLayout) view.findViewById(R.id.ll_property_pay_list_fragment));
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initStateManager();
        initListener();
        initPtrFrameLayout(ptrFrameLayout, recyclerView);
    }

    private void initListener() {
        btPay.setOnClickListener(v -> {
            new AlertDialog.Builder(_mActivity).setTitle("请选择支付类型")
                    .setItems(payTypes, (dialog, which) -> {
                        switch (which) {
                            case 0:
                                params.payMethod = Payment.PAYTYPE_ALI_APP;
                                pay(Pay.aliAppPay());
                                break;
                            case 1:
                                params.payMethod = Payment.PAYTYPE_WECHAT_H5X;
                                pay(Pay.weChatH5xPay());
                                break;
                            default:
                                break;
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        });
    }

    private void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mAdapter = new PropertyPayRVAdapter();
        recyclerView.setAdapter(mAdapter);
        ll.setVisibility(payState == 0 ? View.VISIBLE : View.GONE);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (getParentFragment() instanceof PropertyPayFragment) {
                //2为未支付状态
                if (mAdapter.getData().get(position).getStatus() == 0) {
                    ((PropertyPayFragment) getParentFragment())
                            .start(PropertyNotPayDetailFragment.newInstance(mAdapter.getData().get(position).getFid()));
                } else {
                    ((PropertyPayFragment) getParentFragment())
                            .start(PropertyPayedDetailFragment.newInstance(mAdapter.getData().get(position).getFid()));
                }
            }
        });
    }

    private void initStateManager() {
        mStateManager = StateManager.builder(_mActivity)
                .setContent(recyclerView)
                .setEmptyView(R.layout.state_empty)
                .setEmptyImage(R.drawable.ic_property_pay_empty_state_gray_200px)
                .setEmptyText("暂无缴费信息！")
                .setErrorOnClickListener(v -> ptrFrameLayout.autoRefresh())
                .setEmptyOnClickListener(v -> ptrFrameLayout.autoRefresh())
                .build();
    }

    @Override
    public void onRefresh() {
        params.cmnt_c = UserHelper.communityCode;
        if (payState == 0) {
            mPresenter.requestPropertyNotPay(params);
        } else if (payState == 1) {
            mPresenter.requestPropertyPayed(params);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (payment != null) {
            payment.clear();
        }
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void error(String errorMessage) {
        super.error(errorMessage);
        ptrFrameLayout.refreshComplete();
        mStateManager.showError();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventCommunity event) {
        ptrFrameLayout.autoRefresh();
    }

    @Override
    public void responsePropertyNotPay(PropertyPayBean bean) {
        ptrFrameLayout.refreshComplete();
        PropertyPayBean.DataBean data = bean.getData();
        if (data.getBillList().isEmpty()) {
            mStateManager.showEmpty();
            btPay.setEnabled(false);
        } else {
            mStateManager.showContent();
            mAdapter.setNewData(data.getBillList());
            tvSum.setText("合计：" + data.getTotalAmt() + "元");

            //以下是为生成参数
            StringBuilder sb = new StringBuilder();
            for (PropertyPayBean.DataBean.BillListBean obpptBillsBean : data.getBillList()) {
                sb.append(obpptBillsBean.getFid()).append(",");
            }
            params.billFids = sb.toString();
            if (params.billFids.endsWith(",")) {
                params.billFids = params.billFids.substring(0, params.billFids.length() - 1);
            }
        }
    }

    @Override
    public void responsePropertyPayed(PropertyPayBean bean) {
        ptrFrameLayout.refreshComplete();
        if (bean.getData().getBillList().isEmpty()) {
            mStateManager.showEmpty();
        } else {
            mAdapter.setNewData(bean.getData().getBillList());
        }
    }

    @Override
    public void responsePropertyPayDetail(PropertyPayDetailBean bean) {
        //无用方法,无需理会
    }

    private void pay(IPayable iPayable) {
        //拼参数。
        Map<String, String> params = new HashMap<>();
        params.put("token", this.params.token);
        params.put("billFids", this.params.billFids);
        params.put("payMethod", this.params.payMethod + "");

        //构建支付入口对象。
        payment = Payment.builder()
                .setParams(params)
                .setHttpType(Payment.HTTP_POST)
                .setUrl(PayService.requestPropertyOrder)
                .setActivity(_mActivity)
                .setClient(NetworkClient.okhttp())
                .setPay(iPayable)
                .setOnRequestListener(new PaymentListener.OnRequestListener() {
                    @Override
                    public void onStart() {
                        ALog.e("1.请求 开始-------->");
                        showLoading("请求订单中");
                    }

                    @Override
                    public void onSuccess(String result) {
                        ALog.e("1.请求 成功-------->" + result);
                        showLoading("订单请求成功，等待解析");
                    }

                    @Override
                    public void onError(int errorCode) {
                        ALog.e("1.请求 失败-------->" + errorCode);
                        dismissLoading();
                        DialogHelper.errorSnackbar(getView(), "订单请求失败");

                    }
                })
                .setOnParseListener(new PaymentListener.OnParseListener() {
                    @Override
                    public void onStart(String result) {
                        ALog.e("2.解析 开始-------->" + result);
                        showLoading("正在解析");
                    }

                    @Override
                    public void onSuccess(PayParams params) {
                        ALog.e("2.解析 成功-------->");
                        showLoading("解析成功");

                    }

                    @Override
                    public void onError(int errorCode) {
                        ALog.e("2.解析 失败------->" + errorCode);
                        dismissLoading();
                        DialogHelper.errorSnackbar(getView(), "解析异常");
                    }
                })
                .setOnPayListener(new PaymentListener.OnPayListener() {
                    @Override
                    public void onStart(@Payment.PayType int payType) {
                        ALog.e("3.支付 开始-------->" + payType);
                        showLoading("正在支付");
                    }

                    @Override
                    public void onSuccess(@Payment.PayType int payType) {
                        ALog.e("3.支付 成功-------->" + payType);
                        dismissLoading();
                        DialogHelper.successSnackbar(getView(), "支付成功");
                        ptrFrameLayout.autoRefresh();
                    }

                    @Override
                    public void onFailure(@Payment.PayType int payType, int errorCode) {
                        ALog.e("3.支付 失败-------->" + payType + "----------errorCode-->" + errorCode);
                        dismissLoading();
                        DialogHelper.errorSnackbar(getView(), "支付失败，请重试");
                    }
                })
                .setOnVerifyListener(new PaymentListener.OnVerifyListener() {

                    @Override
                    public void onStart() {
                        ALog.e("4.检验 开始--------");
                        showLoading("正在确认");
                    }

                    @Override
                    public void onSuccess() {
                        ALog.e("4.检验 成功--------");
                        dismissLoading();
                    }

                    @Override
                    public void onFailure(int errorCode) {
                        ALog.e("4.检验 失败--------" + "errorCode-->" + errorCode);
                        dismissLoading();
                        DialogHelper.errorSnackbar(getView(), "确认失败，请稍后再查看");
                        ptrFrameLayout.autoRefresh();
                    }
                })
                .start();
    }
}
