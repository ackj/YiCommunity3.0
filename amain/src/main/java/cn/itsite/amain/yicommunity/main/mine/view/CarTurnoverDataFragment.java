package cn.itsite.amain.yicommunity.main.mine.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import java.util.ArrayList;
import java.util.List;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.entity.bean.AuthorizationGatewayBean;
import cn.itsite.amain.yicommunity.entity.bean.AuthorizationTitleBean;
import cn.itsite.statemanager.StateManager;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by liujia on 2018/5/23.
 * 车辆进出场数据
 */
public class CarTurnoverDataFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = CarTurnoverDataFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private PtrFrameLayout mPtrFrameLayout;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private TextView mToolbarMenu;
    private TextView mTvOperation;
    private CarTurnoverDataRVAdatper mAdapter;
    private boolean isEditModel;//编辑模式
    private StateManager mStateManager;

    public static CarTurnoverDataFragment newInstance(){
        return new CarTurnoverDataFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_turnover_data, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mPtrFrameLayout = view.findViewById(R.id.ptrFrameLayout);
        mToolbar = view.findViewById(R.id.toolbar);
        mToolbarTitle = view.findViewById(R.id.toolbar_title);
        mToolbarMenu = view.findViewById(R.id.toolbar_menu);
        mTvOperation = view.findViewById(R.id.tv_operation);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initStateManager();
        initData();
        initListener();
        mPtrFrameLayout.setEnabled(false);
    }

    private void initToolbar() {
        initStateBar(mToolbar);
        mToolbarTitle.setText("车辆进出场数据");
        mToolbarMenu.setText("编辑");
        mToolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        mToolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    private void initStateManager() {
        mStateManager = StateManager.builder(_mActivity)
                .setContent(mRecyclerView)
                .setEmptyView(R.layout.state_empty)
                .setEmptyImage(R.drawable.ic_nomessage_500px)
                .setEmptyText("您还没添加信息！")
                .setErrorOnClickListener(v -> mPtrFrameLayout.autoRefresh())
                .setEmptyOnClickListener(v -> mPtrFrameLayout.autoRefresh())
                .build();
    }

    private void initData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        List<MultiItemEntity> data = new ArrayList<>();
        for (int j = 0;j<3;j++){
            AuthorizationTitleBean title = new AuthorizationTitleBean();
            title.title = "标题";
            title.subTitle = "(子标题)";
            for (int i = 0;i<5;i++){
                AuthorizationGatewayBean gateway = new AuthorizationGatewayBean();
                gateway.name = "网关名称";
                gateway.open = i%2==1;
                title.addSubItem(gateway);
            }
            data.add(title);
        }
        mAdapter = new CarTurnoverDataRVAdatper(data);
        TextView footerReminder = (TextView) LayoutInflater.from(_mActivity).inflate(R.layout.item_footer_reminder,null);
        footerReminder.setText("*开启后车辆进出场数据将推送到智能家居网关");
        mAdapter.addFooterView(footerReminder);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initListener() {
        mTvOperation.setOnClickListener(this);
        mToolbarMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.tv_operation){
            if(isEditModel){
                //删除车辆接口
                
            }else{
                start(AddCarDataFragment.newInstance(null));
            }
        }else if(view.getId()==R.id.toolbar_menu){
            if(isEditModel){
                mAdapter.switchEditModel(false);
                mToolbarMenu.setText("编辑");
                mTvOperation.setText("添加");
            }else{
                mAdapter.switchEditModel(true);
                mToolbarMenu.setText("完成");
                mTvOperation.setText("删除车辆");
            }
            isEditModel=!isEditModel;
        }
    }
}
