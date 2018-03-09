package cn.itsite.amain.s1.entity.bean;

import java.util.List;

/**
 * Author: LiuJia on 2017/8/9 0009 14:08.
 * Email: liujia95me@126.com
 */

public class DevicesBean extends BaseBean{


    /**
     * data : {"deviceTypeList":[{"code":"door_magnet","icon":"","name":"门磁"},{"code":"glass_detector","icon":"","name":"玻璃探测器"},{"code":"gas_detector","icon":"","name":"煤气探测器"},{"code":"smoke_detector","icon":"","name":"烟雾探测器"},{"code":"emergency_button","icon":"","name":"紧急annual"},{"code":"curtains","icon":"","name":"震动传感器"},{"code":"water_sensor","icon":"","name":"水感应器"},{"code":"infrared_sensor","icon":"","name":"红外感应器"},{"code":"infrared_beam","icon":"","name":"红外光束"},{"code":"remote_control","icon":"","name":"遥控器"},{"code":"RFID","icon":"","name":"RFID"},{"code":"door_bell","icon":"","name":"门铃"},{"code":"id_keypress","icon":"","name":"ID按键"},{"code":"fingerprint_lock","icon":"","name":"指纹锁"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<DeviceTypeListBean> deviceTypeList;

        public List<DeviceTypeListBean> getDeviceTypeList() {
            return deviceTypeList;
        }

        public void setDeviceTypeList(List<DeviceTypeListBean> deviceTypeList) {
            this.deviceTypeList = deviceTypeList;
        }

        public static class DeviceTypeListBean {
            /**
             * code : door_magnet
             * icon :
             * name : 门磁
             */

            private String code;
            private String icon;
            private String name;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
