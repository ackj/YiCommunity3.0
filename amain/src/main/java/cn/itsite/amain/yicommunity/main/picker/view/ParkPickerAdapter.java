package cn.itsite.amain.yicommunity.main.picker.view;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.entity.bean.ParkSelectBean;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 选择停车场模块。
 */

public class ParkPickerAdapter extends BaseMultiItemQuickAdapter<ParkSelectBean.DataBean.ParkPlaceListBean, BaseViewHolder> {
    public static final int TYPE_CONTENT = 0;
    public static final int TYPE_NAME = 1;
    public List<ParkSelectBean.DataBean.ParkPlaceListBean> listHistory;

    public ParkPickerAdapter() {
        super(null);
        addItemType(TYPE_NAME, R.layout.item_rv_park_selector_type);
        addItemType(TYPE_CONTENT, R.layout.item_rv_park_selector);
    }

    @Override
    protected void convert(BaseViewHolder holder, ParkSelectBean.DataBean.ParkPlaceListBean item) {
        switch (holder.getItemViewType()) {
            case TYPE_NAME:
                holder.setText(R.id.tv_name_item_rv_park_selector_type, item.getName())
                        .setVisible(R.id.iv_clean_item_rv_park_selector_type,
                                holder.getLayoutPosition() == 0 && item.getName().equals("历史记录"))
                        .addOnClickListener(R.id.iv_clean_item_rv_park_selector_type);
                break;
            case TYPE_CONTENT:
                holder.setText(R.id.tv_name_item_rv_park_selector, item.getName())
                        .setText(R.id.tv_address_item_rv_park_selector, item.getAddress())
                        .addOnClickListener(R.id.cv_item_rv_park_selector);
                break;
            default:
        }
    }

    public void notifyHistory(List<ParkSelectBean.DataBean.ParkPlaceListBean> listHistory) {
        this.listHistory = new ArrayList<>(listHistory);
        if (!listHistory.isEmpty()) {
            ParkSelectBean.DataBean.ParkPlaceListBean history = new ParkSelectBean.DataBean.ParkPlaceListBean();
            history.setItemType(1);
            history.setName("历史记录");
            listHistory.add(0, history);
        }
        setNewData(listHistory);
    }

    public void removeHistory() {
        mData.remove(0);
        mData.removeAll(listHistory);
        notifyDataSetChanged();
    }
}
