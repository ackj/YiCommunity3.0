package cn.itsite.amain.yicommunity.main.door.contract;


import java.util.List;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.OpenDoorRecordBean;
import rx.Observable;

/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 门禁模块所对应的各层对象应有的接口。
 */
public interface OpenDoorRecordContract {

    interface View extends BaseContract.View {
        void responseRecord(List<OpenDoorRecordBean.DataBean> listRecord);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestRecord(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<OpenDoorRecordBean> getOpenDoorRecord(Params params);
    }
}