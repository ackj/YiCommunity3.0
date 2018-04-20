package cn.itsite.order.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.acommon.model.OrderDetailBean;
import cn.itsite.order.R;

/**
 * Author： Administrator on 2018/2/1 0001.
 * Email： liujia95me@126.com
 */

public class OrderDetailRVAdapter extends BaseRecyclerViewAdapter<OrderDetailBean.ProductsBean, BaseViewHolder> {

    public OrderDetailRVAdapter() {
        super(R.layout.item_linear_goods);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailBean.ProductsBean item) {
        TextView tvApply = helper.getView(R.id.tv_apply);
        TextView tvAmount = helper.getView(R.id.tv_amount);
        ImageView mIvIcon = helper.getView(R.id.iv_icon);
        Glide.with(mIvIcon.getContext())
                .load(item.getImageUrl())
                .apply(new RequestOptions().error(R.drawable.ic_img_error)
                .placeholder(R.drawable.ic_img_loading))
                .into(mIvIcon);
        helper.addOnClickListener(R.id.cl_goods_layout)
                .addOnClickListener(R.id.tv_apply)
                .setText(R.id.tv_name, item.getTitle())
                .setText(R.id.tv_amount,"x" + item.getAmount())
                .setText(R.id.tv_desc, item.getDescription())
                .setText(R.id.tv_price, item.getPay().getCurrency() + " " + item.getPay().getPrice());
        if (item.getActions() != null && item.getActions().size() > 0) {
            tvApply.setVisibility(View.VISIBLE);
            tvApply.setText(item.getActions().get(0).getAction());
        }else{
            tvApply.setVisibility(View.GONE);
        }
    }
}
