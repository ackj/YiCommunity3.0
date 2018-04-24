package com.example.ecmain;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aglhz.yicommunity.R;
import com.orhanobut.logger.Logger;

import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.albs.location.LocationFragment;
import cn.itsite.classify.ClassifyFragment;
import cn.itsite.delivery.view.SelectDeliveryFragment;
import cn.itsite.goodsdetail.view.GoodsDetailFragment;
import cn.itsite.goodshome.view.StoreHomeFragment;
import cn.itsite.goodssearch.view.SearchGoodsFragment;
import cn.itsite.login.LoginFragment;
import cn.itsite.order.view.MineOrderFragment;
import cn.itsite.order.view.OrderDetailFragment;
import cn.itsite.order.view.SubmitOrderFragment;
import cn.itsite.shoppingcart.ShoppingCartFragment;
import rx.Subscriber;

/**
 * Author： Administrator on 2018/2/1 0001.
 * Email： liujia95me@126.com
 */

public class MainFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "MainFragment";

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();
    }

    private void initListener() {
        getView().findViewById(R.id.btn_1).setOnClickListener(this);
        getView().findViewById(R.id.btn_2).setOnClickListener(this);
        getView().findViewById(R.id.btn_3).setOnClickListener(this);
        getView().findViewById(R.id.btn_4).setOnClickListener(this);
        getView().findViewById(R.id.btn_5).setOnClickListener(this);
        getView().findViewById(R.id.btn_6).setOnClickListener(this);
        getView().findViewById(R.id.btn_7).setOnClickListener(this);
        getView().findViewById(R.id.btn_8).setOnClickListener(this);
        getView().findViewById(R.id.btn_9).setOnClickListener(this);
        getView().findViewById(R.id.btn_10).setOnClickListener(this);
        getView().findViewById(R.id.btn_11).setOnClickListener(this);
        getView().findViewById(R.id.btn_12).setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btn_1){
            start(StoreHomeFragment.newInstance());
        }else if(view.getId()==R.id.btn_2){
            start(SearchGoodsFragment.newInstance());
        }else if(view.getId()==R.id.btn_3) {
            start(SelectDeliveryFragment.newInstance());
        }else if(view.getId()==R.id.btn_4){
            start(ClassifyFragment.newInstance());
        }else if(view.getId()==R.id.btn_5) {
            start(ShoppingCartFragment.newInstance());
        }else if(view.getId()==R.id.btn_6){
            start(SubmitOrderFragment.newInstance());
        }else if(view.getId()==R.id.btn_7){
            start(MineOrderFragment.newInstance());
        } else if(view.getId()==R.id.btn_8){
            start(OrderDetailFragment.newInstance(""));
        }else if(view.getId()==R.id.btn_9){
            start(LoginFragment.newInstance());
        }else if(view.getId()==R.id.btn_10){
            start(LocationFragment.newInstance());
        }else if(view.getId()==R.id.btn_11){
//            start(MineFragment.newInstance());
        }else if(view.getId()==R.id.btn_12){
            start(GoodsDetailFragment.newInstance());
        }
    }

    public void net() {
//        HttpHelper.getService(KeywordService.class)
//                .getKeywords()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseSubscriber<BaseResponse<List<KeywordBean>>>() {
//
//                    @Override
//                    public void onSuccess(BaseResponse<List<KeywordBean>> response) {
//
//
//                        Logger.e("111111111111" + response.getData().get(0).getQuery());
//                    }
//                });


    }

    public abstract class BaseSubscriber<T extends BaseResponse> extends Subscriber<T> {

        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onNext(T response) {
            Logger.e( "onNextonNextonNextonNextonNext");


            if (response.isSuccessful()) {
                onSuccess(response);
            } else if (response.getCode() == 123) {
                Logger.e( "123");
            } else {
                Logger.e( "非200");

//                getView().error(response.getMessage());
            }
        }


        @Override
        public void onCompleted() {
            Logger.e( "onCompletedonCompletedonCompleted");

        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }

        public abstract void onSuccess(T t);
    }
}
