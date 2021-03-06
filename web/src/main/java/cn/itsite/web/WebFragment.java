package cn.itsite.web;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.itsite.abase.common.BaseConstants;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author：leguang on 2017/4/12 0009 15:49
 * Email：langmanleguang@qq.com
 * <p>
 * 负责项目中的web部分。
 */

@Route(path = "/web/webfragment")
public class WebFragment extends BaseFragment {
    public static final String TAG = WebFragment.class.getSimpleName();
    private WebView mWebView;
    private PtrFrameLayout ptrFramlayout;
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private TextView toolbarMenu;
    private String title;
    private String link;
    private Boolean isWhite;

    public static WebFragment newInstance(Bundle bundle) {
//        ALog.e(TAG, "link-->" + bundle.getString("link"));
        WebFragment fragment = new WebFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        title = bundle.getString(BaseConstants.KEY_TITLE);
        link = bundle.getString(BaseConstants.KEY_LINK);
        isWhite = bundle.getBoolean(BaseConstants.TOOLBAR_COLOR_IS_WHITE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        mWebView = view.findViewById(R.id.wv_web_fragment);
        toolbarTitle = view.findViewById(R.id.toolbar_title);
        toolbar = view.findViewById(R.id.toolbar);
        toolbarMenu = view.findViewById(R.id.toolbar_menu);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initWebView();
    }

    private void initToolbar() {
        initStateBar(toolbar);
        if (!TextUtils.isEmpty(title)) {
            toolbarTitle.setText(title);
        }
        if(isWhite){
            toolbar.setBackgroundColor(getResources().getColor(R.color.white));
            toolbarTitle.setTextColor(getResources().getColor(R.color.base_black));
            toolbar.setNavigationIcon(R.drawable.ic_arrow_left_gray_24dp);
        }else{
            toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        mWebView.clearCache(true);
        mWebView.clearHistory();

        WebSettings webSettings = mWebView.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //webview自适应屏幕
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                link = url;
                if (ptrFramlayout != null) {
                    ptrFramlayout.refreshComplete();
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                link = url;
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, true);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                ALog.e("onReceiveValue::" + message);
                return super.onJsAlert(view, url, message, result);
            }
        });
        onRefresh();
    }

    @Override
    public void onRefresh() {
        mWebView.loadUrl(link);
    }

    @Override
    public boolean onBackPressedSupport() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            pop();
            _mActivity.finish();
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
            if (mWebView.getParent() != null) {
                ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            }
            mWebView = null;
        }
    }
}
