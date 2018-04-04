package cn.itsite.acommon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.itsite.abase.utils.DensityUtils;
import cn.itsite.abase.utils.ToastUtils;
import cn.itsite.acommon.contract.SkusContract;
import cn.itsite.acommon.presenter.SkusPresenter;
import cn.itsite.adialog.dialog.LoadingDialog;
import cn.itsite.adialog.dialogfragment.BaseDialogFragment;

/**
 * Author： Administrator on 2018/2/7 0007.
 * Email： liujia95me@126.com
 */

public class SpecificationDialog extends BaseDialogFragment implements SkusContract.View {

    private static final String TAG = SpecificationDialog.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private GoodsCounterView mTvGoodsCounter;

    private SkusPresenter mPresenter = new SkusPresenter(this);
    private LoadingDialog loadingDialog;
    private SpecificationRVAdapter mAdapter;
    private TextView mTvName;
    private TextView mTvStockQuantity;
    private TextView mTvSku;
    private HashMap<Integer, SkusBean.AttributesBean.ValuesBean> mPositions = new HashMap<>();//已选的Position集

    private SkusBean.SkuBean selectedSku;

//    private List<String> mInterselectionSkus = new ArrayList<>();//选中的skus交集，最后只会剩下一个值
//    private List<String> mSkuUids = new ArrayList<>();//所有可能性的skus的uid

    private SkusBean skusBean;
    private TextView mTvConfirm;
    private ImageView mIvIcon;
    private TextView mTvPrice;
    private boolean mHasSkus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_specification, null);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mTvGoodsCounter = view.findViewById(R.id.view_goods_counter);
        mTvName = view.findViewById(R.id.tv_name);
        mTvSku = view.findViewById(R.id.tv_sku);
        mTvStockQuantity = view.findViewById(R.id.tv_stock_quantity);
        mTvConfirm = view.findViewById(R.id.tv_confirm);
        mIvIcon = view.findViewById(R.id.iv_icon);
        mTvPrice = view.findViewById(R.id.tv_price);
        return view;
    }

    public SpecificationDialog() {

    }

    Context mContext;
    private String mUid;
    private String mNormalImage;
    private int mNormalAmount = 1;

    @SuppressLint("ValidFragment")
    public SpecificationDialog(Context context, String uid, String normalImage) {
        mContext = context;
        mUid = uid;
        mNormalImage = normalImage;
    }

    @SuppressLint("ValidFragment")
    public SpecificationDialog(Context context, String uid, String normalImage, int normalAmount) {
        mContext = context;
        mUid = uid;
        mNormalImage = normalImage;
        mNormalAmount = normalAmount;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setGravity(Gravity.BOTTOM);
        setAnimStyle(R.anim.slide_enter);
        setDimAmount(0.5f);
        initData();
        initListener();
    }


    private void initData() {
        mTvGoodsCounter.setCountWidth(DensityUtils.dp2px(getContext(), 65));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SpecificationRVAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.getSkus(mUid);
        mTvGoodsCounter.setCounter(mNormalAmount);

        if (mNormalImage != null) {
            refreshImage(mNormalImage);
        }
    }


    private void initListener() {
        mAdapter.setOnSpecificationClickListener(new SpecificationRVAdapter.OnSpecificationClickListener() {
            @Override
            public void onItemClick(SkusBean.AttributesBean.ValuesBean valuesBean, int position, boolean isSelected, FlexboxLayout flexboxLayout) {
                if (isSelected) {
                    mPositions.put(position, valuesBean);
                } else {
                    mPositions.remove(position);
                }
                refreshProduct();
            }
        });
        mTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int counter = mTvGoodsCounter.getCounter();
                if (counter <= 0) {
                    ToastUtils.showToast(getContext(), "请选择数量");
                } else if (mHasSkus && selectedSku == null) {
                    ToastUtils.showToast(getContext(), "请选择规格");
                } else {
                    mOnSkusListener.clickComfirm(selectedSku, counter, SpecificationDialog.this);
                }
            }
        });
    }

    //刷新商品
    private void refreshProduct() {
        List<SkusBean.AttributesBean> data = mAdapter.getData();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.size(); i++) {
            if (mPositions.containsKey(i)) {
                continue;
            }
            sb.append(data.get(i).getAttribute());
        }
        //选择完毕后
        if (TextUtils.isEmpty(sb.toString())) {
            //最后交集只会剩下一个值
            List<String> intersection = new ArrayList<>();
            for (int i = 0; i < mPositions.size(); i++) {
                SkusBean.AttributesBean.ValuesBean valuesBean = mPositions.get(i);
                if (i == 0) {
                    intersection = new ArrayList<>(valuesBean.getSkus());
                } else {
                    intersection.retainAll(valuesBean.getSkus());
                }
            }
            SkusBean.SkuBean skuBean = null;
            if (intersection.size() == 1) {
                for (int i = 0; i < skusBean.getSkus().size(); i++) {
                    if (skusBean.getSkus().get(i).getUid().equals(intersection.get(0))) {
                        skuBean = skusBean.getSkus().get(i);
                    }
                }
            }
            if (skuBean == null) {
                selectedSku = null;
                mTvSku.setText("库存不足，请选择其他类型~");
            } else {
                selectedSku = skuBean;
                mTvSku.setText("已选："+skuBean.getSku());
                refreshInfo(skuBean.getStockQuantity() + "", skuBean.getCurrency() + skuBean.getPrice());
            }
        } else {
            selectedSku = null;
            mTvSku.setText("请选择 "+sb);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void showLoading(String message) {
        if (loadingDialog == null) {
            if (mContext != null)
                loadingDialog = new LoadingDialog(mContext);
            else
                loadingDialog = new LoadingDialog(getContext());
            loadingDialog.setDimAmount(0);
        } else {
            loadingDialog.setText(message);
        }
        loadingDialog.show();
    }

    public void dismissLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void start(Object response) {
        showLoading("玩命加载中...");
    }

    @Override
    public void error(String errorMessage) {
        dismissLoading();
        ToastUtils.showToast(getContext(), errorMessage);
    }

    @Override
    public void complete(Object response) {
        dismissLoading();
    }


    OnSkusListener mOnSkusListener;

    public void setSkuListener(OnSkusListener listener) {
        mOnSkusListener = listener;
    }

    public interface OnSkusListener {
        void clickComfirm(SkusBean.SkuBean sku, int amount, SpecificationDialog dialog);
    }

    private void refreshInfo(String stockQuantity, String price) {
        mTvStockQuantity.setText("库存 " + stockQuantity + " 件");
        mTvPrice.setText(price);
    }

    private void refreshImage(String imageUrl) {
        Glide.with(mContext)
                .load(imageUrl)
                .apply(new RequestOptions().placeholder(R.drawable.ic_img_loading))
                .apply(new RequestOptions().error(R.drawable.ic_img_error))
                .into(mIvIcon);
    }

    @Override
    public void responseGetSkus(SkusBean bean) {
        skusBean = bean;
        mAdapter.setNewData(bean.getAttributes());
        //设置默认数据
        mHasSkus = bean.getAttributes().size() > 0;
        if (bean.getPay() != null) {
            refreshInfo(bean.getStockQuantity(), bean.getPay().getCurrency() + bean.getPay().getPrice());
        }
        refreshProduct();
    }
}
