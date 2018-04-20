package cn.itsite.amain.yicommunity.main.message.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.Constants;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author: LiuJia on 2017/5/19 0019 17:54.
 * Email: liujia95me@126.com
 * 申请结果的View层。
 */

public class ApplyResultFragment extends BaseFragment {
    public static final String TAG = ApplyResultFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private ImageView ivImage;
    private TextView tvDesc;
    private String des;
    private String title;

    /**
     * ApplyResultFragment的创建入口
     *
     * @param title toolbar的标题
     * @param des   结果的描述信息
     * @return
     */
    public static ApplyResultFragment newInstance(String title, String des) {
        ApplyResultFragment fragment = new ApplyResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_TITLE, title);
        bundle.putString(Constants.KEY_DES, des);
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apply_result, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        ivImage = ((ImageView) view.findViewById(R.id.iv_image_apply_result_fargment));
        tvDesc = ((TextView) view.findViewById(R.id.tv_desc_apply_result_fargment));
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
        toolbarTitle.setText(title);
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    private void initData() {
        tvDesc.setText(des);
        if (des.contains("通过")) {
            ivImage.setImageResource(R.drawable.ic_apply_pass_200px);
        } else if (des.contains("来电")) {
            ivImage.setImageResource(R.drawable.ic_photo_200px);
        } else {
            ivImage.setImageResource(R.drawable.ic_apply_refuse_200px);
        }
    }
}
