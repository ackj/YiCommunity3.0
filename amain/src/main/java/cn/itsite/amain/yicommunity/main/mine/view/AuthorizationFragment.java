package cn.itsite.amain.yicommunity.main.mine.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.amain.R;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by liujia on 2018/5/23.
 * 数据授权
 */
public class AuthorizationFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = AuthorizationFragment.class.getSimpleName();
    private Toolbar mToolbar;
    private TextView mToolbarTitle;

    public static AuthorizationFragment newInstance(){
        return new AuthorizationFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authorization, container, false);
        mToolbar = view.findViewById(R.id.toolbar);
        mToolbarTitle = view.findViewById(R.id.toolbar_title);
        view.findViewById(R.id.ll_car_turnover_data).setOnClickListener(this);
        view.findViewById(R.id.ll_gate_data).setOnClickListener(this);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
    }

    private void initToolbar() {
        initStateBar(mToolbar);
        mToolbarTitle.setText("数据授权");
        mToolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        mToolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.ll_car_turnover_data){
            start(CarTurnoverDataFragment.newInstance());
        }else if(view.getId()==R.id.ll_gate_data){
            start(GateguardDataFragment.newInstance());
        }
    }
}
