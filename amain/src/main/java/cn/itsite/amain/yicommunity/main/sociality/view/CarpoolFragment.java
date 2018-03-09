package cn.itsite.amain.yicommunity.main.sociality.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.OnClick;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.main.publish.view.PublishCarpoolFragment;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author: LiuJia on 2017/5/16 0016 17:15.
 * Email: liujia95me@126.com
 * [拼车服务]的View层。
 * 打开方式：StartApp-->社区-->拼车服务
 */

public class CarpoolFragment extends BaseFragment {
    public static final String TAG = CarpoolFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private TabLayout tablayout;
    private ViewPager viewpager;
    private TextView toolbarMenu;

    public static CarpoolFragment newInstance() {
        return new CarpoolFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tablayout_viewpager, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        tablayout = ((TabLayout) view.findViewById(R.id.tablayout));
        viewpager = ((ViewPager) view.findViewById(R.id.viewpager));
        toolbarMenu = ((TextView) view.findViewById(R.id.toolbar_menu));
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initView();
    }

    private void initToolbar() {
        toolbarMenu.setOnClickListener(v -> {
            start(PublishCarpoolFragment.newInstance());
        });
        initStateBar(toolbar);
        toolbarTitle.setText("拼车服务");
        toolbarMenu.setText("发布");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
        toolbar.setOnClickListener(v -> {
            if (getChildFragmentManager().getFragments() != null
                    && !getChildFragmentManager().getFragments().isEmpty()
                    && getChildFragmentManager().getFragments().get(0) instanceof SocialityListFragment) {

                for (Fragment fragment : getChildFragmentManager().getFragments()) {
                    ((SocialityListFragment) fragment).go2Top();
                }
            }
        });
    }

    private void initView() {
        viewpager.setAdapter(new CarpoolVPAdapter(getChildFragmentManager()));
        tablayout.setupWithViewPager(viewpager);
    }
}
