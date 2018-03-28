package cn.itsite.amain.yicommunity.main.home.contract;


import java.util.List;

import cn.itsite.abase.mvp.contract.base.BaseContract;
import cn.itsite.amain.yicommunity.common.Params;
import cn.itsite.amain.yicommunity.entity.bean.BannerBean;
import cn.itsite.acommon.BaseBean;
import cn.itsite.amain.yicommunity.entity.bean.FirstLevelBean;
import cn.itsite.amain.yicommunity.entity.bean.MainDeviceListBean;
import cn.itsite.amain.yicommunity.entity.bean.NoticeBean;
import cn.itsite.amain.yicommunity.entity.bean.OneKeyDoorBean;
import cn.itsite.amain.yicommunity.entity.bean.ServicesTypesBean;
import cn.itsite.amain.yicommunity.entity.bean.SubCategoryBean;
import rx.Observable;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 管家模块所对应的各层对象应有的接口。
 */
public interface HomeContract {

    interface View extends BaseContract.View {
        void responseBanners(List<BannerBean.DataBean.AdvsBean> banners);

        void responseHomeNotices(List<NoticeBean.DataBean.NoticeListBean> notices);

        void responseOpenDoor();

        void responseServiceClassifyList(List<ServicesTypesBean.DataBean.ClassifyListBean> classifys);

        void responseOneKeyOpenDoorDeviceList(List<OneKeyDoorBean.DataBean.ItemListBean> doorList);

        //智慧商城一级列表
        void responseFirstLevel(List<FirstLevelBean.DataBean> datas);

        //智慧商城二级列表
        void responseSubCategoryList(List<SubCategoryBean.DataBean> datas);

        void responseCommEquipmentList(MainDeviceListBean datas);

        void responseScanOpenDoor(BaseBean baseBean);
    }

    interface Presenter extends BaseContract.Presenter {
        void requestBanners(Params params);

        void requestHomeNotices(Params params);

        void requestOpenDoor();

        void requestServiceTypes(Params params);

        void requestOneKeyOpenDoorDeviceList(Params params);

        //智慧商城一级列表
        void requestFirstLevel(Params params);

        //智慧商城二级列表
        void requestSubCategoryList(Params params);

        void requestCommEquipmentList(Params params);

        void requestScanOpenDoor(Params params);//无人便利店二维码扫描开门。
    }

    interface Model extends BaseContract.Model {
        Observable<BannerBean> requestBanners(Params params);

        Observable<NoticeBean> requestHomeNotices(Params params);

        Observable<BaseBean> requestOpenDoor(Params params);

        Observable<ServicesTypesBean> requestServiceTypes(Params params);

        Observable<OneKeyDoorBean> requestOneKeyOpenDoorDeviceList(Params params);

        //智慧商城一级列表
        Observable<FirstLevelBean> requestFirstLevel(Params params);

        //智慧商城二级列表
        Observable<SubCategoryBean> requestSubCategoryList(Params params);

        Observable<MainDeviceListBean> requestCommEquipmentList(Params params);

        Observable<BaseBean> requestScanOpenDoor(Params params);//无人便利店二维码扫描开门。
    }
}