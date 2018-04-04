package cn.itsite.acommon.event;

import cn.itsite.acommon.DeliveryBean;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/31 0031 16:14
 */

public class SwitchStoreEvent {

    public String shopUid;
    public DeliveryBean delivery;

    public DeliveryBean getDelivery() {
        return delivery;
    }

    public void setDelivery(DeliveryBean delivery) {
        this.delivery = delivery;
    }

    public SwitchStoreEvent(String shopUid) {
        this.shopUid = shopUid;
    }

    public SwitchStoreEvent(String shopUid, DeliveryBean delivery) {
        this.shopUid = shopUid;
        this.delivery = delivery;
    }
}
