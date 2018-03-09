package cn.itsite.amain.yicommunity.main.parking.contract;

import java.util.List;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.ParkRecordListBean;
import rx.Observable;

/**
 * Author: LiuJia on 2017/5/23 0023 09:18.
 * Email: liujia95me@126.com
 */

public interface ParkRecordContract {

    interface View extends BaseContract.View {
        void responseParkRecord(List<ParkRecordListBean.PackRecordBean> datas);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestParkReocrd(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<ParkRecordListBean> requestParkRecord(Params params);
    }
}
