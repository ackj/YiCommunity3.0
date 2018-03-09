package cn.itsite.amain.s1.entity.bean;

import java.util.List;

import cn.itsite.amain.yicommunity.entity.bean.MainDeviceListBean;

/**
 * Author： Administrator on 2017/9/29 0029.
 * Email： liujia95me@126.com
 */
public class SmartHomeBean {

    public static final int TYPE_EQUIPMENT = 1;

    public static final int TYPE_CAMERA = 2;

    public List<MainDeviceListBean.DataBean> equipmentList;

    public List<MainDeviceListBean.DataBean> cameraList;

    public int type;

    public SmartHomeBean(int type, List<MainDeviceListBean.DataBean> equipmentList, List<MainDeviceListBean.DataBean> cameraList) {
        this.type = type;
        this.equipmentList = equipmentList;
        this.cameraList = cameraList;
    }

}
