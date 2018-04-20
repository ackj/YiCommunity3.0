package cn.itsite.amain.s1.net.contract;


import cn.itsite.abase.mvp.contract.base.BaseContract;

import rx.Observable;

/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 */
public interface NetContract {

    interface View extends BaseContract.View {
        void command(int cmd, String response);
    }

    interface Presenter extends BaseContract.Presenter {
        void command(int cmd, String params);
    }

    interface Model extends BaseContract.Model {
        Observable command(int cmd, String params);
    }
}