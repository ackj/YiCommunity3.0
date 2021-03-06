package cn.itsite.amain.yicommunity.main.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.common.RxManager;
import cn.itsite.abase.common.UserHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseActivity;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.abase.utils.ToastUtils;
import cn.itsite.adialog.dialog.LoadingDialog;
import cn.itsite.amain.yicommunity.common.ApiService;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.entity.bean.OneKeyDoorBean;
import cn.itsite.amain.yicommunity.widget.OpenDoorDialog;
import cn.itsite.multiselector.MultiSelectorDialog;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by leguang on 2017/4/29 0029.
 * Email：langmanleguang@qq.com
 */

public class QuickOpenActivity extends BaseActivity {
    private ViewGroup rootView;
    private RxManager rxManager = new RxManager();
    private OpenDoorDialog openDoorDialog;
    private LoadingDialog loadingDialog;
    private List<OneKeyDoorBean.DataBean.ItemListBean> oneKeyDoorList = new ArrayList<>();
    private MultiSelectorDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = (ViewGroup) findViewById(android.R.id.content).getRootView();
        UserHelper.init();
        showLoadingDialog();
        rxManager.add(HttpHelper.getService(ApiService.class)
                .requestOneKeyOpenDoorDeviceList(ApiService.requestOneKeyOpenDoorDeviceList, UserHelper.token, UserHelper.communityCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(oneKeyDoorBean -> {
                    dismissLoadingDialog();
                    oneKeyDoorList = oneKeyDoorBean.getData().getItemList();
                    if (oneKeyDoorList.size() == 0) {
                        ToastUtils.showToast(this, "该社区没有指定开门");
                    } else if (oneKeyDoorList.size() == 1) {
                        UserHelper.dir = oneKeyDoorList.get(0).getDir();
                        openDoor();
                    } else {

                        dialog = MultiSelectorDialog.builder(QuickOpenActivity.this)
                                .setTitle("请选择开哪扇门")
                                .setTabVisible(false)
                                .setOnItemClickListener((pagerPosition, optionPosition, option) -> {
                                    dialog.dismiss();
                                    ALog.e("pagerPosition-->" + pagerPosition + "\r\noptionPosition-->" + optionPosition + "\r\noption-->" + option);
                                    UserHelper.dir = oneKeyDoorList.get(optionPosition).getDir();
                                    openDoor();
                                })
                                .show();

                        dialog.setOnCancelListener(dialog1 -> finish());

                        List<String> list = new ArrayList<>();
                        for (OneKeyDoorBean.DataBean.ItemListBean itemListBean : oneKeyDoorList) {
                            list.add(itemListBean.getName());
                        }
                        rootView.post(() -> dialog.notifyDataSetChanged(list));

                    }
                }, throwable -> {
                    exit();
                    dismissLoadingDialog();
                    DialogHelper.errorSnackbar(rootView, "网络异常，请重试！");
                })
        );


        /**
         * 考虑重新登录问题，因为当token失效后要登录。注意登录最好搞记住密码，用户只要直接点击登录就最好。不过密码也最好有个失效，这样ACache框架就有价值。
         */

        /**
         * 考虑失败后，有一个可以重试的按钮。否则就尴尬了，
         * 得不停的等下面的1500ms。当然这个也有点鸡肋，如果能做到在桌面顶部弹框提示的话，就不需要Activity做支撑了。
         * 所以也不需要有一个界面了，不然就违背了快速这一初衷，本来打开失败就应该不停的点图标。
         */
    }

    private void openDoor() {
        showQuickOpenDoorDialog();
        rootView.postDelayed(() -> {
            rxManager.add(HttpHelper.getService(ApiService.class).requestOpenDoor(ApiService.requestOpenDoor, UserHelper.token, UserHelper.dir)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(baseBean -> {
                        exit();
                        if (baseBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                            openDoorDialog.setSuccess();
                            DialogHelper.successSnackbar(rootView, "开门成功，欢迎回家，我的主人！");
                        } else {
                            openDoorDialog.setError();
                            DialogHelper.errorSnackbar(rootView, baseBean.getOther().getMessage());
                        }
                    }, throwable -> {
                        exit();
                        openDoorDialog.setError();
                        DialogHelper.errorSnackbar(rootView, "网络异常，请重试！");
                    })
            );
        }, 1000);
    }

    public void showQuickOpenDoorDialog() {
        if (openDoorDialog == null) {
            openDoorDialog = new OpenDoorDialog(this);
        }
        openDoorDialog.setOpenDoor();
        openDoorDialog.show();
    }

    private void exit() {
        rootView.postDelayed(this::finish, 2000);//这里尽量设置大一点，因为敲门的动画设置是1500ms结束，如果低于1500，那Activity都结束了，可是动画还在，会有泄露。
    }

    public void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
    }

    public void dismissLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        if (rxManager != null) {
            rxManager.clear();
        }
        super.onDestroy();
    }
}
