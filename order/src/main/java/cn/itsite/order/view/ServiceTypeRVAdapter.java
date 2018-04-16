package cn.itsite.order.view;

import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.order.R;
import cn.itsite.order.model.ServiceTypeBean;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/4/13 0013 16:53
 */

public class ServiceTypeRVAdapter extends BaseRecyclerViewAdapter<ServiceTypeBean, BaseViewHolder> {

    public ServiceTypeRVAdapter(List<ServiceTypeBean> data) {
        super(R.layout.item_service_type,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceTypeBean bean) {
        helper.setText(R.id.tv_title, bean.getTitle())
                .setText(R.id.tv_desc, bean.getDesc());
    }
}
