package cn.itsite.aftersales.view;

import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.aftersales.model.ReasonTypeBean;
import me.liujia95.aftersales.R;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/4/13 0013 14:47
 */

public class ReasonRVAdapter extends BaseRecyclerViewAdapter<ReasonTypeBean, BaseViewHolder> {

    public ReasonRVAdapter(List<ReasonTypeBean> reasonTypes) {
        super(R.layout.item_reason, reasonTypes);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReasonTypeBean item) {
        helper.setText(R.id.tv_name, item.getName());
    }
}
