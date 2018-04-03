package cn.itsite.order;

import java.util.List;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/15 0015 14:25
 */

public class OrderBean {


    /**
     * uid : 43433331313
     * deliveryType : 送货上门
     * amount : 4
     * category : 待付款
     * pay : {"cost":"10","discount":"10","price":"10","currency":"¥"}
     * actions : [{"action":"取消订单","type":"cancel","category":"2353552352","link":""},{"action":"去付款","category":"2353343535","type":"pay","link":""},{"action":"删除订单","category":"","type":"delete","link":""}]
     * shop : {"name":"克拉家园店","type":"shop","cartUid":"235353552352","uid":"54545454545"}
     * products : [{"imageUrl":"http://ww3.sinaimg.cn/large/0060lm7Tly1fo6vt0p500j30af0ad758.jpg","detailUrl":"https://item.jd.com/4264502.html","title":"优乐美奶茶","description":"wifi/电话双网 您的智能小卫士","uid":"13212133313"},{"imageUrl":"http://ww3.sinaimg.cn/large/0060lm7Tly1fo6vt0p500j30af0ad758.jpg","detailUrl":"https://item.jd.com/4264502.html","title":"优乐美奶茶","description":"wifi/电话双网 您的智能小卫士","uid":"13212133313"}]
     */

    private String uid;
    private String deliveryType;
    private String amount;
    private String category;
    private PayBean pay;
    private ShopBean shop;
    private List<ActionsBean> actions;
    private List<ProductsBean> products;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public PayBean getPay() {
        return pay;
    }

    public void setPay(PayBean pay) {
        this.pay = pay;
    }

    public ShopBean getShop() {
        return shop;
    }

    public void setShop(ShopBean shop) {
        this.shop = shop;
    }

    public List<ActionsBean> getActions() {
        return actions;
    }

    public void setActions(List<ActionsBean> actions) {
        this.actions = actions;
    }

    public List<ProductsBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsBean> products) {
        this.products = products;
    }

    public static class PayBean {
        /**
         * cost : 10
         * discount : 10
         * price : 10
         * currency : ¥
         */

        private String cost;
        private String discount;
        private String price;
        private String currency;

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

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

    public static class ShopBean {
        /**
         * name : 克拉家园店
         * type : shop
         * cartUid : 235353552352
         * uid : 54545454545
         */

        private String name;
        private String type;
        private String cartUid;
        private String uid;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCartUid() {
            return cartUid;
        }

        public void setCartUid(String cartUid) {
            this.cartUid = cartUid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }

    public static class ActionsBean {
        /**
         * action : 取消订单
         * type : cancel
         * category : 2353552352
         * link :
         */

        private String action;
        private String type;
        private String category;
        private String link;

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }

    public static class ProductsBean {
        /**
         * imageUrl : http://ww3.sinaimg.cn/large/0060lm7Tly1fo6vt0p500j30af0ad758.jpg
         * detailUrl : https://item.jd.com/4264502.html
         * title : 优乐美奶茶
         * description : wifi/电话双网 您的智能小卫士
         * uid : 13212133313
         */

        private String imageUrl;
        private String detailUrl;
        private String title;
        private String description;
        private String uid;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getDetailUrl() {
            return detailUrl;
        }

        public void setDetailUrl(String detailUrl) {
            this.detailUrl = detailUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
