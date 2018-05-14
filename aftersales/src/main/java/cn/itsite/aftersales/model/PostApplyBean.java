package cn.itsite.aftersales.model;

import java.util.List;

/**
 * Created by liujia on 2018/4/20.
 */

public class PostApplyBean {
    /**
     * category : REFUND
     * uid : 13212133313
     * note : 请输入您的售后申请描述
     * reasonType : BAD_GOODS
     * products : [{"uid":"13212133313","amount":"5","sku":"525235","pictures":["url1","url2","url3"]},{"uid":"13212133313","amount":"5","sku":"","pictures":["url1","url2","url3"]}]
     */
    private String category;
    private String uid;
    private String note;
    private String reasonType;
    private List<ProductsBean> products;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getReasonType() {
        return reasonType;
    }

    public void setReasonType(String reasonType) {
        this.reasonType = reasonType;
    }

    public List<ProductsBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsBean> products) {
        this.products = products;
    }

    public static class ProductsBean {
        /**
         * uid : 13212133313
         * amount : 5
         * sku : 525235
         * pictures : ["url1","url2","url3"]
         */

        private String uid;
        private String amount;
        private String sku;
        private List<String> pictures;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public List<String> getPictures() {
            return pictures;
        }

        public void setPictures(List<String> pictures) {
            this.pictures = pictures;
        }
    }
}
