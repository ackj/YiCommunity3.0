package cn.itsite.amain.s1.scene.view;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.itsite.amain.R;
import cn.itsite.amain.s1.App;
import cn.itsite.amain.s1.entity.bean.DeviceListBean;

/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 场景模块。
 */

public class AddSceneRVAdapter extends BaseMultiItemQuickAdapter<DeviceListBean.DataBean.SubDevicesBean, BaseViewHolder> {
    public static final int TYPE_SWITCH = 100;
    public static final int TYPE_RELAY = 101;


    public AddSceneRVAdapter() {
        super(null);
        addItemType(TYPE_SWITCH, R.layout.item_rv_add_scene);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeviceListBean.DataBean.SubDevicesBean item) {
        switch (helper.getItemViewType()) {
            case TYPE_SWITCH:
                helper.setText(R.id.tv_name_item_rv_add_scene_fragment, item.getName())
                        .addOnClickListener(R.id.tv_node_item_rv_add_scene_fragment)
                        .addOnClickListener(R.id.tv_action_item_rv_add_scene_fragment)
                        .addOnClickListener(R.id.iv_delete_item_rv_add_scene_fragment);
                Glide.with(App.mContext)
                        .load(item.getIcon())
                        .into((ImageView) helper.getView(R.id.iv_icon_item_rv_add_scene_fragment));
                break;
        }
    }
}
