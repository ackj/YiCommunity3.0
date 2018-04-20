package cn.itsite.amain.s1.room.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.libhttp.entity.LoginResult;
import com.libhttp.subscribers.SubscriberListener;
import com.p2p.core.P2PHandler;
import com.p2p.core.P2PSpecial.HttpErrorCode;
import com.p2p.core.P2PSpecial.HttpSend;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.utils.ToastUtils;
import cn.itsite.adialog.dialogfragment.SelectorDialogFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.s1.camera.CameraListFragment;
import cn.itsite.amain.s1.camera.CameraPlay2Activity;
import cn.itsite.amain.s1.camera.P2PListener;
import cn.itsite.amain.s1.camera.SettingListener;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.entity.bean.BaseBean;
import cn.itsite.amain.s1.entity.bean.DeviceListBean;
import cn.itsite.amain.s1.entity.bean.RoomsBean;
import cn.itsite.amain.s1.event.EventAddDevice;
import cn.itsite.amain.s1.event.EventDeviceChanged;
import cn.itsite.amain.s1.event.EventSelectedDeviceType;
import cn.itsite.amain.s1.event.EventSwitchHost;
import cn.itsite.amain.s1.room.contract.RoomDeviceListContract;
import cn.itsite.amain.s1.room.presenter.RoomDeviceListPresenter;
import cn.itsite.amain.yicommunity.widget.PtrHTFrameLayout;
import me.yokeyword.fragmentation.SupportActivity;

import static android.content.Context.MODE_PRIVATE;

/**
 * Author： Administrator on 2017/8/18 0018.
 * Email： liujia95me@126.com
 */
public class RoomDeviceListFragment extends BaseFragment<RoomDeviceListContract.Presenter> implements RoomDeviceListContract.View {
    public static final String TAG = RoomDeviceListFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private PtrHTFrameLayout ptrFrameLayout;
    private TextView toolbarMenu;
    private ImageView ivRoom;
    private ImageView ivHeader;
    private Params params = Params.getInstance();
    private RoomsBean.DataBean.RoomListBean selectRoom;
    private boolean isFirst = true;//是否是第一次进来
    private ImageView ivCamera;
    private DeviceGridRVAdapter adapter;
    private DeviceListBean.DataBean.SubDevicesBean addIconDevice;
    private String[] cameraArr = {"设置", "删除"};

    public static RoomDeviceListFragment newInstance() {
        return new RoomDeviceListFragment();
    }

    @NonNull
    @Override
    protected RoomDeviceListContract.Presenter createPresenter() {
        return new RoomDeviceListPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        recyclerView = ((RecyclerView) view.findViewById(R.id.recyclerView));
        ptrFrameLayout = ((PtrHTFrameLayout) view.findViewById(R.id.ptrFrameLayout));
        toolbarMenu = ((TextView) view.findViewById(R.id.toolbar_menu));
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        initListener();
        initPtrFrameLayout(ptrFrameLayout, recyclerView);
    }

    @Override
    public void onRefresh() {
        params.page = 1;
        params.category = Constants.DEVICE_CTRL;
        mPresenter.requestDeviceList(params);
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("大厅");
        toolbarMenu.setText("切换");
        toolbarMenu.setOnClickListener(v -> mPresenter.requestHouseList(params));
    }

    private void initData() {
        recyclerView.setLayoutManager(new GridLayoutManager(_mActivity, 4));
        adapter = new DeviceGridRVAdapter();
        View viewHeader = LayoutInflater.from(_mActivity).inflate(R.layout.layout_room_header, null);
        ivRoom = (ImageView) viewHeader.findViewById(R.id.iv_room);
        ivCamera = (ImageView) viewHeader.findViewById(R.id.iv_camera);
        ivHeader = new ImageView(_mActivity);
        ivHeader.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(_mActivity)
                .load(R.drawable.room_cesuo_1242px_745px)
                .into(ivRoom);
        adapter.setHeaderView(viewHeader);
        recyclerView.setAdapter(adapter);
        mPresenter.requestHouseList(params);
        addIconDevice = new DeviceListBean.DataBean.SubDevicesBean();
        addIconDevice.setIcon("add_icon");
        addIconDevice.setName("添加控制器");
        adapter.addData(addIconDevice);
    }

    private void selectRoom(RoomsBean.DataBean.RoomListBean bean) {
        selectRoom = bean;
        ALog.e(TAG, "selectRoom--->:" + selectRoom);
        params.roomId = selectRoom.getIndex();
        params.category = Constants.DEVICE_CTRL;
        //请求设备
        mPresenter.requestDeviceList(params);
        int resId = R.drawable.room_room_1242px_745px;
        switch (bean.getName()) {
            case "大厅":
                resId = R.drawable.room_dating_1242px_745px;
                break;
            case "厨房":
                resId = R.drawable.room_chufang_1242px_745px;
                break;
            case "卧室":
                resId = R.drawable.room_room_1242px_745px;
                break;
            case "厕所":
            case "浴室":
                resId = R.drawable.room_cesuo_1242px_745px;
                break;
            default:
        }
        toolbarTitle.setText(bean.getName());
        Glide.with(_mActivity)
                .load(resId)
                .into(ivRoom);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventAddDevice(EventAddDevice event) {
        ((SupportActivity) _mActivity).start(AddDeviceFragment.newInstance(null, selectRoom));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSelectedDeviceType(EventSelectedDeviceType event) {
//        new AlertDialog.Builder(_mActivity)
//                .setTitle("学习中...")
//                .setMessage("设备是否收到了正确的反馈？")
//                .setNegativeButton("否", null)
//                .setPositiveButton("是", (dialog, which) -> {
//                    params.status = 1;
//                    mPresenter.requestNewDeviceConfirm(params);
//                }).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeDataEvent(EventDeviceChanged event) {
        onRefresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSwitchHost(EventSwitchHost event) {
        params.page = 1;
        params.roomFid = "0";
        params.category = Constants.DEVICE_CTRL;
        mPresenter.requestDeviceList(params);
    }

    private void initListener() {
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            DeviceListBean.DataBean.SubDevicesBean bean = adapter.getItem(position);
            //点击最后一个跳添加页面
            if (position == adapter.getData().size() - 1) {
                if (selectRoom == null) {
                    DialogHelper.warningSnackbar(getView(), "请选择房间");
                    return;
                }
                ((SupportActivity) _mActivity).start(DeviceTypeFragment.newInstance(selectRoom.getFid()));
            } else if ("camera01".equals(bean.getDeviceType())) {
                Intent intent = new Intent(_mActivity, CameraPlay2Activity.class);
                intent.putExtra("OperateBean", (Serializable) bean);
                _mActivity.startActivity(intent);
            } else {
                ((SupportActivity) _mActivity).start(DeviceOnOffFragment.newInstance(bean, selectRoom));
            }
        });

        adapter.setOnItemLongClickListener((adapter1, view, position) -> {
            DeviceListBean.DataBean.SubDevicesBean bean = adapter.getItem(position);
            if ("camera01".equals(bean.getDeviceType())) {
                new AlertDialog.Builder(_mActivity)
                        .setItems(cameraArr, (dialog, which) -> {
                            if (which == 0) {
//                                        _mActivity.start(CameraSettingFragment.newInstance(OperateBean));
                            } else {
                                showDeleteCameraDialog(bean);
                            }
                        }).show();
            }
            return false;
        });
        ivCamera.setOnClickListener(v -> login());
    }

    private void showDeleteCameraDialog(DeviceListBean.DataBean.SubDevicesBean bean) {
        new AlertDialog.Builder(_mActivity)
                .setTitle("提示")
                .setMessage("你确定要删除吗？")
                .setPositiveButton("确定", (dialog, which) -> {
                    params.index = bean.getIndex();
                    mPresenter.requestDelDevice(params);
                }).setNegativeButton("取消", null).show();
    }

    private void login() {
        SubscriberListener<LoginResult> subscriberListener = new SubscriberListener<LoginResult>() {

            @Override
            public void onStart() {
                showLoading();
            }

            @Override
            public void onNext(LoginResult loginResult) {
                dismissLoading();
                //error code 全部改为了新版,如果没有老版对应 的反馈码则可忽略此错误
                //如果不可以忽略,则反馈给技术支持即可
                switch (loginResult.getError_code()) {
                    case HttpErrorCode.ERROR_0:
                        //成功的逻辑不需要改成下面这样,以下仅演示过程
                        //原有的这部分代码可以不修改
                        //code1与code2是p2p连接的鉴权码,只有在帐号异地登录或者服务器强制刷新(一般不会干这件事)时才会改变
                        //所以可以将code1与code2保存起来,只需在下次登录时刷新即可
                        saveAuthor(loginResult);
                        P2PHandler.getInstance().p2pInit(_mActivity, new P2PListener(), new SettingListener());
                        ALog.e(TAG, "haha1111111");
                        ALog.e(TAG, "session:" + loginResult.getSessionID() + " -- session2:" + loginResult.getSessionID2()
                                + "--code:" + loginResult.getP2PVerifyCode1() + " -- code2:" + loginResult.getP2PVerifyCode2());

                        int sessionid1 = (int) Long.parseLong(loginResult.getSessionID());
                        int sessionid2 = (int) Long.parseLong(loginResult.getSessionID2());

                        P2PHandler.getInstance().p2pConnect(
                                loginResult.getUserID(),
                                sessionid1,
                                sessionid2,
                                Integer.parseInt(loginResult.getP2PVerifyCode1()),
                                Integer.parseInt(loginResult.getP2PVerifyCode2()));

                        ((SupportActivity) _mActivity).start(CameraListFragment.newInstance());
                        break;
                    case HttpErrorCode.ERROR_10902011:
                        ToastUtils.showToast(_mActivity, "用户不存在");
                        break;
                    case HttpErrorCode.ERROR_10902003:
                        ToastUtils.showToast(_mActivity, "密码错误");
                        break;
                    default:
                        //其它错误码需要用户自己实现
                        ToastUtils.showToast(_mActivity, "登录失败:" + loginResult.getError_code());
                        break;
                }
            }

            @Override
            public void onError(String error_code, Throwable throwable) {
                dismissLoading();
                ToastUtils.showToast(_mActivity, "登录失败 error：" + error_code);
                ALog.e(TAG, "onError:" + throwable.getMessage());
            }
        };

//        _mActivity.start(CameraListFragment.newInstance());
        try {
            HttpSend.getInstance().SpecialEmailLogin("huangyk@aglhz.com", subscriberListener);
//            HttpSend.getInstance().ThirdLogin("1", BuildConfig.APPLICATION_ID, "565493619@qq.com", "", "0", "2", subscriberListener);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void saveAuthor(LoginResult loginResult) {
        int code1 = Integer.parseInt(loginResult.getP2PVerifyCode1());
        int code2 = Integer.parseInt(loginResult.getP2PVerifyCode2());
        String sessionId = loginResult.getSessionID();
        String sessionId2 = loginResult.getSessionID2();
        String userId = loginResult.getUserID();
        SharedPreferences sp = _mActivity.getSharedPreferences("Account", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("code1", code1);
        editor.putInt("code2", code2);
        editor.putString("sessionId", sessionId);
        editor.putString("sessionId2", sessionId2);
        editor.putString("userId", userId);
        editor.apply();
    }

    private void changedRoom(String room) {
        toolbarTitle.setText(room);
        Glide.with(_mActivity)
                .load(R.drawable.room_dating_1242px_745px)
                .into(ivRoom);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void responseDeviceList(List<DeviceListBean.DataBean.SubDevicesBean> data) {
        ptrFrameLayout.refreshComplete();
        if (data.size() < Constants.PAGE_SIZE) {
            //如果加载数量小于个数，直接完成
            adapter.loadMoreEnd();
        } else {
            //否则，可继续加载
            adapter.loadMoreComplete();
        }
        if (params.page == 1) {
            data.add(addIconDevice);
            adapter.setNewData(data);
        } else {
            adapter.addData(data);
        }
        //如果个数为0，显示空
        if (adapter.getData().size() == 0) {
//            mStateManager.showEmpty();
            adapter.loadMoreEnd();
        } else {
//            mStateManager.showContent();
        }
    }


    @Override
    public void responseHouseList(List<RoomsBean.DataBean.RoomListBean> data) {
        if (isFirst) {
            selectRoom(data.get(0));//
            isFirst = false;
        } else {
            showRoomSelecotr(data);
        }
    }

    @Override
    public void error(String errorMessage) {
        super.error(errorMessage);
        ptrFrameLayout.refreshComplete();
    }

    @Override
    public void responseDevicectrl(BaseBean bean) {
        DialogHelper.successSnackbar(getView(), bean.getOther().getMessage());
    }

    @Override
    public void responseNewDeviceConfirm(BaseBean bean) {
        DialogHelper.successSnackbar(getView(), bean.getOther().getMessage());
        onRefresh();
    }

    @Override
    public void responseNewDevice24(BaseBean bean) {

    }

    @Override
    public void responseDelSuccess(BaseBean bean) {
        DialogHelper.successSnackbar(getView(), bean.getOther().getMessage());
        onRefresh();
    }

    private void showRoomSelecotr(List<RoomsBean.DataBean.RoomListBean> data) {
        new SelectorDialogFragment()
                .setTitle("切换房间")
                .setItemLayoutId(R.layout.item_rv_simple_selector)
                .setData(data)
                .setOnItemConvertListener((holder, which, dialog) ->
                        holder.setText(R.id.tv_item_rv_simple_selector, data.get(which).getName()))
                .setOnItemClickListener((view, baseViewHolder, which, dialog) -> {
                    dialog.dismiss();
                    selectRoom(data.get(which));
                })
                .setAnimStyle(R.style.SlideAnimation)
                .setGravity(Gravity.BOTTOM)
                .show(getChildFragmentManager());
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        onRefresh();
    }

}
