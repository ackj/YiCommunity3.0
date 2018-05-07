package cn.itsite.amain.yicommunity.main.about.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.utils.AppUtils;
import cn.itsite.amain.BuildConfig;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.App;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.PermissionHelper;
import cn.itsite.amain.yicommunity.common.appupdate.UpdateAppHttpUtils;
import cn.itsite.amain.yicommunity.entity.bean.AppUpdateBean;
import cn.itsite.amain.yicommunity.web.WebActivity;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author: LiuJia on 2017/4/21 17:41.
 * Email: liujia95me@126.com
 * <p>
 * [关于我们]的View层。
 * 打开方式：Start App-->我的-->关于我们
 */
public class AboutUsFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = AboutUsFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private TextView tvVersionName;
    private LinearLayout llProductIntroduction;
    private LinearLayout llServiceTerms;
    private LinearLayout llFeedback;
    private TextView tvCheckUpdate;
    private LinearLayout llPermission;

    public static AboutUsFragment newInstance() {
        return new AboutUsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        tvVersionName = ((TextView) view.findViewById(R.id.tv_version_name));
        llProductIntroduction = ((LinearLayout) view.findViewById(R.id.ll_product_introduction));
        llServiceTerms = ((LinearLayout) view.findViewById(R.id.ll_service_terms));
        llFeedback = ((LinearLayout) view.findViewById(R.id.ll_feedback));
        llPermission = ((LinearLayout) view.findViewById(R.id.ll_permission));
        tvCheckUpdate = ((TextView) view.findViewById(R.id.tv_check_update_about_us_fragment));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        initListener();
    }

    private void initListener() {
        llProductIntroduction.setOnClickListener(this);
        llServiceTerms.setOnClickListener(this);
        llFeedback.setOnClickListener(this);
        llPermission.setOnClickListener(this);
        tvCheckUpdate.setOnClickListener(this);
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("关于我们");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        tvVersionName.setText("版本：" + AppUtils.getVersionName(App.mContext));
    }

    /**
     * 检测是否有新版本需要下载更新。
     */
    public void  updateApp() {
        String random = System.currentTimeMillis() + "";
        String accessKey = Constants.SYS_ACCESS_PREFIX + random + Constants.SYS_ACCESS_KEY;
        ALog.e("random-->" + random);
        ALog.e("accessKey-->" + accessKey);
        ALog.e("getMd5(accessKey)-->" + getMd5(accessKey));

        Map<String, String> params = new HashMap<>();
        params.put("accessKey", getMd5(accessKey));
        params.put("random", random);
        params.put("sc", BuildConfig.SC);
        params.put("appType", Constants.APP_TYPE);

        new UpdateAppManager
                .Builder()
                .setActivity(_mActivity)
                .setHttpManager(new UpdateAppHttpUtils())
                .setUpdateUrl(Constants.APP_UPDATE_URL)
                .setPost(true)
                .setParams(params)
                .dismissNotificationProgress()
                .hideDialogOnDownloading(false)
                .build()
                .checkNewApp(new UpdateCallback() {
                    /**
                     * 解析json,自定义协议
                     *
                     * @param json 服务器返回的json
                     * @return UpdateAppBean
                     */
                    @Override
                    protected UpdateAppBean parseJson(String json) {
                        ALog.e("json-->" + json);
                        UpdateAppBean updateAppBean = new UpdateAppBean();
                        AppUpdateBean mAppUpdateBean = new Gson().fromJson(json, AppUpdateBean.class);
                        ALog.e("AppUtils.getVersionCode(_mActivity)-->" + AppUtils.getVersionCode(_mActivity));
                        updateAppBean
                                //（必须）是否更新Yes,No
                                .setUpdate(AppUtils.getVersionCode(_mActivity) < mAppUpdateBean.getData().getVersionCode() ? "Yes" : "No")
                                //（必须）新版本号，
                                .setNewVersion(mAppUpdateBean.getData().getVersionName())
                                //（必须）下载地址
                                .setApkFileUrl(mAppUpdateBean.getData().getUrl())
                                //（必须）更新内容
                                .setUpdateLog(mAppUpdateBean.getData().getDescription())
                                //大小，不设置不显示大小，可以不设置
                                .setTargetSize(mAppUpdateBean.getData().getSize())
                                //是否强制更新，可以不设置
                                .setConstraint(mAppUpdateBean.getData().isIsForce());

                        return updateAppBean;
                    }

                    @Override
                    protected void hasNewApp(UpdateAppBean updateApp, UpdateAppManager updateAppManager) {
                        updateAppManager.showDialogFragment();
                    }

                    /**
                     * 网络请求之前
                     */
                    @Override
                    public void onBefore() {
                        showLoading();
                    }

                    /**
                     * 网路请求之后
                     */
                    @Override
                    public void onAfter() {
                        dismissLoading();
                    }

                    /**
                     * 没有新版本
                     */
                    @Override
                    public void noNewApp() {
                        DialogHelper.successSnackbar(getView(), "当前版本已是最新版本");
                    }
                });
    }

    private String getMd5(String str) {
        StringBuffer sb = new StringBuffer();
        try {
            //获取加密方式为md5的算法对象
            MessageDigest instance = MessageDigest.getInstance("MD5");
            byte[] digest = instance.digest(str.getBytes());
            for (byte b : digest) {
                String hexString = Integer.toHexString(b & 0xff);
                if (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                sb = sb.append(hexString);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ll_product_introduction) {
            Intent introductionIntent = new Intent(_mActivity, WebActivity.class);
            introductionIntent.putExtra(Constants.KEY_TITLE, "产品介绍");
            introductionIntent.putExtra(Constants.KEY_LINK, ApiService.PRODUCT_INTRODUCTION);
            startActivity(introductionIntent);
        } else if (i == R.id.ll_service_terms) {
            Intent termsIntent = new Intent(_mActivity, WebActivity.class);
            termsIntent.putExtra(Constants.KEY_TITLE, "服务条款");
            termsIntent.putExtra(Constants.KEY_LINK, ApiService.SERVICE_TERMS);
            startActivity(termsIntent);

        } else if (i == R.id.ll_feedback) {
            start(FeedbackFragment.newInstance());
        } else if (i == R.id.ll_permission) {
            new PermissionHelper(_mActivity).gotoPermission();
        } else if (i == R.id.tv_check_update_about_us_fragment) {
            updateApp();
        }
    }
}
