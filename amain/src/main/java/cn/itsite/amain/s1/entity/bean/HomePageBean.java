package cn.itsite.amain.s1.entity.bean;

import java.util.List;

/**
 * Author: LiuJia on 2017/8/9 0009 11:35.
 * Email: liujia95me@126.com
 */

public class HomePageBean extends BaseBean {


    /**
     * data : {"gatewayList":[{"fid":"qbfvfdvd","isCurrent":1,"isManager":1,"isOnline":0,"name":"S1智能网关","residence":{"addr":"惠州江北凯宾斯基C座","fid":"irifirkfk","name":"凯宾斯基"},"status":0},{"fid":"2d68f281-825d-48f9-941f-41d1c73eacec","isCurrent":0,"isManager":1,"isOnline":0,"name":"S1智能网关","residence":{"addr":"惠州江北凯宾斯基C座","fid":"irifirkfk","name":"凯宾斯基"},"status":0}],"subDevices":[]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<GatewayListBean> gatewayList;
        private List<?> subDevices;

        public List<GatewayListBean> getGatewayList() {
            return gatewayList;
        }

        public void setGatewayList(List<GatewayListBean> gatewayList) {
            this.gatewayList = gatewayList;
        }

        public List<?> getSubDevices() {
            return subDevices;
        }

        public void setSubDevices(List<?> subDevices) {
            this.subDevices = subDevices;
        }

        public static class GatewayListBean {
            /**
             * fid : qbfvfdvd
             * isCurrent : 1
             * isManager : 1
             * isOnline : 0
             * name : S1智能网关
             * residence : {"addr":"惠州江北凯宾斯基C座","fid":"irifirkfk","name":"凯宾斯基"}
             * status : 0
             */
            private String fid;
            private int isCurrent;
            private int isManager;
            private int isOnline;
            private String name;
            private ResidenceBean residence;
            private int status;

            public String getFid() {
                return fid;
            }

            public void setFid(String fid) {
                this.fid = fid;
            }

            public int getIsCurrent() {
                return isCurrent;
            }

            public void setIsCurrent(int isCurrent) {
                this.isCurrent = isCurrent;
            }

            public int getIsManager() {
                return isManager;
            }

            public void setIsManager(int isManager) {
                this.isManager = isManager;
            }

            public int getIsOnline() {
                return isOnline;
            }

            public void setIsOnline(int isOnline) {
                this.isOnline = isOnline;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public ResidenceBean getResidence() {
                return residence;
            }

            public void setResidence(ResidenceBean residence) {
                this.residence = residence;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public static class ResidenceBean {
                /**
                 * addr : 惠州江北凯宾斯基C座
                 * fid : irifirkfk
                 * name : 凯宾斯基
                 */

                private String addr;
                private String fid;
                private String name;

                public String getAddr() {
                    return addr;
                }

                public void setAddr(String addr) {
                    this.addr = addr;
                }

                public String getFid() {
                    return fid;
                }

                public void setFid(String fid) {
                    this.fid = fid;
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
}
