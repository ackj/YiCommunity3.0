package cn.itsite.amain.s1.room.view;

import android.widget.ImageView;

import cn.itsite.abase.BaseApp;
import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.amain.R;
import cn.itsite.amain.s1.entity.bean.DeviceListBean;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;


/**
 * Author: LiuJia on 2017/11/13 0013 09:46.
 * Email: liujia95me@126.com
 */

public class DeviceGridRVAdapter extends BaseRecyclerViewAdapter<DeviceListBean.DataBean.SubDevicesBean,BaseViewHolder> {

    public DeviceGridRVAdapter() {
        super(R.layout.item_security);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeviceListBean.DataBean.SubDevicesBean item) {
        helper.setVisible(R.id.iv_state,false)
                .setText(R.id.tv_name_item_security,item.getName());

        ImageView ivIcon = helper.getView(R.id.iv_icon_item_security);
        Glide.with(BaseApp.mContext)
                .load("add_icon".equals(item.getIcon()) ? R.drawable.ic_add_house_red_140px : item.getIcon())
                .into(ivIcon);
    }
}
