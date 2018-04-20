package cn.itsite.goodshome.view;

import com.chad.library.adapter.base.BaseViewHolder;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.goodshome.R;
import cn.itsite.goodshome.model.ShopBean;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/28 0028 16:05
 */

public class StoreDialogRVAdapter extends BaseRecyclerViewAdapter<ShopBean,BaseViewHolder> {
    public StoreDialogRVAdapter() {
        super(R.layout.item_store);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopBean item) {
        helper.setText(R.id.tv_title,item.getName())
                .setText(R.id.tv_address,item.getAddress())
                .setText(R.id.tv_time,"营业："+item.getTime());
    }
}
