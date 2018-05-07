package cn.itsite.acommon.event;

import cn.itsite.acommon.data.bean.DeliveryBean;

/**
 * Created by liujia on 03/05/2018.
 */

public class EventSelectedDelivery {

    public DeliveryBean deliveryBean;

    public EventSelectedDelivery(DeliveryBean delivery){
        deliveryBean = delivery;
    }

}
