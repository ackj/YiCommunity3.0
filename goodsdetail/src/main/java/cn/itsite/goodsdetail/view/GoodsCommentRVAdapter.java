package cn.itsite.goodsdetail.view;

import com.chad.library.adapter.base.BaseViewHolder;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.goodsdetail.R;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/4/12 0012 17:43
 */
public class GoodsCommentRVAdapter extends BaseRecyclerViewAdapter<String,BaseViewHolder> {

    public GoodsCommentRVAdapter() {
        super(R.layout.item_goods_comment);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
