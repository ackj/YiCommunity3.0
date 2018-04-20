package cn.itsite.amain.yicommunity.main.parking.view;

import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;

import org.litepal.crud.DataSupport;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.App;
import cn.itsite.amain.yicommunity.entity.db.PlateHistoryData;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 选择停车场模块。
 */

public class ParkChargeAdapter extends BaseRecyclerViewAdapter<PlateHistoryData, BaseViewHolder> {
    public static final int TYPE_CONTENT = 0;
    public static final int TYPE_NAME = 1;

    public ParkChargeAdapter() {
        super(R.layout.item_rv_park_charge);
    }

    @Override
    protected void convert(BaseViewHolder holder, PlateHistoryData item) {
        TextView tvPark = holder.getView(R.id.tv_park_item_rv_park_charge);
        ImageView ivClean = holder.getView(R.id.iv_clean_item_rv_park_charge);
        if (holder.getLayoutPosition() == 0) {
            tvPark.setGravity(Gravity.CENTER);
            tvPark.setTextColor(ContextCompat.getColor(App.mContext, R.color.default_color));
            ivClean.setImageResource(R.drawable.ic_clean_gray_42);
        }

        holder.setText(R.id.tv_park_item_rv_park_charge, item.getPlate())
                .addOnClickListener(R.id.tv_park_item_rv_park_charge)
                .addOnClickListener(R.id.iv_clean_item_rv_park_charge);
    }

    public void removeHistory() {
        mData.clear();
        DataSupport.deleteAll(PlateHistoryData.class);
        notifyDataSetChanged();
    }

    public void removeHistory(int position) {
        mData.get(position).delete();
        remove(position);
    }
}
