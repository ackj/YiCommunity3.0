package cn.itsite.amain.s1.more.contract;


import cn.itsite.abase.mvp.contract.base.BaseContract;


import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.entity.bean.BaseBean;
import rx.Observable;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 邻里模块所对应的各层对象应有的接口。
 */
public interface MoreContract {

    interface View extends BaseContract.View {
        //退出登录成功
        void responseLogout(String message);
    }

    interface Presenter extends BaseContract.Presenter {
        //退出登录
        void requestLogout(Params params);

    }

    interface Model extends BaseContract.Model {
        Observable<BaseBean> requestLogout(Params params);
    }
}