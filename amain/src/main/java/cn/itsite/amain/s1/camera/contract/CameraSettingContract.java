package cn.itsite.amain.s1.camera.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.entity.bean.BaseBean;

import rx.Observable;


/**
 * Author: LiuJia on 2017/9/19 0019 08:51.
 * Email: liujia95me@126.com
 */

public interface CameraSettingContract {
    interface View extends BaseContract.View {

        void responseSuccess(BaseBean baseBean);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestModCamera(Params params);

    }

    interface Model extends BaseContract.Model {
        Observable<BaseBean> requestModCamera(Params params);
    }
}
