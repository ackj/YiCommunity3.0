package cn.itsite.delivery.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.utils.ScreenUtils;
import cn.itsite.acommon.AddressBean;
import cn.itsite.delivery.R;
import cn.itsite.delivery.contract.DeliveryContract;
import cn.itsite.delivery.model.DeliveryBean;
import cn.itsite.delivery.presenter.DeliveryPresenter;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author： Administrator on 2018/1/31 0031.
 * Email： liujia95me@126.com
 */
@Route(path = "/delivery/selectshoppingaddressfragment")
public class SelectDeliveryFragment extends BaseFragment<DeliveryContract.Presenter> implements DeliveryContract.View {
    public static final String TAG = SelectDeliveryFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RelativeLayout mRlToolbar;
    private TextView mTvAdd;
    private DeliveryRVAdapter mAdapter;

    public static SelectDeliveryFragment newInstance() {
        return new SelectDeliveryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    protected DeliveryContract.Presenter createPresenter() {
        return new DeliveryPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_delivery, container, false);
        mRlToolbar = view.findViewById(R.id.rl_toolbar);
        mTvAdd = view.findViewById(R.id.tv_add);
        mRecyclerView = view.findViewById(R.id.recyclerView);
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mAdapter = new DeliveryRVAdapter();
        View header = LayoutInflater.from(_mActivity).inflate(R.layout.item_nowaday_location, null);
        //测试
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                AddressBean addressBean = new AddressBean();
                addressBean.address = "惠州江北凯宾斯基C座";
                addressBean.latitude = "23.113";
                addressBean.longitude = "114.419";
                bundle.putSerializable("address", addressBean);
                setFragmentResult(RESULT_OK, bundle);
                ((SupportActivity) _mActivity).onBackPressedSupport();
            }
        });
        mAdapter.addHeaderView(header);
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.getAddress();

    }

    private void initListener() {
        mTvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(AddDeliveryFragment.newInstance());
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter1, View view, int position) {
                List<DeliveryBean> data = mAdapter.getData();
                DeliveryBean deliveryBean = data.get(position);
                if (view.getId() == R.id.iv_edit) {
                    start(AddDeliveryFragment.newInstance());
                } else {
                    Bundle bundle = new Bundle();
                    AddressBean addressBean = new AddressBean();
                    addressBean.address = deliveryBean.getAddress();
                    addressBean.latitude = deliveryBean.getLatitude();
                    addressBean.longitude = deliveryBean.getLongitude();
                    bundle.putSerializable("address", addressBean);
                    setFragmentResult(RESULT_OK, bundle);
                    ((SupportActivity) _mActivity).onBackPressedSupport();
                }
            }
        });
    }

    @Override
    public void responseGetAddress(List<DeliveryBean> data) {
        mAdapter.setNewData(data);
    }

    @Override
    public void responseDeleteAddressSuccess() {
        DialogHelper.successSnackbar(getView(), "删除成功");
    }
}
