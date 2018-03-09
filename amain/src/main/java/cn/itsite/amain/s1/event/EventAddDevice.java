package cn.itsite.amain.s1.event;


import cn.itsite.amain.s1.entity.bean.NotificationBean;

/**
 * Author: LiuJia on 2017/8/24 0024 17:24.
 * Email: liujia95me@126.com
 */

public class EventAddDevice {
    public NotificationBean notificationBean;

    public EventAddDevice(NotificationBean notificationBean) {
        this.notificationBean = notificationBean;
    }
}
