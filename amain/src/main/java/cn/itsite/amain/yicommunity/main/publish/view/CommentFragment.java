package cn.itsite.amain.yicommunity.main.publish.view;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Method;
import java.util.List;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.utils.KeyBoardUtils;
import cn.itsite.abase.utils.ScreenUtils;
import cn.itsite.abase.utils.ToastUtils;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.abase.common.BaseBean;
import cn.itsite.amain.yicommunity.entity.bean.CommentBean;
import cn.itsite.amain.yicommunity.event.EventCommunity;
import cn.itsite.amain.yicommunity.event.EventRefreshRemarkList;
import cn.itsite.amain.yicommunity.event.EventRefreshSocialityList;
import cn.itsite.amain.yicommunity.event.KeyboardChangeListener;
import cn.itsite.amain.yicommunity.main.publish.contract.CommentContract;
import cn.itsite.amain.yicommunity.main.publish.presenter.CommentPresenter;
import cn.itsite.amain.yicommunity.main.sociality.view.SocialityListFragment;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author: LiuJia on 2017/5/11 0011 15:52.
 * Email: liujia95me@126.com
 * [评论]的View层
 */

public class CommentFragment extends BaseFragment<CommentContract.Presenter> implements CommentContract.View {
    public static final String TAG = CommentFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private PtrFrameLayout ptrFrameLayout;
    private EditText etInput;
    private TextView toolbarMenu;
    private View viewBottomSpace;
    private CommentRVAdapter adapter;
    private Params commentListParams = Params.getInstance();
    private Params commentPostParams = Params.getInstance();
    private String fid;
    private int type;
    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener;
    String[] arr = {"回复", "复制"};
    private String replyName = "";
    private TextView tvSend;

    public static CommentFragment newInstance(String fid, int type) {
        ALog.e(TAG, " fid:" + fid);
        ALog.e(TAG, "newInstance type:" + type);
        CommentFragment fragment = new CommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_FID, fid);
        bundle.putInt(Constants.KEY_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            fid = bundle.getString(Constants.KEY_FID);
            type = bundle.getInt(Constants.KEY_TYPE);
        }
    }

    @NonNull
    @Override
    protected CommentContract.Presenter createPresenter() {
        return new CommentPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        recyclerView = ((RecyclerView) view.findViewById(R.id.recyclerView));
        ptrFrameLayout = ((PtrFrameLayout) view.findViewById(R.id.ptrFrameLayout));
        etInput = ((EditText) view.findViewById(R.id.et_input_fragment_comment));
        toolbarMenu = ((TextView) view.findViewById(R.id.toolbar_menu));
        viewBottomSpace = ((View) view.findViewById(R.id.view_bottom_space));
        tvSend = ((TextView) view.findViewById(R.id.tv_send_fragment_comment));
        EventBus.getDefault().register(this);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        initPtrFrameLayout(ptrFrameLayout, recyclerView);
        initListener();
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("评论留言");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    @Override
    public void onRefresh() {
        commentListParams.fid = fid;
        requestComments();//请求评论列表
    }

    private KeyboardChangeListener mKeyboardChangeListener;

    private boolean isVisiableForLast;

    @SuppressLint("SetTextI18n")
    private void initListener() {
        mKeyboardChangeListener = new KeyboardChangeListener(_mActivity);
        mKeyboardChangeListener.setKeyBoardListener((isShow, keyboardHeight) -> ALog.e(TAG, "isShow = [" + isShow + "], keyboardHeight = [" + keyboardHeight + "]"));

        final View decorView = _mActivity.getWindow().getDecorView();
        //计算出可见屏幕的高度
        //获得屏幕整体的高度
        //获得键盘高度
        globalLayoutListener = () -> {
            Rect rect = new Rect();
            decorView.getWindowVisibleDisplayFrame(rect);
            //计算出可见屏幕的高度
            int displayHight = rect.bottom - rect.top;
            //获得屏幕整体的高度
            int hight = decorView.getHeight();
            //获得键盘高度
            int keyboardHeight = hight - displayHight;
            boolean visible = (double) displayHight / hight < 0.8;
            if (visible != isVisiableForLast) {
                listener.onSoftKeyBoardVisible(visible, keyboardHeight);
            }
            isVisiableForLast = visible;
        };
        //注册布局变化监听
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);

        adapter.setOnItemClickListener((adapter, view, position) -> {
            CommentBean bean = (CommentBean) adapter.getData().get(position);
            AlertDialog.Builder builder = new AlertDialog.Builder(_mActivity);
            builder.setItems(arr, (dialog, which) -> {
                switch (which) {
                    case 0:
                        String inputContent = etInput.getText().toString();
                        if (!TextUtils.isEmpty(inputContent) && inputContent.contains(replyName)) {
                            inputContent = inputContent.substring(replyName.length());
                        }
                        replyName = "@" + bean.getMember().getMemberNickName() + " ";
                        if (!inputContent.startsWith(replyName)) {
                            etInput.setText(replyName + inputContent);
                        }
                        etInput.setSelection(etInput.getText().toString().length());
                        break;
                    case 1:
                        ClipboardManager cm = (ClipboardManager) _mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                        cm.setPrimaryClip(ClipData.newPlainText(null, bean.getContent()));
                        ToastUtils.showToast(_mActivity, "复制成功");
                        break;
                    default:
                }
            });
            builder.show();
        });
    }

    IKeyBoardVisibleListener listener = (visible, windowBottom) -> {
        windowBottom -= ScreenUtils.getStatusBarHeight(_mActivity);
        if (checkDeviceHasNavigationBar(_mActivity)) {
            windowBottom -= getBottomKeyboardHeight();
        }
        if (visible) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, windowBottom);
            viewBottomSpace.setLayoutParams(lp);
        } else {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
            viewBottomSpace.setLayoutParams(lp);
        }
    };

    interface IKeyBoardVisibleListener {
        void onSoftKeyBoardVisible(boolean visible, int windowBottom);
    }

    //获取是否存在NavigationBar
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
    }

    public int getBottomKeyboardHeight() {
        int screenHeight = getAccurateScreenDpi()[1];
        DisplayMetrics dm = new DisplayMetrics();
        _mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int heightDifference = screenHeight - dm.heightPixels;
        return heightDifference;
    }

    /**
     * 获取精确的屏幕大小
     */
    public int[] getAccurateScreenDpi() {
        int[] screenWH = new int[2];
        Display display = _mActivity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            Class<?> c = Class.forName("android.view.Display");
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            screenWH[0] = dm.widthPixels;
            screenWH[1] = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenWH;
    }

    private void initData() {
        tvSend.setOnClickListener(v -> sendComment());
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        adapter = new CommentRVAdapter();
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(() -> {
            commentListParams.page++;
            requestComments();//请求评论列表
        }, recyclerView);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 请求评论列表
     */
    private void requestComments() {
        switch (type) {
            case SocialityListFragment.TYPE_EXCHANGE:
            case SocialityListFragment.TYPE_MY_EXCHANGE:
                mPresenter.requestExchangeCommentList(commentListParams);//请求闲置交换的评论列表
                break;
            case SocialityListFragment.TYPE_CARPOOL_OWNER:
            case SocialityListFragment.TYPE_CARPOOL_PASSENGER:
            case SocialityListFragment.TYPE_MY_CARPOOL:
                mPresenter.requestCarpoolCommentList(commentListParams);//请求拼车服务的评论列表
                break;
            case SocialityListFragment.TYPE_NEIGHBOUR:
            case SocialityListFragment.TYPE_MY_NEIGHBOUR:
                mPresenter.requestNeighbourCommentList(commentListParams);//请求左邻右里的评论列表
                break;
            case Constants.TYPE_REMARK:
                mPresenter.requestRemarkReplyList(commentListParams);//请求社区服务点评回复列表。
                break;
            default:
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        KeyBoardUtils.hideKeybord(getView(), _mActivity);
        //移除布局变化监听
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            _mActivity.getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(globalLayoutListener);
        } else {
            _mActivity.getWindow().getDecorView().getViewTreeObserver().removeGlobalOnLayoutListener(globalLayoutListener);
        }
    }

    @Override
    public void start(Object response) {

    }

    @Override
    public void error(String errorMessage) {
        super.error(errorMessage);
        ptrFrameLayout.refreshComplete();
        DialogHelper.warningSnackbar(getView(), errorMessage);//后面换成pagerstate的提示，不需要这种了
        if (commentListParams.page == 1) {
            //为后面的pageState做准备
//            mStateManager.showError();
        } else if (commentListParams.page > 1) {
            adapter.loadMoreFail();
            commentListParams.page--;
        }
    }

    /**
     * 响应请求评论列表
     *
     * @param datas
     */
    @Override
    public void responseCommentList(List<CommentBean> datas) {
        ptrFrameLayout.refreshComplete();
        if (datas == null || datas.isEmpty()) {
            if (commentListParams.page == 1) {
//                mStateManager.showEmpty();
            }
            adapter.loadMoreEnd();
            return;
        }

        if (commentListParams.page == 1) {
//            mStateManager.showContent();
            adapter.setNewData(datas);
            adapter.disableLoadMoreIfNotFullPage(recyclerView);
        } else {
            adapter.addData(datas);
            adapter.setEnableLoadMore(true);
            adapter.loadMoreComplete();
        }
    }

    /**
     * 响应请求发送评论成功
     *
     * @param bean
     */
    @Override
    public void responseCommentSuccess(BaseBean bean) {
        etInput.setText("");
        ptrFrameLayout.autoRefresh();
        if (type == Constants.TYPE_REMARK) {//因为邻里、闲置交换、拼车服务、点评都是公用这一个，所以最好判断一下，避免不必要的刷新。
            EventBus.getDefault().post(new EventRefreshRemarkList());
        } else {
            EventBus.getDefault().post(new EventRefreshSocialityList());
        }
    }

    /**
     * 发送评论
     */
    private void sendComment() {
        String comment = etInput.getText().toString();
        if (TextUtils.isEmpty(comment)) {
            DialogHelper.warningSnackbar(getView(), "请输入评论内容！不能为空");
            return;
        }
        if (containsEmoji(comment)) {
            DialogHelper.warningSnackbar(getView(), "“抱歉，评论中不能含有表情”");
            return;
        }
        commentPostParams.fid = fid;
        commentPostParams.content = comment;
        switch (type) {
            case SocialityListFragment.TYPE_EXCHANGE:
            case SocialityListFragment.TYPE_MY_EXCHANGE:
                mPresenter.requestSubmitExchangeComment(commentPostParams);//请求提交闲置交换评论
                break;
            case SocialityListFragment.TYPE_CARPOOL_OWNER:
            case SocialityListFragment.TYPE_CARPOOL_PASSENGER:
            case SocialityListFragment.TYPE_MY_CARPOOL:
                mPresenter.requestSubmitCarpoolComment(commentPostParams);//请求提交拼车服务评论
                break;
            case SocialityListFragment.TYPE_NEIGHBOUR:
            case SocialityListFragment.TYPE_MY_NEIGHBOUR:
                mPresenter.requestSubmitNeighbourComment(commentPostParams);//请求提交左邻右里评论
                break;
            case Constants.TYPE_REMARK:
                mPresenter.requestSubmitRemark(commentPostParams);//请求社区服务点评。
                break;
            default:
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventCommunity event) {
        ptrFrameLayout.autoRefresh();
    }

    // 判别是否包含Emoji表情
    private static boolean containsEmoji(String str) {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (isEmojiCharacter(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }
}
