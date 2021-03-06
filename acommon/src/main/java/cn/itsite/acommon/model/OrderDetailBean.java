package cn.itsite.acommon.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/20 0020 16:42
 * 两个以上的module需要传递的对象，所以抽到common来
 */
public class OrderDetailBean implements Serializable{

    /**
     * actions : [{"action":"取消订单","category":"2353552352","link":"","type":"cancel"},{"action":"去付款","category":"2353343535","link":"","type":"pay"},{"action":"删除订单","category":"","link":"","type":"delete"}]
     * amount : 4
     * category : 待付款
     * cost : 10
     * delivery : {"address":"C栋801","gender":"male","latitude":"36.33","location":"凯宾斯基","longitude":"85.66","name":"黄沙","phoneNumber":"13888888888"}
     * deliveryType : 送货上门
     * note : 带一包烟上来
     * orderNumber : 64614646464
     * products : [{"amount":"2","description":"wifi/电话双网 您的智能小卫士","imageUrl":"http://ww3.sinaimg.cn/large/0060lm7Tly1fo6vt0p500j30af0ad758.jpg","link":"https://item.jd.com/4264502.html","pay":{"cost":"4.125","currency":"¥","discount":"7.5","price":"5.5"},"title":"优乐美奶茶","uid":"13212133313"}]
     * shop : {"cartUid":"235353552352","name":"克拉家园店","type":"shop","uid":"54545454545"}
     * time : 2018-03-12 15:33:24
     * uid : 43433331313
     */

    private String amount;
    private String category;
    private String cost;
    private DeliveryBean delivery;
    private String deliveryType;
    private String note;
    private String orderNumber;
    private ShopBean shop;
    private String time;
    private String uid;
    private List<ActionsBean> actions;
    private List<ProductsBean> products;
    private ProductsBean.PayBean pay;
    private ExpressBean express;

    public ExpressBean getExpress() {
        return express;
    }

    public void setExpress(ExpressBean express) {
        this.express = express;
    }

    public ProductsBean.PayBean getPay() {
        return pay;
    }

    public void setPay(ProductsBean.PayBean pay) {
        this.pay = pay;
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

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public DeliveryBean getDelivery() {
        return delivery;
    }

    public void setDelivery(DeliveryBean delivery) {
        this.delivery = delivery;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public ShopBean getShop() {
        return shop;
    }

    public void setShop(ShopBean shop) {
        this.shop = shop;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public static class DeliveryBean implements Serializable{
        /**
         * address : C栋801
         * gender : male
         * latitude : 36.33
         * location : 凯宾斯基
         * longitude : 85.66
         * name : 黄沙
         * phoneNumber : 13888888888
         */

        private String address;
        private String gender;
        private String latitude;
        private String location;
        private String longitude;
        private String name;
        private String phoneNumber;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    }

    public static class ShopBean implements Serializable{
        /**
         * cartUid : 235353552352
         * name : 克拉家园店
         * type : shop
         * uid : 54545454545
         */

        private String cartUid;
        private String name;
        private String type;
        private String uid;

        public String getCartUid() {
            return cartUid;
        }

        public void setCartUid(String cartUid) {
            this.cartUid = cartUid;
        }

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

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }

    public static class ActionsBean implements Serializable{
        /**
         * action : 取消订单
         * category : 2353552352
         * link :
         * type : cancel
         */

        private String action;
        private String category;
        private String link;
        private String type;

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class ProductsBean implements Serializable{
        /**
         * amount : 2
         * description : wifi/电话双网 您的智能小卫士
         * imageUrl : http://ww3.sinaimg.cn/large/0060lm7Tly1fo6vt0p500j30af0ad758.jpg
         * link : https://item.jd.com/4264502.html
         * pay : {"cost":"4.125","currency":"¥","discount":"7.5","price":"5.5"}
         * title : 优乐美奶茶
         * uid : 13212133313
         */

        private String amount;
        private String description;
        private String imageUrl;
        private String link;
        private PayBean pay;
        private String title;
        private String uid;
        private String sku;
        private List<ActionBean> actions;

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public List<ActionBean> getActions() {
            return actions;
        }

        public void setActions(List<ActionBean> actions) {
            this.actions = actions;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public PayBean getPay() {
            return pay;
        }

        public void setPay(PayBean pay) {
            this.pay = pay;
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

        public static class ActionBean implements Serializable{

            private String action;
            private String category;
            private String link;
            private String type;

            public String getAction() {
                return action;
            }

            public void setAction(String action) {
                this.action = action;
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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }


        public static class PayBean implements Serializable{
            /**
             * cost : 4.125
             * currency : ¥
             * discount : 7.5
             * price : 5.5
             */

            private String cost;
            private String currency;
            private String discount;
            private String price;

            public String getCost() {
                return cost;
            }

            public void setCost(String cost) {
                this.cost = cost;
            }

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
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
        }
    }

    public static class ExpressBean implements Serializable{

        /**
         * name : 小明同志
         * phoneNumber : 13811112222
         * imageUrl : http://ww3.sinaimg.cn/large/0060lm7Tly1fo6vt0p500j30af0ad758.jpg
         * identity : 派送员
         */

        private String name;
        private String phoneNumber;
        private String imageUrl;
        private String identity;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getIdentity() {
            return identity;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }
    }
}
