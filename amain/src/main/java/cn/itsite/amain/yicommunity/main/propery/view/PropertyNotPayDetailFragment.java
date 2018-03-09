package cn.itsite.amain.yicommunity.main.propery.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.mvp.view.base.Decoration;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.PropertyPayBean;
import cn.itsite.amain.yicommunity.entity.bean.PropertyPayDetailBean;
import cn.itsite.amain.yicommunity.main.propery.contract.PropertyPayContract;
import cn.itsite.amain.yicommunity.main.propery.presenter.PropertyPayPresenter;
import cn.itsite.apayment.payment.PayParams;
import cn.itsite.apayment.payment.Payment;
import cn.itsite.apayment.payment.PaymentListener;
import cn.itsite.apayment.payment.network.NetworkClient;
import cn.itsite.apayment.payment.network.PayService;
import cn.itsite.apayment.payment.pay.IPayable;
import cn.itsite.apayment.payment.pay.Pay;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by leguang on 2017/4/29 0029.
 * Email：langmanleguang@qq.com
 */
public class PropertyNotPayDetailFragment extends BaseFragment<PropertyPayContract.Presenter> implements PropertyPayContract.View {
    public static final String TAG = PropertyNotPayDetailFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private TextView tvAddress;
    private RecyclerView recyclerView;
    private PtrFrameLayout ptrFrameLayout;
    private TextView tvSum;
    private Button btPay;
    private Params params = Params.getInstance();
    private PropertyNotPayDetailRVAdapter mAdapter;
    private String[] payTypes = {Constants.ALIPAY, Constants.WXPAY};
    private Payment payment;

    public static PropertyNotPayDetailFragment newInstance(String fid) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_FID, fid);
        PropertyNotPayDetailFragment fragment = new PropertyNotPayDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            params.fid = bundle.getString(Constants.KEY_FID);
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
        View view = inflater.inflate(R.layout.fragment_property_not_pay_detail, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        tvAddress = ((TextView) view.findViewById(R.id.tv_address_property_not_pay_detail_fragment));
        recyclerView = ((RecyclerView) view.findViewById(R.id.recyclerView));
        ptrFrameLayout = ((PtrFrameLayout) view.findViewById(R.id.ptrFrameLayout));
        tvSum = ((TextView) view.findViewById(R.id.tv_sum_property_not_pay_detail_fragment));
        btPay = ((Button) view.findViewById(R.id.bt_pay_property_not_pay_detail_fragment));
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        initListener();
        initPtrFrameLayout(ptrFrameLayout, recyclerView);
    }

    private void initListener() {
        btPay.setOnClickListener(v -> new AlertDialog.Builder(_mActivity).setTitle("请选择支付类型")
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
                .show());
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("账单详情");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    private void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mAdapter = new PropertyNotPayDetailRVAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new Decoration(_mActivity, Decoration.VERTICAL_LIST));
    }

    @Override
    public void onRefresh() {
        mPresenter.requestPropertyPayDetail(params);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (payment != null) {
            payment.clear();
        }
    }

    @Override
    public void error(String errorMessage) {
        super.error(errorMessage);
        ptrFrameLayout.refreshComplete();
    }

    @Override
    public void responsePropertyNotPay(PropertyPayBean bean) {
        //无用方法,无需理会
    }

    @Override
    public void responsePropertyPayed(PropertyPayBean bean) {
        //无用方法,无需理会
    }

    @Override
    public void responsePropertyPayDetail(PropertyPayDetailBean bean) {
        ptrFrameLayout.refreshComplete();
        mAdapter.setNewData(bean.getData().getItemList());
        tvAddress.setText(bean.getData().getHouseInfo());
        tvSum.setText("合计：" + bean.getData().getAmount() + "元");
        params.billFids = bean.getData().getFid();
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
