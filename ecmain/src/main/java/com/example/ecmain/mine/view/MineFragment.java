package com.example.ecmain.mine.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ecmain.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import cn.itsite.abase.common.UserHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.utils.ToastUtils;
import cn.itsite.acommon.data.GoodsParams;
import cn.itsite.acommon.event.EventRefreshAvator;
import cn.itsite.acommon.event.EventRefreshNickname;
import cn.itsite.acommon.event.EventRefreshOrdersPoint;
import cn.itsite.delivery.view.SelectDeliveryFragment;
import cn.itsite.order.contract.MineOrderContract;
import cn.itsite.order.model.CategoryBean;
import cn.itsite.order.presenter.MineOrderPresenter;
import cn.itsite.order.view.MineOrderFragment;
import me.yokeyword.fragmentation.SupportActivity;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * Author： Administrator on 2018/3/9 0009.
 * Email： liujia95me@126.com
 */
@Route(path = "/mine/minefragment")
public class MineFragment extends BaseFragment<MineOrderContract.Presenter> implements MineOrderContract.View, View.OnClickListener {

    private static final String TAG = MineFragment.class.getSimpleName();
    private TextInputLayout mTilPhone;
    private TextView mTvPhone;
    private TextView mTvNickname;
    private ImageView mIvWaitForPay;
    private ImageView mIvWaitForGet;
    private ImageView mIvAfterSale;
    private Badge mBadgeAfterSales;
    private Badge mBadgeWaftForPay;
    private Badge mBadgeWaftForGet;
    private String[] mOrdersArray;

    private GoodsParams params = new GoodsParams();
    private ImageView mIvAvator;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    protected MineOrderContract.Presenter createPresenter() {
        return new MineOrderPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        view.findViewById(R.id.ll_location).setOnClickListener(this);
        view.findViewById(R.id.ll_set_score).setOnClickListener(this);
        view.findViewById(R.id.ll_feedback).setOnClickListener(this);
        view.findViewById(R.id.ll_about).setOnClickListener(this);
        view.findViewById(R.id.ll_setting).setOnClickListener(this);
        view.findViewById(R.id.tv_update_info).setOnClickListener(this);
        view.findViewById(R.id.ll_mine_orders).setOnClickListener(this);
        view.findViewById(R.id.rl_all_orders).setOnClickListener(this);
        view.findViewById(R.id.rl_wait_pay).setOnClickListener(this);
        view.findViewById(R.id.rl_wait_take).setOnClickListener(this);
        view.findViewById(R.id.rl_after_sale).setOnClickListener(this);
        mTvNickname = view.findViewById(R.id.tv_nickname);
        mTvPhone = view.findViewById(R.id.tv_phone);
        mIvWaitForPay = view.findViewById(R.id.iv_wait_for_pay);
        mIvWaitForGet = view.findViewById(R.id.iv_wait_for_get);
        mIvAfterSale = view.findViewById(R.id.iv_after_sale);
        mIvAvator = view.findViewById(R.id.iv_icon);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initListener();
        EventBus.getDefault().register(this);
    }

    private void initData() {
        mTvNickname.setText(UserHelper.nickName);
        mTvPhone.setText(UserHelper.mobile);
        refreshAvator();

        mOrdersArray = getResources().getStringArray(R.array.order_tabs);
        //提示小点
        mBadgeAfterSales = new QBadgeView(_mActivity)
                .bindTarget(mIvAfterSale)
                .setBadgeTextSize(8, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setBadgeBackgroundColor(getResources().getColor(R.color.base_color))
                .setBadgeTextColor(0x33FFFFFF);
        mBadgeWaftForPay = new QBadgeView(_mActivity)
                .bindTarget(mIvWaitForPay)
                .setBadgeTextSize(8, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setBadgeBackgroundColor(getResources().getColor(R.color.base_color))
                .setBadgeTextColor(0x33FFFFFF);
        mBadgeWaftForGet = new QBadgeView(_mActivity)
                .bindTarget(mIvWaitForGet)
                .setBadgeTextSize(8, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setBadgeBackgroundColor(getResources().getColor(R.color.base_color))
                .setBadgeTextColor(0xFFFFFF);

        params.type = "orders";
        mPresenter.getCategories(params);
    }

    private void refreshAvator() {
        Glide.with(_mActivity)
                .load(UserHelper.avator)
                .apply(new RequestOptions().error(R.drawable.ic_avatar_150px).placeholder(R.drawable.ic_avatar_150px).circleCrop())
                .into(mIvAvator);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(false).init();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventRefreshOrdersPoint event){
        mPresenter.getCategories(params);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventRefreshAvator event){
        refreshAvator();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventRefreshNickname event){
        mTvNickname.setText(UserHelper.nickName);
    }

    private void initListener() {
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_location) {
            ((SupportActivity) _mActivity).start(SelectDeliveryFragment.newInstance());
        } else if (v.getId() == R.id.ll_set_score) {
            ToastUtils.showToast(_mActivity,"此功能正在开发");
        } else if (v.getId() == R.id.ll_feedback) {
            ((SupportActivity) _mActivity).start(FeedbackFragment.newInstance());
        } else if (v.getId() == R.id.ll_about) {
            ((SupportActivity) _mActivity).start(AboutFragment.newInstance());
        } else if (v.getId() == R.id.ll_setting) {
            ((SupportActivity) _mActivity).start(SettingFragment.newInstance());
        } else if (v.getId() == R.id.tv_update_info) {
            ((SupportActivity) _mActivity).start(EditInfoFragment.newInstance());
        } else if (v.getId() == R.id.ll_mine_orders) {
            ((SupportActivity) _mActivity).start(MineOrderFragment.newInstance());
        } else if (v.getId() == R.id.rl_all_orders) {
            ((SupportActivity) _mActivity).start(MineOrderFragment.newInstance(mOrdersArray[0]));
        } else if (v.getId() == R.id.rl_wait_pay) {
            ((SupportActivity) _mActivity).start(MineOrderFragment.newInstance(mOrdersArray[1]));
        } else if (v.getId() == R.id.rl_wait_take) {
            ((SupportActivity) _mActivity).start(MineOrderFragment.newInstance(mOrdersArray[2]));
        } else if (v.getId() == R.id.rl_after_sale) {
            ((SupportActivity) _mActivity).start(MineOrderFragment.newInstance(mOrdersArray[3]));
        }
    }

    @Override
    public void responseGetCategories(List<CategoryBean> data) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getCategory().equals(mOrdersArray[1])) {
                mBadgeWaftForPay.setBadgeNumber(Integer.valueOf(data.get(i).getCounts()));
            } else if (data.get(i).getCategory().equals(mOrdersArray[2])) {
                mBadgeWaftForGet.setBadgeNumber(Integer.valueOf(data.get(i).getCounts()));
            } else if (data.get(i).getCategory().equals(mOrdersArray[3])) {
                mBadgeAfterSales.setBadgeNumber(Integer.valueOf(data.get(i).getCounts()));
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
