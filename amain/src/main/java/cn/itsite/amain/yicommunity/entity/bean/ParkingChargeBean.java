package cn.itsite.amain.yicommunity.entity.bean;

import java.io.Serializable;

import cn.itsite.abase.common.BaseBean;

/**
 * Author: LiuJia on 2017/6/1 0001 10:02.
 * Email: liujia95me@126.com
 */

public class ParkingChargeBean extends BaseBean implements Serializable {

    /**
     * data : {"amount":0.3,"carNo":"京A88888","costMoney":0.3,"freeTime":30,"inTime":"2017-11-29 14:49","outTime":"2017-11-30 09:24","paidMoney":0,"parkPlaceFid":"f7a18873-88d8-4de4-ba55-daee01ce9d57","parkPlaceName":"东湖花园1期停车场","totalCostTime":"18小时34分"}
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
         * amount : 0.3
         * carNo : 京A88888
         * costMoney : 0.3
         * freeTime : 30
         * inTime : 2017-11-29 14:49
         * outTime : 2017-11-30 09:24
         * paidMoney : 0
         * parkPlaceFid : f7a18873-88d8-4de4-ba55-daee01ce9d57
         * parkPlaceName : 东湖花园1期停车场
         * totalCostTime : 18小时34分
         */

        private double amount;
        private String carNo;
        private double costMoney;
        private double freeTime;
        private String inTime;
        private String outTime;
        private double paidMoney;
        private String parkPlaceFid;
        private String parkPlaceName;
        private String totalCostTime;

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getCarNo() {
            return carNo;
        }

        public void setCarNo(String carNo) {
            this.carNo = carNo;
        }

        public double getCostMoney() {
            return costMoney;
        }

        public void setCostMoney(double costMoney) {
            this.costMoney = costMoney;
        }

        public double getFreeTime() {
            return freeTime;
        }

        public void setFreeTime(int freeTime) {
            this.freeTime = freeTime;
        }

        public String getInTime() {
            return inTime;
        }

        public void setInTime(String inTime) {
            this.inTime = inTime;
        }

        public String getOutTime() {
            return outTime;
        }

        public void setOutTime(String outTime) {
            this.outTime = outTime;
        }

        public double getPaidMoney() {
            return paidMoney;
        }

        public void setPaidMoney(int paidMoney) {
            this.paidMoney = paidMoney;
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

        public String getTotalCostTime() {
            return totalCostTime;
        }

        public void setTotalCostTime(String totalCostTime) {
            this.totalCostTime = totalCostTime;
        }
    }
}
