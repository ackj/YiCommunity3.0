package cn.itsite.order.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.ArrayList;
import java.util.List;

import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.utils.ScreenUtils;
import cn.itsite.acommon.data.GoodsParams;
import cn.itsite.order.model.CategoryBean;
import cn.itsite.order.R;
import cn.itsite.order.contract.MineOrderContract;
import cn.itsite.order.presenter.MineOrderPresenter;

/**
 * Author： Administrator on 2018/2/1 0001.
 * Email： liujia95me@126.com
 */
@Route(path = "/order/mineorderfragment")
public class MineOrderFragment extends BaseFragment<MineOrderContract.Presenter> implements MineOrderContract.View {

    public static final String TAG = MineOrderFragment.class.getSimpleName();

    private RelativeLayout mLlToolbar;
    private SubmitOrderRVAdapter mAdapter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private GoodsParams mGoodsParams = new GoodsParams();
    private CategoryBean mAllCategory;
    private List<CategoryBean> mCategories;
    private ImageView mIvBack;
    private String category;

    public static MineOrderFragment newInstance(String category) {
        MineOrderFragment fragment =  new MineOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category",category);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static MineOrderFragment newInstance() {
        return new MineOrderFragment();
    }

    @NonNull
    @Override
    protected MineOrderContract.Presenter createPresenter() {
        return new MineOrderPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        category = getArguments().getString("category");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_order, container, false);
        mLlToolbar = view.findViewById(R.id.rl_toolbar);
        mTabLayout = view.findViewById(R.id.tabLayout);
        mViewPager = view.findViewById(R.id.viewPager);
        mIvBack = view.findViewById(R.id.iv_back);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initStatusBar();
        initData();
        initListener();
    }

    private void initStatusBar() {
        mLlToolbar.setPadding(mLlToolbar.getPaddingLeft(), mLlToolbar.getPaddingTop() + ScreenUtils.getStatusBarHeight(_mActivity), mLlToolbar.getPaddingRight(), mLlToolbar.getPaddingBottom());
    }

    private void initData() {
        mAllCategory = new CategoryBean();
        mAllCategory.setCategory("全部");
        mCategories = new ArrayList<>();
        mCategories.add(mAllCategory);

        mGoodsParams.type = "orders";
        mPresenter.getCategories(mGoodsParams);
    }

    private void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
    }

    @Override
    public void responseGetCategories(List<CategoryBean> data) {
        mCategories.addAll(data);
        MineOrderVPAdapter mAdapter = new MineOrderVPAdapter(getChildFragmentManager(), mCategories);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        if(!TextUtils.isEmpty(category)){
            for (int i = 0; i < data.size(); i++) {
                if(data.get(i).getCategory().equals(category)){
                    mViewPager.setCurrentItem(i+1);
                    break;
                }
            }
        }
    }
}
