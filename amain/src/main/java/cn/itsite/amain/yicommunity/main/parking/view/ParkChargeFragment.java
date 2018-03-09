package cn.itsite.amain.yicommunity.main.parking.view;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.mvp.view.base.Decoration;
import cn.itsite.adialog.dialogfragment.BaseDialogFragment;
import cn.itsite.akeyboard.KeyboardHelper;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.ParkPayResultBean;
import cn.itsite.amain.yicommunity.entity.bean.ParkSelectBean.DataBean.ParkPlaceListBean;
import cn.itsite.amain.yicommunity.entity.bean.ParkingChargeBean;
import cn.itsite.amain.yicommunity.entity.db.PlateHistoryData;
import cn.itsite.amain.yicommunity.main.parking.contract.TempParkContract;
import cn.itsite.amain.yicommunity.main.parking.presenter.TempParkPresenter;
import cn.itsite.amain.yicommunity.main.picker.view.ParkPickerFragment;
import cn.itsite.apayment.payment.PayParams;
import cn.itsite.apayment.payment.Payment;
import cn.itsite.apayment.payment.PaymentListener;
import cn.itsite.apayment.payment.network.NetworkClient;
import cn.itsite.apayment.payment.network.PayService;
import cn.itsite.apayment.payment.pay.IPayable;
import cn.itsite.apayment.payment.pay.Pay;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 临时停车场模块的容器Activity。
 */
public class ParkChargeFragment extends BaseFragment<TempParkContract.Presenter> implements TempParkContract.View, View.OnClickListener {
    private static final String TAG = ParkChargeFragment.class.getSimpleName();
    public static final int REQUEST_CODE_CITY = 0x010;
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private TextView tvPark;
    private TextView tvPlate;
    private Button btSearch;
    private KeyboardView keyboard;
    private RecyclerView recyclerView;
    private KeyboardHelper keyboardHelper;
    private Params params = Params.getInstance();
    private ParkChargeAdapter adapter;
    private ParkingChargeBean parkCharge;

    public static ParkChargeFragment newInstance(Bundle bundle) {
        ParkChargeFragment fragment = new ParkChargeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    protected TempParkContract.Presenter createPresenter() {
        return new TempParkPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            params.parkPlaceFid = bundle.getString(Constants.PARAM_PARKPLACEFID);
            params.name = bundle.getString(Constants.PARAM_PARKNAME);
            if (TextUtils.isEmpty(params.parkPlaceFid) || TextUtils.isEmpty(params.name)) {
                startForResult(ParkPickerFragment.newInstance(), REQUEST_CODE_CITY);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parking_charge, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        tvPark = ((TextView) view.findViewById(R.id.tv_park_parking_charge_fragment));
        tvPlate = ((TextView) view.findViewById(R.id.tv_plate_parking_charge_fragment));
        btSearch = ((Button) view.findViewById(R.id.bt_search_parking_charge_fragment));
        keyboard = ((KeyboardView) view.findViewById(R.id.keyboard));
        recyclerView = ((RecyclerView) view.findViewById(R.id.recyclerView));
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        initListener();
    }

    private void initListener() {
        tvPlate.setOnClickListener(this);
        btSearch.setOnClickListener(this);
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("临停缴费");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    private void initData() {
        mPresenter.requestPlateHistory();
        keyboardHelper = new KeyboardHelper(keyboard, tvPlate);
        tvPark.setText(params.name);
        tvPlate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ALog.e("CharSequence--" + s);
                btSearch.setEnabled(s.length() >= 7);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        adapter = new ParkChargeAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        recyclerView.addItemDecoration(new Decoration(_mActivity, Decoration.VERTICAL_LIST));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            int i = view.getId();
            if (i == R.id.iv_clean_item_rv_park_charge) {
                new AlertDialog.Builder(_mActivity)
                        .setTitle("温馨提示")
                        .setMessage("是否删除历史记录？")
                        .setPositiveButton("是", (dialog, which) -> {
                            if (position == 0) {
                                adapter.removeHistory();
                            } else {
                                adapter.removeHistory(position);
                            }
                        }).setNegativeButton("否", null)
                        .show();
            } else if (i == R.id.tv_park_item_rv_park_charge) {
                if (position > 0) {
                    PlateHistoryData plate = adapter.getItem(position);
                    tvPlate.setText(plate.getPlate());
                    mPresenter.cachePlateHistory(plate);
                }
            }
        });
    }

    @Override
    public void responseParkingCharge(ParkingChargeBean data) {
        showParkingCharge(data);
    }

    private void showParkingCharge(ParkingChargeBean data) {
        this.parkCharge = data;
        new BaseDialogFragment()
                .setLayoutId(R.layout.dialog_parking_charge)
                .setConvertListener((holder, dialog) -> {
                    holder.setOnClickListener(R.id.iv_cancel_parking_charge_dialog, v -> {
                        dialog.dismiss();
                    }).setOnClickListener(R.id.tv_alipay_parking_charge_dialog, v -> {
                        params.payMethod = Payment.PAYTYPE_ALI_APP;
                        pay(Pay.aliAppPay());
                        dialog.dismiss();
                    }).setOnClickListener(R.id.tv_weixin_parking_charge_dialog, v -> {
                        params.payMethod = Payment.PAYTYPE_WECHAT_H5X;
                        pay(Pay.weChatH5xPay());
                        dialog.dismiss();
                    }).setText(R.id.tv_park_parking_charge_dialog, data.getData().getParkPlaceName())
                            .setText(R.id.tv_plate_parking_charge_dialog, data.getData().getCarNo())
                            .setText(R.id.tv_in_time_parking_charge_dialog, data.getData().getInTime())
                            .setText(R.id.tv_pay_time_parking_charge_dialog, data.getData().getOutTime())
                            .setText(R.id.tv_duration_parking_charge_dialog, data.getData().getTotalCostTime())
                            .setText(R.id.tv_amount_parking_charge_dialog, data.getData().getAmount() + " 元")
                            .setText(R.id.tv_paid_parking_charge_dialog, data.getData().getPaidMoney() + " 元")
                            .setText(R.id.tv_charge_parking_charge_dialog, data.getData().getCostMoney() + " 元");
                })
                .setMargin(20)
                .setDimAmount(0.3f)
                .setGravity(Gravity.CENTER)
                .show(getFragmentManager())
                .setCancelable(false);
    }

    @Override
    public void responsePlateHistory(List<PlateHistoryData> plates) {
        adapter.setNewData(plates);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (data == null) {
            ((SupportActivity) _mActivity).onBackPressedSupport();
            return;
        }

        ParkPlaceListBean parkBean = (ParkPlaceListBean) data.getSerializable(Constants.KEY_PARK);
        if (parkBean == null) {
            ((SupportActivity) _mActivity).onBackPressedSupport();
            return;
        }

        params.parkPlaceFid = parkBean.getFid();
        params.name = parkBean.getName();
        tvPark.setText(params.name);

        if (TextUtils.isEmpty(params.parkPlaceFid)
                || TextUtils.isEmpty(params.name)) {
            ((SupportActivity) _mActivity).onBackPressedSupport();
        }
    }

    private void pay(IPayable iPayable) {
        //拼参数。
        Map<String, String> params = new HashMap<>();
        params.put("carNo", this.params.carNo);
        params.put("parkPlaceFid", this.params.parkPlaceFid);
        params.put("payMethod", this.params.payMethod + "");
        final PayParams[] payParams = new PayParams[1];

        //构建支付入口对象。
        Payment.builder()
                .setParams(params)
                .setHttpType(Payment.HTTP_POST)
                .setUrl(PayService.requestTempParkOrder)
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
                        payParams[0] = params;
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
//                        DialogHelper.successSnackbar(getView(), "支付成功");
                        Bundle bundle = new Bundle();
                        ParkPayResultBean result = new ParkPayResultBean();
                        result.order = payParams[0].getOutTradeNo();
                        result.park = parkCharge.getData().getParkPlaceName();
                        result.plate = parkCharge.getData().getCarNo();
                        result.time = parkCharge.getData().getOutTime();
                        result.amount = parkCharge.getData().getCostMoney() + "";
                        bundle.putSerializable(Constants.KEY_PAR_KPAY_RESULT, result);
                        start(ParkPayResultFragment.newInstance(bundle));
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
                    }
                })
                .start();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_plate_parking_charge_fragment) {
            keyboardHelper.show();
        } else if (i == R.id.bt_search_parking_charge_fragment) {
            params.carNo = tvPlate.getText().toString();
            mPresenter.requestParkingCharge(params);
            mPresenter.cachePlateHistory(new PlateHistoryData(tvPlate.getText().toString()));
            keyboardHelper.hide();
        }
    }
}
