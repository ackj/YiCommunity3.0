package cn.itsite.amain.yicommunity.entity.bean;

import java.util.List;

/**
 * Author： Administrator on 2017/9/29 0029.
 * Email： liujia95me@126.com
 */
public class CommEquipmentBean extends BaseBean{

    /**
     * data : {"dataList":[{"deviceSn":"G111EU6B1000300","houseInfoDetails":"凯宾斯基3栋1单元12层1房","deviceName":"NB300的主机"}]}
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
             * deviceSn : G111EU6B1000300
             * houseInfoDetails : 凯宾斯基3栋1单元12层1房
             * deviceName : NB300的主机
             */

            private String deviceSn;
            private String houseInfoDetails;
            private String deviceName;

            public String getDeviceSn() {
                return deviceSn;
            }

            public void setDeviceSn(String deviceSn) {
                this.deviceSn = deviceSn;
            }

            public String getHouseInfoDetails() {
                return houseInfoDetails;
            }

            public void setHouseInfoDetails(String houseInfoDetails) {
                this.houseInfoDetails = houseInfoDetails;
            }

            public String getDeviceName() {
                return deviceName;
            }

            public void setDeviceName(String deviceName) {
                this.deviceName = deviceName;
            }
        }
    }
}
