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
import android.widget.CheckBox;
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
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.common.UserHelper;
import cn.itsite.amain.yicommunity.entity.bean.BaseBean;
import cn.itsite.amain.yicommunity.event.EventCommunity;
import cn.itsite.amain.yicommunity.event.EventPublish;
import cn.itsite.amain.yicommunity.main.picker.PickerActivity;
import cn.itsite.amain.yicommunity.main.publish.contract.PublishContract;
import cn.itsite.amain.yicommunity.main.publish.presenter.PublishExchangePresenter;
import cn.itsite.amain.yicommunity.web.WebActivity;

/**
 * Author: LiuJia on 2017/5/11 0011 21:13.
 * Email: liujia95me@126.com
 * [闲置交换发布]的View层。
 * 打开方式：StartApp-->社区-->闲置交换-->发布
 */

public class PublishExchangeFragment extends BaseFragment<PublishContract.Presenter> implements PublishContract.View, View.OnClickListener {
    public final String TAG = PublishExchangeFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private EditText etInputMoney;
    private TextView toolbarMenu;
    private EditText etInputContent;
    private TextView tvCommunityAddress;
    private CheckBox cbAgreement;
    private PublishImageRVAdapter adapter;
    private Params params = Params.getInstance();
    BaseMedia addMedia = new BaseMedia() {
        @Override
        public TYPE getType() {
            return TYPE.IMAGE;
        }
    };
    private ArrayList<BaseMedia> selectedMedia = new ArrayList<>();
    private LinearLayout llLocation;
    private Button btSubmit;
    private TextView tvAgreement;

    public static PublishExchangeFragment newInstance() {
        return new PublishExchangeFragment();
    }

    @NonNull
    @Override
    protected PublishContract.Presenter createPresenter() {
        return new PublishExchangePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publish_exchange, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        recyclerView = ((RecyclerView) view.findViewById(R.id.recyclerView));
        etInputMoney = ((EditText) view.findViewById(R.id.et_input_money));
        toolbarMenu = ((TextView) view.findViewById(R.id.toolbar_menu));
        etInputContent = ((EditText) view.findViewById(R.id.et_input_content));
        tvCommunityAddress = ((TextView) view.findViewById(R.id.tv_community_address));
        cbAgreement = ((CheckBox) view.findViewById(R.id.cb_agreement_publish_exchange_fragment));
        llLocation = ((LinearLayout) view.findViewById(R.id.ll_location));
        btSubmit = ((Button) view.findViewById(R.id.btn_submit));
        tvAgreement = ((TextView) view.findViewById(R.id.tv_agreement_publish_exchange_fragment));
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

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("发布");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    private void initData() {
        //因为params是单例，所以要将上次选择的清除
        params.files = new ArrayList<>();

        tvCommunityAddress.setText(UserHelper.communityName);
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
        cbAgreement.setChecked(UserHelper.isExchangeAgree);
    }

    private void initListener() {
        llLocation.setOnClickListener(this);
        btSubmit.setOnClickListener(this);
        tvAgreement.setOnClickListener(this);
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            selectPhoto();
        });
        cbAgreement.setOnCheckedChangeListener((buttonView, isChecked) -> {
            UserHelper.setExchangeAgree(isChecked);
        });
    }

    /**
     * 跳转选择相片
     */
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        KeyBoardUtils.hideKeybord(getView(), _mActivity);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void error(String errorMessage) {
        super.error(errorMessage);
        dismissLoading();
        DialogHelper.errorSnackbar(getView(), errorMessage);
    }

    @Override
    public void responseSuccess(BaseBean bean) {
        dismissLoading();
        DialogHelper.successSnackbar(getView(), "提交成功!");
        EventBus.getDefault().post(new EventPublish());
        pop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventCommunity event) {
        tvCommunityAddress.setText(event.bean.getName());
        params.cmnt_c = event.bean.getCode();
    }

    private void submit(String money, String content) {
        params.cmnt_c = UserHelper.communityCode;
        params.content = content;
        params.price = money;
        showLoading();
        mPresenter.requestSubmit(params);//请求提交闲置交换
    }

    @Override
    public boolean onBackPressedSupport() {
        if (!TextUtils.isEmpty(etInputContent.getText().toString())
                || !TextUtils.isEmpty(etInputMoney.getText().toString())
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
        if (i == R.id.ll_location) {
            _mActivity.startActivity(new Intent(_mActivity, PickerActivity.class));

        } else if (i == R.id.btn_submit) {
            String money = etInputMoney.getText().toString().trim();
            if (TextUtils.isEmpty(money)) {
                DialogHelper.errorSnackbar(getView(), "请输入金额");
                return;
            }
            String content = etInputContent.getText().toString().trim();
            if (TextUtils.isEmpty(content)) {
                DialogHelper.errorSnackbar(getView(), "请输入内容");
                return;
            }
            if (!cbAgreement.isChecked()) {
                new AlertDialog.Builder(_mActivity).setTitle("提示")
                        .setMessage("是否同意我们的协议？")
                        .setPositiveButton("同意", (dialog, which) -> cbAgreement.setChecked(true))
                        .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                        .show();
                return;
            }
            submit(money, content);
        } else if (i == R.id.tv_agreement_publish_exchange_fragment) {
            Intent introductionIntent = new Intent(_mActivity, WebActivity.class);
            introductionIntent.putExtra(Constants.KEY_TITLE, "亿社区闲置交换用户协议");
            introductionIntent.putExtra(Constants.KEY_LINK, ApiService.AGREEMENT_EXCHANGE);
            startActivity(introductionIntent);
        }
    }
}
