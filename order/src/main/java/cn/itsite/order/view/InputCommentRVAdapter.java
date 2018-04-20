package cn.itsite.order.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bilibili.boxing.model.entity.BaseMedia;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import cn.itsite.abase.BaseApp;
import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.abase.utils.DensityUtils;
import cn.itsite.order.R;
import cn.itsite.order.model.SubmitCommentBean;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/4/13 0013 9:17
 */

public class InputCommentRVAdapter extends BaseRecyclerViewAdapter<SubmitCommentBean, BaseViewHolder> {

    public InputCommentRVAdapter() {
        super(R.layout.item_input_comment);
    }

    @Override
    protected void convert(BaseViewHolder helper, SubmitCommentBean item) {
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(BaseApp.mContext, 4));
        ImageRVAdapter adapter = new ImageRVAdapter();
        recyclerView.setAdapter(adapter);

        ImageView ivIcon = helper.getView(R.id.iv_icon);
        Glide.with(ivIcon.getContext())
                .load(item.getImgUrl())
                .apply(new RequestOptions().error(R.drawable.ic_img_error)
                        .placeholder(R.drawable.ic_img_loading))
                .into(ivIcon);

        helper.setText(R.id.tv_input, item.getEvaDescription())
                .addOnClickListener(R.id.tv_input)
                .addOnClickListener(R.id.rb_level_good)
                .addOnClickListener(R.id.rb_level_mid)
                .addOnClickListener(R.id.rb_level_bad);

        adapter.setNewData(item.getMedias());

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (adapter.getData().size() - 1 == position) {
                    //跳选择图片区
                    mOnClickAddPicListener.clickAddPic(item.getMedias(), helper.getLayoutPosition());
                } else {
                    //todo 跳预览页

                }
            }
        });
    }

    private OnClickAddPicListener mOnClickAddPicListener;

    public void setOnClickAddPicListener(OnClickAddPicListener listener) {
        mOnClickAddPicListener = listener;
    }

    public interface OnClickAddPicListener {
        void clickAddPic(ArrayList<BaseMedia> medias, int position);
    }

    public class ImageRVAdapter extends BaseRecyclerViewAdapter<BaseMedia, BaseViewHolder> {

        public ImageRVAdapter() {
            super(R.layout.item_picture);
        }

        @Override
        protected void convert(BaseViewHolder helper, BaseMedia item) {
            ImageView ivImg = helper.getView(R.id.iv_img);
            int sizeLength = (DensityUtils.getDisplayWidth(BaseApp.mContext) - DensityUtils.dp2px(BaseApp.mContext, 12) * 5) / 4;
            ivImg.setLayoutParams(new FrameLayout.LayoutParams(sizeLength, sizeLength));

            Glide.with(ivImg.getContext())
                    .load(item.getPath())
                    .apply(new RequestOptions().placeholder(R.drawable.ic_img_loading)
                            .error(R.drawable.ic_img_error))
                    .into(ivImg);
        }
    }

}
