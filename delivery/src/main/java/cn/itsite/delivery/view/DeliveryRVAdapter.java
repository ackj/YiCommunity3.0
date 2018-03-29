package cn.itsite.delivery.view;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.delivery.R;
import cn.itsite.delivery.model.DeliveryBean;

/**
 * Author： Administrator on 2018/1/31 0031.
 * Email： liujia95me@126.com
 */

public class DeliveryRVAdapter extends BaseRecyclerViewAdapter<DeliveryBean, BaseViewHolder> {

    public DeliveryRVAdapter() {
        super(R.layout.item_address);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeliveryBean item) {
        TextView tvIsDefault = helper.getView(R.id.tv_is_default);
        tvIsDefault.setVisibility(item.isIsDeafult() ? View.VISIBLE : View.GONE);
        helper.setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_phone, item.getPhoneNumber())
                .setText(R.id.tv_address, item.getLocation() + item.getAddress())
                .addOnClickListener(R.id.iv_edit)
                .addOnClickListener(R.id.container_layout);
    }
}
