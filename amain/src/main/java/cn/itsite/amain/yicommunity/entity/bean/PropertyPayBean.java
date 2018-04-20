package cn.itsite.amain.yicommunity.entity.bean;

import java.util.List;

import cn.itsite.abase.common.BaseBean;

/**
 * Author: LiuJia on 2017/5/7 0007 20:53.
 * Email: liujia95me@126.com
 */

public class PropertyPayBean extends BaseBean {

    /**
     * data : {"billList":[{"fid":"6fde1aaf-d48a-4e0b-97ec-6e0040dad2bf","billCode":"201712280058182955648","billName":"老李11月份的账单","settlementStartDate":"2017-11-01","settlementEndDate":"2017-11-30","amount":0.1,"status":0,"remark":"","createTime":"2017-12-28 00:58:18","houseInfo":"凯宾斯基3栋1单元30层8房","itemList":[]},{"fid":"ba346577-eee8-4dd9-916a-fbf3f9ceea19","billCode":"201712271359007701502","billName":"李勇11月份的物业账单","settlementStartDate":"2017-11-01","settlementEndDate":"2017-11-30","amount":0.1,"status":0,"remark":"","createTime":"2017-12-27 13:59:01","houseInfo":"凯宾斯基3栋1单元30层8房","itemList":[]},{"fid":"ce7c868a-a40a-4c70-84b8-142a22b86eee","billCode":"201712271011025431479","billName":"我我我","settlementStartDate":"2017-12-27","settlementEndDate":"2017-12-28","amount":0.01,"status":0,"remark":"","createTime":"2017-12-27 10:11:03","houseInfo":"凯宾斯基3栋1单元30层8房","itemList":[]}],"totalAmt":0.21000000000000002}
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
         * billList : [{"fid":"6fde1aaf-d48a-4e0b-97ec-6e0040dad2bf","billCode":"201712280058182955648","billName":"老李11月份的账单","settlementStartDate":"2017-11-01","settlementEndDate":"2017-11-30","amount":0.1,"status":0,"remark":"","createTime":"2017-12-28 00:58:18","houseInfo":"凯宾斯基3栋1单元30层8房","itemList":[]},{"fid":"ba346577-eee8-4dd9-916a-fbf3f9ceea19","billCode":"201712271359007701502","billName":"李勇11月份的物业账单","settlementStartDate":"2017-11-01","settlementEndDate":"2017-11-30","amount":0.1,"status":0,"remark":"","createTime":"2017-12-27 13:59:01","houseInfo":"凯宾斯基3栋1单元30层8房","itemList":[]},{"fid":"ce7c868a-a40a-4c70-84b8-142a22b86eee","billCode":"201712271011025431479","billName":"我我我","settlementStartDate":"2017-12-27","settlementEndDate":"2017-12-28","amount":0.01,"status":0,"remark":"","createTime":"2017-12-27 10:11:03","houseInfo":"凯宾斯基3栋1单元30层8房","itemList":[]}]
         * totalAmt : 0.21000000000000002
         */

        private double totalAmt;
        private List<BillListBean> billList;

        public double getTotalAmt() {
            return totalAmt;
        }

        public void setTotalAmt(double totalAmt) {
            this.totalAmt = totalAmt;
        }

        public List<BillListBean> getBillList() {
            return billList;
        }

        public void setBillList(List<BillListBean> billList) {
            this.billList = billList;
        }

        public static class BillListBean {
            /**
             * fid : 6fde1aaf-d48a-4e0b-97ec-6e0040dad2bf
             * billCode : 201712280058182955648
             * billName : 老李11月份的账单
             * settlementStartDate : 2017-11-01
             * settlementEndDate : 2017-11-30
             * amount : 0.1
             * status : 0
             * remark :
             * createTime : 2017-12-28 00:58:18
             * houseInfo : 凯宾斯基3栋1单元30层8房
             * itemList : []
             */

            private String fid;
            private String billCode;
            private String billName;
            private String settlementStartDate;
            private String settlementEndDate;
            private double amount;
            private int status;
            private String remark;
            private String createTime;
            private String houseInfo;
            private List<?> itemList;

            public String getFid() {
                return fid;
            }

            public void setFid(String fid) {
                this.fid = fid;
            }

            public String getBillCode() {
                return billCode;
            }

            public void setBillCode(String billCode) {
                this.billCode = billCode;
            }

            public String getBillName() {
                return billName;
            }

            public void setBillName(String billName) {
                this.billName = billName;
            }

            public String getSettlementStartDate() {
                return settlementStartDate;
            }

            public void setSettlementStartDate(String settlementStartDate) {
                this.settlementStartDate = settlementStartDate;
            }

            public String getSettlementEndDate() {
                return settlementEndDate;
            }

            public void setSettlementEndDate(String settlementEndDate) {
                this.settlementEndDate = settlementEndDate;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getHouseInfo() {
                return houseInfo;
            }

            public void setHouseInfo(String houseInfo) {
                this.houseInfo = houseInfo;
            }

            public List<?> getItemList() {
                return itemList;
            }

            public void setItemList(List<?> itemList) {
                this.itemList = itemList;
            }
        }
    }
}
