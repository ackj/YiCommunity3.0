package com.example.ecmain.mine.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.ecmain.R;
import com.example.ecmain.mine.contract.SettingContract;
import com.example.ecmain.mine.presenter.SettingPresenter;
import com.kyleduo.switchbutton.SwitchButton;

import org.greenrobot.eventbus.EventBus;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.common.UserHelper;
import cn.itsite.abase.event.EventECLogout;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.network.http.BaseOldResponse;
import cn.itsite.acommon.data.UserParams;
import cn.itsite.login.LoginActivity;
import cn.itsite.login.model.PushEnableBean;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author： Administrator on 2018/3/9 0009.
 * Email： liujia95me@126.com
 */
@Route(path = "/mine/settingfragment")
public class SettingFragment extends BaseFragment<SettingContract.Presenter> implements SettingContract.View,View.OnClickListener {

    private static final String TAG = SettingFragment.class.getSimpleName();
    private RelativeLayout mRlToolbar;
    private ImageView mIvBack;

    private UserParams params = new UserParams();
    private SwitchButton mSwitchButton;
    private TextView mTvCache;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    protected SettingContract.Presenter createPresenter() {
        return new SettingPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        mRlToolbar = view.findViewById(R.id.rl_toolbar);
        mIvBack = view.findViewById(R.id.iv_back);
        view.findViewById(R.id.ll_edit_info).setOnClickListener(this);
        view.findViewById(R.id.ll_clear).setOnClickListener(this);
        view.findViewById(R.id.ll_logout).setOnClickListener(this);
        view.findViewById(R.id.iv_back).setOnClickListener(this);
        mSwitchButton = view.findViewById(R.id.switch_button);
        mTvCache = view.findViewById(R.id.tv_cache);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initStateBar(mRlToolbar);
        initData();
        initListener();
    }

    private void initData() {
        mPresenter.requestCache();
        mPresenter.requestMemberConfigInfo();
    }

    private void initListener() {
        mIvBack.setOnClickListener(this);
        mSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                params.pushEnable = b;
                mPresenter.requestPushConfig(params);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.ll_edit_info){
            ((SupportActivity) _mActivity).start(EditInfoFragment.newInstance());
        }else if(v.getId()==R.id.iv_back){
            pop();
        }else if(v.getId()==R.id.ll_clear){
            mPresenter.requestClearCache();
        }else if(v.getId()==R.id.ll_logout){
            mPresenter.requestLogout(params);
        }
    }

    @Override
    public void responsePushConfig(BaseOldResponse response) {
        DialogHelper.successSnackbar(getView(),response.getOther().getMessage());
    }

    @Override
    public void responseLogout(BaseOldResponse response) {
        DialogHelper.successSnackbar(getView(),response.getOther().getMessage());
        UserHelper.clear();
        EventBus.getDefault().post(new EventECLogout());
        startActivity(new Intent(_mActivity,LoginActivity.class));
    }

    @Override
    public void requestMemberConfigInfo(PushEnableBean pushEnable) {
        mSwitchButton.setCheckedNoEvent(pushEnable.isPushEnable());
    }

    @Override
    public void responseCache(String cache) {
        mTvCache.setText(cache);
    }
}
