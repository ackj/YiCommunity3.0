package cn.itsite.amain.yicommunity.main.publish.view;

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
import cn.itsite.amain.yicommunity.common.UserHelper;
import cn.itsite.amain.yicommunity.entity.bean.BaseBean;
import cn.itsite.amain.yicommunity.event.EventCommunity;
import cn.itsite.amain.yicommunity.main.picker.PickerActivity;
import cn.itsite.amain.yicommunity.main.publish.contract.PublishContract;
import cn.itsite.amain.yicommunity.main.publish.presenter.ComplainPresenter;

/**
 * Created by Administrator on 2017/4/19 16:09.
 * [我要投诉]的View层
 * 打开方式-->StartApp-->管家-->管理投诉
 */
public class ComplainFragment extends BaseFragment<PublishContract.Presenter> implements PublishContract.View, View.OnClickListener {
    public final String TAG = ComplainFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private TextView tvCommunity;
    private EditText etName;
    private EditText etPhone;
    private EditText etContent;
    private RecyclerView recyclerView;
    private Button btSubmit;
    private PublishImageRVAdapter adapter;
    Params params = Params.getInstance();
    BaseMedia addMedia = new BaseMedia() {
        @Override
        public TYPE getType() {
            return TYPE.IMAGE;
        }
    };
    private ArrayList<BaseMedia> selectedMedia = new ArrayList<>();

    public static ComplainFragment newInstance() {
        return new ComplainFragment();
    }

    @NonNull
    @Override
    protected PublishContract.Presenter createPresenter() {
        return new ComplainPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complain, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        tvCommunity = ((TextView) view.findViewById(R.id.tv_community_fragment_complain));
        etName = ((EditText) view.findViewById(R.id.et_name_fragment_complain));
        etPhone = ((EditText) view.findViewById(R.id.et_phone_fragment_complain));
        etContent = ((EditText) view.findViewById(R.id.et_content_fragment_complain));
        recyclerView = ((RecyclerView) view.findViewById(R.id.recyclerView_fragment_complain));
        btSubmit = ((Button) view.findViewById(R.id.btn_submit_fragment_complain));
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        initListener();
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("我要投诉");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    private void initData() {
        tvCommunity.setText(TextUtils.isEmpty(UserHelper.communityName) ? "请选择小区" : UserHelper.communityName);

        //因为params是单例，所以要将上次选择的清除
        params.files = new ArrayList<>();
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

    private void initListener() {
        tvCommunity.setOnClickListener(this);
        btSubmit.setOnClickListener(this);
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            selectPhoto();
        });
    }

    @Override
    public void error(String errorMessage) {
        super.error(errorMessage);
        dismissLoading();
        DialogHelper.warningSnackbar(getView(), errorMessage);
    }

    /**
     * 响应请求投诉成功
     *
     * @param baseBean
     */
    @Override
    public void responseSuccess(BaseBean baseBean) {
        dismissLoading();
        DialogHelper.successSnackbar(getView(), "提交成功!");
        getView().postDelayed(() -> _mActivity.finish(), 300);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        KeyBoardUtils.hideKeybord(getView(), _mActivity);
        EventBus.getDefault().unregister(this);
    }

    private void selectPhoto() {
        BoxingConfig config = new BoxingConfig(BoxingConfig.Mode.MULTI_IMG); // Mode：Mode.SINGLE_IMG, Mode.MULTI_IMG, Mode.VIDEO
        config.needCamera(R.drawable.ic_boxing_camera_white).needGif().withMaxCount(3) // 支持gif，相机，设置最大选图数
                .withMediaPlaceHolderRes(R.drawable.ic_boxing_default_image); // 设置默认图片占位图，默认无
        Boxing.of(config).withIntent(_mActivity, BoxingActivity.class, selectedMedia).start(this, 100);
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

    private void submit(Params params) {
        params.cmnt_c = UserHelper.communityCode;
        mPresenter.requestSubmit(params);//上传
        showLoading();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventCommunity event) {
        tvCommunity.setText(UserHelper.communityName);
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
                    .setPositiveButton("退出", (dialog, which) -> _mActivity.finish())
                    .show();
            return true;
        } else {
            _mActivity.finish();
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_community_fragment_complain) {
            _mActivity.startActivity(new Intent(_mActivity, PickerActivity.class));
        } else if (i == R.id.btn_submit_fragment_complain) {
            params.name = etName.getText().toString().trim();
            params.phoneNo = etPhone.getText().toString().trim();
            params.content = etContent.getText().toString().trim();
            if (params.name.isEmpty()) {
                DialogHelper.errorSnackbar(getView(), "请输入联系人");
                return;
            }
            if (params.phoneNo.isEmpty()) {
                DialogHelper.errorSnackbar(getView(), "请输入您的联系方式");
                return;
            }
            if (params.content.isEmpty()) {
                DialogHelper.errorSnackbar(getView(), "请输入详情");
                return;
            }
            submit(params);
        }
    }
}
