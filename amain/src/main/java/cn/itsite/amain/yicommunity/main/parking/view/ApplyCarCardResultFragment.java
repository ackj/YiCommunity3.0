package cn.itsite.amain.yicommunity.main.parking.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.Constants;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 申请结果展示页。
 */
public class ApplyCarCardResultFragment extends BaseFragment {
    public static final String TAG = ApplyCarCardResultFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private ImageView ivIcon;
    private TextView tvDes;
    private Button btBack;
    private String title;
    private String des;

    public static ApplyCarCardResultFragment newInstance(Bundle bundle) {
        ApplyCarCardResultFragment fragment = new ApplyCarCardResultFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            title = bundle.getString(Constants.KEY_TITLE);
            des = bundle.getString(Constants.KEY_DES);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apply_car_card_result, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        ivIcon = ((ImageView) view.findViewById(R.id.iv_icon_apply_result_fargment));
        tvDes = ((TextView) view.findViewById(R.id.tv_des_apply_result_fargment));
        btBack = ((Button) view.findViewById(R.id.bt_back_park_pay_result_fragment));
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
        toolbarTitle.setText(title);
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    private void initData() {
        tvDes.setText(des);
    }

    private void initListener() {
        btBack.setOnClickListener(v -> {
            ((SupportActivity) _mActivity).onBackPressedSupport();
        });
    }
}
