package cn.itsite.amain.s1.about.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.utils.AppUtils;
import cn.itsite.amain.R;
import cn.itsite.amain.s1.App;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author: LiuJia on 2017/8/11 0011 09:29.
 * Email: liujia95me@126.com
 */

public class AboutUsFragment extends BaseFragment {
    public static final String TAG = AboutUsFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private TextView tvVersionName;

    public static AboutUsFragment newInstance() {
        return new AboutUsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        tvVersionName = ((TextView) view.findViewById(R.id.tv_version_name));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        initStateBar(toolbar);
        toolbarTitle.setText("关于我们");
        tvVersionName.setText("版本：" + AppUtils.getVersionName(App.mContext));
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }
}
