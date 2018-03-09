package cn.itsite.amain.s1.main.smarthome.view;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.amain.R;
import cn.itsite.amain.s1.App;
import cn.itsite.amain.yicommunity.entity.bean.MainDeviceListBean;

/**
 * Author: LiuJia on 2017/9/25 0025 10:17.
 * Email: liujia95me@126.com
 */

public class SmartHomeEquipementAdapter extends BaseRecyclerViewAdapter<MainDeviceListBean.DataBean, BaseViewHolder> {

    public SmartHomeEquipementAdapter() {
        super(R.layout.s1_item_smart_home_grid_item);
    }

    @Override
    protected void convert(BaseViewHolder helper,MainDeviceListBean.DataBean item) {
        helper.setText(R.id.tv_name, item.getName());

        ImageView ivLogo = helper.getView(R.id.iv_logo);
        Glide.with(App.mContext)
                .load(item.getName().equals("添加中控") ? R.drawable.ic_add_house_red_140px : R.drawable.ic_zhuji_220px)
                .into(ivLogo);
    }
}
