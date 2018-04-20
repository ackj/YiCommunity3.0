package cn.itsite.amain.yicommunity.main.services.view;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.App;
import cn.itsite.amain.yicommunity.entity.bean.ServiceDetailBean;

/**
 * Author: LiuJia on 2017/7/2 0002 12:06.
 * Email: liujia95me@126.com
 */

public class ServiceDetailSceneRVAdapter extends BaseRecyclerViewAdapter<ServiceDetailBean.DataBean.MerchantSceneBean, BaseViewHolder> {

    public ServiceDetailSceneRVAdapter() {
        super(R.layout.item_rv_service_detail_pics);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceDetailBean.DataBean.MerchantSceneBean item) {
        ImageView ivPics = helper.getView(R.id.iv_pic);
        Glide.with(App.mContext)
                .load(item.getUrl())
                .apply(new RequestOptions()
                        .error(R.drawable.ic_default_img_120px)
                        .placeholder(R.drawable.ic_default_img_120px))
                .into(ivPics);
    }
}
