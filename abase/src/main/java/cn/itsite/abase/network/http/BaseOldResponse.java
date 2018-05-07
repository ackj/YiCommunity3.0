package cn.itsite.abase.network.http;

import cn.itsite.abase.common.BaseBean;

/**
 * Created by liujia on 03/05/2018.
 */

public class BaseOldResponse<T> {
    public BaseBean.OtherBean other;
    public T data;

    public BaseBean.OtherBean getOther() {
        return other;
    }

    public void setOther(BaseBean.OtherBean other) {
        this.other = other;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

