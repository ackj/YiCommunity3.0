package cn.itsite.amain.yicommunity.main.message.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.RepairDetailBean;
import cn.itsite.amain.yicommunity.main.message.contract.RepairDetailContract;
import cn.itsite.amain.yicommunity.main.message.presenter.RepairDetailPresenter;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author: LiuJia on 2017/5/18 0018 17:10.
 * Email: liujia95me@126.com
 * [报修详情]的View层。
 * 打开方式：StartApp-->管家-->物业报修-->点击一个Item
 */

public class RepairDetailFragment extends BaseFragment<RepairDetailContract.Presenter> implements RepairDetailContract.View {
    private static final String TAG = RepairDetailFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private TextView tvRepairType;
    private TextView tvRepairTime;
    private TextView tvName;
    private TextView tvContact;
    private TextView tvDesc;
    private RecyclerView recyclerviewPics;
    private RecyclerView recyclerviewReply;
    private LinearLayout llPicsLayout;
    private TextView tvCommunityName;
    private Params params = Params.getInstance();

    /**
     * RepairDetailFragment的创建入口
     *
     * @param fid 物业报修某个item的fid
     * @return
     */
    public static RepairDetailFragment newInstance(String fid) {
        RepairDetailFragment detailFragment = new RepairDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_FID, fid);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @NonNull
    @Override
    protected RepairDetailContract.Presenter createPresenter() {
        return new RepairDetailPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            params.fid = bundle.getString(Constants.KEY_FID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repair_detail, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        tvRepairType = ((TextView) view.findViewById(R.id.tv_repair_type_repair_detail_fragment));
        tvRepairTime = ((TextView) view.findViewById(R.id.tv_repair_time_repair_detail_fragment));
        tvName = ((TextView) view.findViewById(R.id.tv_name_repair_detail_fragment));
        tvContact = ((TextView) view.findViewById(R.id.tv_contact_repair_detail_fragment));
        tvDesc = ((TextView) view.findViewById(R.id.tv_desc_repair_detail_fragment));
        recyclerviewPics = ((RecyclerView) view.findViewById(R.id.recyclerview_pics_repair_detail_fragment));
        recyclerviewReply = ((RecyclerView) view.findViewById(R.id.recyclerview_reply_repair_detail_fragment));
        llPicsLayout = ((LinearLayout) view.findViewById(R.id.ll_pics_layout_repair_detail_fragment));
        tvCommunityName = ((TextView) view.findViewById(R.id.tv_community_name));
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
        toolbarTitle.setText("报修详情");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    private void initData() {
        mPresenter.requestRepairDetail(params); //请求报修详情
    }

    @Override
    public void error(String errorMessage) {
        super.error(errorMessage);
        DialogHelper.warningSnackbar(getView(), errorMessage);
    }

    /**
     * 响应请求报修详情
     *
     * @param repairDetailBean 报修详情的bean
     */
    @Override
    public void responseRepairDetail(RepairDetailBean repairDetailBean) {
        RepairDetailBean.DataBean bean = repairDetailBean.getData();
        tvCommunityName.setText(bean.getHouse());
        tvName.setText(bean.getName());
        tvContact.setText(bean.getContact());
        tvDesc.setText(bean.getDes());
        tvRepairTime.setText(bean.getCtime());
        tvRepairType.setText(bean.getType());

        if (bean.getPics().size() > 0) {
            llPicsLayout.setVisibility(View.VISIBLE);
            recyclerviewPics.setLayoutManager(new GridLayoutManager(_mActivity, 3) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            recyclerviewPics.setAdapter(new RepairDetailPicsRVAdapter(bean.getPics()));
        } else {
            llPicsLayout.setVisibility(View.GONE);
        }

        if (bean.getReplys().size() > 0) {
            recyclerviewReply.setLayoutManager(new LinearLayoutManager(_mActivity) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            recyclerviewReply.setAdapter(new RepairDetailReplyRVAdapter(bean.getReplys()));
        }
    }
}
