package cn.itsite.amain.yicommunity.main.parking.view;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseViewHolder;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.entity.bean.CarCardListBean;


/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 车卡管理模块。
 */
public class CarCardAdapter extends BaseRecyclerViewAdapter<CarCardListBean.DataBean.CardListBean, BaseViewHolder> {
    public static final String TAG = CarCardAdapter.class.getSimpleName();

    public CarCardAdapter() {
        super(R.layout.item_rv_car_card);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarCardListBean.DataBean.CardListBean item) {
        //设置基本数据。
        helper.setText(R.id.tv_plate_item_rv_car_card, item.getCarNo())
                .setText(R.id.tv_name_item_rv_car_card, item.getCustomerName())
                .setText(R.id.tv_time_item_rv_car_card, TextUtils.isEmpty(item.getEndTime()) ? "" : "到期时间：" + item.getEndTime())
                .setText(R.id.tv_park_item_rv_car_card, item.getParkPlace().getName());

        //设置卡的类型，以不同的背景区分。
        boolean isMonthly = Constants.CARD_TYPE_MONTHLY.equals(item.getCardType());
        helper.setBackgroundRes(R.id.cl_header_item_rv_car_card,
                isMonthly ? R.drawable.bg_apply_header_0 : R.drawable.bg_apply_header_1)
                .setVisible(R.id.tv_action_item_rv_car_card, isMonthly);

        //设置章印样式。
        int imageResource = 0;
        if (item.getApproveState() == 2) {
            //-------------- 审核未通过 -------------
            imageResource = R.drawable.ic_stamp_notpass_red_240;
        } else if (item.getApproveState() == 0) {
            //-------------- 审核中 -------------
            imageResource = R.drawable.ic_stamp_review_240;
        } else if (item.getApproveState() == 1) {
            //-------------- 审核通过 -------------
            if (item.getNeedToPayType() == 1) {
                //-------------- 待缴费 -------------
                imageResource = R.drawable.ic_stamp_wait_pay_240;
            } else if (item.getNeedToPayType() == 2) {
                if (item.getSurplusDays() <= 0) {
                    //-------------- 已过期 -------------
                    imageResource = R.drawable.ic_stamp_expired_240;
                }
            }
        }
        helper.setImageResource(R.id.iv_stamp_item_rv_car_card, imageResource);
    }
}
