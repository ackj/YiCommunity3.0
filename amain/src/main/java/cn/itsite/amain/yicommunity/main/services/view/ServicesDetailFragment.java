package cn.itsite.amain.yicommunity.main.services.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.abase.common.UserHelper;
import cn.itsite.amain.yicommunity.entity.bean.ServiceDetailBean;
import cn.itsite.amain.yicommunity.event.EventRefreshRemarkList;
import cn.itsite.amain.yicommunity.main.publish.CommentActivity;
import cn.itsite.amain.yicommunity.main.services.contract.ServicesDetailContract;
import cn.itsite.amain.yicommunity.main.services.presenter.ServicesDetailPresenter;
import cn.itsite.acommon.view.PreviewActivity;
import cn.itsite.amain.yicommunity.web.WebActivity;

/**
 * Author: leguang on 2017/4/21 9:14.
 * Email: langmanleguang@qq.com
 * <p>
 * [上门服务列表]的View层。
 * 打开方式：AppStart-->首页-->社区服务列表-->Item。
 */
public class ServicesDetailFragment extends BaseFragment<ServicesDetailContract.Presenter> implements ServicesDetailContract.View, View.OnClickListener {
    public static final String TAG = ServicesDetailFragment.class.getSimpleName();
    public static final int SERVICES_DETAIL_REQUESTCODE = 1101;
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private TextView tvCost;
    private ImageView ivFirm;
    private TextView tvName;
    private TextView tvAge;
    private TextView tvScope;
    private TextView tvAddress;
    private TextView tvTime;
    private TextView tvContact;
    private LinearLayout llFirmPhoto;
    private ImageView ivCommodity;
    private TextView tvTitle;
    private TextView tvSubtitle;
    private TextView tvInfo;
    private RecyclerView rvScene;
    private TextView toolbarMenu;
    private RecyclerView rvRemark;
    private Button btAll;
    private TextView tvUserRemark;
    private ScrollView sv;
    private Params params = Params.getInstance();
    private String contactWay, firmName;
    private ServiceDetailSceneRVAdapter adapterScene;
    private ServicesDetailRemarkRVAdapter adapterRemark;
    private Button btCall;
    private TextView tvBusiness;

    /**
     * 该页的入口
     *
     * @param fid 请求详情接口需要的fid
     * @return
     */
    public static ServicesDetailFragment newInstance(String fid) {
        Bundle args = new Bundle();
        args.putString(Constants.SERVICE_FID, fid);
        ServicesDetailFragment fragment = new ServicesDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            params.fid = bundle.getString(Constants.SERVICE_FID);
            ALog.e("params.fid-->" + params.fid);
        }
    }

    @NonNull
    @Override
    protected ServicesDetailContract.Presenter createPresenter() {
        return new ServicesDetailPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_services_detail, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        tvCost = ((TextView) view.findViewById(R.id.tv_cost_services_detail_fragment));
        ivFirm = ((ImageView) view.findViewById(R.id.iv_firm_services_detail_fragment));
        tvName = ((TextView) view.findViewById(R.id.tv_name_services_detail_fragment));
        tvAge = ((TextView) view.findViewById(R.id.tv_age_services_detail_fragment));
        tvScope = ((TextView) view.findViewById(R.id.tv_scope_services_detail_fragment));
        tvAddress = ((TextView) view.findViewById(R.id.tv_address_services_detail_fragment));
        tvTime = ((TextView) view.findViewById(R.id.tv_time_services_detail_fragment));
        tvContact = ((TextView) view.findViewById(R.id.tv_contact_services_detail_fragment));
        llFirmPhoto = ((LinearLayout) view.findViewById(R.id.ll_firm_photo_services_detail_fragment));
        ivCommodity = ((ImageView) view.findViewById(R.id.iv_commodity_services_detail_fragment));
        tvTitle = ((TextView) view.findViewById(R.id.tv_title_services_detail_fragment));
        tvSubtitle = ((TextView) view.findViewById(R.id.tv_subtitle_services_detail_fragment));
        tvInfo = ((TextView) view.findViewById(R.id.tv_info_services_detail_fragment));
        rvScene = ((RecyclerView) view.findViewById(R.id.rv_scene_services_detail_fragment));
        toolbarMenu = ((TextView) view.findViewById(R.id.toolbar_menu));
        rvRemark = ((RecyclerView) view.findViewById(R.id.rv_remark_services_detail_fragment));
        btAll = ((Button) view.findViewById(R.id.bt_all_remark_services_detail_fragment));
        tvUserRemark = ((TextView) view.findViewById(R.id.tv_user_remark_services_detail_fragment));
        sv = ((ScrollView) view.findViewById(R.id.sv_services_detail_fragment));
        btCall = ((Button) view.findViewById(R.id.bt_call_services_detail_fragment));
        tvBusiness = ((TextView) view.findViewById(R.id.tv_business_license_services_detail_fragment));
        EventBus.getDefault().register(this);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        initListener();
    }

    private void initListener() {
        btCall.setOnClickListener(this);
        tvBusiness.setOnClickListener(this);
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("商品详情");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> pop());
        toolbar.setOnClickListener(v -> go2Top());
        toolbar.inflateMenu(R.menu.menu_services_detail);
        toolbar.setOnMenuItemClickListener(item -> {
            int i = item.getItemId();
            if (i == R.id.action_remark) {
                startForResult(RemarkFragment.newInstance(params.fid, firmName), 100);
            } else if (i == R.id.action_report) {
                Intent introductionIntent = new Intent(_mActivity, WebActivity.class);
                introductionIntent.putExtra(Constants.KEY_TITLE, "举报投诉");
                String link = String.format(ApiService.REPORT_SERVICE_URL, UserHelper.token, params.fid);
                introductionIntent.putExtra(Constants.KEY_LINK, link);
                _mActivity.startActivity(introductionIntent);
            }
            return true;
        });
    }

    private void initData() {
        mPresenter.requestServiceDetail(params);
        adapterScene = new ServiceDetailSceneRVAdapter();
        adapterRemark = new ServicesDetailRemarkRVAdapter();
        //————————————————————用户点评RecyclerView——————————————————
        rvRemark.setLayoutManager(new LinearLayoutManager(_mActivity) {

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        adapterRemark.setOnItemChildClickListener((adapter, view, position) -> {
            ServiceDetailBean.DataBean.CommorityCommentBean bean = adapterRemark.getData().get(position);
            int i = view.getId();
            if (i == R.id.iv_head_item_rv_remark) {
                Intent preIntent = new Intent(_mActivity, PreviewActivity.class);
                preIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                ArrayList<String> picsUrls = new ArrayList<>();
                picsUrls.add(bean.getMember().getAvator());
                bundle.putStringArrayList("picsList", picsUrls);
                preIntent.putExtra("pics", bundle);
                preIntent.putExtra("position", 0);
                _mActivity.startActivity(preIntent);

            } else if (i == R.id.ll_comment_item_rv_remark || i == R.id.tv_comment_count_item_rv_remark) {
                Intent intent = new Intent(_mActivity, CommentActivity.class);
                intent.putExtra(Constants.KEY_FID, bean.getFid());
                intent.putExtra(Constants.KEY_TYPE, Constants.TYPE_REMARK);
                _mActivity.startActivityForResult(intent, SERVICES_DETAIL_REQUESTCODE);
            }
        });
        rvRemark.setAdapter(adapterRemark);
    }

    @Override
    public void error(String errorMessage) {
        super.error(errorMessage);
        DialogHelper.warningSnackbar(getView(), errorMessage);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 响应请求详情数据
     *
     * @param bean
     */
    @Override
    public void responseServiceDetail(ServiceDetailBean bean) {
        Glide.with(_mActivity)
                .load(bean.getData().getMerchantIconUrl())
                .apply(new RequestOptions().error(R.drawable.ic_default_img_120px)
                        .placeholder(R.drawable.ic_default_img_120px))
                .into(ivFirm);
        Glide.with(_mActivity)
                .load(bean.getData().getCommodityUrl())
                .apply(new RequestOptions().error(R.drawable.ic_default_img_120px)
                        .placeholder(R.drawable.ic_default_img_120px))
                .into(ivCommodity);

        contactWay = bean.getData().getContactWay();
        firmName = bean.getData().getMerchantName();

        tvName.setText(firmName);
        tvTitle.setText(bean.getData().getCommodityTitle());
        tvAge.setText("商家年龄：" + bean.getData().getMerchantAge() + "年");
        tvScope.setText("服务范畴：" + bean.getData().getServiceScopes());
        tvAddress.setText(bean.getData().getAddress());
        tvTime.setText("经营时间：" + bean.getData().getBusinessHours());
        tvContact.setText(Html.fromHtml("联系方式：<font color=red>" + contactWay.substring(0, 3) + "****" + contactWay.substring(7, 11) + "</font>"));

        tvSubtitle.setText(bean.getData().getCommodityDesc());

        tvCost.setText(bean.getData().getCommodityPrice());
        List<ServiceDetailBean.DataBean.MerchantSceneBean> sceneBeans = bean.getData().getMerchantScene();

        //——————————————商家实景——————————————————
        rvScene.setLayoutManager(new LinearLayoutManager(_mActivity, LinearLayoutManager.HORIZONTAL, false));
        rvScene.setAdapter(adapterScene);
        adapterScene.setNewData(sceneBeans);
        adapterScene.setOnItemClickListener((adapter1, view, position) -> {
            Intent intent = new Intent(_mActivity, PreviewActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle = new Bundle();
            ArrayList<String> picsUrls = new ArrayList<>();
            for (int i = 0; i < sceneBeans.size(); i++) {
                picsUrls.add(sceneBeans.get(i).getUrl());
            }
            bundle.putStringArrayList("picsList", picsUrls);
            intent.putExtra("pics", bundle);
            intent.putExtra("position", position);
            _mActivity.startActivity(intent);
        });

        //————————————————服务介绍————————————————————
        StringBuilder sb = new StringBuilder()
                .append("【服务优点】\n")
                .append(bean.getData().getCommodityMerit())
                .append("\n")
                .append("【服务流程】\n")
                .append(bean.getData().getCommodityServiceFlow())
                .append("\n")
                .append("【服务时长参考】\n")
                .append(bean.getData().getDuration())
                .append("\n")
                .append("【覆盖区域】\n")
                .append(bean.getData().getCoverageArea())
                .append("\n");
        tvInfo.setText(sb.toString());

        //——————————————用户点评——————————————————
        if (!bean.getData().getCommorityComment().isEmpty()) {
            tvUserRemark.setVisibility(View.VISIBLE);
            rvRemark.setVisibility(View.VISIBLE);
            btAll.setVisibility(View.VISIBLE);
            tvUserRemark.setText("用户点评（" + bean.getData().getCommorityComment().size() + "）");
            adapterRemark.setNewData(bean.getData().getCommorityComment());
            btAll.setOnClickListener(v -> {
                start(RemarkListFragment.newInstance(params.fid, bean.getData().getMerchantName()));
            });
        }
    }

    public void go2Web(String title, String link) {
        Intent intent = new Intent(_mActivity, WebActivity.class);
        intent.putExtra(Constants.KEY_TITLE, title);
        intent.putExtra(Constants.KEY_LINK, link);
        _mActivity.startActivity(intent);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RemarkFragment.RESULT_RECORD && data != null) {
            mPresenter.requestServiceDetail(params);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(EventRefreshRemarkList event) {
        if (mPresenter == null) {
            return;
        }
        mPresenter.requestServiceDetail(params);
    }

    public void go2Top() {
        sv.post(() -> sv.fullScroll(ScrollView.FOCUS_UP));//滑动到顶部，提高用户体验，方便用户点击头像登录。
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.bt_call_services_detail_fragment) {
            if (!TextUtils.isEmpty(contactWay)) {
                //跳系统的拨打电话
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contactWay)));
            }
        } else if (i == R.id.tv_business_license_services_detail_fragment) {
            go2Web("营业执照", ApiService.BUSINESS_LICENSE_URL + params.fid);
        }
    }
}
