package cn.itsite.amain.yicommunity.main.parking.contract;


import java.util.List;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.MonthCardBillListBean;
import rx.Observable;

/**
 * Author: LiuJia on 2017/5/27 0027 15:31.
 * Email: liujia95me@126.com
 */

public interface RechargeRecordContract {

    interface View extends BaseContract.View {
        void responseRechargeRecord(List<MonthCardBillListBean.DataBean.MonthCardBillBean> datas);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestMonthCardBillList(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<MonthCardBillListBean> requsetMonthCardBillList(Params params);
    }

}
