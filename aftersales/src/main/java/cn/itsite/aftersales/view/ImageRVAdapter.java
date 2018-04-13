package cn.itsite.aftersales.view;

import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bilibili.boxing.model.entity.BaseMedia;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.abase.utils.DensityUtils;
import cn.itsite.abase.utils.ScreenUtils;
import me.liujia95.aftersales.R;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/4/11 0011 10:55
 */

public class ImageRVAdapter extends BaseRecyclerViewAdapter<BaseMedia, BaseViewHolder> {
    public ImageRVAdapter() {
        super(R.layout.item_image_aftersales);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseMedia media) {
        ImageView ivImg = helper.getView(R.id.iv_img);
        int screenWidth = ScreenUtils.getScreenWidth(ivImg.getContext());
        int sideLength = screenWidth / 4 - DensityUtils.dp2px(ivImg.getContext(), 10);
        ivImg.setLayoutParams(new FrameLayout.LayoutParams(sideLength, sideLength));
        Glide.with(ivImg.getContext())
                .load(media.getPath())
                .apply(new RequestOptions().error(R.drawable.ic_img_error))
                .apply(new RequestOptions().placeholder(R.drawable.ic_img_loading))
                .into(ivImg);
    }
}
