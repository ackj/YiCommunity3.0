package cn.itsite.amain.yicommunity.main.sociality.contract;

import java.util.List;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.CommunityBean;
import rx.Observable;


/**
 * Author: LiuJia on 2017/9/5 0005 17:21.
 * Email: liujia95me@126.com
 */

public interface SocialityContract {

    interface View extends BaseContract.View {
        void responseCommunitys(List<CommunityBean.DataBean.CommunityInfoListBean> datas);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestCommunitys(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<CommunityBean> requestCommunitys(Params params);
    }
}
