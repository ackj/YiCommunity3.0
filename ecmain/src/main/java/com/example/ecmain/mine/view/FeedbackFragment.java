package com.example.ecmain.mine.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.ecmain.R;
import com.example.ecmain.mine.contract.FeedbackContract;
import com.example.ecmain.mine.presenter.FeedbackPresenter;

import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.network.http.BaseOldResponse;

/**
 * Author： Administrator on 2018/3/9 0009.
 * Email： liujia95me@126.com
 */
@Route(path = "/mine/minefragment")
public class FeedbackFragment extends BaseFragment<FeedbackContract.Presenter> implements View.OnClickListener,FeedbackContract.View {

    private static final String TAG = FeedbackFragment.class.getSimpleName();
    private View mRlToolbar;
    private EditText mEtContent;

    public static FeedbackFragment newInstance() {
        return new FeedbackFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    protected FeedbackContract.Presenter createPresenter() {
        return new FeedbackPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        mRlToolbar = view.findViewById(R.id.rl_toolbar);
        view.findViewById(R.id.iv_back).setOnClickListener(this);
        view.findViewById(R.id.tv_submit).setOnClickListener(this);
        mEtContent = view.findViewById(R.id.et_content);
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
    }

    private void initListener() {
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_back){
            pop();
        }else if(v.getId()==R.id.tv_submit){
            String content = mEtContent.getText().toString().trim();
            if(TextUtils.isEmpty(content)){
                DialogHelper.warningSnackbar(getView(),"请输入内容");
                return;
            }
            mPresenter.requestFeedback(content);
        }
    }

    @Override
    public void responseFeedback(BaseOldResponse response) {
        DialogHelper.successSnackbar(_mActivity.getCurrentFocus(),response.getOther().getMessage());
        pop();
    }
}
