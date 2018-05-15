package com.example.ecmain.mine.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ecmain.BuildConfig;
import com.example.ecmain.R;
import com.example.ecmain.common.UpdateAppHttpUtils;
import com.example.ecmain.entity.AppUpdateBean;
import com.google.gson.Gson;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import cn.itsite.abase.common.BaseConstants;
import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.common.GlideRoundTransform;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.utils.AppUtils;
import cn.itsite.acommon.ApiService;
import cn.itsite.acommon.Constants;
import cn.itsite.web.WebActivity;

/**
 * Author： Administrator on 2018/3/9 0009.
 * Email： liujia95me@126.com
 */

public class AboutFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = AboutFragment.class.getSimpleName();
    private TextInputLayout mTilPhone;
    private View mRlToolbar;
    private TextView mTvVersion;
    private ImageView mIvIcon;

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        mRlToolbar = view.findViewById(R.id.rl_toolbar);
        mTvVersion = view.findViewById(R.id.tv_version);
        mIvIcon = view.findViewById(R.id.iv_icon);
        view.findViewById(R.id.iv_back).setOnClickListener(this);
        view.findViewById(R.id.ll_check_version).setOnClickListener(this);
        view.findViewById(R.id.ll_protocol).setOnClickListener(this);
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
        mTvVersion.setText("版本："+ BuildConfig.VERSION_NAME);
        Glide.with(_mActivity)
                .load(R.mipmap.ic_launcher)
                .apply(new RequestOptions().transform(new GlideRoundTransform(_mActivity, 23)))
                .into(mIvIcon);
    }

    private void initListener() {
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_back){
            pop();
        }else if(v.getId()==R.id.ll_check_version){
            updateApp();
        }else if(v.getId()==R.id.ll_protocol){
            Intent termsIntent = new Intent(_mActivity, WebActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Constants.KEY_TITLE, "服务条款");
            bundle.putString(Constants.KEY_LINK, ApiService.SERVICE_TERMS);
            bundle.putBoolean(BaseConstants.TOOLBAR_COLOR_IS_WHITE,true);
            termsIntent.putExtra("bundle",bundle);
            startActivity(termsIntent);
        }
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
        params.put("sc", Constants.SC);
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
}
