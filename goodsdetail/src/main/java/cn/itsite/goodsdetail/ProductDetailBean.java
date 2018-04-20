package cn.itsite.goodsdetail;

import java.io.Serializable;
import java.util.List;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/21 0021 17:44
 */

public class ProductDetailBean implements Serializable {

    /**
     * attributes : [{"attribute":"配送范围","name":"50.0公里内配送","type":{"name":"服务","uid":""},"uid":""}]
     * description :
     * detail : {"images":["http://aglhzmall.image.alimmdn.com/goods/20161214113637620173.jpg@750w"],"url":"http://www.aglhz.com/mall/m/html/description.html?id=235776f2-ef43-4612-9238-958016a3cdaf&galleryType=GoodsType"}
     * images : [{"discription":"","image":"http://aglhzmall.image.alimmdn.com/goods/20161214113637620173.jpg@750w"}]
     * pay : {"cost":"20.00","currency":"¥","price":"20.00"}
     * share : http://www.aglhz.com/mall/m/html/photo.html?shopId=6716f108-a244-4d99-8d7e-b929e3c9290a&goodsId=235776f2-ef43-4612-9238-958016a3cdaf
     * shop : {"name":"亿社区自营店(内测..)","serviceType":"","type":"O2OType","uid":"6716f108-a244-4d99-8d7e-b929e3c9290a"}
     * title : 自然派果仁世家原果仁
     * uid : c9d9748a-07e9-4810-a52e-12ac355cad4f
     */

    private String description;
    private DetailBean detail;
    private PayBean pay;
    private String share;
    private ShopBean shop;
    private String title;
    private String uid;
    private List<AttributesBean> attributes;
    private List<ImagesBean> images;
    private List<EvaluatesBean> evaluates;

    public List<EvaluatesBean> getEvaluates() {
        return evaluates;
    }

    public void setEvaluates(List<EvaluatesBean> evaluates) {
        this.evaluates = evaluates;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DetailBean getDetail() {
        return detail;
    }

    public void setDetail(DetailBean detail) {
        this.detail = detail;
    }

    public PayBean getPay() {
        return pay;
    }

    public void setPay(PayBean pay) {
        this.pay = pay;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public ShopBean getShop() {
        return shop;
    }

    public void setShop(ShopBean shop) {
        this.shop = shop;
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

    public List<AttributesBean> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributesBean> attributes) {
        this.attributes = attributes;
    }

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public static class DetailBean {
        /**
         * images : ["http://aglhzmall.image.alimmdn.com/goods/20161214113637620173.jpg@750w"]
         * url : http://www.aglhz.com/mall/m/html/description.html?id=235776f2-ef43-4612-9238-958016a3cdaf&galleryType=GoodsType
         */

        private String url;
        private List<String> images;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }

    public static class PayBean implements Serializable{
        /**
         * cost : 20.00
         * currency : ¥
         * price : 20.00
         */

        private String cost;
        private String currency;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }

    public static class ShopBean implements Serializable{
        /**
         * name : 亿社区自营店(内测..)
         * serviceType :
         * type : O2OType
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

    public static class AttributesBean implements Serializable{
        /**
         * attribute : 配送范围
         * name : 50.0公里内配送
         * type : {"name":"服务","uid":""}
         * uid :
         */

        private String attribute;
        private String name;
        private TypeBean type;
        private String uid;

        public String getAttribute() {
            return attribute;
        }

        public void setAttribute(String attribute) {
            this.attribute = attribute;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public TypeBean getType() {
            return type;
        }

        public void setType(TypeBean type) {
            this.type = type;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public static class TypeBean {
            /**
             * name : 服务
             * uid :
             */

            private String name;
            private String uid;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }
        }
    }

    public static class ImagesBean implements Serializable{
        /**
         * discription :
         * image : http://aglhzmall.image.alimmdn.com/goods/20161214113637620173.jpg@750w
         */

        private String discription;
        private String image;

        public String getDiscription() {
            return discription;
        }

        public void setDiscription(String discription) {
            this.discription = discription;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

    public static class EvaluatesBean implements Serializable{

        /**
         * member : {"name":"Óã·ÇÓã","icon":"http://aglhzmall.image.alimmdn.com/member/20180104091724544143.jpg","grade":0,"gradeN":"","level":0,"levelN":""}
         * productDes : dafa
         * evaTime : 2018-04-19
         * evaDes : goodgood
         * evaPictures : ["http://aglhzmall.image.alimmdn.com/goods/20170406091521850464.jpg","http://aglhzmall.image.alimmdn.com/goods/20170406091521850464.jpg"]
         */

        private MemberBean member;
        private String productDes;
        private String evaTime;
        private String evaDes;
        private List<String> evaPictures;

        public MemberBean getMember() {
            return member;
        }

        public void setMember(MemberBean member) {
            this.member = member;
        }

        public String getProductDes() {
            return productDes;
        }

        public void setProductDes(String productDes) {
            this.productDes = productDes;
        }

        public String getEvaTime() {
            return evaTime;
        }

        public void setEvaTime(String evaTime) {
            this.evaTime = evaTime;
        }

        public String getEvaDes() {
            return evaDes;
        }

        public void setEvaDes(String evaDes) {
            this.evaDes = evaDes;
        }

        public List<String> getEvaPictures() {
            return evaPictures;
        }

        public void setEvaPictures(List<String> evaPictures) {
            this.evaPictures = evaPictures;
        }

        public static class MemberBean implements Serializable{
            /**
             * name : Óã·ÇÓã
             * icon : http://aglhzmall.image.alimmdn.com/member/20180104091724544143.jpg
             * grade : 0
             * gradeN :
             * level : 0
             * levelN :
             */

            private String name;
            private String icon;
            private int grade;
            private String gradeN;
            private int level;
            private String levelN;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public int getGrade() {
                return grade;
            }

            public void setGrade(int grade) {
                this.grade = grade;
            }

            public String getGradeN() {
                return gradeN;
            }

            public void setGradeN(String gradeN) {
                this.gradeN = gradeN;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getLevelN() {
                return levelN;
            }

            public void setLevelN(String levelN) {
                this.levelN = levelN;
            }
        }
    }

}
