package cn.itsite.goodshome.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import cn.itsite.acommon.DeliveryBean;
import cn.itsite.acommon.GoodsGridBean;

/**
 * Author： Administrator on 2018/1/30 0030.
 * Email： liujia95me@126.com
 */

public class StoreItemGridBean extends GoodsGridBean implements MultiItemEntity {

    public static final int TYPE_BANNER = 0;
    public static final int TYPE_MORE = 1;
    public static final int TYPE_RECOMMEND = 2;

    private int itemType;
    private int spanSize;

    private CategoryBean categoryBean;
    private ProductsBean productsBean;
    private List<ProductsBean> banners;
    private DeliveryBean deliveryBean;
    public String shopType;//区分首页展示用的

    public DeliveryBean getDeliveryBean() {
        return deliveryBean;
    }

    public void setDeliveryBean(DeliveryBean deliveryBean) {
        this.deliveryBean = deliveryBean;
    }

    public List<ProductsBean> getBanners() {
        return banners;
    }

    public void setBanners(List<ProductsBean> banners) {
        this.banners = banners;
    }

    public CategoryBean getCategoryBean() {
        return categoryBean;
    }

    public void setCategoryBean(CategoryBean categoryBean) {
        this.categoryBean = categoryBean;
    }

    public ProductsBean getProductsBean() {
        return productsBean;
    }

    public void setProductsBean(ProductsBean productsBean) {
        this.productsBean = productsBean;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    public int getSpanSize() {
        return spanSize;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
