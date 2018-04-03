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
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.itsite.abase.common.DialogHelper;
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

    private String selectedSku;

//    private List<String> mInterselectionSkus = new ArrayList<>();//选中的skus交集，最后只会剩下一个值
//    private List<String> mSkuUids = new ArrayList<>();//所有可能性的skus的uid

    private String testJson = "{\n" +
            "        \"attributes\": [\n" +
            "            {\n" +
            "                \"attribute\": \"性别\",\n" +
            "                \"values\": [\n" +
            "                    {\n" +
            "                        \"skus\": [\n" +
            "                            \"2\",\n" +
            "                            \"3\",\n" +
            "                            \"4\",\n" +
            "                            \"6\",\n" +
            "                            \"7\",\n" +
            "                            \"8\"\n" +
            "                        ],\n" +
            "                        \"stockQuantity\": 600,\n" +
            "                        \"value\": \"男\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"skus\": [\n" +
            "                            \"10\",\n" +
            "                            \"11\",\n" +
            "                            \"13\",\n" +
            "                            \"15\",\n" +
            "                            \"17\",\n" +
            "                            \"18\"\n" +
            "                        ],\n" +
            "                        \"stockQuantity\": 600,\n" +
            "                        \"value\": \"女\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"attribute\": \"颜色\",\n" +
            "                \"values\": [\n" +
            "                    {\n" +
            "                        \"skus\": [\n" +
            "                            \"2\",\n" +
            "                            \"3\",\n" +
            "                            \"10\",\n" +
            "                            \"11\"\n" +
            "                        ],\n" +
            "                        \"stockQuantity\": 400,\n" +
            "                        \"value\": \"红色\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"skus\": [\n" +
            "                            \"4\",\n" +
            "                            \"6\",\n" +
            "                            \"13\",\n" +
            "                            \"15\"\n" +
            "                        ],\n" +
            "                        \"stockQuantity\": 400,\n" +
            "                        \"value\": \"黄色\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"skus\": [\n" +
            "                            \"7\",\n" +
            "                            \"8\",\n" +
            "                            \"17\",\n" +
            "                            \"18\"\n" +
            "                        ],\n" +
            "                        \"stockQuantity\": 400,\n" +
            "                        \"value\": \"蓝色\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"attribute\": \"尺码\",\n" +
            "                \"values\": [\n" +
            "                    {\n" +
            "                        \"skus\": [\n" +
            "                            \"4\",\n" +
            "                            \"7\",\n" +
            "                            \"10\",\n" +
            "                            \"13\"\n" +
            "                        ],\n" +
            "                        \"stockQuantity\": 400,\n" +
            "                        \"value\": \"X码\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"skus\": [\n" +
            "                            \"2\",\n" +
            "                            \"8\",\n" +
            "                            \"11\",\n" +
            "                            \"15\"\n" +
            "                        ],\n" +
            "                        \"stockQuantity\": 400,\n" +
            "                        \"value\": \"M码\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"skus\": [\n" +
            "                            \"3\",\n" +
            "                            \"6\",\n" +
            "                            \"15\",\n" +
            "                            \"18\"\n" +
            "                        ],\n" +
            "                        \"stockQuantity\": 400,\n" +
            "                        \"value\": \"L码\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        ],\n" +
            "        \"skus\": [\n" +
            "            {\n" +
            "                \"currency\": \"¥\",\n" +
            "                \"imageUrl\": \"http://ww3.sinaimg.cn/large/0060lm7Tly1fo6vt0p500j30af0ad758.jpg\",\n" +
            "                \"note\": \"备注：该商品XXX\",\n" +
            "                \"price\": 50,\n" +
            "                \"sku\": \"男+红色+M码\",\n" +
            "                \"stockQuantity\": 100,\n" +
            "                \"uid\": \"2\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"currency\": \"¥\",\n" +
            "                \"imageUrl\": \"http://ww3.sinaimg.cn/large/0060lm7Tly1fo6vt0p500j30af0ad758.jpg\",\n" +
            "                \"note\": \"备注：该商品XXX\",\n" +
            "                \"price\": 50,\n" +
            "                \"sku\": \"男+红色+L码\",\n" +
            "                \"stockQuantity\": 100,\n" +
            "                \"uid\": \"3\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"currency\": \"¥\",\n" +
            "                \"imageUrl\": \"http://ww3.sinaimg.cn/large/0060lm7Tly1fo6vt0p500j30af0ad758.jpg\",\n" +
            "                \"note\": \"备注：该商品XXX\",\n" +
            "                \"price\": 50,\n" +
            "                \"sku\": \"男+黄色+X码\",\n" +
            "                \"stockQuantity\": 100,\n" +
            "                \"uid\": \"4\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"currency\": \"¥\",\n" +
            "                \"imageUrl\": \"http://ww3.sinaimg.cn/large/0060lm7Tly1fo6vt0p500j30af0ad758.jpg\",\n" +
            "                \"note\": \"备注：该商品XXX\",\n" +
            "                \"price\": 50,\n" +
            "                \"sku\": \"男+黄色+L码\",\n" +
            "                \"stockQuantity\": 100,\n" +
            "                \"uid\": \"6\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"currency\": \"¥\",\n" +
            "                \"imageUrl\": \"http://ww3.sinaimg.cn/large/0060lm7Tly1fo6vt0p500j30af0ad758.jpg\",\n" +
            "                \"note\": \"备注：该商品XXX\",\n" +
            "                \"price\": 50,\n" +
            "                \"sku\": \"男+蓝色+X码\",\n" +
            "                \"stockQuantity\": 100,\n" +
            "                \"uid\": \"7\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"currency\": \"¥\",\n" +
            "                \"imageUrl\": \"http://ww3.sinaimg.cn/large/0060lm7Tly1fo6vt0p500j30af0ad758.jpg\",\n" +
            "                \"note\": \"备注：该商品XXX\",\n" +
            "                \"price\": 50,\n" +
            "                \"sku\": \"男+蓝色+M码\",\n" +
            "                \"stockQuantity\": 100,\n" +
            "                \"uid\": \"8\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"currency\": \"¥\",\n" +
            "                \"imageUrl\": \"http://ww3.sinaimg.cn/large/0060lm7Tly1fo6vt0p500j30af0ad758.jpg\",\n" +
            "                \"note\": \"备注：该商品XXX\",\n" +
            "                \"price\": 50,\n" +
            "                \"sku\": \"女+红色+X码\",\n" +
            "                \"stockQuantity\": 100,\n" +
            "                \"uid\": \"10\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"currency\": \"¥\",\n" +
            "                \"imageUrl\": \"http://ww3.sinaimg.cn/large/0060lm7Tly1fo6vt0p500j30af0ad758.jpg\",\n" +
            "                \"note\": \"备注：该商品XXX\",\n" +
            "                \"price\": 50,\n" +
            "                \"sku\": \"女+红色+M码\",\n" +
            "                \"stockQuantity\": 100,\n" +
            "                \"uid\": \"11\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"currency\": \"¥\",\n" +
            "                \"imageUrl\": \"http://ww3.sinaimg.cn/large/0060lm7Tly1fo6vt0p500j30af0ad758.jpg\",\n" +
            "                \"note\": \"备注：该商品XXX\",\n" +
            "                \"price\": 50,\n" +
            "                \"sku\": \"女+黄色+X码\",\n" +
            "                \"stockQuantity\": 100,\n" +
            "                \"uid\": \"13\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"currency\": \"¥\",\n" +
            "                \"imageUrl\": \"http://ww3.sinaimg.cn/large/0060lm7Tly1fo6vt0p500j30af0ad758.jpg\",\n" +
            "                \"note\": \"备注：该商品XXX\",\n" +
            "                \"price\": 50,\n" +
            "                \"sku\": \"女+黄色+L码\",\n" +
            "                \"stockQuantity\": 100,\n" +
            "                \"uid\": \"15\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"currency\": \"¥\",\n" +
            "                \"imageUrl\": \"http://ww3.sinaimg.cn/large/0060lm7Tly1fo6vt0p500j30af0ad758.jpg\",\n" +
            "                \"note\": \"备注：该商品XXX\",\n" +
            "                \"price\": 50,\n" +
            "                \"sku\": \"女+蓝色+M码\",\n" +
            "                \"stockQuantity\": 100,\n" +
            "                \"uid\": \"17\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"currency\": \"¥\",\n" +
            "                \"imageUrl\": \"http://ww3.sinaimg.cn/large/0060lm7Tly1fo6vt0p500j30af0ad758.jpg\",\n" +
            "                \"note\": \"备注：该商品XXX\",\n" +
            "                \"price\": 50,\n" +
            "                \"sku\": \"女+蓝色+L码\",\n" +
            "                \"stockQuantity\": 100,\n" +
            "                \"uid\": \"18\"\n" +
            "            }\n" +
            "        ]\n" +
            "    }";
    private SkusBean skusBean;
    private TextView mTvConfirm;

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
        return view;
    }

    public SpecificationDialog() {

    }

    Context mContext;

    @SuppressLint("ValidFragment")
    public SpecificationDialog(Context context) {
        mContext = context;
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

//        mPresenter.getSkus("123");

        skusBean = new Gson().fromJson(testJson, SkusBean.class);
        mAdapter.setNewData(skusBean.getAttributes());
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
                } else if (selectedSku == null) {
                    ToastUtils.showToast(getContext(), "请选择规格");
                } else {
                    mOnSkusListener.clickComfirm(selectedSku, counter);
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
                selectedSku = skuBean.getSku();
                mTvSku.setText(skuBean.getSku());
            }
        } else {
            selectedSku = null;
            mTvSku.setText(sb);
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
        DialogHelper.errorSnackbar(getView(), errorMessage);
    }

    @Override
    public void complete(Object response) {
        dismissLoading();
    }


    OnSkusListener mOnSkusListener;

    public void getSkus(String uid, OnSkusListener listener) {
        mPresenter.getSkus(uid);
        mOnSkusListener = listener;
    }

    public interface OnSkusListener {
        void hasSkus(boolean hasSkus);

        void clickComfirm(String sku, int amount);
    }


    @Override
    public void responseGetSkus(SkusBean bean) {
        mOnSkusListener.hasSkus(bean.getSkus().size() > 0);
        mAdapter.setNewData(bean.getAttributes());
    }
}
