package cn.itsite.amain.yicommunity.main.services.view;


import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.entity.bean.ServicesListBean;


/**
 * Created by leguang on 2017/6/26 0026.
 * Email：langmanleguang@qq.com
 */
public class ServicesRVAdapter extends BaseRecyclerViewAdapter<ServicesListBean.DataBean.DataListBean, BaseViewHolder> {

    public ServicesRVAdapter() {
        super(R.layout.item_rv_services);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServicesListBean.DataBean.DataListBean item) {
        helper.setText(R.id.tv_title_item_rv_services, item.getCommodityTitle())
                .setText(R.id.tv_describe_item_rv_services, item.getCommodityDesc())
                .setText(R.id.tv_cost_item_rv_services, "¥" + item.getCommodityPrice())
                .setText(R.id.tv_address_item_rv_services, item.getCoverageArea());

        ImageView ivPhoto = helper.getView(R.id.iv_photo_item_rv_services);
        Glide.with(ivPhoto.getContext())
                .load(item.getCommodityUrl())
                .apply(new RequestOptions()
                        .error(R.drawable.ic_default_img_120px)
                        .placeholder(R.drawable.ic_default_img_120px))
                .into(ivPhoto);
    }
}
