package cn.itsite.amain.yicommunity.main.door.view;


import com.chad.library.adapter.base.BaseViewHolder;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.entity.bean.DoorListBean;


/**
 * Author: LiuJia on 2017/4/21 9:27.
 * Email: liujia95me@126.com
 */
public class AppointOpenDoorRVAdapter extends BaseRecyclerViewAdapter<DoorListBean.DataBean, BaseViewHolder> {

    public AppointOpenDoorRVAdapter() {
        super(R.layout.item_appoint_opendoor);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoorListBean.DataBean item) {
        helper.setText(R.id.tv_name, item.getName());
    }
}
