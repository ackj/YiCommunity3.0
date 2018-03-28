package cn.itsite.amain.yicommunity.main.publish.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing_impl.ui.BoxingActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.utils.KeyBoardUtils;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.acommon.UserHelper;
import cn.itsite.acommon.BaseBean;
import cn.itsite.amain.yicommunity.entity.bean.MyHousesBean;
import cn.itsite.amain.yicommunity.entity.bean.RepairTypesBean;
import cn.itsite.amain.yicommunity.event.EventCommunity;
import cn.itsite.amain.yicommunity.main.picker.PickerActivity;
import cn.itsite.amain.yicommunity.main.publish.contract.PublishContract;
import cn.itsite.amain.yicommunity.main.publish.presenter.RepairPresenter;

/**
 * Created by Administrator on 2017/4/19 16:06.
 * [我要报修]的View层。
 * 打开方式：StartApp-->管家-->物业报修-->我要报修
 */
@SuppressLint("ValidFragment")
public class RepairFragment extends BaseFragment<PublishContract.Presenter> implements PublishContract.View, View.OnClickListener {
    private final String TAG = RepairFragment.class.getSimpleName();
    private RelativeLayout rlHouseName;
    private Button btSubmit;
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private TextView tvLocation;
    private EditText etName;
    private EditText etPhone;
    private EditText etContent;
    private RecyclerView recyclerView;
    private TextView tvHouseName;
    private TextView tvRepairType;
    private PublishImageRVAdapter adapter;
    private boolean isPrivate;//是否是私人报修
    private Params params = Params.getInstance();
    BaseMedia addMedia = new BaseMedia() {
        @Override
        public TYPE getType() {
            return TYPE.IMAGE;
        }
    };
    private ArrayList<BaseMedia> selectedMedia = new ArrayList<>();
    private RelativeLayout rlType;

    private RepairFragment(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    /**
     * RepairFragment的创建入口
     *
     * @param isPrivate 用于区分是私人保修还是公共报修
     * @return
     */
    public static RepairFragment newInstance(boolean isPrivate) {
        return new RepairFragment(isPrivate);
    }

    @NonNull
    @Override
    protected PublishContract.Presenter createPresenter() {
        return new RepairPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publish_repair, container, false);
        rlHouseName = ((RelativeLayout) view.findViewById(R.id.rl_house_name));
        btSubmit = ((Button) view.findViewById(R.id.bt_submit_fragment_repair));
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        tvLocation = ((TextView) view.findViewById(R.id.tv_location_fragment_repair));
        etName = ((EditText) view.findViewById(R.id.et_name_fragment_repair));
        etPhone = ((EditText) view.findViewById(R.id.et_phone_fragment_repair));
        etContent = ((EditText) view.findViewById(R.id.et_content_fragment_repair));
        recyclerView = ((RecyclerView) view.findViewById(R.id.recyclerView_fragment_repair));
        tvHouseName = ((TextView) view.findViewById(R.id.tv_house_name));
        tvRepairType = ((TextView) view.findViewById(R.id.tv_repair_type));
        rlType = ((RelativeLayout) view.findViewById(R.id.tl_repair_type));
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
        btSubmit.setOnClickListener(this);
        tvLocation.setOnClickListener(this);
        rlHouseName.setOnClickListener(this);
        rlType.setOnClickListener(this);

        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            selectPhoto();
        });
    }

    private void initData() {
        //因为params是单例，所以要将上次选择的清除
        params.files = new ArrayList<>();
        if (isPrivate) {
            rlHouseName.setVisibility(View.VISIBLE);
            params.cmnt_c = UserHelper.communityCode;
        } else {
            rlHouseName.setVisibility(View.GONE);
        }

        tvLocation.setText(TextUtils.isEmpty(UserHelper.communityName) ? "请选择小区" : UserHelper.communityName);
        recyclerView.setLayoutManager(new GridLayoutManager(_mActivity, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        List<BaseMedia> datas = new ArrayList<>();
        addMedia.setPath("android.resource://" + _mActivity.getPackageName() + "/" + R.drawable.ic_image_add_tian_80px);
        datas.add(addMedia);
        adapter = new PublishImageRVAdapter(datas);
        recyclerView.setAdapter(adapter);
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("我要报修");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        KeyBoardUtils.hideKeybord(getView(), _mActivity);
        EventBus.getDefault().unregister(this);
    }

    /**
     * 选择系统的相册
     */
    private void selectPhoto() {
        BoxingConfig config = new BoxingConfig(BoxingConfig.Mode.MULTI_IMG); // Mode：Mode.SINGLE_IMG, Mode.MULTI_IMG, Mode.VIDEO
        config.needCamera(R.drawable.ic_boxing_camera_white).needGif().withMaxCount(3) // 支持gif，相机，设置最大选图数
                .withMediaPlaceHolderRes(R.drawable.ic_boxing_default_image); // 设置默认图片占位图，默认无
        Boxing.of(config).withIntent(_mActivity, BoxingActivity.class, selectedMedia).start(this, 100);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventCommunity event) {
        params.cmnt_c = event.bean.getCode();
        tvLocation.setText(event.bean.getName());
    }

    private void submit(Params params) {
        showLoading();
        params.cmnt_c = UserHelper.communityCode;
        mPresenter.requestSubmit(params);//请求提交
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ALog.d(TAG, "onActivityResult:" + requestCode + " --- :" + resultCode);
        if (resultCode == RESULT_OK && requestCode == 100) {
            ArrayList<BaseMedia> medias = new ArrayList<>(Boxing.getResult(data));
            selectedMedia = Boxing.getResult(data);
            params.files.clear();
            for (int i = 0; i < medias.size(); i++) {
                params.files.add(new File(medias.get(i).getPath()));
            }
            if (params.files.size() > 0) {
                params.type = 1;
            }
            medias.add(addMedia);
            adapter.setNewData(medias);
        }
    }

    @Override
    public void start(Object response) {
    }

    @Override
    public void error(String errorMessage) {
        super.error(errorMessage);
        dismissLoading();
        DialogHelper.warningSnackbar(getView(), errorMessage);
    }

    /**
     * 响应请求房屋列表
     *
     * @param
     */
    public void responseMyHouse(List<MyHousesBean.DataBean.AuthBuildingsBean> datas) {
        dismissLoading();
        String[] houseTitles = new String[datas.size()];
        for (int i = 0; i < datas.size(); i++) {
            houseTitles[i] = datas.get(i).getAddress();
        }

        new AlertDialog.Builder(_mActivity)
                .setTitle("请选择")
                .setItems(houseTitles, (dialog, which) -> {
                    //网络访问
                    tvHouseName.setText(datas.get(which).getAddress());
                    params.ofid = datas.get(which).getFid();
                })
                .setPositiveButton("取消", null)
                .show();
    }

    /**
     * 响应请求报修类型
     *
     * @param datas
     */
    public void responseRepairTypes(List<RepairTypesBean.DataBean.TypesBean> datas) {
        dismissLoading();
        String[] arr = new String[datas.size()];
        for (int i = 0; i < datas.size(); i++) {
            arr[i] = datas.get(i).getName();
        }
        new AlertDialog.Builder(_mActivity).setItems(arr, (dialog, which) -> {
            //网络访问
            dialog.dismiss();
            ALog.e("AlertDialog which:::" + which);
            tvRepairType.setText(arr[which]);
            params.repairType = datas.get(which).getCode();
        }).setTitle("请选择").setPositiveButton("取消", null).show();
    }

    /**
     * 响应请求提交成功
     *
     * @param bean
     */
    @Override
    public void responseSuccess(BaseBean bean) {
        dismissLoading();
        DialogHelper.successSnackbar(getView(), "提交成功!");
        setFragmentResult(RESULT_OK, null);
        pop();
    }

    @Override
    public boolean onBackPressedSupport() {
        if (!TextUtils.isEmpty(etName.getText().toString())
                || !TextUtils.isEmpty(etPhone.getText().toString())
                || !TextUtils.isEmpty(etContent.getText().toString())
                || !selectedMedia.isEmpty()) {
            new AlertDialog.Builder(_mActivity)
                    .setTitle("提示")
                    .setMessage("如果退出，当前填写信息将会丢失，是否退出？")
                    .setPositiveButton("退出", (dialog, which) -> pop())
                    .show();
            return true;
        } else {
            pop();
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.rl_house_name) {
            if (isPrivate) {
                showLoading();
                ((RepairPresenter) mPresenter).requestMyhouse(params);//请求房屋名称
            }

        } else if (i == R.id.tl_repair_type) {
            showLoading();
            if (isPrivate) {
                params.type = 1;
            } else {
                params.type = 2;
            }
            ((RepairPresenter) mPresenter).requestRepairTypes(params);//请求报修类型
        } else if (i == R.id.tv_location_fragment_repair) {
            _mActivity.startActivity(new Intent(_mActivity, PickerActivity.class));
        } else if (i == R.id.bt_submit_fragment_repair) {
            params.name = etName.getText().toString().trim();
            params.des = etContent.getText().toString().trim();
            params.contact = etPhone.getText().toString().trim();
            params.single = isPrivate;
            if (TextUtils.isEmpty(params.repairType)) {
                DialogHelper.errorSnackbar(getView(), "请选择报修类型");
                return;
            }
            if (TextUtils.isEmpty(params.name)) {
                DialogHelper.errorSnackbar(getView(), "请输入联系人");
                return;
            }
            if (TextUtils.isEmpty(params.contact)) {
                DialogHelper.errorSnackbar(getView(), "请输入您的联系方式");
                return;
            }
            if (TextUtils.isEmpty(params.des)) {
                DialogHelper.errorSnackbar(getView(), "请输入详情");
                return;
            }
            submit(params);

        }
    }
}
