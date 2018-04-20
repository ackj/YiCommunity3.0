package cn.itsite.amain.yicommunity.main.propery.view;

import com.chad.library.adapter.base.BaseViewHolder;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.entity.bean.PropertyPayDetailBean;

/**
 * Created by leguang on 2017/4/29 0029.
 * Email：langmanleguang@qq.com
 */

public class PropertyNotPayDetailRVAdapter extends BaseRecyclerViewAdapter<PropertyPayDetailBean.DataBean.ItemListBean, BaseViewHolder> {

    public PropertyNotPayDetailRVAdapter() {
        super(R.layout.item_rv_property_not_pay);
    }

    @Override
    protected void convert(BaseViewHolder helper, PropertyPayDetailBean.DataBean.ItemListBean item) {
        helper.setText(R.id.tv_sum_item_rv_property_not_pay_detail, item.getItemAmt() + "元")
                .setText(R.id.tv_name_item_rv_property_not_pay_detail, item.getItemName())
                .setText(R.id.tv_description_item_rv_property_not_pay_detail, item.getItemRemark());
    }
}
