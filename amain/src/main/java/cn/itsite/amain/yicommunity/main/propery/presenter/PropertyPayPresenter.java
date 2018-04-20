package cn.itsite.amain.yicommunity.main.propery.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.PropertyPayDetailBean;
import cn.itsite.amain.yicommunity.main.propery.contract.PropertyPayContract;
import cn.itsite.amain.yicommunity.main.propery.model.PropertyPayModel;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Author: LiuJia on 2017/5/7 0007 21:41.
 * Email: liujia95me@126.com
 */

public class PropertyPayPresenter extends BasePresenter<PropertyPayContract.View, PropertyPayContract.Model> implements PropertyPayContract.Presenter {

    public PropertyPayPresenter(PropertyPayContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected PropertyPayContract.Model createModel() {
        return new PropertyPayModel();
    }

    @Override
    public void requestPropertyNotPay(Params params) {
        mRxManager.add(mModel.requestPropertyNotPay(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == 200) {
                        getView().responsePropertyNotPay(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }


    @Override
    public void requestPropertyPayed(Params params) {
        mRxManager.add(mModel.requestPropertyPayed(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    if (bean.getOther().getCode() == 200) {
                        getView().responsePropertyPayed(bean);
                    } else {
                        getView().error(bean.getOther().getMessage());
                    }
                }, this::error));
    }

    @Override
    public void requestPropertyPayDetail(Params params) {
        mRxManager.add(mModel.requestPropertyPayDetail(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<PropertyPayDetailBean>() {
                    @Override
                    public void _onNext(PropertyPayDetailBean bean) {
                        if (bean.getOther().getCode() == 200) {
                            getView().responsePropertyPayDetail(bean);
                        } else {
                            getView().error(bean.getOther().getMessage());
                        }
                    }
                }));
    }

//    @Override
//    public void requestBill(Params params) {
//        mRxManager.add(mModel.requestBill(params)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new RxSubscriber<ResponseBody>() {
//                    @Override
//                    public void _onNext(ResponseBody body) {
//                        JSONObject jsonObject;
//                        try {
//                            jsonObject = new JSONObject(body.string());
//                            JSONObject jsonOther = jsonObject.optJSONObject("other");
//                            String code = jsonOther.optString("code");
//                            if ("200".equals(code)) {
//                                JSONObject jsonData = jsonObject.optJSONObject("data");
//                                getView().responseBill(jsonData);
//                            } else {
//                                getView().error(jsonOther.optString("message"));
//                            }
//                        } catch (JSONException | IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }));
//    }
}
