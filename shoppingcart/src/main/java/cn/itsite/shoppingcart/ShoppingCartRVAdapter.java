package cn.itsite.shoppingcart;

import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daimajia.swipe.SwipeLayout;

import cn.itsite.abase.BaseApp;
import cn.itsite.acommon.GoodsCounterView;

/**
 * Author： Administrator on 2018/1/31 0031.
 * Email： liujia95me@126.com
 */

public class ShoppingCartRVAdapter extends BaseMultiItemQuickAdapter<StoreBean, BaseViewHolder> {


    public ShoppingCartRVAdapter() {
        super(null);
        addItemType(StoreBean.TYPE_EMPTY, R.layout.item_empty);
        addItemType(StoreBean.TYPE_STORE_TITLE, R.layout.item_store_title);
        addItemType(StoreBean.TYPE_STORE_GOODS, R.layout.item_cart_goods);
        addItemType(StoreBean.TYPE_RECOMMEND_TITLE, R.layout.item_recommend_title);
        addItemType(StoreBean.TYPE_RECOMMEND_GOODS, R.layout.item_grid_goods);
    }

    @Override
    protected void convert(BaseViewHolder helper, StoreBean item) {
        switch (item.getItemType()) {
            case StoreBean.TYPE_EMPTY:
                helper.addOnClickListener(R.id.empty_layout);
                break;
            case StoreBean.TYPE_STORE_TITLE:
                helper
                        .setText(R.id.tv_store_name, item.getShopBean().getName())
                        .setOnCheckedChangeListener(R.id.checkBox, null)
                        .setChecked(R.id.checkBox, item.isChecked())
                        .setOnCheckedChangeListener(R.id.checkBox, new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                listener.onStoreCheckedChanged(helper.getLayoutPosition(), isChecked);
                            }
                        })
                        .setText(R.id.tv_delivery_type, item.getShopBean().getServiceType())
                        .setTextColor(R.id.tv_delivery_type, item.getShopBean().getServiceType().contains("上门") ?
                                BaseApp.mContext.getResources().getColor(R.color.base_color) :
                                BaseApp.mContext.getResources().getColor(R.color.green))
                        .setBackgroundRes(R.id.tv_delivery_type, item.getShopBean().getServiceType().contains("上门") ?
                                R.drawable.shape_bg_round_orange : R.drawable.shape_bg_round_green);
                break;
            case StoreBean.TYPE_STORE_GOODS:
                TextView tvSku = helper.getView(R.id.tv_specification);
                if (TextUtils.isEmpty(item.getProductsBean().getSkuID())) {
                    tvSku.setVisibility(View.GONE);
                } else {
                    tvSku.setVisibility(View.VISIBLE);
                    tvSku.setText(item.getProductsBean().getSku());
                }

                SwipeLayout swipeLayout = helper.getView(R.id.swipeLayout);
                swipeLayout.setSwipeEnabled(false);

                GoodsCounterView goodsCounterView = helper.getView(R.id.goodsCounterView);
                goodsCounterView.setCounter(item.getProductsBean().getCount());
                helper.setOnCheckedChangeListener(R.id.checkBox, null)
                        .setText(R.id.tv_name, item.getProductsBean().getTitle())
                        .setText(R.id.tv_goods_count, "x"+item.getProductsBean().getCount())
                        .setChecked(R.id.checkBox, item.isChecked())
                        .setOnCheckedChangeListener(R.id.checkBox, new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                listener.onGoodsCheckedChanged(helper.getLayoutPosition(), isChecked);
                            }
                        })
                        .addOnClickListener(R.id.tv_specification)
                        .addOnClickListener(R.id.tv_confirm)
                        .addOnClickListener(R.id.iv_edit)
                        .setText(R.id.tv_name, item.getProductsBean().getTitle())
                        .setText(R.id.tv_desc, item.getProductsBean().getDescription())
                        .setText(R.id.tv_price, item.getProductsBean().getPay().getCurrency() + " " + item.getProductsBean().getPay().getPrice());

                ImageView ivIcon = helper.getView(R.id.iv_icon);
                Glide.with(ivIcon.getContext())
                        .load(item.getProductsBean().getIcon())
                        .apply(new RequestOptions().placeholder(R.drawable.ic_img_loading))
                        .apply(new RequestOptions().error(R.drawable.ic_img_error))
                        .into(ivIcon);
                break;
            case StoreBean.TYPE_RECOMMEND_TITLE:
                break;
            case StoreBean.TYPE_RECOMMEND_GOODS:
                ImageView mIvIcon = helper.getView(R.id.iv_icon);
                Glide.with(mIvIcon.getContext())
                        .load(item.getRecommendGoodsBean().getImageUrl())
                        .apply(new RequestOptions().placeholder(R.drawable.ic_img_loading))
                        .apply(new RequestOptions().error(R.drawable.ic_img_error))
                        .into(mIvIcon);
                helper.addOnClickListener(R.id.cl_goods_layout)
                        .setText(R.id.tv_name, item.getRecommendGoodsBean().getTitle())
                        .setText(R.id.tv_desc, item.getRecommendGoodsBean().getDescription())
                        .setText(R.id.tv_price, item.getRecommendGoodsBean().getCurrency() + " " + item.getRecommendGoodsBean().getPrice());
                break;
            default:
        }
    }

    private OnCheckedChangedListener listener;

    public void setOnCheckedChangedListener(OnCheckedChangedListener listener) {
        this.listener = listener;
    }

    public interface OnCheckedChangedListener {
        void onStoreCheckedChanged(int position, boolean isChecked);

        void onGoodsCheckedChanged(int position, boolean isChecked);
    }

//    private GoodsCounterView.OnAddMinusClickListener addMinusClickListener;
//
//    public void setOnAddMinusClickListener(GoodsCounterView.OnAddMinusClickListener listener) {
//        addMinusClickListener = listener;
//    }

}
