package cn.itsite.amain.yicommunity.main.house.view;

import com.chad.library.adapter.base.BaseViewHolder;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.entity.bean.HouseRightsBean;


/**
 * Author: LiuJia on 2017/4/20 9:37.
 * Email: liujia95me@126.com
 */
public class PermissionRVAdapter extends BaseRecyclerViewAdapter<HouseRightsBean.DataBean.AuthorityBean, BaseViewHolder> {
    public PermissionRVAdapter() {
        super(R.layout.item_rv_permission);
    }

    @Override
    protected void convert(BaseViewHolder helper, HouseRightsBean.DataBean.AuthorityBean item) {
        helper.setText(R.id.tv_desc, item.getName())
                .setChecked(R.id.switch_button, 1 == item.getStatus());
    }
}
