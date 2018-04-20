package cn.itsite.amain.yicommunity.entity.bean;


import java.util.List;

import cn.itsite.abase.common.BaseBean;

/**
 * Author: LiuJia on 2017/5/7 0007 20:53.
 * Email: liujia95me@126.com
 */

public class PropertyPayDetailBean extends BaseBean {

    /**
     * data : {"fid":"6fde1aaf-d48a-4e0b-97ec-6e0040dad2bf","billCode":"201712280058182955648","billName":"老李11月份的账单","settlementStartDate":"2017-11-01","settlementEndDate":"2017-11-30","amount":0.1,"status":0,"remark":"","createTime":"2017-12-28 00:58:18","houseInfo":"凯宾斯基3栋1单元30层8房","payChannel":"","payMethod":"","propertyBillBillCode":"","tradeBillNo":"","itemList":[{"itemName":"电梯公摊费","itemAmt":0.1,"itemRemark":"电梯公摊费"}]}
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
         * payChannel :
         * payMethod :
         * propertyBillBillCode :
         * tradeBillNo :
         * itemList : [{"itemName":"电梯公摊费","itemAmt":0.1,"itemRemark":"电梯公摊费"}]
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
        private String payChannel;
        private String payMethod;
        private String propertyBillBillCode;
        private String tradeBillNo;
        private List<ItemListBean> itemList;

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

        public String getPayChannel() {
            return payChannel;
        }

        public void setPayChannel(String payChannel) {
            this.payChannel = payChannel;
        }

        public String getPayMethod() {
            return payMethod;
        }

        public void setPayMethod(String payMethod) {
            this.payMethod = payMethod;
        }

        public String getPropertyBillBillCode() {
            return propertyBillBillCode;
        }

        public void setPropertyBillBillCode(String propertyBillBillCode) {
            this.propertyBillBillCode = propertyBillBillCode;
        }

        public String getTradeBillNo() {
            return tradeBillNo;
        }

        public void setTradeBillNo(String tradeBillNo) {
            this.tradeBillNo = tradeBillNo;
        }

        public List<ItemListBean> getItemList() {
            return itemList;
        }

        public void setItemList(List<ItemListBean> itemList) {
            this.itemList = itemList;
        }

        public static class ItemListBean {
            /**
             * itemName : 电梯公摊费
             * itemAmt : 0.1
             * itemRemark : 电梯公摊费
             */

            private String itemName;
            private double itemAmt;
            private String itemRemark;

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public double getItemAmt() {
                return itemAmt;
            }

            public void setItemAmt(double itemAmt) {
                this.itemAmt = itemAmt;
            }

            public String getItemRemark() {
                return itemRemark;
            }

            public void setItemRemark(String itemRemark) {
                this.itemRemark = itemRemark;
            }
        }
    }
}
