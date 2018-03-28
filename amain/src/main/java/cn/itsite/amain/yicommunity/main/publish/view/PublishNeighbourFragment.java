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
import android.widget.LinearLayout;
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
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.acommon.UserHelper;
import cn.itsite.acommon.BaseBean;
import cn.itsite.amain.yicommunity.event.EventCommunity;
import cn.itsite.amain.yicommunity.event.EventPublish;
import cn.itsite.amain.yicommunity.main.picker.PickerActivity;
import cn.itsite.amain.yicommunity.main.publish.contract.PublishContract;
import cn.itsite.amain.yicommunity.main.publish.presenter.PublishNeighbourPresenter;

/**
 * Author: LiuJia on 2017/5/11 0011 20:36.
 * Email: liujia95me@126.com
 * [左邻右里发布]的View层。
 * 打开方式：StartApp-->邻里-->发布
 */

public class PublishNeighbourFragment extends BaseFragment<PublishContract.Presenter> implements PublishContract.View, View.OnClickListener {
    private final String TAG = PublishNeighbourFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private EditText etInputContent;
    private TextView tvCommunityName;
    private TextView tvResTitle;
    private PublishImageRVAdapter adapter;
    private Params params = Params.getInstance();
    BaseMedia addMedia = new BaseMedia() {
        @Override
        public TYPE getType() {
            return TYPE.IMAGE;
        }
    };
    private int which;
    private ArrayList<BaseMedia> medias = new ArrayList<>();
    private ArrayList<BaseMedia> selectedMedia = new ArrayList<>();
    private Button btSubmit;
    private LinearLayout llCommunity;

    /**
     * PublishNeighbourFragment的创建入口
     *
     * @param which 区分选择发布的是视频还是图片
     * @return
     */
    public static PublishNeighbourFragment newInstance(int which) {
        PublishNeighbourFragment fragment = new PublishNeighbourFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.KEY_WHICH, which);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    protected PublishContract.Presenter createPresenter() {
        return new PublishNeighbourPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            which = bundle.getInt(Constants.KEY_WHICH);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publish_neighbour, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        recyclerView = ((RecyclerView) view.findViewById(R.id.recyclerView));
        etInputContent = ((EditText) view.findViewById(R.id.et_input_content));
        tvCommunityName = ((TextView) view.findViewById(R.id.tv_community_name));
        tvResTitle = ((TextView) view.findViewById(R.id.tv_selected_res_title));
        btSubmit = ((Button) view.findViewById(R.id.btn_submit));
        llCommunity = ((LinearLayout) view.findViewById(R.id.ll_community_name));
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        initToolbar();
        initData();
        initListener();
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("发布");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    private void initData() {
        tvResTitle.setText(which == 0 ? "图片" : "视频");
        //因为params是单例，所以要将上次选择的清除
        params.files = new ArrayList<>();
        tvCommunityName.setText(UserHelper.communityName);
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
        btSubmit.setOnClickListener(this);
        llCommunity.setOnClickListener(this);
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            selectPhoto();
        });
    }

    /**
     * 选择视频或者照片。
     */
    private void selectPhoto() {
        if (which == 0) {
            //跳转选择照片
            BoxingConfig config = new BoxingConfig(BoxingConfig.Mode.MULTI_IMG); // Mode：Mode.SINGLE_IMG, Mode.MULTI_IMG, Mode.VIDEO
            config.needCamera(R.drawable.ic_boxing_camera_white).needGif().withMaxCount(3) // 支持gif，相机，设置最大选图数
                    .withMediaPlaceHolderRes(R.drawable.ic_boxing_default_image); // 设置默认图片占位图，默认无
            Boxing.of(config).withIntent(_mActivity, BoxingActivity.class, selectedMedia).start(this, 100);
        } else {
            //跳转选择视频
            BoxingConfig config = new BoxingConfig(BoxingConfig.Mode.VIDEO).withVideoDurationRes(R.drawable.ic_boxing_play);
            Boxing.of(config).withIntent(_mActivity, BoxingActivity.class).start(this, 101);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ALog.d(TAG, "onActivityResult:" + requestCode + " --- :" + resultCode);
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                medias = new ArrayList<>(Boxing.getResult(data));
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
            } else if (requestCode == 101) {
                medias = new ArrayList<>(Boxing.getResult(data));
                params.files.clear();
                if (medias.size() > 0) {
                    File file = new File(medias.get(0).getPath());
                    if (file.length() >= 1024 * 1024 * 10) {
                        DialogHelper.warningSnackbar(getView(), "视频文件不能大于10M");
                    } else {
                        params.type = 2;
                        params.files.add(file);
                        medias.add(addMedia);
                        adapter.setNewData(medias);
                    }
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        KeyBoardUtils.hideKeybord(getView(), _mActivity);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void start(Object response) {

    }

    @Override
    public void error(String errorMessage) {
        super.error(errorMessage);
        dismissLoading();
        DialogHelper.errorSnackbar(getView(), errorMessage);
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
        EventBus.getDefault().post(new EventPublish());
        pop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventCommunity event) {
        ALog.e(TAG, "onEvent:::" + event.bean.getName());
        tvCommunityName.setText(event.bean.getName());
        params.cmnt_c = event.bean.getCode();
    }

    private void submit(String content) {
        params.cmnt_c = UserHelper.communityCode;
        params.content = content;
        showLoading();
        mPresenter.requestSubmit(params);//请求提交左邻右里
    }

    @Override
    public boolean onBackPressedSupport() {
        if (!TextUtils.isEmpty(etInputContent.getText().toString())
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
        if (i == R.id.btn_submit) {
            String content = etInputContent.getText().toString().trim();
            if (TextUtils.isEmpty(content)) {
                DialogHelper.errorSnackbar(getView(), "请输入内容");
                return;
            }
            submit(content);
        } else if (i == R.id.ll_community_name) {
            _mActivity.startActivity(new Intent(_mActivity, PickerActivity.class));
        }
    }
}
