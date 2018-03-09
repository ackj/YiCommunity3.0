package cn.itsite.amain.yicommunity.main.propery.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.UserHelper;
import cn.itsite.amain.yicommunity.event.EventCommunity;
import cn.itsite.amain.yicommunity.main.picker.PickerActivity;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author: LiuJia on 2017/5/7 0007 20:16.
 * Email: liujia95me@126.com
 * [物业账单]的View层
 */

public class PropertyPayFragment extends BaseFragment {
    public static final String TAG = PropertyPayFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewpager;
    private TextView tvCommunity;

    public static PropertyPayFragment newInstance() {
        return new PropertyPayFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_property_pay, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        tabLayout = ((TabLayout) view.findViewById(R.id.tablayout_property_pay_fragment));
        viewpager = ((ViewPager) view.findViewById(R.id.viewpager_property_pay_fragment));
        tvCommunity = ((TextView) view.findViewById(R.id.tv_community_property_pay_fragment));
        EventBus.getDefault().register(this);
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
        toolbarTitle.setText("物业账单");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    private void initData() {
        tvCommunity.setOnClickListener(v -> {
            _mActivity.startActivity(new Intent(_mActivity, PickerActivity.class));
        });
        viewpager.setAdapter(new PropertyPayVPAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewpager);
        tvCommunity.setText(TextUtils.isEmpty(UserHelper.city) ? "请选择小区" : UserHelper.city + "　" + UserHelper.communityName);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventCommunity event) {
        tvCommunity.setText(UserHelper.city + "　" + UserHelper.communityName);
    }
}
