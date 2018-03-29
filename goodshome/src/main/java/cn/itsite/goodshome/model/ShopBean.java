package cn.itsite.goodshome.model;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/28 0028 15:29
 */

public class ShopBean {

    /**
     * address : 广东省惠州市惠城区凯宾斯基
     * cartUid : -1
     * latitude : 23.113157
     * longitude : 114.419061
     * name : 亿社区自营店(内测..)
     * time : 08:00~18:00
     * uid : 6716f108-a244-4d99-8d7e-b929e3c9290a
     * url : http://agl.image.alimmdn.com/default/shopLogoDefault_800x800.png?t=1521876135425
     */

    private String address;
    private String cartUid;
    private String latitude;
    private String longitude;
    private String name;
    private String time;
    private String uid;
    private String url;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCartUid() {
        return cartUid;
    }

    public void setCartUid(String cartUid) {
        this.cartUid = cartUid;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
