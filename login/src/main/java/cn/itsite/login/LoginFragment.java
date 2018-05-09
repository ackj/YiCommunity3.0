package cn.itsite.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import cn.itsite.abase.BaseApp;
import cn.itsite.abase.common.BaseConstants;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.acommon.ApiService;
import cn.itsite.acommon.Constants;
import cn.itsite.acommon.ContentViewPager;

/**
 * Author： Administrator on 2018/3/7 0007.
 * Email： liujia95me@126.com
 */
@Route(path = "/login/loginfragment")
public class LoginFragment extends BaseFragment implements View.OnClickListener {


    private static final String TAG = LoginFragment.class.getSimpleName();
    private ContentViewPager mViewPager;
    private MagicIndicator mMagicIndicator;
    private ImageView mIvBack;
    private LoginVPAdapter mAdapter;
    private LinearLayout mLlProtocol;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mViewPager = view.findViewById(R.id.viewPager);
        mIvBack = view.findViewById(R.id.iv_back);
        mMagicIndicator = view.findViewById(R.id.magicIndicator);
        view.findViewById(R.id.tv_protocol).setOnClickListener(this);
        mLlProtocol = view.findViewById(R.id.ll_protocol);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initStateBar(mIvBack);
        initData();
        initListener();
        initMagicIndicator();
    }

    private void initData() {
        mAdapter = new LoginVPAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
    }

    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(_mActivity);
        commonNavigator.setScrollPivotX(0.65f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            private String[] mTitles = BaseApp.mContext.getResources().getStringArray(R.array.login_register_tabs);

            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(mTitles[index]);
                simplePagerTitleView.setNormalColor(_mActivity.getResources().getColor(R.color.tpf_white));
                simplePagerTitleView.setSelectedColor(_mActivity.getResources().getColor(R.color.white));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 3));
                indicator.setLineWidth(UIUtil.dip2px(context, 18));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setYOffset(UIUtil.dip2px(context, 4));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(_mActivity.getResources().getColor(R.color.white));
                return indicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(getResources().getDrawable(R.drawable.simple_splitter));
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }

    private void initListener() {
        mIvBack.setOnClickListener(this);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    mLlProtocol.setVisibility(View.GONE);
                }else{
                    mLlProtocol.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_back){
            _mActivity.finish();
        }else if(v.getId()==R.id.tv_protocol){
            Bundle bundle = new Bundle();
            bundle.putString(Constants.KEY_TITLE, "服务条款");
            bundle.putString(Constants.KEY_LINK, ApiService.SERVICE_TERMS);
            bundle.putBoolean(BaseConstants.TOOLBAR_COLOR_IS_WHITE,true);
            ARouter.getInstance().build("/web/webactivity").withBundle("bundle",bundle)
                    .navigation();
        }
    }


}
