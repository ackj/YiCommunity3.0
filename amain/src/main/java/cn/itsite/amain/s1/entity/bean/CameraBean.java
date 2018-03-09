package cn.itsite.amain.s1.entity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author: LiuJia on 2017/9/18 0018 09:27.
 * Email: liujia95me@126.com
 */

public class CameraBean extends BaseBean {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * defenseStatus :
         * defenseStatusDes :
         * fid : 55cff86f-10fb-4419-9dc2-a37b890bea86
         * isManager : 1
         * name : 5971076
         * no : 5971076
         * password : qq123456789
         * residence : {"addrDet":"","fid":"714820c1-4b0a-46c7-9af6-7d0e8a410060","name":""}
         * status : 0
         */

        private String deviceId;
        private String defenseStatus;
        private String defenseStatusDes;
        private String fid;
        private int isManager;
        private String name;
        private String no;
        private String password;
        private ResidenceBean residence;
        private int status;
        private String gateway;
        private String icon;



        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getDefenseStatus() {
            return defenseStatus;
        }

        public void setDefenseStatus(String defenseStatus) {
            this.defenseStatus = defenseStatus;
        }

        public String getDefenseStatusDes() {
            return defenseStatusDes;
        }

        public void setDefenseStatusDes(String defenseStatusDes) {
            this.defenseStatusDes = defenseStatusDes;
        }

        public String getFid() {
            return fid;
        }

        public void setFid(String fid) {
            this.fid = fid;
        }

        public int getIsManager() {
            return isManager;
        }

        public void setIsManager(int isManager) {
            this.isManager = isManager;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
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

        public static class ResidenceBean implements Serializable {
            /**
             * addrDet :
             * fid : 714820c1-4b0a-46c7-9af6-7d0e8a410060
             * name :
             */

            private String addrDet;
            private String fid;
            private String name;

            public String getAddrDet() {
                return addrDet;
            }

            public void setAddrDet(String addrDet) {
                this.addrDet = addrDet;
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
