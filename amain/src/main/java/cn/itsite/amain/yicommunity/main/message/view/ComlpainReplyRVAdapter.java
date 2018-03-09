package cn.itsite.amain.yicommunity.main.message.view;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.entity.bean.ComplainReplyBean;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Author: LiuJia on 2017/5/18 0018 17:38.
 * Email: liujia95me@126.com
 */

public class ComlpainReplyRVAdapter extends BaseRecyclerViewAdapter<ComplainReplyBean.DataBean.ReplyListBean, BaseViewHolder> {

    public ComlpainReplyRVAdapter() {
        super(R.layout.item_rv_complain_reply);
    }

    @Override
    protected void convert(BaseViewHolder helper, ComplainReplyBean.DataBean.ReplyListBean item) {
        helper.setText(R.id.tv_content_item_rv_complain_reply, item.getContent())
                .setText(R.id.tv_time_item_rv_complain_reply, item.getCreateTime());
    }
}
