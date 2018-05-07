package cn.itsite.goodsdetail.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.acommon.data.GoodsParams;
import cn.itsite.goodsdetail.contract.GoodsCommentContract;
import cn.itsite.goodsdetail.model.EvaluatesBean;
import cn.itsite.goodsdetail.model.GoodsCommentModel;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by liujia on 2018/4/22.
 */

public class GoodsCommentPresenter extends BasePresenter<GoodsCommentContract.View,GoodsCommentContract.Model> implements GoodsCommentContract.Presenter {

    /**
     * 创建Presenter的时候就绑定View和创建model。
     *
     * @param mView 所要绑定的view层对象，一般在View层创建Presenter的时候通过this把自己传过来。
     */
    public GoodsCommentPresenter(GoodsCommentContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected GoodsCommentContract.Model createModel() {
        return new GoodsCommentModel();
    }

    @Override
    public void getComments(GoodsParams params) {
        mRxManager.add(mModel.getComments(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse<List<EvaluatesBean>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<EvaluatesBean>> response) {
                        getView().responseGetComments(response.getData());
                    }
                }));
    }
}
