package cn.itsite.amain.yicommunity.main.parking.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.entity.bean.ParkPayResultBean;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 临时停车场模块的容器Activity。
 */
public class ParkPayResultFragment extends BaseFragment {
    private static final String TAG = ParkPayResultFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private TextView tvPark;
    private TextView tvPlate;
    private TextView tvOrder;
    private TextView tvPayTime;
    private TextView tvCharge;
    private Button btBack;
    private ParkPayResultBean result;

    public static ParkPayResultFragment newInstance(Bundle bundle) {
        ParkPayResultFragment fragment = new ParkPayResultFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            result = ((ParkPayResultBean) bundle.getSerializable(Constants.KEY_PAR_KPAY_RESULT));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_park_pay_result, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        tvPark = ((TextView) view.findViewById(R.id.tv_park_park_pay_result_fragment));
        tvPlate = ((TextView) view.findViewById(R.id.tv_plate_park_pay_result_fragment));
        tvOrder = ((TextView) view.findViewById(R.id.tv_order_park_pay_result_fragment));
        tvPayTime = ((TextView) view.findViewById(R.id.tv_pay_time_park_pay_result_fragment));
        tvCharge = ((TextView) view.findViewById(R.id.tv_charge_park_pay_result_fragment));
        btBack = ((Button) view.findViewById(R.id.bt_back_park_pay_result_fragment));
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("缴费成功");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
        btBack.setOnClickListener(v -> {
            ((SupportActivity) _mActivity).onBackPressedSupport();
        });
    }

    private void initData() {
        tvPark.setText(result.park);
        tvPlate.setText(result.plate);
        tvOrder.setText(result.order);
        tvPayTime.setText(result.time);
        tvCharge.setText(result.amount + " 元");
    }
}
