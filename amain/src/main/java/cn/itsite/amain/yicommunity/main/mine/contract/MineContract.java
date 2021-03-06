package cn.itsite.amain.yicommunity.main.mine.contract;


import cn.itsite.abase.common.BaseBean;
import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.abase.network.http.BaseOldResponse;
import cn.itsite.acommon.data.bean.UserInfoBean;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.UnreadMessageBean;
import rx.Observable;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 邻里模块所对应的各层对象应有的接口。
 */
public interface MineContract {

    interface View extends BaseContract.View {
        //退出登录成功
        void responseLogout(String message);

        void responseCache(String message);

        void responseUnreadMark(UnreadMessageBean bean);

        void responseInfo(BaseOldResponse<UserInfoBean.MemberInfoBean> response);


    }

    interface Presenter extends BaseContract.Presenter {
        //退出登录
        void requestLogout(Params params);

        void requestCache();

        void requestClearCache();

        void requestUnreadMark(Params params);

        void requestInfo(String token);


    }

    interface Model extends BaseContract.Model {
        Observable<BaseBean> requestLogout(Params params);

        Observable<String> requestCache();

        Observable<String> requestClearCache();

        Observable<UnreadMessageBean> requestUnreadMark(Params params);

        Observable<BaseOldResponse<UserInfoBean.MemberInfoBean>> requestInfo(String token);


    }
}