package cn.itsite.amain.yicommunity.main.parking.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.entity.bean.ParkSelectBean.DataBean.ParkPlaceListBean;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * [我的车卡]的View层。
 * 打开方式：StartApp-->管家-->智慧管家[办理车卡]
 */
public class ApplyCardFragment extends BaseFragment {
    public static final String TAG = ApplyCardFragment.class.getSimpleName();
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private ViewPager viewPager;

    public static ApplyCardFragment newInstance() {
        return new ApplyCardFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apply_card, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        viewPager = ((ViewPager) view.findViewById(R.id.viewPager));
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
        toolbarTitle.setText("办理车卡");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    private void initData() {
        viewPager.setAdapter(new ApplyCardAdapter(getChildFragmentManager()));
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        ALog.e("requestCode--" + requestCode);
        ALog.e("resultCode--" + resultCode);
        ALog.e("data--" + data);

        if (data == null) {
            return;
        }
        ParkPlaceListBean parkBean = (ParkPlaceListBean) data.getSerializable(Constants.KEY_PARK);
        if (parkBean != null) {
            ((ApplyFragment) getChildFragmentManager()
                    .getFragments()
                    .get(requestCode))
                    .setPlate(parkBean);
        }
    }
}
