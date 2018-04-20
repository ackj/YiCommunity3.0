package cn.itsite.amain.yicommunity.main.sociality.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.FloatingActionButton.OnVisibilityChangedListener;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.adialog.dialogfragment.SelectorDialogFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.CommunityBean;
import cn.itsite.amain.yicommunity.main.publish.view.PublishExchangeFragment;
import cn.itsite.amain.yicommunity.main.publish.view.PublishNeighbourFragment;
import cn.itsite.amain.yicommunity.main.sociality.contract.SocialityContract;
import cn.itsite.amain.yicommunity.main.sociality.presenter.SocialityPresenter;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author: LiuJia on 2017/5/11 0011 14:11.
 * Email: liujia95me@126.com
 * 闲置交换和左邻右里的父层Fragment
 */

public class SocialityFragment extends BaseFragment<SocialityContract.Presenter> implements SocialityContract.View {
    private static final String TAG = SocialityFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private FloatingActionButton fabPublish;
    private int type;
    private CommunityBean.DataBean.CommunityInfoListBean allCommunity = new CommunityBean.DataBean.CommunityInfoListBean();

    /**
     * SocialityFragment创建入口
     *
     * @param type 用于区分内容显示[左邻右里]还是[闲置交换]
     * @return
     */
    public static SocialityFragment newInstance(int type) {
        SocialityFragment fragment = new SocialityFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.KEY_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt(Constants.KEY_TYPE);
        }
    }

    @NonNull
    @Override
    protected SocialityContract.Presenter createPresenter() {
        return new SocialityPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sociality, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        fabPublish = ((FloatingActionButton) view.findViewById(R.id.fab_publish));
        if (type == SocialityListFragment.TYPE_EXCHANGE) {
            view = attachToSwipeBack(view);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadRootFragment(R.id.fl_container_fragment_fragment, SocialityListFragment.newInstance(type));
        initToolbar();
        initData();
    }

    private void initData() {
        allCommunity.setCommunityFid("");
        allCommunity.setCommunityName("所有社区");
        fabPublish.setOnClickListener(v -> {
            switch (type) {
                case SocialityListFragment.TYPE_EXCHANGE:
                    fabPublish.hide(new OnVisibilityChangedListener() {
                        @Override
                        public void onHidden(FloatingActionButton fab) {
                            start(PublishExchangeFragment.newInstance());
                        }
                    });
                    break;
                case SocialityListFragment.TYPE_NEIGHBOUR:
                    fabPublish.hide();
                    String[] arr = {"发布照片", "发布视频"};
                    new AlertDialog.Builder(_mActivity)
                            .setItems(arr, (dialog, which) -> {
                                //网络访问
                                dialog.dismiss();
                                ((SupportActivity) _mActivity).start(PublishNeighbourFragment.newInstance(which));
                            })
                            .setTitle("请选择")
                            .setPositiveButton("取消", (dialog, which) -> fabPublish.show())
                            .setOnCancelListener(dialog -> fabPublish.show())
                            .show();
                    break;
                default:
                    break;
            }
        });
    }

    private void initToolbar() {
        initStateBar(toolbar);
        switch (type) {
            case SocialityListFragment.TYPE_EXCHANGE:
                toolbarTitle.setText("闲置交换");
                toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
                toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
                break;
            case SocialityListFragment.TYPE_NEIGHBOUR:
                toolbarTitle.setText("左邻右里");
                break;
            default:
                break;
        }
        toolbar.setOnClickListener(v -> {
            if (getChildFragmentManager().getFragments() != null
                    && !getChildFragmentManager().getFragments().isEmpty()
                    && getChildFragmentManager().getFragments().get(0) instanceof SocialityListFragment) {
                ((SocialityListFragment) getChildFragmentManager()
                        .getFragments().get(0)).go2Top();
            }
        });

        toolbar.inflateMenu(R.menu.menu_sociality);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_switch) {
                mPresenter.requestCommunitys(Params.getInstance());
            }
            return true;
        });
    }

    public void go2TopAndRefresh() {
        if (getChildFragmentManager().getFragments() != null
                && !getChildFragmentManager().getFragments().isEmpty()
                && getChildFragmentManager().getFragments().get(0) instanceof SocialityListFragment) {
            ((SocialityListFragment) getChildFragmentManager()
                    .getFragments().get(0)).go2TopAndRefresh(null);
        }
    }

    @Override
    public void responseCommunitys(List<CommunityBean.DataBean.CommunityInfoListBean> datas) {
        datas.add(0, allCommunity);
        new SelectorDialogFragment()
                .setTitle("请选择要切换的社区")
                .setItemLayoutId(R.layout.item_rv_simple_selector)
                .setData(datas)
                .setOnItemConvertListener((holder, position, dialog) -> {
                    CommunityBean.DataBean.CommunityInfoListBean bean = datas.get(position);
                    holder.setText(R.id.tv_item_rv_simple_selector, bean.getCommunityName());
                })
                .setOnItemClickListener((view, baseViewHolder, position, dialog) -> {
                    dialog.dismiss();
                    CommunityBean.DataBean.CommunityInfoListBean bean = datas.get(position);
                    if (getChildFragmentManager().getFragments() != null
                            && !getChildFragmentManager().getFragments().isEmpty()
                            && getChildFragmentManager().getFragments().get(0) instanceof SocialityListFragment) {
                        ((SocialityListFragment) getChildFragmentManager()
                                .getFragments().get(0)).refresh(bean.getCommunityFid());
                    }
                })
                .setAnimStyle(R.style.SlideAnimation)
                .setGravity(Gravity.BOTTOM)
                .show(getChildFragmentManager());
    }

    @Override
    public void onResume() {
        super.onResume();
        fabPublish.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        fabPublish.hide();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        fabPublish.show();
    }
}
