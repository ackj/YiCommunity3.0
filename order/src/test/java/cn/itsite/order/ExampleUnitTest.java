package cn.itsite.order;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import cn.itsite.abase.network.http.BaseRequest;
import cn.itsite.acommon.OperateBean;
import cn.itsite.order.model.PayParams;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);

        OperateBean operateBean0 = new OperateBean();
        operateBean0.uid = "0";

        OperateBean operateBean1 = new OperateBean();
        operateBean0.uid = "1";

        OperateBean operateBean2 = new OperateBean();
        operateBean0.uid = "2";


        List<OperateBean> operateBeans = Arrays.asList(operateBean0, operateBean1, operateBean2);

        BaseRequest<PayParams> request = new BaseRequest<>();

        request.message = "请求统一订单";

        Observable.from(operateBeans).map(OperateBean::getUid).toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(strings -> {
                    PayParams payParams = new PayParams();
                    payParams.setOrders(strings);
                    request.data = payParams;

                    System.out.println(request.toString());

                });

    }
}