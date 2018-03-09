package cn.itsite.amain.s1.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import cn.itsite.adialog.dialogfragment.BaseDialogFragment;
import cn.itsite.amain.R;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Author: LiuJia on 2017/11/9 0009 15:58.
 * Email: liujia95me@126.com
 */

public class CameraCountDownDialog extends BaseDialogFragment {
    private static final String TAG = CameraCountDownDialog.class.getSimpleName();
    private ImageView ivLinkLoading;
    private TextView tvCountDown;
    private Subscription subscribe;
    int countdown;
    private boolean isShowing;
    private Button btStop;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutId = R.layout.layout_link_loading;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        ivLinkLoading = ((ImageView) view.findViewById(R.id.iv_link_loading));
        tvCountDown = ((TextView) view.findViewById(R.id.tv_count_down));
        btStop = ((Button) view.findViewById(R.id.btn_stop));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isShowing = true;
        setCancelable(false);
        initAnimator();
        countDown();
        btStop.setOnClickListener(v -> {
            listener.onStopListener();
            dismiss();
        });
    }

    private void initAnimator() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.dialog_scan_rotate);
        ivLinkLoading.startAnimation(animation);//开始动画
    }

    private void countDown() {
        countdown = 60;
        subscribe = Observable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    tvCountDown.setText((--countdown) + "");
                    if (countdown <= 0) {
                        listener.onStopListener();
                        dismiss();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isShowing = false;
        subscribe.unsubscribe();
    }

    public boolean isShowing() {
        return isShowing;
    }

    private OnListener listener;

    public void setOnListener(OnListener listener) {
        this.listener = listener;
    }

    public interface OnListener {
        void onStopListener();
    }
}
