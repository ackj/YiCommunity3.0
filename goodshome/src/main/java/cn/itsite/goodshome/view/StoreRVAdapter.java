package cn.itsite.goodshome.view;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.itsite.abase.BaseApp;
import cn.itsite.abase.utils.DensityUtils;
import cn.itsite.goodshome.R;
import cn.itsite.goodshome.model.StoreItemGridBean;

/**
 * Author： Administrator on 2018/1/30 0030.
 * Email： liujia95me@126.com
 */

public class StoreRVAdapter extends BaseMultiItemQuickAdapter<StoreItemGridBean, BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    public StoreRVAdapter() {
        super(null);
        addItemType(StoreItemGridBean.TYPE_BANNER, R.layout.item_store_banner);
        addItemType(StoreItemGridBean.TYPE_MORE, R.layout.item_goods_more);
        addItemType(StoreItemGridBean.TYPE_RECOMMEND, R.layout.item_goods_recommend);
        addItemType(StoreItemGridBean.TYPE_GOODS, R.layout.item_grid_goods);
    }

    @Override
    protected void convert(BaseViewHolder helper, final StoreItemGridBean item) {
        switch (item.getItemType()) {
            case StoreItemGridBean.TYPE_BANNER:
                Banner banner = helper.getView(R.id.banner);
                helper.addOnClickListener(R.id.ll_location)
                        .setVisible(R.id.ll_location, item.shopType.equals("shop"));

                List<Object> bannerImages = new ArrayList<>();
                List<String> bannerTitles = new ArrayList<>();
                for (int i = 0; i < item.getBanners().size(); i++) {
                    bannerImages.add(item.getBanners().get(i).getImageUrl());
                    bannerTitles.add(item.getBanners().get(i).getTitle());
                }
                banner.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(BaseApp.mContext, 150)));
                if ("shop".equals(item.shopType)) {
                    banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
                } else {
                    banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                }
                banner.setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        Glide.with(context).load(path).into(imageView);
                    }
                })
                        .setBannerTitles(bannerTitles)
                        .setImages(bannerImages)
                        .setIndicatorGravity(BannerConfig.RIGHT)
                        .isAutoPlay(true)
                        .start();

                break;
            case StoreItemGridBean.TYPE_MORE:
                helper.setText(R.id.tv_name, item.getCategoryBean().getCategory());
                break;
            case StoreItemGridBean.TYPE_RECOMMEND:
                ImageView ivRmIcon = helper.getView(R.id.iv_icon);
                Glide.with(ivRmIcon.getContext())
                        .load(item.getProductsBean().getImageUrl())
                        .apply(new RequestOptions().placeholder(R.drawable.ic_img_loading))
                        .apply(new RequestOptions().error(R.drawable.ic_img_error))
                        .into(ivRmIcon);
                helper.setText(R.id.tv_name, item.getProductsBean().getTitle())
                        .setText(R.id.tv_desc, item.getProductsBean().getDescription())
                        .setText(R.id.tv_price, item.getProductsBean().getCurrency() + item.getProductsBean().getPrice());
                break;
            case StoreItemGridBean.TYPE_GOODS:
                ImageView ivIcon = helper.getView(R.id.iv_icon);
                Glide.with(ivIcon.getContext())
                        .load(item.getProductsBean().getImageUrl())
                        .apply(new RequestOptions().placeholder(R.drawable.ic_img_loading))
                        .apply(new RequestOptions().error(R.drawable.ic_img_error))
                        .into(ivIcon);
                helper.setText(R.id.tv_name, item.getProductsBean().getTitle())
                        .setText(R.id.tv_desc, item.getProductsBean().getDescription())
                        .setText(R.id.tv_price, item.getProductsBean().getCurrency() + item.getProductsBean().getPrice());
                break;
            default:
        }
    }
}
