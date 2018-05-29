package cn.itsite.amain.yicommunity.main.mine.view;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.akeyboard.KeyboardHelper;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.ParkingChargeBean;
import cn.itsite.amain.yicommunity.main.parking.view.ParkChargeFragment;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by liujia on 2018/5/23.
 * 添加车辆页面
 */

public class AddCarDataFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = ParkChargeFragment.class.getSimpleName();
    public static final int REQUEST_CODE_CITY = 0x010;
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private TextView tvPark;
    private TextView tvPlate;
    private KeyboardView keyboard;
    private KeyboardHelper keyboardHelper;
    private Params params = Params.getInstance();
    private ParkingChargeBean parkCharge;
    private TextView toolbarMenu;

    public static AddCarDataFragment newInstance(Bundle bundle) {
        AddCarDataFragment fragment = new AddCarDataFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            params.parkPlaceFid = bundle.getString(Constants.PARAM_PARKPLACEFID);
            params.name = bundle.getString(Constants.PARAM_PARKNAME);
            if (TextUtils.isEmpty(params.parkPlaceFid) || TextUtils.isEmpty(params.name)) {
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_car_data, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbarMenu = view.findViewById(R.id.toolbar_menu);
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        tvPark = ((TextView) view.findViewById(R.id.tv_park));
        tvPlate = ((TextView) view.findViewById(R.id.tv_plate_num));
        keyboard = ((KeyboardView) view.findViewById(R.id.keyboard));
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
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("添加");
        toolbarMenu.setText("保存");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    private void initData() {
        keyboardHelper = new KeyboardHelper(keyboard, tvPlate);
        tvPark.setText(params.name);
        tvPlate.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ALog.e("CharSequence--" + s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_plate_num) {
            keyboardHelper.show();
        }
    }
}
