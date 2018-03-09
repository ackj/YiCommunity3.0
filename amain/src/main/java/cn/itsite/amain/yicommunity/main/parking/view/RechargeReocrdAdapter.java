package cn.itsite.amain.yicommunity.main.parking.view;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.entity.bean.MonthCardBillListBean;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 车卡管理充值记录模块。
 */

public class RechargeReocrdAdapter extends BaseRecyclerViewAdapter<MonthCardBillListBean.DataBean.MonthCardBillBean, BaseViewHolder> {

    public RechargeReocrdAdapter() {
        super(R.layout.item_recharge_record);
    }

    @Override
    protected void convert(BaseViewHolder helper, MonthCardBillListBean.DataBean.MonthCardBillBean item) {
        helper.setText(R.id.tv_plate_item_recharge_record, item.getCarNo())
                .setText(R.id.tv_park_item_recharge_record, item.getParkPlace().getName())
                .setText(R.id.tv_pay_time_item_recharge_record, item.getStartTime() + "至" + item.getEndTime())
                .setText(R.id.tv_charge_item_recharge_record, item.getMoney() + "元")
                .setText(R.id.tv_recharge_time_item_recharge_record, "充值日期：" + item.getPayTime())
                .setText(R.id.tv_order_item_recharge_record, item.getBillCode());
    }
}
