package cn.itsite.amain.yicommunity.main.message.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.abase.common.BaseBean;
import cn.itsite.amain.yicommunity.entity.bean.MessageCenterBean;
import cn.itsite.amain.yicommunity.main.message.contract.ApplyCheckContract;
import cn.itsite.amain.yicommunity.main.message.presenter.ApplyCheckPresenter;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author: LiuJia on 2017/5/19 0019 15:07.
 * Email: liujia95me@126.com
 * 申请审核的View层
 */

public class ApplyCheckFragment extends BaseFragment<ApplyCheckContract.Presenter> implements ApplyCheckContract.View, View.OnClickListener {
    private static final String TAG = ApplyCheckFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private TextView tvDesc;
    private Params params = Params.getInstance();
    private MessageCenterBean.DataBean.MemNewsBean bean;
    private Button btPass;
    private Button btRefuse;

    /**
     * ApplyCheckFragment的创建入口
     *
     * @param bean 消息中心的Bean
     * @return
     */
    public static ApplyCheckFragment newInstance(MessageCenterBean.DataBean.MemNewsBean bean) {
        ApplyCheckFragment checkFragment = new ApplyCheckFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.KEY_BEAN, bean);
        checkFragment.setArguments(bundle);
        return checkFragment;
    }

    @NonNull
    @Override
    protected ApplyCheckContract.Presenter createPresenter() {
        return new ApplyCheckPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            bean = (MessageCenterBean.DataBean.MemNewsBean) bundle.getSerializable(Constants.KEY_BEAN);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apply_check, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        tvDesc = ((TextView) view.findViewById(R.id.tv_desc_apply_check_fragment));
        btPass = ((Button) view.findViewById(R.id.bt_pass_check_fragment));
        btRefuse = ((Button) view.findViewById(R.id.bt_refuse_check_fragment));
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        initListener();
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText(bean.getTitle());
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    private void initData() {
        params.fid = bean.getFid();
        params.authFid = bean.getSfid();
        tvDesc.setText(bean.getDes());
    }

    private void initListener() {
        btPass.setOnClickListener(this);
        btRefuse.setOnClickListener(this);
    }

    @Override
    public void error(String errorMessage) {
        super.error(errorMessage);
        DialogHelper.warningSnackbar(getView(), errorMessage);
    }

    /**
     * 响应请求申请通过
     *
     * @param bean
     */
    @Override
    public void responseApplySuccess(BaseBean bean) {
        DialogHelper.successSnackbar(getView(), "申请已通过");
        pop();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.bt_pass_check_fragment) {
            params.status = 1;
            mPresenter.requestApplyCheck(params);//请求同意申请
        } else if (i == R.id.bt_refuse_check_fragment) {
            params.status = 2;
            mPresenter.requestApplyCheck(params);//请求拒绝申请
        }
    }
}
