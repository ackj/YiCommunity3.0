package cn.itsite.amain.yicommunity.main.mine.view;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.common.UserHelper;
import cn.itsite.abase.event.EventLogout;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.utils.DensityUtils;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.App;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.DoorManager;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.UnreadMessageBean;
import cn.itsite.amain.yicommunity.event.EventData;
import cn.itsite.amain.yicommunity.event.EventUnread;
import cn.itsite.amain.yicommunity.login.LoginActivity;
import cn.itsite.amain.yicommunity.main.MainActivity;
import cn.itsite.amain.yicommunity.main.about.AboutActivity;
import cn.itsite.amain.yicommunity.main.message.view.MessageCenterFragment;
import cn.itsite.amain.yicommunity.main.mine.contract.MineContract;
import cn.itsite.amain.yicommunity.main.mine.presenter.MinePresenter;
import cn.itsite.amain.yicommunity.main.mypublish.MyPublishActivity;
import cn.itsite.amain.yicommunity.main.view.MainFragment;
import cn.itsite.amain.yicommunity.main.wallet.view.WalletFragment;
import cn.itsite.amain.yicommunity.web.WebActivity;
import jp.wasabeef.glide.transformations.BlurTransformation;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;


/**
 * Author: LiuJia on 2017/4/21 14:25.
 * Email: liujia95me@126.com
 */
public class MineFragment extends BaseFragment<MineContract.Presenter> implements MineContract.View, View.OnClickListener {
    public static final String TAG = MineFragment.class.getSimpleName();
    private ImageView ivHead;
    private TextView tvName;
    private TextView tvPhoneNumber;
    private TextView tvUserData;
    private LinearLayout llMessageCenter;
    private LinearLayout llMyIndent;
    private LinearLayout llMyAddress;
    private LinearLayout llMakeShortcut;
    private LinearLayout llMyPublish;
    private LinearLayout llCleanCache;
    private LinearLayout llAboutUs;
    private LinearLayout llMyHouse;
    private TextView tvLogout;
    private TextView tvCache;
    private ImageView ivHeaderBackground;
    private View viewUnreadMark;
    private ScrollView svMine;
    private Params params = Params.getInstance();
    private LinearLayout mLlMineWallet;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @NonNull
    @Override
    protected MineContract.Presenter createPresenter() {
        return new MinePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ivHead = ((ImageView) view.findViewById(R.id.iv_head_item_comment));
        tvName = ((TextView) view.findViewById(R.id.tv_name));
        tvPhoneNumber = ((TextView) view.findViewById(R.id.tv_phone_number));
        tvUserData = ((TextView) view.findViewById(R.id.tv_user_data));
        llMessageCenter = ((LinearLayout) view.findViewById(R.id.ll_message_center));
        llMyIndent = ((LinearLayout) view.findViewById(R.id.ll_my_indent));
        mLlMineWallet = view.findViewById(R.id.ll_mine_wallet);
        llMyAddress = ((LinearLayout) view.findViewById(R.id.ll_my_address));
        llMakeShortcut = ((LinearLayout) view.findViewById(R.id.ll_make_shortcut));
        llMyPublish = ((LinearLayout) view.findViewById(R.id.ll_my_publish));
        llCleanCache = ((LinearLayout) view.findViewById(R.id.ll_clean_cache));
        llAboutUs = ((LinearLayout) view.findViewById(R.id.ll_about_us));
        llMyHouse = ((LinearLayout) view.findViewById(R.id.ll_my_house));
        tvLogout = ((TextView) view.findViewById(R.id.tv_logout));
        tvCache = ((TextView) view.findViewById(R.id.tv_cache_sum));
        ivHeaderBackground = ((ImageView) view.findViewById(R.id.iv_header_background));
        viewUnreadMark = ((View) view.findViewById(R.id.view_unread_mark_mine_fragment));
        svMine = ((ScrollView) view.findViewById(R.id.sv_mine_fragment));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        initData();
        initListener();
    }

    private void initData() {
        mPresenter.requestCache();
        mPresenter.requestUnreadMark(params);
        //动态给“个人资料”TextView设置drawableLeft并改变其大小
        Drawable drawableLeft = ContextCompat.getDrawable(_mActivity, R.drawable.ic_oneself_info_80px);
        drawableLeft.setBounds(0, 0, DensityUtils.dp2px(App.mContext, 16.f), DensityUtils.dp2px(App.mContext, 16.f));
        tvUserData.setCompoundDrawables(drawableLeft, null, null, null);
        updataView();
    }

    private void initListener() {
        ivHead.setOnClickListener(this);
        tvUserData.setOnClickListener(this);
        llMessageCenter.setOnClickListener(this);
        llMyIndent.setOnClickListener(this);
        llMyAddress.setOnClickListener(this);
        llMakeShortcut.setOnClickListener(this);
        llMyPublish.setOnClickListener(this);
        llCleanCache.setOnClickListener(this);
        llAboutUs.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
        llMyHouse.setOnClickListener(this);
        mLlMineWallet.setOnClickListener(this);
    }

    private boolean isLogined() {
        if (UserHelper.isLogined()) {
            return true;
        } else {
            startActivity(new Intent(_mActivity, LoginActivity.class));
            _mActivity.overridePendingTransition(0, 0);
            return false;
        }
    }

    @Override
    public void start(Object response) {
        if (response instanceof String) {
            tvCache.setText((String) response);
        }
    }

    @Override
    public void error(String errorMessage) {
        super.error(errorMessage);
        DialogHelper.warningSnackbar(getView(), errorMessage);
    }

    private void createShortCut() {
        Intent shortcutIntent = new Intent();
        //设置点击快捷方式时启动的Activity,因为是从Lanucher中启动，所以包名类名要写全。
        shortcutIntent.setComponent(new ComponentName("com.aglhz.yicommunity", "com.aglhz.yicommunity.main.mine.QuickOpenActivity"));
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent resultIntent = new Intent();
        //设置快捷方式图标
        resultIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(_mActivity, R.mipmap.ic_shortcut_red_144px));
        // 不允许重建
        resultIntent.putExtra("duplicate", true);
        //启动的Intent
        resultIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        //设置快捷方式的名称
        resultIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "芝麻开门");
        resultIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        _mActivity.sendBroadcast(resultIntent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(EventData event) {
        if (event.code == Constants.login) {
            if (_mActivity instanceof MainActivity) {
                ((MainActivity) _mActivity).setCallListener();
            }
            updataView();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUnreadEvent(EventUnread event) {
        mPresenter.requestUnreadMark(params);
    }

    private void updataView() {
        if (UserHelper.isLogined()) {
            Glide.with(this)
                    .load(UserHelper.userInfo.getFace())
                    .apply(new RequestOptions()
                            .error(R.drawable.ic_mine_avatar_normal_320px)
                            .placeholder(R.drawable.ic_mine_avatar_normal_320px)
                            .circleCrop())
                    .into(ivHead);
            updateHeaderBackground();

            tvName.setText(UserHelper.userInfo.getNickName());
            tvPhoneNumber.setText(UserHelper.userInfo.getMobile());
            tvLogout.setVisibility(View.VISIBLE);
        }
    }

    private void updateHeaderBackground() {
        Glide.with(this)
                .load(UserHelper.userInfo.getFace())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25)))
                .into(ivHeaderBackground);
    }

    @Override
    public void responseLogout(String message) {
        DialogHelper.successSnackbar(getView(), message);
    }

    @Override
    public void responseCache(String str) {
        tvCache.setText(str);
    }

    @Override
    public void responseUnreadMark(UnreadMessageBean bean) {
        if (bean != null) {
            viewUnreadMark.setVisibility(bean.getData() > 0 ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogoutEvent(EventLogout event) {
        tvName.setText("访客");
        tvPhoneNumber.setText("");
        ivHeaderBackground.setImageResource(R.drawable.bg_mine_1920px_1080px);
        ivHead.setImageResource(R.drawable.ic_mine_avatar_normal_320px);
        tvLogout.setVisibility(View.GONE);
        svMine.post(() -> svMine.fullScroll(ScrollView.FOCUS_UP));//滑动到顶部，提高用户体验，方便用户点击头像登录。
        DoorManager.getInstance().exit();// 停止SipService，用户明确的退出。
        UserHelper.clear();//要放在最后清除，不然上面用到UserHelper.account也为空了
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    private void go2Web(String title, String link) {
        Intent intent = new Intent(getContext(), WebActivity.class);
        intent.putExtra(Constants.KEY_TITLE, title);
        intent.putExtra(Constants.KEY_LINK, link);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_head_item_comment) {
            if (isLogined()) {
                //后面再改成iOS那种
                ALog.e("111111111");
            }
        } else if (i == R.id.ll_my_house) {
            if (isLogined()) {
                ((MainFragment) getParentFragment()).start(MyHousesFragment.newInstance(), SupportFragment.STANDARD);
            }
        } else if (i == R.id.tv_user_data) {
            if (isLogined()) {
                ((MainFragment) getParentFragment()).start(UserDataFragment.newInstance(), SupportFragment.STANDARD);
            }
        } else if (i == R.id.ll_message_center) {
            if (isLogined()) {
                ((SupportActivity) _mActivity).start(MessageCenterFragment.newInstance());
            }
        } else if (i == R.id.ll_mine_wallet) {
            ((SupportActivity) _mActivity).start(WalletFragment.newInstance());
        } else if (i == R.id.ll_my_indent) {
            if (isLogined()) {
                Fragment mineorderfragment = (Fragment) ARouter.getInstance().build("/order/mineorderfragment").navigation();
                ((SupportActivity) _mActivity).start((ISupportFragment) mineorderfragment);
//                String url = ApiService.INDENT_CENTER + UserHelper.token;
//                go2Web("我的订单", url);
//                    _mActivity.start(MineOrderformFragment.newInstance());
            }
        } else if (i == R.id.ll_my_address) {
            if (isLogined()) {
//                String url = ApiService.MY_ADDRESS + UserHelper.token;
//                go2Web("我的地址", url);
                Fragment fragment = (Fragment) ARouter.getInstance().build("/delivery/selectshoppingaddressfragment").navigation();
                ((SupportActivity) _mActivity).start((ISupportFragment) fragment);

            }
        } else if (i == R.id.ll_make_shortcut) {//                if (TextUtils.isEmpty(UserHelper.dir)) {
//                    new AlertDialog.Builder(_mActivity)
//                            .setTitle("提醒")
//                            .setMessage("检测到您尚未设置一键开门，如需设置，请点击确定。")
//                            .setPositiveButton("确定", (dialog, which) -> go2SmartDoor(Constants.SET_OPEN_DOOR))
//                            .setNegativeButton("取消", null)
//                            .show();
//                } else {
            createShortCut();
//                }
        } else if (i == R.id.ll_my_publish) {
            startActivity(new Intent(_mActivity, MyPublishActivity.class));
        } else if (i == R.id.ll_clean_cache) {
            mPresenter.requestClearCache();
        } else if (i == R.id.ll_about_us) {
            startActivity(new Intent(_mActivity, AboutActivity.class));
        } else if (i == R.id.tv_logout) {
            new AlertDialog.Builder(_mActivity)
                    .setTitle("提示")
                    .setMessage("确定退出登录？")
                    .setPositiveButton("确定", (dialog, which) -> {
                        mPresenter.requestLogout(params);//请求服务器注销。
                        onLogoutEvent(null);
                    })
                    .setNegativeButton("取消", null)
                    .show();
        }
    }
}
