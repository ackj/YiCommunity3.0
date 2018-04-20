package cn.itsite.amain.yicommunity.main.mypublish.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.main.publish.view.PublishCarpoolFragment;
import cn.itsite.amain.yicommunity.main.publish.view.PublishExchangeFragment;
import cn.itsite.amain.yicommunity.main.publish.view.PublishNeighbourFragment;
import cn.itsite.amain.yicommunity.main.sociality.view.SocialityListFragment;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author: LiuJia on 2017/4/21 18:56.
 * Email: liujia95me@126.com
 * [我的发布]的View层。
 * 打开方式：StartApp-->我的-->我的发布
 */
public class MyPublishFragment extends BaseFragment {
    public static final String TAG = MyPublishFragment.class.getSimpleName();
    TextView toolbarTitle;
    TextView toolbarMenu;
    Toolbar toolbar;
    TabLayout tablayout;
    ViewPager viewpager;
    private int currentPostion = 0;

    public static MyPublishFragment newInstance() {
        return new MyPublishFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tablayout_viewpager, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbarMenu = ((TextView) view.findViewById(R.id.toolbar_menu));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        tablayout = ((TabLayout) view.findViewById(R.id.tablayout));
        viewpager = ((ViewPager) view.findViewById(R.id.viewpager));
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initView();
        initListener();
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("我的发布");
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
        toolbarMenu.setText("发布");
    }

    private void initView() {
        viewpager.setAdapter(new MyPublishVPAdapter(getChildFragmentManager()));
        tablayout.setupWithViewPager(viewpager);
        viewpager.setOffscreenPageLimit(3);
    }

    private void initListener() {
        toolbarMenu.setOnClickListener(v -> {
            switch (currentPostion) {
                case 0:
                    ((SupportActivity) _mActivity).start(PublishExchangeFragment.newInstance());
                    break;
                case 1:
                    ((SupportActivity) _mActivity).start(PublishCarpoolFragment.newInstance());
                    break;
                case 2:
                    String[] arr = {"发布照片", "发布视频"};
                    new AlertDialog.Builder(_mActivity)
                            .setItems(arr, (dialog, which) -> {
                                //网络访问
                                dialog.dismiss();
                                ((SupportActivity) _mActivity).start(PublishNeighbourFragment.newInstance(which));
                            })
                            .setTitle("请选择")
                            .setPositiveButton("取消", null)
                            .show();
                    break;
                default:
                    break;
            }
        });
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPostion = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
