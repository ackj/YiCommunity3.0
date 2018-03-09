package cn.itsite.amain.s1.camera.contract;

import cn.itsite.abase.mvp.contract.base.BaseContract;

import cn.itsite.amain.s1.common.Params;
import cn.itsite.amain.s1.entity.bean.BaseBean;
import cn.itsite.amain.s1.entity.bean.CameraBean;

import java.util.List;

import cn.itsite.amain.s1.entity.bean.CameraBean;
import rx.Observable;


/**
 * Author: LiuJia on 2017/9/15 0015 18:16.
 * Email: liujia95me@126.com
 */

public interface CameraListContract {

    interface View extends BaseContract.View {
        void responseCameraList(List<CameraBean.DataBean> data);

        void responseSuccess(BaseBean baseBean);

        void responseModSuccess(BaseBean baseBean);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestCameraList(Params params);

        void requestNewCamera(Params params);

        void requestDelCamera(Params params);

        void requestModCamera(Params params);
    }

    interface Model extends BaseContract.Model {
        Observable<CameraBean> requestCameraList(Params params);

        Observable<BaseBean> requestNewCamera(Params params);

        Observable<BaseBean> requestDelCamera(Params params);

        Observable<BaseBean> requestModCamera(Params params);
    }

}
