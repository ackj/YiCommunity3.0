package cn.itsite.amain.yicommunity.main.smarthome.view;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.App;
import cn.itsite.amain.yicommunity.entity.bean.FirstLevelBean;

/**
 * Author: LiuJia on 2017/5/22 0022 10:49.
 * Email: liujia95me@126.com
 */

public class GoodsCategoryRVAdapter extends BaseRecyclerViewAdapter<FirstLevelBean.DataBean, BaseViewHolder> {

    public GoodsCategoryRVAdapter() {
        super(R.layout.item_goods_gategory);
    }

    @Override
    protected void convert(BaseViewHolder helper, FirstLevelBean.DataBean item) {
        ImageView image = helper.getView(R.id.iv_goods_gategory);

        Glide.with(App.mContext)
                .load(item.getImage())
                .into(image);
    }
}
