package cn.itsite.acommon.model;

import com.google.gson.Gson;

import cn.itsite.abase.mvp.model.base.BaseModel;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.abase.network.http.HttpHelper;
import cn.itsite.acommon.SkusService;
import cn.itsite.acommon.contract.SkusContract;
import cn.itsite.acommon.data.bean.OperateBean;
import cn.itsite.acommon.data.bean.SkusBean;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/22 0022 14:39
 */

public class SkusModel extends BaseModel implements SkusContract.Model {

    @Override
    public Observable<BaseResponse<SkusBean>> getSkus(String uid,String sku) {
        OperateBean operatorOperateBean = new OperateBean();
        operatorOperateBean.product = uid;
        operatorOperateBean.sku = sku;
        return HttpHelper.getService(SkusService.class)
                .getSkus(new Gson().toJson(operatorOperateBean))
                .subscribeOn(Schedulers.io());
    }
}
