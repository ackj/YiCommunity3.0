package cn.itsite.amain.s1.net.model;

import cn.itsite.abase.mvp.model.base.BaseModel;

import com.tsvclient.ipc.WifiIpc;

import cn.itsite.amain.s1.net.contract.NetContract;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 */

public class NetModel extends BaseModel implements NetContract.Model {
    public static final String TAG = NetModel.class.getSimpleName();
    public static final String IP = "10.10.10.250";
    public static final int PORT = 12368;

    @Override
    public Observable command(int cmd, String params) {
        return Observable.create((Observable.OnSubscribe<String>) s -> {
            try {
                String result = WifiIpc.TSV_C_SendXmlCommand(IP, PORT, cmd, cmd, params);
                s.onNext(result);
            } catch (Exception e) {
                s.onError(e);
            }
            s.onCompleted();
        }).subscribeOn(Schedulers.io());
    }
}