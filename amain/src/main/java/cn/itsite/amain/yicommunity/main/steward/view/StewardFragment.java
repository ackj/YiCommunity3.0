package cn.itsite.amain.yicommunity.main.steward.view;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.itsite.abase.common.BaseBean;
import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.common.UserHelper;
import cn.itsite.abase.mvp.view.base.BaseLazyFragment;
import cn.itsite.adialog.dialogfragment.SelectorDialogFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.s1.main.smarthome.view.SmartHomeFragment;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.DoorManager;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.DoorListBean;
import cn.itsite.amain.yicommunity.entity.bean.HouseInfoBean;
import cn.itsite.amain.yicommunity.entity.bean.IconBean;
import cn.itsite.amain.yicommunity.entity.bean.MyHousesBean;
import cn.itsite.amain.yicommunity.event.EventCommunity;
import cn.itsite.amain.yicommunity.login.LoginActivity;
import cn.itsite.amain.yicommunity.main.door.DoorActivity;
import cn.itsite.amain.yicommunity.main.door.view.FamilyPhoneFragment;
import cn.itsite.amain.yicommunity.main.house.HouseActivity;
import cn.itsite.amain.yicommunity.main.parking.ParkingActivity;
import cn.itsite.amain.yicommunity.main.picker.PickerActivity;
import cn.itsite.amain.yicommunity.main.publish.PropertyActivity;
import cn.itsite.amain.yicommunity.main.steward.contract.StewardContract;
import cn.itsite.amain.yicommunity.main.steward.presenter.StewardPresenter;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author：leguang on 2017/4/13 0009 15:49
 * Email：langmanleguang@qq.com
 * <p>
 * 负责管家模块的View层内容。
 * 此类不能用懒加载，因为在未进入此页面，PtrFrameLayout尚未加载的完全的时候，
 * 由于切换小区，这个页面的EventBus会调用下拉刷新，导致再进入此页面后，下拉刷新无法归为，基本瘫痪。
 */
public class StewardFragment extends BaseLazyFragment<StewardContract.Presenter> implements StewardContract.View {
    public static final String TAG = StewardFragment.class.getSimpleName();
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private RecyclerView rvMyHouse;
    private RecyclerView rvSmartHome;
    private RecyclerView rvSmartDoor;
    private RecyclerView rvSmartPark;
    private RecyclerView rvPropertyService;
    private PtrFrameLayout ptrFrameLayout;
    private ScrollView svSteward;
    private StewardRVAdapter myHouseAdapter;
    private StewardRVAdapter smartHomeAdapter;
    private StewardRVAdapter smartDoorAdapter;
    private StewardRVAdapter smartParkAdapter;
    private StewardRVAdapter propertyServiceAdapter;
    private List<IconBean> listIcons = new ArrayList<>();
    private Params params = Params.getInstance();
    private final static int SELECT_COMMUNIT = 100;   //选择社区

    public static StewardFragment newInstance() {
        return new StewardFragment();
    }

    @NonNull
    @Override
    protected StewardContract.Presenter createPresenter() {
        return new StewardPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_steward, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        rvMyHouse = ((RecyclerView) view.findViewById(R.id.rv_my_house));
        rvSmartHome = ((RecyclerView) view.findViewById(R.id.rv_smart_home));
        rvSmartDoor = ((RecyclerView) view.findViewById(R.id.rv_smart_door));
        rvSmartPark = ((RecyclerView) view.findViewById(R.id.rv_smart_park));
        rvPropertyService = ((RecyclerView) view.findViewById(R.id.rv_property_service));
        ptrFrameLayout = ((PtrFrameLayout) view.findViewById(R.id.ptrFrameLayout));
        svSteward = ((ScrollView) view.findViewById(R.id.sv_steward_fragment));
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar(toolbar);
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        initPtrFrameLayout(ptrFrameLayout, svSteward);
        initData();
        initListener();
    }

    private void initData() {
        //我的房屋卡片
        rvMyHouse.setLayoutManager(new GridLayoutManager(_mActivity, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;//禁止RecyclerView的滑动，避免嵌套ScorllView时滑动卡顿。
            }
        });
        rvMyHouse.setAdapter(myHouseAdapter = new StewardRVAdapter());
        listIcons.add(new IconBean(R.drawable.ic_add_house_red_140px, "添加房屋", ""));
        myHouseAdapter.setNewData(listIcons);
        //智能家居卡片
        rvSmartHome.setLayoutManager(new GridLayoutManager(_mActivity, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rvSmartHome.setAdapter(smartHomeAdapter = new StewardRVAdapter());
        List<IconBean> listSmartHome = new ArrayList<IconBean>();
        listSmartHome.add(new IconBean(R.drawable.ic_smart_store_blue_140px, "智能设备商城", ""));
        smartHomeAdapter.setNewData(listSmartHome);

        //智慧门禁卡片
        rvSmartDoor.setLayoutManager(new GridLayoutManager(_mActivity, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rvSmartDoor.setAdapter(smartDoorAdapter = new StewardRVAdapter());
        List<IconBean> listSmartDoor = new ArrayList<>();
        listSmartDoor.add(new IconBean(R.drawable.ic_key_green_140px_140px, "设置开门", ""));
        listSmartDoor.add(new IconBean(R.drawable.ic_open_door_green_140px, "指定开门", ""));
        listSmartDoor.add(new IconBean(R.drawable.ic_password_open_door_green_140px, "密码开门", ""));
//        listSmartDoor.add(new IconBean(R.drawable.ic_call_door_green_140px, "门禁监控", ""));
        listSmartDoor.add(new IconBean(R.drawable.ic_open_recording_green_140px, "开门记录", ""));
        listSmartDoor.add(new IconBean(R.drawable.ic_qinqinghaoma_240px, "亲情号码", ""));
        smartDoorAdapter.setNewData(listSmartDoor);

        //智慧停车卡片
        rvSmartPark.setLayoutManager(new GridLayoutManager(_mActivity, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rvSmartPark.setAdapter(smartParkAdapter = new StewardRVAdapter());
        List<IconBean> listSmartPark = new ArrayList<>();
        listSmartPark.add(new IconBean(R.drawable.ic_car_card_200px, "我的车卡", ""));
        listSmartPark.add(new IconBean(R.drawable.ic_stop_record_140px, "车位查询", ""));
        listSmartPark.add(new IconBean(R.drawable.ic_add_car_card_200px, "办理车卡", ""));
        smartParkAdapter.setNewData(listSmartPark);

        //物业服务卡片
        rvPropertyService.setLayoutManager(new GridLayoutManager(_mActivity, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rvPropertyService.setAdapter(propertyServiceAdapter = new StewardRVAdapter());
        List<IconBean> listPropertyService = new ArrayList<>();
        listPropertyService.add(new IconBean(R.drawable.ic_repair_orange_140px, "物业报修", ""));
        listPropertyService.add(new IconBean(R.drawable.ic_call_property_orange_140px, "联系物业", ""));
        listPropertyService.add(new IconBean(R.drawable.ic_property_complaints_orange_140px, "管理投诉", ""));
        propertyServiceAdapter.setNewData(listPropertyService);

    }

    private void initListener() {

        //设置我的房屋卡片的点击事件。
        myHouseAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (position == adapter.getData().size() - 1) {
                //点击的最后一个item，此时应该跳转到添加房屋界面。
                go2House(Constants.ADD_HOUSE, listIcons.get(position).title, "");
            } else {
                go2House(Constants.HOUSE_RIGHTS, listIcons.get(position).title, listIcons.get(position).fid);
            }
        });

        //设置智能家居卡片点击事件。
        smartHomeAdapter.setOnItemClickListener((adapter, view, position) -> {
            IconBean bean = smartHomeAdapter.getItem(position);
            if (position == adapter.getData().size() - 1) {
                //点击的最后一个item，此时应该跳转到添加房屋界面。
                go2DeviceStore();
            } else {
                ((SupportActivity) _mActivity).start(SmartHomeFragment.newInstance(bean.roomDir));
            }
        });

        //设置智能门禁卡片点击事件。
        smartDoorAdapter.setOnItemClickListener((adapter, view, position) -> {
//            if (position == 3) {
//                showLoading();
//                mPresenter.requestDoors(params);
//            } else
            if (position == 4) {
                mPresenter.requestHouseInfoList(params);
                showLoading();
            } else {
                go2SmartDoor(position);
            }
        });

        //设置智慧停车卡片点击事件。
        smartParkAdapter.setOnItemClickListener((adapter, view, position) -> {
            go2Park(position);
        });

        //物业服务卡片点击事件。
        propertyServiceAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (!hasTokenAndCommunity()) {
                return;
            }
            if (position == 1) {
                params.cmnt_c = UserHelper.communityCode;
                showLoading();
                mPresenter.requestContact(params);
            } else {
                go2PropertyService(position);
            }
        });
    }


    private boolean hasTokenAndCommunity() {
        if (!UserHelper.isLogined()) {
            startActivity(new Intent(_mActivity, LoginActivity.class));
            return false;
        } else if (!UserHelper.hasCommunity()) {
            DialogHelper.warningSnackbar(getView(), "需要先选择社区！");
            Intent intent = new Intent(_mActivity, PickerActivity.class);
            startActivityForResult(intent, SELECT_COMMUNIT);
            return false;
        } else {
            return true;
        }
    }

    //跳转到智能门禁模块。
    private void go2SmartDoor(int position) {
        Intent intent = new Intent(_mActivity, DoorActivity.class);
        intent.putExtra(Constants.KEY_FROM_TO, position);
        startActivity(intent);
    }

    //跳转到物业模块。
    private void go2PropertyService(int position) {
        Intent intent = new Intent(_mActivity, PropertyActivity.class);
        intent.putExtra(Constants.KEY_FROM_TO, position);
        startActivity(intent);
    }

    //跳转到停车模块。
    private void go2Park(int position) {
        if (position == 2) {
            go2ParkActivity(position);
        } else {
            go2ParkActivity(position);
        }
    }

    private void go2ParkActivity(int position) {
        Intent intent = new Intent(_mActivity, ParkingActivity.class);
        intent.putExtra(Constants.KEY_FROM_TO, position);
        startActivity(intent);
    }

    //跳转到添加房屋模块。
    private void go2House(int position, String address, String fid) {
        Intent intent = new Intent(_mActivity, HouseActivity.class);
        intent.putExtra(Constants.KEY_FROM_TO, position);
        intent.putExtra(Constants.KEY_FID, fid);
        intent.putExtra(Constants.KEY_ADDRESS, address);
        startActivity(intent);
    }

    private void go2DeviceStore() {
//        _mActivity.start(GoodsCategoryFragment.newInstance());
//        ((SupportActivity) _mActivity).start(SmartHomeMallFragment.newInstance(null, 0));
        ARouter.getInstance().build("/goodshome/main").navigation();
    }

    @Override
    public void onRefresh() {
        params.token = UserHelper.token;
        params.cmnt_c = UserHelper.communityCode;
        mPresenter.start(params);
    }

    @Override
    public void error(String errorMessage) {
        super.error(errorMessage);
        ptrFrameLayout.refreshComplete();
        DialogHelper.errorSnackbar(getView(), errorMessage);
    }

    protected void initToolbar(Toolbar toolbar) {
        initStateBar(toolbar);
        toolbarTitle.setText("智能管家");
        toolbarTitle.setOnClickListener(v -> svSteward.fullScroll(ScrollView.FOCUS_UP));
    }

    @Override
    public void onDestroyView() {
        if (myHouseAdapter != null) {
            myHouseAdapter = null;
            smartHomeAdapter = null;
            smartDoorAdapter = null;
            smartParkAdapter = null;
            propertyServiceAdapter = null;
        }
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void responseHouses(List<MyHousesBean.DataBean.AuthBuildingsBean> data) {
        ptrFrameLayout.refreshComplete();
        listIcons.clear();
        for (int i = 0; i < data.size(); i++) {
            MyHousesBean.DataBean.AuthBuildingsBean bean = data.get(i);
            listIcons.add(new IconBean(R.drawable.ic_my_house_red_140px, bean.getAddress(), bean.getFid(), bean.getRoomDir()));
        }
        if (myHouseAdapter != null) {
            myHouseAdapter.setNewData(listIcons);
            myHouseAdapter.addData(new IconBean(R.drawable.ic_add_house_red_140px, "添加房屋", ""));
        }

        ArrayList<IconBean> iconBeans = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            MyHousesBean.DataBean.AuthBuildingsBean bean = data.get(i);
            iconBeans.add(new IconBean(R.drawable.ic_myhouse_blue_140px, bean.getAddress(), bean.getFid(), bean.getRoomDir()));
        }

        if (smartHomeAdapter != null) {
            smartHomeAdapter.setNewData(iconBeans);
            smartHomeAdapter.addData(new IconBean(R.drawable.ic_smart_store_blue_140px, "智能设备商城", ""));
        }
    }

    @Override
    public void responseContact(String[] arrayPhones) {
        dismissLoading();
        new AlertDialog.Builder(_mActivity)
                .setTitle("联系方式")
                .setItems(arrayPhones, (dialog, which) -> {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + arrayPhones[which].substring(3)));
                    if (intent.resolveActivity(_mActivity.getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        DialogHelper.warningSnackbar(getView(), "亲！抱歉，未找到拨号器");
                    }
                }).show();
    }

    @Override
    public void responseDoors(DoorListBean bean) {
        dismissLoading();
        if (bean == null || bean.getData() == null || bean.getData().isEmpty()) {
            DialogHelper.errorSnackbar(getView(), "没找到门禁");
            return;
        }

        //创建对话框。
        new SelectorDialogFragment()
                .setTitle("请选择门禁")
                .setItemLayoutId(R.layout.item_rv_door_selector)
                .setData(bean.getData())
                .setOnItemConvertListener((holder, position, dialog) -> {
                    DoorListBean.DataBean item = bean.getData().get(position);
                    holder.setText(R.id.tv_name_item_rv_door_selector, item.getName())
                            .setText(R.id.tv_community_item_rv_door_selector, UserHelper.communityName)
                            .setText(R.id.tv_online_item_rv_door_selector, item.isOnline() ? "在线" : "离线")
                            .setTextColor(R.id.tv_online_item_rv_door_selector,
                                    item.isOnline() ? Color.parseColor("#999999") : Color.parseColor("#FF0000"));
                })
                .setOnItemClickListener((view, baseViewHolder, position, dialog) -> {
                    dialog.dismiss();
                    params.dir = bean.getData().get(position).getDir();
                    params.powerCode = Constants.PERMISSION_REMOTEWATCH;
                    mPresenter.requestCheckPermission(params);
                    showLoading();
                })
                .setAnimStyle(R.style.SlideAnimation)
                .setGravity(Gravity.BOTTOM)
                .setHeight(350)
                .show(getChildFragmentManager());
    }

    @Override
    public void responseCheckPermission(BaseBean mBaseBean) {
        dismissLoading();
        DoorManager.getInstance()
                .callOut(params.dir);
    }

    @Override
    public void responseHouseInfoList(List<HouseInfoBean.DataBean> datas) {
        dismissLoading();
        new SelectorDialogFragment()
                .setTitle("请选择房屋")
                .setItemLayoutId(R.layout.item_rv_simple_selector)
                .setData(datas)
                .setOnItemConvertListener((holder, position, dialog) -> {
                    HouseInfoBean.DataBean bean = datas.get(position);
                    holder.setText(R.id.tv_item_rv_simple_selector, bean.getHouseName());
                })
                .setOnItemClickListener((view, baseViewHolder, position, dialog) -> {
                    dialog.dismiss();
                    HouseInfoBean.DataBean bean = datas.get(position);
                    ((SupportActivity) _mActivity).start(FamilyPhoneFragment.newInstance(bean.getRoomDir(), bean.getFamilyNumber()));
                })
                .setAnimStyle(R.style.SlideAnimation)
                .setGravity(Gravity.BOTTOM)
                .show(getChildFragmentManager());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void go2TopAndRefresh(EventCommunity event) {
        svSteward.fullScroll(ScrollView.FOCUS_UP);
        ptrFrameLayout.postDelayed(this::onRefresh, 100);
    }
}


