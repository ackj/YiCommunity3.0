package cn.itsite.amain.yicommunity.entity.bean;

import java.util.List;

import cn.itsite.acommon.BaseBean;

/**
 * Author: LiuJia on 2017/5/24 0024 11:55.
 * Email: liujia95me@126.com
 */

public class MonthlyPayRulesBean extends BaseBean {

    /**
     * data : {"parkCardFid":"d9543e9d-e870-41b3-b081-acdf69e9567b","parkPlaceFid":"51b56def-7530-449c-83fa-1d5e0660de6c","parkPlaceName":"凯宾斯基停车场","customerName":"awgfqw","carNo":"冀667778","phoneNo":"18086527290","monthCardRuleList":[{"fid":"103ad16d-006a-4f4f-8fda-0b8d47c462d6","name":"一个月","monthCount":1,"money":0.1,"startDate":"2017-11-08","endDate":"2017-12-08","sequenceNum":1,"remark":""},{"fid":"c421ec88-82ba-48f5-8824-250be1ddfe57","name":"两个月","monthCount":2,"money":0.2,"startDate":"2017-11-08","endDate":"2018-01-08","sequenceNum":2,"remark":""},{"fid":"3b11a7e7-d186-4356-bfb1-b0f7c849399b","name":"三个月","monthCount":3,"money":0.3,"startDate":"2017-11-08","endDate":"2018-02-08","sequenceNum":3,"remark":""}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * parkCardFid : d9543e9d-e870-41b3-b081-acdf69e9567b
         * parkPlaceFid : 51b56def-7530-449c-83fa-1d5e0660de6c
         * parkPlaceName : 凯宾斯基停车场
         * customerName : awgfqw
         * carNo : 冀667778
         * phoneNo : 18086527290
         * monthCardRuleList : [{"fid":"103ad16d-006a-4f4f-8fda-0b8d47c462d6","name":"一个月","monthCount":1,"money":0.1,"startDate":"2017-11-08","endDate":"2017-12-08","sequenceNum":1,"remark":""},{"fid":"c421ec88-82ba-48f5-8824-250be1ddfe57","name":"两个月","monthCount":2,"money":0.2,"startDate":"2017-11-08","endDate":"2018-01-08","sequenceNum":2,"remark":""},{"fid":"3b11a7e7-d186-4356-bfb1-b0f7c849399b","name":"三个月","monthCount":3,"money":0.3,"startDate":"2017-11-08","endDate":"2018-02-08","sequenceNum":3,"remark":""}]
         */

        private String parkCardFid;
        private String parkPlaceFid;
        private String parkPlaceName;
        private String customerName;
        private String carNo;
        private String phoneNo;
        private List<MonthCardRuleListBean> monthCardRuleList;

        public String getParkCardFid() {
            return parkCardFid;
        }

        public void setParkCardFid(String parkCardFid) {
            this.parkCardFid = parkCardFid;
        }

        public String getParkPlaceFid() {
            return parkPlaceFid;
        }

        public void setParkPlaceFid(String parkPlaceFid) {
            this.parkPlaceFid = parkPlaceFid;
        }

        public String getParkPlaceName() {
            return parkPlaceName;
        }

        public void setParkPlaceName(String parkPlaceName) {
            this.parkPlaceName = parkPlaceName;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCarNo() {
            return carNo;
        }

        public void setCarNo(String carNo) {
            this.carNo = carNo;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public List<MonthCardRuleListBean> getMonthCardRuleList() {
            return monthCardRuleList;
        }

        public void setMonthCardRuleList(List<MonthCardRuleListBean> monthCardRuleList) {
            this.monthCardRuleList = monthCardRuleList;
        }

        public static class MonthCardRuleListBean {
            /**
             * fid : 103ad16d-006a-4f4f-8fda-0b8d47c462d6
             * name : 一个月
             * monthCount : 1
             * money : 0.1
             * startDate : 2017-11-08
             * endDate : 2017-12-08
             * sequenceNum : 1
             * remark :
             */

            private String fid;
            private String name;
            private int monthCount;
            private double money;
            private String startDate;
            private String endDate;
            private int sequenceNum;
            private String remark;

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

            public int getMonthCount() {
                return monthCount;
            }

            public void setMonthCount(int monthCount) {
                this.monthCount = monthCount;
            }

            public double getMoney() {
                return money;
            }

            public void setMoney(double money) {
                this.money = money;
            }

            public String getStartDate() {
                return startDate;
            }

            public void setStartDate(String startDate) {
                this.startDate = startDate;
            }

            public String getEndDate() {
                return endDate;
            }

            public void setEndDate(String endDate) {
                this.endDate = endDate;
            }

            public int getSequenceNum() {
                return sequenceNum;
            }

            public void setSequenceNum(int sequenceNum) {
                this.sequenceNum = sequenceNum;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }
}
