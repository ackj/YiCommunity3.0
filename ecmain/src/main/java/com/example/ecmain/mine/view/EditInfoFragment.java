package com.example.ecmain.mine.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing_impl.ui.BoxingActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ecmain.R;
import com.example.ecmain.mine.contract.EditInfoContract;
import com.example.ecmain.mine.presenter.EditInfoPresenter;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.common.UserHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.network.http.BaseOldResponse;
import cn.itsite.abase.utils.ToastUtils;
import cn.itsite.acommon.data.UserParams;
import cn.itsite.acommon.event.EventRefreshInfo;
import cn.itsite.adialog.dialogfragment.BaseDialogFragment;

/**
 * Author： Administrator on 2018/3/10 0010.
 * Email： liujia95me@126.com
 */
@Route(path = "/mine/editinfofragment")
public class EditInfoFragment extends BaseFragment<EditInfoContract.Presenter> implements EditInfoContract.View, View.OnClickListener {

    private static final String TAG = EditInfoFragment.class.getSimpleName();
    private View mRlToolbar;
    private TextView mTvNickname;
    private ImageView mIvAvator;

    UserParams params = new UserParams();

    public static EditInfoFragment newInstance() {
        return new EditInfoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    protected EditInfoContract.Presenter createPresenter() {
        return new EditInfoPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_info, container, false);
        mRlToolbar = view.findViewById(R.id.rl_toolbar);
        mIvAvator = view.findViewById(R.id.iv_avator);
        mTvNickname = view.findViewById(R.id.tv_nickname);
        view.findViewById(R.id.ll_avator).setOnClickListener(this);
        view.findViewById(R.id.ll_nickname).setOnClickListener(this);
        view.findViewById(R.id.iv_back).setOnClickListener(this);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initStateBar(mRlToolbar);
        initData();
        initListener();
    }

    private void initData() {
        mTvNickname.setText(UserHelper.nickName);
        refreshAvator(UserHelper.avator);
    }

    private void initListener() {
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.ll_nickname){
            showInputDialog();
        }else if(v.getId()==R.id.ll_avator){
            BoxingConfig config = new BoxingConfig(BoxingConfig.Mode.SINGLE_IMG); // Mode：Mode.SINGLE_IMG, Mode.MULTI_IMG, Mode.VIDEO
            config//.needCamera(R.drawable.ic_boxing_camera_white) // 支持gif，相机，设置最大选图数
                    .withMediaPlaceHolderRes(R.drawable.ic_boxing_default_image); // 设置默认图片占位图，默认无
            Boxing.of(config).withIntent(_mActivity, BoxingActivity.class).start(this, 100);
        }else if(v.getId()==R.id.iv_back){
            pop();
        }
    }

    private void showInputDialog() {
        new BaseDialogFragment()
                .setLayoutId(R.layout.dialog_input_nickname)
                .setConvertListener((holder, dialog) -> {
                    EditText etInput = holder.getView(cn.itsite.order.R.id.et_input);
                    etInput.setText(mTvNickname.getText().toString().trim());
                    etInput.setSelection(etInput.getText().toString().length());
                    holder
                            .setOnClickListener(cn.itsite.order.R.id.btn_cancel, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            })
                            .setOnClickListener(cn.itsite.order.R.id.btn_comfirm, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String input = etInput.getText().toString().trim();
                                    if (TextUtils.isEmpty(input)) {
                                        ToastUtils.showToast(_mActivity, "请输入留言内容");
                                    } else {
                                        params.username = input;
                                        mPresenter.requestNickname(params);
                                        dialog.dismiss();
                                    }
                                }
                            });
                })
                .setDimAmount(0.3f)
                .setMargin(40)
                .setGravity(Gravity.CENTER)
                .show(getChildFragmentManager());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ALog.d(TAG, "onActivityResult:" + requestCode + " --- :" + resultCode);
        if (resultCode == RESULT_OK && requestCode == 100) {
            ArrayList<BaseMedia> medias = Boxing.getResult(data);
            if (medias.size() > 0) {
                File file = new File(medias.get(0).getPath());
                refreshAvator(file.getPath());
                params.file = file;
                showLoading();
                mPresenter.requestUpdateAvator(params);
            }
        }
    }

    private void refreshAvator(String path) {
        Glide.with(_mActivity)
                .load(path)
                .apply(new RequestOptions().error(R.drawable.ic_avatar_150px).placeholder(R.drawable.ic_avatar_150px).circleCrop())
                .into(mIvAvator);
    }

    @Override
    public void responseUpdateAvator(BaseOldResponse<String> response) {
        DialogHelper.successSnackbar(getView(),"修改头像成功");
        UserHelper.setAvator(params.file.getAbsolutePath());
        EventBus.getDefault().post(new EventRefreshInfo());
    }

    @Override
    public void responseNickname(BaseOldResponse response) {
        DialogHelper.successSnackbar(getView(),response.getOther().getMessage());
        mTvNickname.setText(params.username);
        UserHelper.setNickname(params.username);
        EventBus.getDefault().post(new EventRefreshInfo());
    }
}