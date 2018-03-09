package cn.itsite.amain.yicommunity.main.publish.view;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.abase.utils.DateUtils;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.App;
import cn.itsite.amain.yicommunity.entity.bean.CommentBean;

/**
 * Author: LiuJia on 2017/5/11 0011 16:10.
 * Email: liujia95me@126.com
 */

public class CommentRVAdapter extends BaseRecyclerViewAdapter<CommentBean, BaseViewHolder> {

    public CommentRVAdapter() {
        super(R.layout.item_comment, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentBean item) {
        helper.setText(R.id.tv_username_item_comment, item.getMember().getMemberNickName())
                .setText(R.id.tv_content_item_comment, item.getContent())
                .setText(R.id.tv_time_item_comment, DateUtils.formatCommentDate(item.getCreateTime()));

        ImageView ivHead = helper.getView(R.id.iv_head_item_comment);
        Glide.with(App.mContext)
                .load(item.getMember().getAvator())
                .apply(new RequestOptions()
                        .circleCrop()
                        .placeholder(R.drawable.ic_default_head_image_200px)
                        .error(R.drawable.ic_default_head_image_200px))
                .into(ivHead);
    }
}
