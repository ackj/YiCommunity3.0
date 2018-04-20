package cn.itsite.amain.yicommunity.main.parking.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.OnClick;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.CarportBeam;
import cn.itsite.amain.yicommunity.entity.bean.ParkSelectBean;
import cn.itsite.amain.yicommunity.main.parking.contract.CarportContract;
import cn.itsite.amain.yicommunity.main.parking.presenter.CarportPresenter;
import cn.itsite.amain.yicommunity.main.picker.view.ParkPickerFragment;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 临时停车场模块的容器Activity。
 */
public class CarportFragment extends BaseFragment<CarportContract.Presenter> implements CarportContract.View {
    public static final String TAG = CarportFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private TextView tvPark;
    private TextView tvAmount;
    private Params params = Params.getInstance();

    public static CarportFragment newInstance() {
        return new CarportFragment();
    }

    @NonNull
    @Override
    protected CarportContract.Presenter createPresenter() {
        return new CarportPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carport, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        tvPark = ((TextView) view.findViewById(R.id.tv_park_carport_fragment));
        tvAmount = ((TextView) view.findViewById(R.id.tv_amount_carport_fragment));
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initListener();
    }

    private void initListener() {
        tvPark.setOnClickListener(v -> {
            startForResult(ParkPickerFragment.newInstance(), ParkPickerFragment.RESULT_CODE_PARK);
        });
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("车位查询");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    @Override
    public void responseCarports(CarportBeam data) {
        tvAmount.setText(data.getData().getSurplusSpace() + "");
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        ParkSelectBean.DataBean.ParkPlaceListBean parkBean = (ParkSelectBean.DataBean.ParkPlaceListBean) data.getSerializable(Constants.KEY_PARK);
        if (parkBean != null) {
            tvPark.setText(parkBean.getName());
            params.parkPlaceFid = parkBean.getFid();
            mPresenter.requestCarports(params);
        }
    }
}
