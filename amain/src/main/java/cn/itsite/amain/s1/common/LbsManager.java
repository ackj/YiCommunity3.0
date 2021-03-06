package cn.itsite.amain.s1.common;


import cn.itsite.abase.log.ALog;
import cn.itsite.amain.s1.App;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;


/**
 * Author：leguang on 2017/4/12 0009 15:49
 * Email：langmanleguang@qq.com
 * <p>
 * LBS服务管理类。
 */

public class LbsManager {
    private static final String TAG = LbsManager.class.getSimpleName();
    private static AMapLocationClient mLocationClient;
    private static LocateCallBack mCallBack;
    private static volatile LbsManager INSTANCE;

    //构造方法私有
    private LbsManager() {
        init();
    }

    //获取单例
    public static LbsManager getInstance() {
        if (INSTANCE == null) {
            synchronized (LbsManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LbsManager();
                }
            }
        }
        return INSTANCE;
    }

    private void init() {
        //初始化定位
        if (mLocationClient != null) {
            return;
        }
        mLocationClient = new AMapLocationClient(App.mContext);
        //设置定位回调监听

        //初始化AMapLocationClientOption对象
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //该方法默认为false。
        mLocationOption.setOnceLocation(false);

        //设置setOnceLocationLatest(boolean b)接口为true，
        // 启动定位时SDK会返回最近3s内精度最高的一次定位结果。
        // 如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        //获取最近3s内精度最高的一次定位结果：
        mLocationOption.setOnceLocationLatest(false);

        //定位的间隔
        mLocationOption.setInterval(1000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
    }


    public void startLocation(LocateCallBack callBack) {
        try {
            //启动定位
            mCallBack = callBack;

            mLocationClient.setLocationListener(amapLocation -> {
                if (mCallBack != null) {
                    mCallBack.CallBack(amapLocation);
                }

                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        ALog.e(TAG, amapLocation.toString());
                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        ALog.e("AmapError", "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                    }
                }
            });

            mLocationClient.startLocation();

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void startLocation() {
        mLocationClient.startLocation();
    }

    public void stopLocation() {
        if (mLocationClient == null) {
            return;
        }
        mLocationClient.stopLocation();
    }

    public void geocode(String address, String city, OnGeocodeListener listener) {

        GeocodeSearch geocoderSearch = new GeocodeSearch(App.mContext);
        geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult result, int resultCode) {
            }

            @Override
            public void onGeocodeSearched(GeocodeResult result, int resultCode) {

                if (resultCode == AMapException.CODE_AMAP_SUCCESS) {
                    if (result != null && result.getGeocodeAddressList() != null
                            && result.getGeocodeAddressList().size() > 0) {
                        GeocodeAddress address = result.getGeocodeAddressList().get(0);
                        ALog.e("经纬度值:" + address.getLatLonPoint() + "\n位置描述:" + address.getFormatAddress());

                        listener.onGeocode(address.getLatLonPoint().getLongitude(), address.getLatLonPoint().getLatitude());
                    }
                }
            }
        });

        GeocodeQuery query = new GeocodeQuery(address, city);

        geocoderSearch.getFromLocationNameAsyn(query);

    }

    //若销毁，则需要重新创建LocationClient，所以一般只要stopLocation。
    public void clear() {
        if (mLocationClient == null) {
            return;
        }
        if (mLocationClient.isStarted()) {
            mLocationClient.stopLocation();
        }
        mLocationClient.onDestroy();
    }

    public interface LocateCallBack {

        void CallBack(AMapLocation aMapLocation);
    }

    public interface OnGeocodeListener {

        void onGeocode(double longitude, double latitude);
    }

    public interface OnReGeocodeListener {

        void onReGeocode(String result);
    }
}
