package cn.itsite.amain.yicommunity.main.propery.contract;

import java.util.List;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.NoticeBean;
import rx.Observable;


/**
 * Author: LiuJia on 2017/5/9 0009 22:33.
 * Email: liujia95me@126.com
 */

public interface NoticeListContract {

    interface View extends BaseContract.View {
        void responseNotices(List<NoticeBean.DataBean.NoticeListBean> datas);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestNotices(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<NoticeBean> requestNotices(Params params);
    }
}
