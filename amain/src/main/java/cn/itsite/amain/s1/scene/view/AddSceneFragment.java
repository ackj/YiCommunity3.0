package cn.itsite.amain.s1.scene.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.mvp.view.base.Decoration;
import cn.itsite.abase.utils.KeyBoardUtils;
import cn.itsite.adialog.dialogfragment.SelectorDialogFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.s1.App;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.entity.bean.BaseBean;
import cn.itsite.amain.s1.entity.bean.CommandBean;
import cn.itsite.amain.s1.entity.bean.DeviceListBean;
import cn.itsite.amain.s1.event.EventRefreshSceneList;
import cn.itsite.amain.s1.scene.contract.AddSceneContract;
import cn.itsite.amain.s1.scene.presenter.AddScenePresenter;
import cn.itsite.amain.yicommunity.widget.PtrHTFrameLayout;
import cn.itsite.statemanager.StateManager;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 场景模块。
 */

public class AddSceneFragment extends BaseFragment<AddSceneContract.Presenter> implements AddSceneContract.View, View.OnClickListener {
    public static final String TAG = AddSceneFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private TextView toolbarMenu;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private PtrHTFrameLayout ptrFrameLayout;
    private EditText etName;
    private LinearLayout llAddDevice;
    private Params params = Params.getInstance();
    private AddSceneRVAdapter adapter;
    private StateManager mStateManager;
    private List<CommandBean> commandList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_scene, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbarMenu = ((TextView) view.findViewById(R.id.toolbar_menu));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        recyclerView = ((RecyclerView) view.findViewById(R.id.recyclerView));
        ptrFrameLayout = ((PtrHTFrameLayout) view.findViewById(R.id.ptrFrameLayout));
        etName = ((EditText) view.findViewById(R.id.et_name_add_scene_fragment));
        llAddDevice = ((LinearLayout) view.findViewById(R.id.ll_add_device_add_scene_fragment));
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        initStateManager();
        initListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        KeyBoardUtils.hideKeybord(etName, App.mContext);
    }

    public static AddSceneFragment newInstance() {
        return new AddSceneFragment();
    }

    @NonNull
    @Override
    protected AddSceneContract.Presenter createPresenter() {
        return new AddScenePresenter(this);
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("场景编辑");
        toolbarMenu.setText("确定");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    private void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        adapter = new AddSceneRVAdapter();
        adapter.setEnableLoadMore(true);
        recyclerView.addItemDecoration(new Decoration(_mActivity, Decoration.VERTICAL_LIST));
        recyclerView.setAdapter(adapter);
    }

    private void initStateManager() {
        mStateManager = StateManager.builder(_mActivity)
                .setContent(recyclerView)
                .setEmptyView(R.layout.layout_state_empty)
                .setEmptyText("还没有设备，请点击添加设备！")
                .setEmptyOnClickListener(v -> startForResult(DeviceListFragment.newInstance(), SupportFragment.RESULT_OK))
                .setConvertListener((holder, stateLayout) ->
                        holder.setOnClickListener(R.id.bt_empty_state,
                                v -> startForResult(DeviceListFragment.newInstance(), SupportFragment.RESULT_OK))
                                .setText(R.id.bt_empty_state, "点击添加"))
                .build();
        mStateManager.showEmpty();
    }

    private void initListener() {
        llAddDevice.setOnClickListener(this);
        toolbarMenu.setOnClickListener(this);

        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            int viewType = adapter1.getItemViewType(position);
            switch (viewType) {
                case AddSceneRVAdapter.TYPE_SWITCH:
                    int i = view.getId();
                    if (i == R.id.tv_node_item_rv_add_scene_fragment) {
                        showNodeSelecotr(position, ((TextView) view));
                    } else if (i == R.id.tv_action_item_rv_add_scene_fragment) {
                        showActionSelecotr(position, ((TextView) view));
                    } else if (i == R.id.iv_delete_item_rv_add_scene_fragment) {
                        commandList.remove(position);
                        adapter.remove(position);
                        if (adapter.getData().isEmpty()) {
                            mStateManager.showEmpty();
                        }
                    }
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == SupportFragment.RESULT_OK && data != null) {
            ArrayList<DeviceListBean.DataBean.SubDevicesBean> selector = data.getParcelableArrayList(Constants.KEY_SELECTOR);
            if (selector != null) {
                for (DeviceListBean.DataBean.SubDevicesBean subDevice : selector) {
                    commandList.add(new CommandBean(subDevice.getIndex(), 0, 1));
                }
            }
            if (adapter.getData().isEmpty()) {
                adapter.getData().addAll(selector);
                adapter.setNewData(adapter.getData());
            } else {
                adapter.addData(selector);
            }
            mStateManager.showContent();
        }
    }

    private void showActionSelecotr(int position, TextView textView) {
        List<String> list = new ArrayList<>();
        list.add(0, "关闭");
        list.add(1, "打开");
        new SelectorDialogFragment()
                .setTitle("请选择开关")
                .setItemLayoutId(R.layout.item_rv_simple_selector)
                .setData(list)
                .setOnItemConvertListener((holder, which, dialog) ->
                        holder.setText(R.id.tv_item_rv_simple_selector, list.get(which)))
                .setOnItemClickListener((view, baseViewHolder, which, dialog) -> {
                    dialog.dismiss();
                    commandList.get(position).setAction1(which);
                    textView.setText(which == 1 ? "打开" : "关闭");
                })
                .setAnimStyle(R.style.SlideAnimation)
                .setGravity(Gravity.BOTTOM)
                .show(getChildFragmentManager());
    }

    private void showNodeSelecotr(int position, TextView textView) {
        List<String> list = new ArrayList<>();
        int node = adapter.getData().get(position).getExtInfo().getNode();
        for (int i = 0; i < node; i++) {
            list.add("第　" + (i + 1) + "　路");
        }

        new SelectorDialogFragment()
                .setTitle("请选择")
                .setItemLayoutId(R.layout.item_rv_simple_selector)
                .setData(list)
                .setOnItemConvertListener((holder, which, dialog) ->
                        holder.setText(R.id.tv_item_rv_simple_selector, list.get(which)))
                .setOnItemClickListener((view, baseViewHolder, which, dialog) -> {
                    dialog.dismiss();
                    commandList.get(position).setNodeId(which);
                    textView.setText("第　" + (which + 1) + "　路");
                })
                .setAnimStyle(R.style.SlideAnimation)
                .setGravity(Gravity.BOTTOM)
                .show(getChildFragmentManager());
    }

    @Override
    public void responseAddScene(BaseBean bean) {
        DialogHelper.successSnackbar(getView(), bean.getOther().getMessage());
        EventBus.getDefault().post(new EventRefreshSceneList());
        pop();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.toolbar_menu) {
            params.name = etName.getText().toString().trim();
            if (TextUtils.isEmpty(params.name)) {
                DialogHelper.warningSnackbar(getView(), "场景名称不能为空！");
                return;
            }
            if (adapter.getData().isEmpty()) {
                DialogHelper.warningSnackbar(getView(), "设备不能为空！");
                return;
            }
            params.paramJson = new Gson().toJson(commandList);
            ALog.e("params.paramJson-->" + params.paramJson);
            mPresenter.requestAddScene(params);
        } else if (i == R.id.ll_add_device_add_scene_fragment) {
            KeyBoardUtils.hideKeybord(etName, App.mContext);
            startForResult(DeviceListFragment.newInstance(), SupportFragment.RESULT_OK);
        }
    }
}
