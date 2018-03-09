package cn.itsite.amain.s1.entity.bean;

import java.util.List;

/**
 * Author: LiuJia on 2017/9/26 0026 10:09.
 * Email: liujia95me@126.com
 */

public class EquipmentBean extends BaseBean{

    /**
     * data : {"dataList":[{"deviceName":"刘嘉1","deviceSn":"G111EU6B1000305"},{"deviceName":"刘嘉1","deviceSn":"G111EU6B1000305"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<DataListBean> dataList;

        public List<DataListBean> getDataList() {
            return dataList;
        }

        public void setDataList(List<DataListBean> dataList) {
            this.dataList = dataList;
        }

        public static class DataListBean {
            /**
             * deviceName : 刘嘉1
             * deviceSn : G111EU6B1000305
             */

            private String deviceName;
            private String deviceSn;

            public String getDeviceName() {
                return deviceName;
            }

            public void setDeviceName(String deviceName) {
                this.deviceName = deviceName;
            }

            public String getDeviceSn() {
                return deviceSn;
            }

            public void setDeviceSn(String deviceSn) {
                this.deviceSn = deviceSn;
            }
        }
    }
}
