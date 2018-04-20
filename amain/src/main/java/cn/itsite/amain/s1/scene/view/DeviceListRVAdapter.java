package cn.itsite.amain.s1.scene.view;

import android.widget.CheckBox;
import android.widget.ImageView;

import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.amain.R;
import cn.itsite.amain.s1.App;
import cn.itsite.amain.s1.entity.bean.DeviceListBean;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

/**
 * Author: LiuJia on 2017/8/12 0012 16:56.
 * Email: liujia95me@126.com
 */

public class DeviceListRVAdapter extends BaseRecyclerViewAdapter<DeviceListBean.DataBean.SubDevicesBean, BaseViewHolder> {
    private ArrayList<DeviceListBean.DataBean.SubDevicesBean> selectedDevices = new ArrayList<>();

    public DeviceListRVAdapter() {
        super(R.layout.item_rv_device_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeviceListBean.DataBean.SubDevicesBean item) {
        helper.itemView.setOnClickListener(v -> {
            CheckBox cbSelect = helper.getView(R.id.ck_select_item_rv_device_list_fragment);
            cbSelect.setChecked(!cbSelect.isChecked());
            if (cbSelect.isChecked()) {
                if (selectedDevices != null) {
                    selectedDevices.add(item);
                    ALog.e("选中了-->" + selectedDevices.size());
                }
            } else {
                if (selectedDevices != null) {
                    selectedDevices.remove(item);
                    ALog.e("没有选中-->" + selectedDevices.size());

                }
            }
        });

        helper.setText(R.id.tv_name_item_rv_device_list_fragment, item.getName())
                .setText(R.id.tv_online_item_rv_device_list_fragment, item.getIsOline() == 1 ? "在线" : "离线");

        Glide.with(App.mContext)
                .load(item.getIcon())
                .into((ImageView) helper.getView(R.id.iv_icon_item_rv_device_list_fragment));
    }

    public void clearSelector() {
        if (selectedDevices != null) {
            selectedDevices.clear();
        }
    }

    public ArrayList<DeviceListBean.DataBean.SubDevicesBean> getSelector() {
        return selectedDevices;
    }
}
