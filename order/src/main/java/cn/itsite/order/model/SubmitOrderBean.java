package cn.itsite.order.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import cn.itsite.acommon.DeliveryBean;
import cn.itsite.acommon.StorePojo;

/**
 * Author： Administrator on 2018/2/1 0001.
 * Email： liujia95me@126.com
 */

public class SubmitOrderBean implements MultiItemEntity {
    private static final String TAG = SubmitOrderBean.class.getSimpleName();

    public static final int TYPE_STORE_TITLE = 1;
    public static final int TYPE_STORE_GOODS = 2;
    public static final int TYPE_ORDER_INFO = 3;

    private int itemType;

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    private StorePojo.ShopBean shopBean;
    private StorePojo.ProductsBean productsBean;
    private DeliveryBean deliveryBean;
    private int amount;
    private float totalPrice;
    private String leaveMessage;
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getLeaveMessage() {
        return leaveMessage;
    }

    public void setLeaveMessage(String leaveMessage) {
        this.leaveMessage = leaveMessage;
    }

    public DeliveryBean getDeliveryBean() {
        return deliveryBean;
    }

    public void setDeliveryBean(DeliveryBean deliveryBean) {
        this.deliveryBean = deliveryBean;
    }

    public StorePojo.ShopBean getShopBean() {
        return shopBean;
    }

    public void setShopBean(StorePojo.ShopBean shopBean) {
        this.shopBean = shopBean;
    }

    public StorePojo.ProductsBean getProductsBean() {
        return productsBean;
    }

    public void setProductsBean(StorePojo.ProductsBean productsBean) {
        this.productsBean = productsBean;
    }
}
