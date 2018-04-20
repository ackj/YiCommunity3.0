package cn.itsite.order.model;

import java.util.List;

/**
 * Created by liujia on 2018/4/20.
 * 提交评论到服务器的容器
 */
public class PostCommentBean {

    /**
     * category : EVALUATE
     * uid : 13212133313
     * products : [{"uid":"13212133313","amount":"","sku":"525235","evaLevel":1,"evaDescription":"商品还是几不错的","pictures":["url1","url2","url3"]},{"uid":"33","amount":"","sku":"22","evaLevel":2,"evaDescription":"商品还是几好的","pictures":["url1","url2","url3"]}]
     */

    private String category;
    private String uid;
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

    public List<ProductsBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsBean> products) {
        this.products = products;
    }

    public static class ProductsBean {
        /**
         * uid : 13212133313
         * amount :
         * sku : 525235
         * evaLevel : 1
         * evaDescription : 商品还是几不错的
         * pictures : ["url1","url2","url3"]
         */

        private String uid;
        private String amount;
        private String sku;
        private int evaLevel;
        private String evaDescription;
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

        public int getEvaLevel() {
            return evaLevel;
        }

        public void setEvaLevel(int evaLevel) {
            this.evaLevel = evaLevel;
        }

        public String getEvaDescription() {
            return evaDescription;
        }

        public void setEvaDescription(String evaDescription) {
            this.evaDescription = evaDescription;
        }

        public List<String> getPictures() {
            return pictures;
        }

        public void setPictures(List<String> pictures) {
            this.pictures = pictures;
        }
    }
}
