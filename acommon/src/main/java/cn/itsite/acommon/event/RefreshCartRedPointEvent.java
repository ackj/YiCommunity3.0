package cn.itsite.acommon.event;

import cn.itsite.abase.BaseApp;
import cn.itsite.abase.cache.SPCache;
import cn.itsite.abase.common.BaseConstants;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/4/8 0008 14:23
 */

public class RefreshCartRedPointEvent {

    public int number;

    public RefreshCartRedPointEvent(int number) {
        SPCache.put(BaseApp.mContext, BaseConstants.KEY_CART_NUM, number);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

}
