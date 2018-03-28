package cn.itsite.amain.s1.host.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.common.RxManager;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.abase.utils.KeyBoardUtils;
import cn.itsite.amain.R;
import cn.itsite.amain.s1.App;
import cn.itsite.amain.s1.common.ApiService;
import cn.itsite.amain.s1.common.Constants;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.event.EventDeviceNameChanged;
import cn.itsite.abase.common.UserHelper;
import me.yokeyword.fragmentation.SupportActivity;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by leguang on 2017/6/22 0022.
 * Email：langmanleguang@qq.com
 */

public class EditHostFragment extends BaseFragment {
    public static final String TAG = EditHostFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private TextView toolbarMenu;
    private Toolbar toolbar;
    private EditText etName;
    private RxManager rxManager = new RxManager();
    private Params params = Params.getInstance();

    public static EditHostFragment newInstance() {
        return new EditHostFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_host, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbarMenu = ((TextView) view.findViewById(R.id.toolbar_menu));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        etName = ((EditText) view.findViewById(R.id.et_name_edit_host_fragment));
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
    }

    private void initData() {
        etName.setText(UserHelper.deviceName);
        toolbarMenu.setOnClickListener(v -> {
            if (TextUtils.isEmpty(etName.getText().toString())) {
                DialogHelper.warningSnackbar(getView(), "主机名称不能为空！");
                return;
            }

            params.name = etName.getText().toString();
            rxManager.add(HttpHelper.getService(ApiService.class)
                    .requestModGateway(ApiService.requestModGateway,
                            params.token,
                            Constants.FC,
                            params.deviceSn,
                            params.name)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(baseBean -> {
                        if (baseBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                            DialogHelper.successSnackbar(getView(), baseBean.getOther().getMessage());
                            Bundle bundle = new Bundle();
                            UserHelper.deviceName = params.name;
                            EventBus.getDefault().post(new EventDeviceNameChanged());
                            bundle.putString("name", params.name);
                            setFragmentResult(HostSettingsFragment.RESULT_HOST_SETTINGS, bundle);
                        } else {
                            error(baseBean.getOther().getMessage());
                        }
                    }, this::error/*, () -> complete(null), disposable -> start("")*/));
        });
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("主机名称");
        toolbarMenu.setText("保存");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        KeyBoardUtils.hideKeybord(etName, App.mContext);
        rxManager.clear();
    }
}
