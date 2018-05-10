package cn.itsite.aftersales.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing_impl.ui.BoxingActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.itsite.abase.common.BaseConstants;
import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.abase.utils.ToastUtils;
import cn.itsite.acommon.data.bean.OperateBean;
import cn.itsite.acommon.model.OrderDetailBean;
import cn.itsite.adialog.dialogfragment.BaseDialogFragment;
import cn.itsite.aftersales.contract.AfterSalesContract;
import cn.itsite.aftersales.model.PostApplyBean;
import cn.itsite.aftersales.model.ReasonTypeBean;
import cn.itsite.aftersales.presenter.AfterSalesPresenter;
import me.liujia95.aftersales.R;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/4/11 0011 8:54
 * 退款退货和换货共用一个页面，用isExhange字段区分
 */
@Route(path = "/aftersales/aftersalesfragment")
public class AfterSalesFragment extends BaseFragment<AfterSalesContract.Presenter> implements AfterSalesContract.View {

    public static final String TAG = AfterSalesFragment.class.getSimpleName();

    private RelativeLayout mRlToolbar;
    private ImageView mIvBack;
    private RecyclerView mRecyclerView;
    private ImageRVAdapter mAdapter;
    BaseMedia addMedia = new BaseMedia() {
        @Override
        public TYPE getType() {
            return TYPE.IMAGE;
        }
    };
    ArrayList<BaseMedia> mSelectedMedia = new ArrayList<>();
    private ReasonTypeBean mSelectedReasonType;

    private EditText mEtExplain;
    private TextView mTvBackprice;
    private TextView mTvReason;
    private LinearLayout mLlPrice;
    private TextView mTvAnchorReason;
    private TextView mTvAnchorExplain;
    private TextView mTvApply;
    private OrderDetailBean.ProductsBean mProductsBean;
    private TextView mTvPrice;
    private TextView mTvName;
    private TextView mTvDesc;
    private ImageView mIvIcon;
    private TextView mTvAmount;
    private TextView mTvTitle;
    private TextView mTvRefundPrice;
    private TextView mTvMenu;
    private String mOrderUid;
    private PostApplyBean mApplyBean;
    private String mServiceType;

    public static AfterSalesFragment newInstance() {
        AfterSalesFragment fragment = new AfterSalesFragment();
        return fragment;
    }

    @NonNull
    @Override
    protected AfterSalesContract.Presenter createPresenter() {
        return new AfterSalesPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mServiceType = getArguments().getString("serviceType");
        mOrderUid = getArguments().getString("orderUid");
        mProductsBean = (OrderDetailBean.ProductsBean) getArguments().getSerializable("product");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aftersafes, container, false);
        mRlToolbar = view.findViewById(R.id.rl_toolbar);
        mTvTitle = view.findViewById(R.id.tv_title);
        mIvBack = view.findViewById(R.id.iv_back);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mTvBackprice = view.findViewById(R.id.tv_backprice);
        mEtExplain = view.findViewById(R.id.et_explain);
        mTvReason = view.findViewById(R.id.tv_reason);
        mTvAnchorReason = view.findViewById(R.id.tv_anchor_reason);
        mTvAnchorExplain = view.findViewById(R.id.tv_anchor_explain);
        mLlPrice = view.findViewById(R.id.ll_price);
        mTvApply = view.findViewById(R.id.tv_apply);
        mIvIcon = view.findViewById(R.id.iv_icon);
        mTvName = view.findViewById(R.id.tv_name);
        mTvPrice = view.findViewById(R.id.tv_price);
        mTvDesc = view.findViewById(R.id.tv_desc);
        mTvAmount = view.findViewById(R.id.tv_amount);
        mTvMenu = view.findViewById(R.id.tv_menu);
        mTvRefundPrice = view.findViewById(R.id.tv_refund_price);
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
        if (mServiceType.equals(BaseConstants.SERVICE_TYPE_EXCHANGE)) {
            //如果是换货
            mTvBackprice.setVisibility(View.GONE);
            mLlPrice.setVisibility(View.GONE);
            mTvAnchorExplain.setText("换货说明：");
            mTvAnchorReason.setText("换货原因：");
            mTvTitle.setText("申请换货");
        }

        mTvApply.setVisibility(View.GONE);

        if (mProductsBean != null) {
            //将数据映射到界面上
            Glide.with(mIvIcon.getContext())
                    .load(mProductsBean.getImageUrl())
                    .apply(new RequestOptions().error(R.drawable.ic_img_error)
                            .placeholder(R.drawable.ic_img_loading))
                    .into(mIvIcon);
            mTvName.setText(mProductsBean.getTitle());
            mTvDesc.setText(mProductsBean.getDescription());
            String currency = mProductsBean.getPay().getCurrency();
            float price = Float.valueOf(mProductsBean.getPay().getPrice());
            mTvPrice.setText(currency + price);
            mTvAmount.setText("x" + mProductsBean.getAmount());
            float refundPrice = price * Integer.valueOf(mProductsBean.getAmount());
            mTvRefundPrice.setText(currency + refundPrice);
            mTvBackprice.setText("最多" + currency + price + "，含发货邮票" + currency + "0.00");

            //创建一个提交的对象
            mApplyBean = new PostApplyBean();
            mApplyBean.setCategory(mServiceType);
            mApplyBean.setUid(mOrderUid);
            PostApplyBean.ProductsBean productsBean = new PostApplyBean.ProductsBean();
            productsBean.setSku(mProductsBean.getSku());
            productsBean.setAmount(mProductsBean.getAmount());
            productsBean.setUid(mProductsBean.getUid());
            List<PostApplyBean.ProductsBean> productsBeans = new ArrayList<>();
            productsBeans.add(productsBean);
            mApplyBean.setProducts(productsBeans);
        }

        mRecyclerView.setLayoutManager(new GridLayoutManager(_mActivity, 4));
        mAdapter = new ImageRVAdapter();
        mRecyclerView.setAdapter(mAdapter);

        //默认图
        addMedia.setPath("android.resource://" + _mActivity.getPackageName() + "/" + R.drawable.ic_aftermarket_200px);
        mAdapter.addData(addMedia);
    }

    private void initListener() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                selectPhoto();
            }
        });
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });
        mTvReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getReasontType();
            }
        });
        mTvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkApply()) {
                    if(!submitPictures()){
                        submitApply();
                    }
                }
            }
        });
    }

    //检查是否填写完整
    private boolean checkApply() {
        String reason = mTvReason.getText().toString().trim();
        String explain = mEtExplain.getText().toString().trim();
        if(TextUtils.isEmpty(reason)){
            ToastUtils.showToast(_mActivity,"请选择原因");
            return false;
        }else if(TextUtils.isEmpty(explain)){
            ToastUtils.showToast(_mActivity,"请输入说明");
            return false;
        }
        return true;
    }

    private boolean submitPictures() {
        List<File> files = new ArrayList<>();
        for (int i = 0; i < mAdapter.getData().size() - 1; i++) {
            File file = new File(mAdapter.getItem(i).getPath());
            files.add(file);
        }
        if (files.size() > 0) {
            mPresenter.postPicture(files);
            return true;
        }
        return false;
    }

    //提交申请
    private void submitApply() {
        String explain = mEtExplain.getText().toString().trim();
        mApplyBean.setNote(explain);
        mApplyBean.setReasonType(mSelectedReasonType.getValue());
        mPresenter.postApply(mApplyBean);
    }

    private void showReasonDialog(List<ReasonTypeBean> reasonTypes) {
        new BaseDialogFragment()
                .setLayoutId(R.layout.dialog_reason)
                .setConvertListener((holder, dialog) -> {
                    RecyclerView recyclerView = holder.getView(R.id.recyclerView);
                    ReasonRVAdapter adapter = new ReasonRVAdapter(reasonTypes);
                    recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
                            mTvReason.setText(reasonTypes.get(position).getName());
                            mSelectedReasonType = reasonTypes.get(position);
                            dialog.dismiss();
                        }
                    });
                })
                .setDimAmount(0.3f)
                .setGravity(Gravity.BOTTOM)
                .show(getChildFragmentManager());
    }

    private void selectPhoto() {
        BoxingConfig config = new BoxingConfig(BoxingConfig.Mode.MULTI_IMG); // Mode：Mode.SINGLE_IMG, Mode.MULTI_IMG, Mode.VIDEO
        config.needCamera(R.drawable.ic_boxing_camera_white).needGif().withMaxCount(6) // 支持gif，相机，设置最大选图数
                .withMediaPlaceHolderRes(R.drawable.ic_boxing_default_image); // 设置默认图片占位图，默认无
        Boxing.of(config).withIntent(_mActivity, BoxingActivity.class, mSelectedMedia).start(this, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            mSelectedMedia = Boxing.getResult(data);
            mAdapter.setNewData(null);
            mAdapter.addData(mSelectedMedia);
            mAdapter.addData(addMedia);
        }
    }

    @Override
    public void responsePostSuccess(BaseResponse response) {
        DialogHelper.successSnackbar(getView(), response.getMessage());
        pop();
    }

    @Override
    public void responseReasonType(BaseResponse<List<ReasonTypeBean>> response) {
        showReasonDialog(response.getData());
    }

    @Override
    public void responsePostPicture(BaseResponse<List<OperateBean>> response) {
        DialogHelper.successSnackbar(getView(), response.getMessage());
        ALog.e(TAG, "图片上传成功 个数：" + response.getData().size());
        List<String> files = new ArrayList<>();
        for (int i = 0; i < response.getData().size(); i++) {
            files.add(response.getData().get(i).getUrl());
        }
        mApplyBean.getProducts().get(0).setPictures(files);
        submitApply();
    }
}
