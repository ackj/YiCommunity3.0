package cn.itsite.amain.yicommunity.main.house.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.App;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.BaseBean;
import cn.itsite.amain.yicommunity.entity.bean.HouseRightsBean;
import cn.itsite.amain.yicommunity.event.EventCommunity;
import cn.itsite.amain.yicommunity.main.house.contract.MemberPermissionContract;
import cn.itsite.amain.yicommunity.main.house.presenter.MemberPermissionPresenter;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author: LiuJia on 2017/4/20 9:26.
 * Email: liujia95me@126.com
 * [房屋成员]的View层。
 */
public class MemberPermissionFragment extends BaseFragment<MemberPermissionContract.Presenter> implements MemberPermissionContract.View {
    public static final String TAG = MemberPermissionFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private RecyclerView rvPermission;
    private TextView toolbarMenu;
    private PtrFrameLayout ptrFrameLayout;
    private int memberPosition = 0;
    private int permissionPosition;
    private MemberRVAdapter memberAdapter;
    private PermissionRVAdapter permissionAdapter;
    private View footerView;
    private RecyclerView rvMember;
    private HouseRightsBean mHouseRights;
    private Params params = Params.getInstance();
    private String title;

    public static MemberPermissionFragment newInstance(String fid, String address) {
        Bundle args = new Bundle();
        args.putString(Constants.KEY_FID, fid);
        args.putString(Constants.KEY_ADDRESS, address);
        MemberPermissionFragment fragment = new MemberPermissionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    protected MemberPermissionContract.Presenter createPresenter() {
        return new MemberPermissionPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            params.fid = args.getString(Constants.KEY_FID);
            title = args.getString(Constants.KEY_ADDRESS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_house_rights, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        rvPermission = ((RecyclerView) view.findViewById(R.id.rv_permission));
        toolbarMenu = ((TextView) view.findViewById(R.id.toolbar_menu));
        ptrFrameLayout = ((PtrFrameLayout) view.findViewById(R.id.ptrFrameLayout));
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initPtrFrameLayout(ptrFrameLayout, rvPermission);
        initData();
        initListener();
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText(title);
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    @Override
    public void onRefresh() {
        mPresenter.requestRights(params);
    }

    private void initData() {
        //房屋成员
        TextView tvMember = new TextView(_mActivity);
        tvMember.setBackgroundColor(ContextCompat.getColor(App.mContext, R.color.base_grey));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvMember.setPadding(30, 30, 0, 14);

        tvMember.setLayoutParams(lp);
        tvMember.setText("房屋成员");
        tvMember.setTextColor(Color.parseColor("#999999"));

        //成员头像网格表
        rvMember = new RecyclerView(_mActivity);
        rvMember.setBackgroundColor(ContextCompat.getColor(App.mContext, R.color.base_grey));
        rvMember.setLayoutParams(new GridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        rvMember.setLayoutManager(new GridLayoutManager(_mActivity, 5));
        memberAdapter = new MemberRVAdapter();
        rvMember.setAdapter(memberAdapter);

        //权限列表
        rvPermission.setLayoutManager(new LinearLayoutManager(_mActivity));
        permissionAdapter = new PermissionRVAdapter();
        permissionAdapter.addHeaderView(tvMember);
        permissionAdapter.addHeaderView(rvMember);

        //删除成员footer
        footerView = LayoutInflater.from(App.mContext).inflate(R.layout.item_remove_member, null);

        rvPermission.setAdapter(permissionAdapter);
    }

    private void initListener() {
        //每选中一次，重新赋值选中的那个成员对象
        memberAdapter.setOnItemClickListener((adapter, view, position) -> {
            permissionAdapter.setNewData(mHouseRights.getData().get(position).getAuthority());
            adapter.getViewByPosition(rvMember, memberPosition, R.id.iv_avatar).setBackgroundResource(0);
            view.findViewById(R.id.iv_avatar).setBackgroundResource(R.drawable.shape_stroke_round_red_border);
            memberPosition = position;
            ALog.e("prePosition::" + memberPosition);

            if (position == 0) {
                permissionAdapter.removeFooterView(footerView);
            } else {
                permissionAdapter.removeAllFooterView();
                permissionAdapter.addFooterView(footerView);
            }
        });

        footerView.setOnClickListener(v -> {
            ALog.e("prePosition::" + memberPosition);
            HouseRightsBean.DataBean.MemberBean member = memberAdapter.getData().get(memberPosition).getMember();
            params.mfid = member.getFid();
            params.identityType = member.getIdentityType();
            mPresenter.requestDeleteMember(params);//请求删除成员
            showLoading();
        });

        permissionAdapter.setOnItemClickListener((adapter, view, position) -> {
            permissionPosition = position;
            SwitchButton switchButton = (SwitchButton) view.findViewById(R.id.switch_button);

            params.mfid = memberAdapter.getData().get(memberPosition).getMember().getFid();
            params.rfid = memberAdapter.getData().get(memberPosition).getMember().getRfid();
            params.picode = permissionAdapter.getData().get(position).getCode();

            ALog.e("switchButton.isChecked()::" + switchButton.isChecked());

            params.status = switchButton.isChecked() ? 0 : 1;

            if (memberAdapter.getData().size() == 1) {
                params.url = ApiService.UPDATE_PERMISSION_MYSELF;
            } else {
                if (memberAdapter.getData().get(memberPosition).getMember().getIdentityType() == 1) {
                    params.url = ApiService.UPDATE_PERMISSION_MYSELF;
                } else {
                    params.url = ApiService.UPDATE_PERMISSION_OTHER;
                }
            }

            mPresenter.requestUpdateRights(params);//请求修改成员信息
            showLoading();

        });
    }

    /**
     * 响应请求成员列表
     *
     * @param mHouseRights
     */
    @Override
    public void responseRights(HouseRightsBean mHouseRights) {
        ptrFrameLayout.refreshComplete();
        this.mHouseRights = mHouseRights;
        memberAdapter.setNewData(mHouseRights.getData());
        permissionAdapter.setNewData(mHouseRights.getData().get(memberPosition).getAuthority());
    }

    /**
     * 响应请求更改成员信息。
     *
     * @param mBaseBean
     */
    @Override
    public void responseUpdateRights(BaseBean mBaseBean) {
        dismissLoading();
        permissionAdapter.getData().get(permissionPosition).setStatus(params.status);
        permissionAdapter.notifyDataSetChanged();
        DialogHelper.successSnackbar(getView(), mBaseBean.getOther().getMessage());
    }

    /**
     * 响应请求删除成员。
     *
     * @param mBaseBean
     */
    @Override
    public void responseDeleteMember(BaseBean mBaseBean) {
        dismissLoading();
        DialogHelper.successSnackbar(getView(), mBaseBean.getOther().getMessage());
        memberAdapter.remove(memberPosition);
        permissionAdapter.setNewData(null);
        if (memberAdapter.getData().size() == 1) {
            permissionAdapter.removeFooterView(footerView);
        }
        memberPosition = 0;
    }

    @Override
    public void error(String errorMessage) {
        dismissLoading();
        permissionAdapter.notifyDataSetChanged();
        ptrFrameLayout.refreshComplete();
        DialogHelper.errorSnackbar(getView(), errorMessage);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventCommunity event) {
        ptrFrameLayout.autoRefresh();
    }
}
