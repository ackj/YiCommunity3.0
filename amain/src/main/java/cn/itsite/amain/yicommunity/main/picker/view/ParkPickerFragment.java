package cn.itsite.amain.yicommunity.main.picker.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.List;

import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.utils.KeyBoardUtils;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.App;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.acommon.UserHelper;
import cn.itsite.amain.yicommunity.entity.bean.ParkSelectBean;
import cn.itsite.amain.yicommunity.entity.db.ParkHistoryData;
import cn.itsite.amain.yicommunity.main.picker.contract.ParkPickerContract;
import cn.itsite.amain.yicommunity.main.picker.presenter.ParkPickerPresenter;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.yokeyword.fragmentation.SupportActivity;


/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 选择停车场模块。
 */
public class ParkPickerFragment extends BaseFragment<ParkPickerContract.Presenter> implements ParkPickerContract.View {
    private static final String TAG = ParkPickerFragment.class.getSimpleName();
    private PtrFrameLayout ptrFrameLayout;
    private RecyclerView recyclerView;
    private EditText etSearch;
    private ParkPickerAdapter adapter;
    private TextView tvCity;
    private TextView tvTitle;
    private Toolbar toolbar;
    private Params params = Params.getInstance();
    public static final int REQUEST_CODE_CITY = 100;
    public static final int RESULT_CODE_PARK = 101;

    public static ParkPickerFragment newInstance() {
        return new ParkPickerFragment();
    }

    @NonNull
    @Override
    protected ParkPickerContract.Presenter createPresenter() {
        return new ParkPickerPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picker_park, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        ptrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.ptrFrameLayout);
        etSearch = (EditText) view.findViewById(R.id.et_search);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        tvTitle = (TextView) view.findViewById(R.id.toolbar_title);
        tvCity = (TextView) view.findViewById(R.id.toolbar_menu);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        initListener();
        initPtrFrameLayout(ptrFrameLayout, recyclerView);
    }

    private void initToolbar() {
        initStateBar(toolbar);
        tvTitle.setText("选择停车场");
        tvCity.setText(TextUtils.isEmpty(UserHelper.city) ? "选择城市" : UserHelper.city);
        tvCity.setOnClickListener(v -> startForResult(CityPickerFragment.newInstance(), REQUEST_CODE_CITY));
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    private void initData() {
        params.regionKeywords = UserHelper.city;
        etSearch.setHint("请输入停车场关键字");
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        adapter = new ParkPickerAdapter();
        recyclerView.setAdapter(adapter);

        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                params.naKeywords = etSearch.getText().toString();
                params.regionKeywords = "";
                ptrFrameLayout.autoRefresh();
                return true;
            }
            return false;
        });
    }

    private void initListener() {
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            switch (adapter1.getItemViewType(position)) {
                case ParkPickerAdapter.TYPE_NAME:
                    if (view.getId() == R.id.iv_clean_item_rv_park_selector_type) {
                        new AlertDialog.Builder(_mActivity)
                                .setTitle("温馨提醒：")
                                .setMessage("确认删除历史记录吗？")
                                .setNegativeButton("取消", null)
                                .setPositiveButton("确认", (dialog, which) -> {
                                    DataSupport.deleteAll(ParkHistoryData.class);
                                    adapter.removeHistory();
                                }).show();
                    }
                    break;
                case ParkPickerAdapter.TYPE_CONTENT:
                    mPresenter.cacheParkHistory(adapter.getItem(position));
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.KEY_PARK, adapter.getItem(position));
                    setFragmentResult(RESULT_CODE_PARK, bundle);
                    pop();
                    break;
                default:
            }
        });
    }

    @Override
    public void onRefresh() {
        mPresenter.requestParks(params);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CITY) {
            tvCity.setText(params.regionKeywords = data.getString(Constants.CITY));
            params.naKeywords = "";
            ptrFrameLayout.autoRefresh();
        }
    }

    @Override
    public void error(String errorMessage) {
        super.error(errorMessage);
        ptrFrameLayout.refreshComplete();
    }

    /**
     * 响应数据库的历史停车记录。
     *
     * @param parkHistory
     */
    @Override
    public void responseParkHistory(List<ParkSelectBean.DataBean.ParkPlaceListBean> parkHistory) {
        adapter.notifyHistory(parkHistory);
    }

    /**
     * 响应请求停车场列表。
     *
     * @param parks
     */
    @Override
    public void responseParks(List<ParkSelectBean.DataBean.ParkPlaceListBean> parks) {
        ptrFrameLayout.refreshComplete();
        adapter.addData(parks);
    }

    @Override
    public void onDestroyView() {
        KeyBoardUtils.hideKeybord(getView(), App.mContext);
        super.onDestroyView();
    }
}
