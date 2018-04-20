package cn.itsite.amain.yicommunity.main.parking.presenter;

import android.support.annotation.NonNull;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.abase.common.BaseBean;
import cn.itsite.amain.yicommunity.entity.bean.MonthlyPayRulesBean;
import cn.itsite.amain.yicommunity.main.parking.contract.CarCardPayContract;
import cn.itsite.amain.yicommunity.main.parking.model.CarCardPayModel;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Author：leguang on 2016/10/9 0009 10:35
 * Email：langmanleguang@qq.com
 * <p>
 * 负责邻里模块Presenter层内容。
 */

public class CarCardPayPresenter extends BasePresenter<CarCardPayContract.View, CarCardPayContract.Model> implements CarCardPayContract.Presenter {
    private final String TAG = CarCardPayPresenter.class.getSimpleName();

    public CarCardPayPresenter(CarCardPayContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected CarCardPayContract.Model createModel() {
        return new CarCardPayModel();
    }

    @Override
    public void requestDeleteCarCard(Params params) {
        mRxManager.add(mModel.requestDeleteCarCard(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<BaseBean>() {
                    @Override
                    public void _onNext(BaseBean baseBean) {
                        if (baseBean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                            getView().responseDeleteCarCard(baseBean);
                        } else {
                            getView().error(baseBean.getOther().getMessage());
                        }
                    }
                }));
    }

    @Override
    public void requestMonthlyPayRules(Params params) {
        mRxManager.add(mModel.requestMonthlyPayRules(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<MonthlyPayRulesBean>() {
                    @Override
                    public void _onNext(MonthlyPayRulesBean bean) {
                        if (bean.getOther().getCode() == Constants.RESPONSE_CODE_SUCCESS) {
                            getView().responseMonthlyPayRules(bean);
                        } else {
                            getView().error(bean.getOther().getMessage());
                        }
                    }
                }));
    }

//    @Override
//    public void requestCarCardBill(Params params) {
//        mRxManager.add(mModel.requestCarCardBill(params)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new RxSubscriber<ResponseBody>() {
//                    @Override
//                    public void _onNext(ResponseBody responseBody) {
//                        JSONObject jsonObject;
//                        try {
//                            jsonObject = new JSONObject(responseBody.string());
//                            JSONObject jsonOther = jsonObject.optJSONObject("other");
//                            String code = jsonOther.optString("code");
//                            if ("200".equals(code)) {
//                                JSONObject jsonData = jsonObject.optJSONObject("data");
//                                getView().responseCarCardBill(jsonData);
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