package cn.itsite.amain.yicommunity.main.picker.contract;

import java.util.List;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.CommunitySelectBean;
import rx.Observable;


/**
 * Author: LiuJia on 2017/5/8 0008 11:33.
 * Email: liujia95me@126.com
 */

public interface CommunityPickerContract {

    interface View extends BaseContract.View {
        void responseCommunitys(List<CommunitySelectBean.DataBean.CommunitiesBean> bean);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestCommunitys(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<CommunitySelectBean> requestCommunitys(Params params);
    }
}
