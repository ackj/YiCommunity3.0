package cn.itsite.aftersales.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.acommon.model.OrderDetailBean;
import me.liujia95.aftersales.R;

/**
 * Created by liujia on 2018/5/15.
 */

public class ProductRVAdapter extends BaseRecyclerViewAdapter<OrderDetailBean.ProductsBean, BaseViewHolder> {

    public ProductRVAdapter() {
        super(R.layout.item_linear_goods);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailBean.ProductsBean item) {
        TextView tvApply = helper.getView(R.id.tv_apply);
        ImageView mIvIcon = helper.getView(R.id.iv_icon);
        Glide.with(mIvIcon.getContext())
                .load(item.getImageUrl())
                .apply(new RequestOptions().error(R.drawable.ic_img_error)
                        .placeholder(R.drawable.ic_img_loading))
                .into(mIvIcon);
        helper.addOnClickListener(R.id.cl_goods_layout)
                .setText(R.id.tv_name, item.getTitle())
                .setText(R.id.tv_amount, "x" + item.getAmount())
                .setText(R.id.tv_desc, item.getDescription())
                .setText(R.id.tv_price, item.getPay().getCurrency() + " " + item.getPay().getPrice());
        tvApply.setVisibility(View.GONE);
    }
}
