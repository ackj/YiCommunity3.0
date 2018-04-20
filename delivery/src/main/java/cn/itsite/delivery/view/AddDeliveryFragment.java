package cn.itsite.delivery.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.abase.utils.ScreenUtils;
import cn.itsite.abase.utils.ToastUtils;
import cn.itsite.albs.location.LocationFragment;
import cn.itsite.delivery.R;
import cn.itsite.delivery.contract.AddDeliveryContract;
import cn.itsite.acommon.DeliveryBean;
import cn.itsite.delivery.presenter.AddDeliveryPresenter;

/**
 * Author： Administrator on 2018/1/31 0031.
 * Email： liujia95me@126.com
 */
@Route(path = "/delivery/addaddressfragment")
public class AddDeliveryFragment extends BaseFragment<AddDeliveryContract.Presenter> implements AddDeliveryContract.View, View.OnClickListener {
    public static final String TAG = AddDeliveryFragment.class.getSimpleName();
    public static final int REQUEST_CODE = 0x11;
    private RelativeLayout mRlToolbar;
    private TextView mTvSave;
    private boolean isAdd;
    private EditText mEtName;
    private EditText mEtPhone;
    private EditText mEtDetailAddress;
    private TextView mTvSelectAddress;
    private RadioGroup mRgGender;
    private DeliveryBean deliveryBean;
    private String gender = GENDER_SECRECY;
    public static final String GENDER_MALE = "male";
    public static final String GENDER_FEMALE = "female";
    public static final String GENDER_SECRECY = "secrecy";
    private CheckBox mCbDefault;
    private TextView mTvTitle;

    public static AddDeliveryFragment newInstance(DeliveryBean deliveryBean) {
        AddDeliveryFragment fragment = new AddDeliveryFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("OperateBean", deliveryBean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    protected AddDeliveryContract.Presenter createPresenter() {
        return new AddDeliveryPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deliveryBean = (DeliveryBean) getArguments().getSerializable("OperateBean");
        isAdd = deliveryBean == null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_delivery, container, false);
        mRlToolbar = view.findViewById(R.id.rl_toolbar);
        mTvSave = view.findViewById(R.id.tv_save);
        mTvSelectAddress = view.findViewById(R.id.tv_select_address);
        mEtName = view.findViewById(R.id.et_name);
        mEtPhone = view.findViewById(R.id.et_phone);
        mEtDetailAddress = view.findViewById(R.id.et_detail_address);
        mRgGender = view.findViewById(R.id.rg_gender);
        mCbDefault = view.findViewById(R.id.cb_default);
        mTvTitle = view.findViewById(R.id.tv_title);
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
        mRlToolbar.setPadding(mRlToolbar.getPaddingLeft(),
                mRlToolbar.getPaddingTop() + ScreenUtils.getStatusBarHeight(_mActivity),
                mRlToolbar.getPaddingRight(),
                mRlToolbar.getPaddingBottom());
    }

    private void initData() {
        if (isAdd) {
            deliveryBean = new DeliveryBean();
        } else {
            mEtPhone.setText(deliveryBean.getPhoneNumber());
            //对界面进行初始化
            mTvTitle.setText("修改地址");
            mEtName.setText(deliveryBean.getName());
            mEtDetailAddress.setText(deliveryBean.getAddress());
            mTvSelectAddress.setText(deliveryBean.getLocation());
            mCbDefault.setChecked(deliveryBean.isIsDefault());
            String gender = deliveryBean.getGender();
            if (!TextUtils.isEmpty(gender)) {
                switch (gender) {
                    case GENDER_MALE:
                        mRgGender.check(R.id.rb_male);
                        break;
                    case GENDER_FEMALE:
                        mRgGender.check(R.id.rb_female);
                        break;
                    case GENDER_SECRECY:
                        mRgGender.check(R.id.rb_secrecy);
                        break;
                    default:
                }
            }
        }
    }

    private void initListener() {
        mTvSave.setOnClickListener(this);
        mTvSelectAddress.setOnClickListener(this);
        mRgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_male) {
                    gender = GENDER_MALE;
                } else if (checkedId == R.id.rb_female) {
                    gender = GENDER_FEMALE;
                } else {
                    gender = GENDER_SECRECY;
                }
            }
        });

    }

    @Override
    public void responsePostAddressSuccess(BaseResponse response) {
        DialogHelper.successSnackbar(getView(), response.getMessage());
        setFragmentResult(RESULT_OK, null);
        pop();
    }

    @Override
    public void responsePutAddressSuccess(BaseResponse response) {
        DialogHelper.successSnackbar(getView(), response.getMessage());
        setFragmentResult(RESULT_OK, null);
        pop();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_save) {
            if (checkInput()) {
                if (isAdd) {
                    mPresenter.postAddress(deliveryBean);
                } else {
                    mPresenter.putAddress(deliveryBean);
                }
            }
        } else if (v.getId() == R.id.tv_select_address) {
            startForResult(LocationFragment.newInstance(), REQUEST_CODE);
        }
    }

    private boolean checkInput() {
        String name = mEtName.getText().toString();
        String phone = mEtPhone.getText().toString();
        String detailAddress = mEtDetailAddress.getText().toString();
        String address = mTvSelectAddress.getText().toString();
        boolean isDefault = mCbDefault.isChecked();
        String message = null;
        if (TextUtils.isEmpty(name)) {
            message = "请输入姓名";
        } else if (TextUtils.isEmpty(phone)) {
            message = "请输入电话";
        } else if (TextUtils.isEmpty(address)) {
            message = "请选择地址";
        } else if (TextUtils.isEmpty(detailAddress)) {
            message = "请填写详细地址";
        }
        if (TextUtils.isEmpty(message)) {
            deliveryBean.setGender(gender);
            deliveryBean.setAddress(detailAddress);
            deliveryBean.setLocation(address);
            deliveryBean.setIsDefault(isDefault);
            deliveryBean.setPhoneNumber(phone);
            deliveryBean.setName(name);
            return true;
        } else {
            ToastUtils.showToast(_mActivity, message);
            return false;
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == LocationFragment.RESULT_CODE) {
            if (data != null) {
                String address = data.getString(LocationFragment.ADDRESS);
                deliveryBean.setLocation(address);
                deliveryBean.setLongitude("" + data.getDouble(LocationFragment.LONGITUDE));
                deliveryBean.setLatitude("" + data.getDouble(LocationFragment.LATITUDE));
                mTvSelectAddress.setText(address);
            }
        }
    }
}
