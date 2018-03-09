package cn.itsite.amain.s1.entity.bean;

import cn.itsite.abase.utils.DensityUtils;
import cn.itsite.amain.s1.App;


/**
 * Author: LiuJia on 2017/5/17 0017 20:58.
 * Email: liujia95me@126.com
 */

public class DeviceButtonBean {

    public int x;
    public int y;

    private DeviceButtonBean() {
    }

    public DeviceButtonBean(float percentX, float percentY) {
        x = (int) (DensityUtils.getDisplayWidth(App.mContext) * percentX);
        int displayHeight = DensityUtils.getDisplayHeight(App.mContext)-DensityUtils.dp2px(App.mContext,56);
        y = (int) (displayHeight * percentY);
    }
}
