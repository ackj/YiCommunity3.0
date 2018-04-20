package cn.itsite.amain.yicommunity.main.parking.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.adialog.dialogfragment.BaseDialogFragment;
import cn.itsite.adialog.dialogfragment.SelectorDialogFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.abase.common.BaseBean;
import cn.itsite.amain.yicommunity.entity.bean.CarCardListBean;
import cn.itsite.amain.yicommunity.entity.bean.MonthlyPayRulesBean;
import cn.itsite.amain.yicommunity.entity.bean.ParkPayResultBean;
import cn.itsite.amain.yicommunity.main.parking.contract.CarCardPayContract;
import cn.itsite.amain.yicommunity.main.parking.presenter.CarCardPayPresenter;
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
public class CarcardPayFragment extends BaseFragment<CarCardPayContract.Presenter> implements CarCardPayContract.View, View.OnClickListener {
    public static final String TAG = CarcardPayFragment.class.getSimpleName();
    public static final int RESULT_CODE = 0x01;
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private TextView tvPark;
    private TextView tvPlate;
    private ConstraintLayout clHeader;
    private TextView tvName;
    private TextView tvPhone;
    private TextView tvMonth;
    private TextView tvIndate;
    private TextView tvAmount;
    private TextView tvAlipay;
    private TextView tvWeixin;
    private ImageView ivDelete;
    private View viewLine0;
    private View viewLine1;
    private View viewLine2;
    private View viewLine3;
    private View viewLine4;
    private View viewLine5;
    private TextView tvMonth0;
    private TextView tvIndate0;
    private TextView tvAmount0;
    private ConstraintLayout clContain;
    private Params params = Params.getInstance();
    private CarCardListBean.DataBean.CardListBean carCard;
    private BaseDialogFragment selector;

    public static CarcardPayFragment newInstance(Bundle bundle) {
        CarcardPayFragment fragment = new CarcardPayFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    protected CarCardPayContract.Presenter createPresenter() {
        return new CarCardPayPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            carCard = ((CarCardListBean.DataBean.CardListBean) arguments.getSerializable(Constants.KEY_ITEM));
            params.parkCardFid = carCard.getFid();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_card_pay, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        tvPark = ((TextView) view.findViewById(R.id.tv_park_car_card_pay_fragment));
        tvPlate = ((TextView) view.findViewById(R.id.tv_plate_car_card_pay_fragment));
        clHeader = ((ConstraintLayout) view.findViewById(R.id.cl_header_car_card_pay_fragment));
        tvName = ((TextView) view.findViewById(R.id.tv_name_car_card_pay_fragment));
        tvPhone = ((TextView) view.findViewById(R.id.tv_phone_car_card_pay_fragment));
        tvMonth = ((TextView) view.findViewById(R.id.tv_month_car_card_pay_fragment));
        tvIndate = ((TextView) view.findViewById(R.id.tv_indate_car_card_pay_fragment));
        tvAmount = ((TextView) view.findViewById(R.id.tv_amount_car_card_pay_fragment));
        tvAlipay = ((TextView) view.findViewById(R.id.tv_alipay_car_card_pay_fragment));
        tvWeixin = ((TextView) view.findViewById(R.id.tv_weixin_car_card_pay_fragment));
        ivDelete = ((ImageView) view.findViewById(R.id.iv_delete_car_card_pay_fragment));
        viewLine0 = ((View) view.findViewById(R.id.view_line_0));
        viewLine1 = ((View) view.findViewById(R.id.view_line_1));
        viewLine2 = ((View) view.findViewById(R.id.view_line_2));
        viewLine3 = ((View) view.findViewById(R.id.view_line_3));
        viewLine4 = ((View) view.findViewById(R.id.view_line_4));
        viewLine5 = ((View) view.findViewById(R.id.view_line_5));
        tvMonth0 = ((TextView) view.findViewById(R.id.tv_month));
        tvIndate0 = ((TextView) view.findViewById(R.id.tv_indate));
        tvAmount0 = ((TextView) view.findViewById(R.id.tv_amount));
        clContain = ((ConstraintLayout) view.findViewById(R.id.cl_contain_car_card_pay_fragment));
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initDate();
        initListener();
    }

    private void initListener() {
        ivDelete.setOnClickListener(this);
        tvMonth.setOnClickListener(this);
        tvAlipay.setOnClickListener(this);
        tvWeixin.setOnClickListener(this);
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    private void initDate() {
        if (carCard.getApproveState() == 2) {
            new AlertDialog.Builder(_mActivity).setTitle("温馨提示：")
                    .setMessage("您的车卡审核未通过\n" + carCard.getApproveDes())
                    .setPositiveButton("退出", (dialog, which) -> {
                        pop();
                    }).show();
        }

        if (Constants.CARD_TYPE_MONTHLY.equals(carCard.getCardType())) {
            mPresenter.requestMonthlyPayRules(params);
            toolbarTitle.setText("月卡充值");
            clHeader.setBackgroundResource(R.drawable.bg_apply_header_0);
            if (carCard.getApproveState() == 1
                    && carCard.getNeedToPayType() == 2
                    && carCard.getSurplusDays() <= 0) {
                //-------------- 已过期 -------------
                new AlertDialog.Builder(_mActivity)
                        .setTitle("温馨提示：")
                        .setMessage("您的月卡已经过期，可充值继续使用！")
                        .setPositiveButton("充值", null)
                        .show();
            }
        } else {
            toolbarTitle.setText("车位卡");
            clHeader.setBackgroundResource(R.drawable.bg_apply_header_1);
            tvIndate.setText("永久有效");
            clContain.setPadding(clContain.getPaddingLeft(), clContain.getPaddingTop(),
                    clContain.getPaddingRight(), clContain.getPaddingBottom() + 30);
            tvMonth.setVisibility(View.GONE);
            tvAmount.setVisibility(View.GONE);
            tvMonth0.setVisibility(View.GONE);
            tvAmount0.setVisibility(View.GONE);
            tvWeixin.setVisibility(View.GONE);
            tvAlipay.setVisibility(View.GONE);
            viewLine1.setVisibility(View.GONE);
            viewLine2.setVisibility(View.GONE);
            viewLine3.setVisibility(View.GONE);
            viewLine4.setVisibility(View.GONE);
            viewLine5.setVisibility(View.GONE);
        }
        tvPhone.setText(carCard.getPhoneNo());
        tvName.setText(carCard.getCustomerName());
        tvPark.setText(carCard.getParkPlace().getName());
        tvPlate.setText(carCard.getCarNo());
    }

    @Override
    public void responseDeleteCarCard(BaseBean baseBean) {
        DialogHelper.successSnackbar(getView(), baseBean.getOther().getMessage());
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.KEY_ITEM, carCard);
        setFragmentResult(RESULT_CODE, bundle);
        pop();
    }

    @Override
    public void responseMonthlyPayRules(MonthlyPayRulesBean bean) {
        List<MonthlyPayRulesBean.DataBean.MonthCardRuleListBean> rules = bean.getData().getMonthCardRuleList();
        selector = new SelectorDialogFragment()
                .setTitle("请选择充值时长")
                .setItemLayoutId(R.layout.item_rv_simple_selector)
                .setData(rules)
                .setOnItemConvertListener((holder, position, dialog) -> {
                    MonthlyPayRulesBean.DataBean.MonthCardRuleListBean rule = rules.get(position);
                    holder.setText(R.id.tv_item_rv_simple_selector, rule.getName());
                })
                .setOnItemClickListener((view, baseViewHolder, position, dialog) -> {
                    dialog.dismiss();
                    MonthlyPayRulesBean.DataBean.MonthCardRuleListBean rule = rules.get(position);
                    tvMonth.setText(rule.getName());
                    tvIndate.setText(rule.getStartDate() + "　至　" + rule.getEndDate());
                    tvAmount.setText(rule.getMoney() + "");
                    params.monthName = rule.getName();
                    params.monthCount = rule.getMonthCount();
                })
                .setAnimStyle(R.style.SlideAnimation)
                .setGravity(Gravity.BOTTOM);

        if (rules == null || rules.isEmpty()) {
            return;
        }
        //默认设置第一个。
        MonthlyPayRulesBean.DataBean.MonthCardRuleListBean firstRule = rules.get(0);
        tvMonth.setText(firstRule.getName());
        tvIndate.setText(firstRule.getStartDate() + "　至　" + firstRule.getEndDate());
        tvAmount.setText(firstRule.getMoney() + "");
        params.monthName = firstRule.getName();
        params.monthCount = firstRule.getMonthCount();
    }

    private void pay(IPayable iPayable) {
        //拼参数。
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("token", this.params.token);
        requestParams.put("parkCardFid", this.params.parkCardFid);
        requestParams.put("monthName", this.params.monthName);
        requestParams.put("monthCount", this.params.monthCount + "");
        requestParams.put("payMethod", this.params.payMethod + "");

        final PayParams[] payParams = new PayParams[1];
        //构建支付入口对象。
        Payment.builder()
                .setParams(requestParams)
                .setHttpType(Payment.HTTP_POST)
                .setUrl(PayService.requestCarCardOrder)
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
                        payParams[0] = params;
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
                        mPresenter.requestMonthlyPayRules(params);//刷新界面。

                        dismissLoading();

                        Bundle bundle = new Bundle();
                        ParkPayResultBean result = new ParkPayResultBean();
                        result.order = payParams[0].getOutTradeNo();
                        result.park = carCard.getParkPlace().getName();
                        result.plate = carCard.getCarNo();
                        result.time = carCard.getCreateTime();
                        result.amount = tvAmount.getText().toString();
                        bundle.putSerializable(Constants.KEY_PAR_KPAY_RESULT, result);
                        start(ParkPayResultFragment.newInstance(bundle));
                    }

                    @Override
                    public void onFailure(@Payment.PayType int payType, int errorCode) {
                        ALog.e("3.支付 失败-------->" + payType + "----------errorCode-->" + errorCode);
                        dismissLoading();

                        if (errorCode == Payment.PAY_REPAY) {
                            DialogHelper.errorSnackbar(getView(), "您已支付，无需重复支付！");
                        } else {
                            DialogHelper.errorSnackbar(getView(), "支付失败，请重试！");
                        }
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
        if (i == R.id.iv_delete_car_card_pay_fragment) {
            new AlertDialog.Builder(_mActivity).setTitle("温馨提示")
                    .setMessage("是否删除该停车卡？")
                    .setNegativeButton("否", null)
                    .setPositiveButton("是", (dialog, which) -> {
                        mPresenter.requestDeleteCarCard(params);
                    })
                    .show();

        } else if (i == R.id.tv_month_car_card_pay_fragment) {
            if (selector != null) {
                selector.show(getChildFragmentManager());
            }
        } else if (i == R.id.tv_alipay_car_card_pay_fragment) {
            params.payMethod = Payment.PAYTYPE_ALI_APP;
            pay(Pay.aliAppPay());
        } else if (i == R.id.tv_weixin_car_card_pay_fragment) {
            params.payMethod = Payment.PAYTYPE_WECHAT_H5X;
            pay(Pay.weChatH5xPay());
        }
    }
}
