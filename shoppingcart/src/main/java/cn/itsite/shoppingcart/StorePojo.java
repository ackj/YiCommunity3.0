package cn.itsite.shoppingcart;

import java.util.List;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/14 0014 9:39
 */

public class StorePojo {


    /**
     * shop : {"name":"亿社区自营店(内测..)","serviceType":"送货上门","type":"shop","uid":"6716f108-a244-4d99-8d7e-b929e3c9290a"}
     * products : [{"icon":"http://aglhzmall.image.alimmdn.com/goods/20161126163849959791.jpg@400h_400w_1e_1c","specification":"","count":7,"title":"士力架 花生夹心巧克力 35g/条","uid":"6716f108-a244-4d99-8d7e-b929e3c9290a","skuID":"58b8a1e6-2a49-468d-b4c7-36d7a617843a","sku":"","description":"","share":"http://www.aglhz.com/mall/m/html/photo.html?shopId=6716f108-a244-4d99-8d7e-b929e3c9290a&goodsId=2489e4a3-4043-4813-8341-4f8bbfcd9919","pay":{"price":"3.50","currency":"¥"}}]
     */

    private ShopBean shop;
    private List<ProductsBean> products;

    public ShopBean getShop() {
        return shop;
    }

    public void setShop(ShopBean shop) {
        this.shop = shop;
    }

    public List<ProductsBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsBean> products) {
        this.products = products;
    }

    public static class ShopBean {
        /**
         * name : 亿社区自营店(内测..)
         * serviceType : 送货上门
         * type : shop
         * uid : 6716f108-a244-4d99-8d7e-b929e3c9290a
         */

        private String name;
        private String serviceType;
        private String type;
        private String uid;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getServiceType() {
            return serviceType;
        }

        public void setServiceType(String serviceType) {
            this.serviceType = serviceType;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }

    public static class ProductsBean {
        /**
         * icon : http://aglhzmall.image.alimmdn.com/goods/20161126163849959791.jpg@400h_400w_1e_1c
         * specification :
         * count : 7
         * title : 士力架 花生夹心巧克力 35g/条
         * uid : 6716f108-a244-4d99-8d7e-b929e3c9290a
         * skuID : 58b8a1e6-2a49-468d-b4c7-36d7a617843a
         * sku :
         * description :
         * share : http://www.aglhz.com/mall/m/html/photo.html?shopId=6716f108-a244-4d99-8d7e-b929e3c9290a&goodsId=2489e4a3-4043-4813-8341-4f8bbfcd9919
         * pay : {"price":"3.50","currency":"¥"}
         */

        private String icon;
        private String specification;
        private int count;
        private String title;
        private String uid;
        private String skuID;
        private String sku;
        private String description;
        private String share;
        private PayBean pay;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getSpecification() {
            return specification;
        }

        public void setSpecification(String specification) {
            this.specification = specification;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getSkuID() {
            return skuID;
        }

        public void setSkuID(String skuID) {
            this.skuID = skuID;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getShare() {
            return share;
        }

        public void setShare(String share) {
            this.share = share;
        }

        public PayBean getPay() {
            return pay;
        }

        public void setPay(PayBean pay) {
            this.pay = pay;
        }

        public static class PayBean {
            /**
             * price : 3.50
             * currency : ¥
             */

            private String price;
            private String currency;

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }
        }
    }
}
