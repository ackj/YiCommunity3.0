package cn.itsite.goodsdetail.view;

import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.abase.utils.DensityUtils;
import cn.itsite.abase.utils.ScreenUtils;
import cn.itsite.goodsdetail.R;

/**
 * Created by liujia on 2018/4/22.
 */

public class ImageRVAdapter extends BaseRecyclerViewAdapter<String,BaseViewHolder> {

    public ImageRVAdapter() {
        super(R.layout.item_comment_image);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView ivImg = helper.getView(R.id.iv_img);
        int screenWidth = ScreenUtils.getScreenWidth(ivImg.getContext());
        int sideLength = (screenWidth - DensityUtils.dp2px(ivImg.getContext(), 8)*5)/4;
        ivImg.setLayoutParams(new FrameLayout.LayoutParams(sideLength, sideLength));
        Glide.with(ivImg.getContext())
                .load(item)
                .apply(new RequestOptions().error(R.drawable.ic_img_error).placeholder(R.drawable.ic_img_loading))
                .into(ivImg);
    }
}
