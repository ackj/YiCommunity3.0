package cn.itsite.order.presenter;

import android.support.annotation.NonNull;

import java.io.File;
import java.util.List;

import cn.itsite.abase.BaseApp;
import cn.itsite.abase.common.luban.Luban;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.presenter.base.BasePresenter;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.acommon.OperateBean;
import cn.itsite.order.contract.InputCommentContract;
import cn.itsite.order.model.InputCommentModel;
import cn.itsite.order.model.PostCommentBean;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liujia on 2018/4/20.
 */

public class InputCommentPresenter extends BasePresenter<InputCommentContract.View,InputCommentContract.Model> implements InputCommentContract.Presenter {
    /**
     * 创建Presenter的时候就绑定View和创建model。
     *
     * @param mView 所要绑定的view层对象，一般在View层创建Presenter的时候通过this把自己传过来。
     */
    public InputCommentPresenter(InputCommentContract.View mView) {
        super(mView);
    }

    @NonNull
    @Override
    protected InputCommentContract.Model createModel() {
        return new InputCommentModel();
    }

    @Override
    public void postPicture(List<File> pictures,int position) {
        Luban.get(BaseApp.mContext)
                .load(pictures)
                .putGear(Luban.THIRD_GEAR)
                .asList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        files -> {
                            ALog.e(Thread.currentThread().getName());
                            mRxManager.add(mModel.postPictures(files)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new BaseSubscriber<BaseResponse<List<OperateBean>>>() {
                                        @Override
                                        public void onSuccess(BaseResponse<List<OperateBean>> response) {
                                            getView().responsePostPicture(response,position);
                                        }
                                    }));
                        });

    }

    @Override
    public void postComments(PostCommentBean commentBean) {
        mRxManager.add(mModel.postComments(commentBean)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        getView().responsePostCommentsSuccess(response);
                    }
                }));
    }
}
