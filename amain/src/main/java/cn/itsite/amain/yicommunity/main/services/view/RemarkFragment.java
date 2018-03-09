package cn.itsite.amain.yicommunity.main.services.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.Params;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: leguang on 2017/4/21 9:14.
 * Email: langmanleguang@qq.com
 * <p>
 * [点评]的View层。
 * 打开方式：AppStart-->首页-->社区服务列表-->服务详情-->点评。
 */
public class RemarkFragment extends BaseFragment {
    public static final String TAG = RemarkFragment.class.getSimpleName();
    public static final int RESULT_RECORD = 110;
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private RatingBar rbRemark;
    private EditText etDescribe;
    private Button btSubmit;
    private Params params = Params.getInstance();
    private String title;

    /**
     * 该页的入口
     *
     * @param commodityFid 请求详情接口需要的fid
     * @return
     */
    public static RemarkFragment newInstance(String commodityFid, String firmName) {
        Bundle args = new Bundle();
        args.putString(Constants.COMMODITY_FID, commodityFid);
        args.putString(Constants.FIRM_NAME, firmName);
        RemarkFragment fragment = new RemarkFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            params.commodityFid = args.getString(Constants.COMMODITY_FID);
            title = args.getString(Constants.FIRM_NAME);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remark_services, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        rbRemark = ((RatingBar) view.findViewById(R.id.rb_remark_services_fragment));
        etDescribe = ((EditText) view.findViewById(R.id.et_describe_remark_services_fragment));
        btSubmit = ((Button) view.findViewById(R.id.bt_submit_remark_services_fragment));
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText(TextUtils.isEmpty(title) ? "点评" : title);
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> pop());
    }

    private void initData() {
        btSubmit.setOnClickListener(v -> {
            ALog.e("NumStars-->" + rbRemark.getNumStars());
            ALog.e("getRating-->" + rbRemark.getRating());
            ALog.e("getStepSize-->" + rbRemark.getStepSize());

            if (TextUtils.isEmpty(etDescribe.getText().toString())) {
                DialogHelper.warningSnackbar(getView(), "评论不能为空");
                return;
            }

            if (etDescribe.getText().toString().length() < 10) {
                DialogHelper.warningSnackbar(getView(), "评论字数不能小于10");
                return;
            }

            showLoading();
            params.startLevel = (int) rbRemark.getRating();
            params.content = etDescribe.getText().toString();
            HttpHelper.getService(ApiService.class)
                    .requestRemarkService(ApiService.requestRemarkService,
                            params.token,
                            params.commodityFid,
                            params.startLevel,
                            params.content)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(bean -> {
                        dismissLoading();
                        if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                            DialogHelper.successSnackbar(getView(), bean.getOther().getMessage());
                            setFragmentResult(RESULT_RECORD, new Bundle());
                            pop();
                        } else {
                            DialogHelper.errorSnackbar(getView(), bean.getOther().getMessage());
                        }
                    }, throwable -> {
                        dismissLoading();
                        DialogHelper.errorSnackbar(getView(), "网络异常");
                    });
        });
    }
}
