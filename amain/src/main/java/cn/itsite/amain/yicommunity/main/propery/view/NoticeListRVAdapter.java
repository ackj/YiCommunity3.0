package cn.itsite.amain.yicommunity.main.propery.view;

import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.entity.bean.NoticeBean;

/**
 * Author: LiuJia on 2017/5/9 0009 22:43.
 * Email: liujia95me@126.com
 */

public class NoticeListRVAdapter extends BaseRecyclerViewAdapter<NoticeBean.DataBean.NoticeListBean, BaseViewHolder> {
    public static final String TAG = NoticeListRVAdapter.class.getSimpleName();

    public NoticeListRVAdapter(List<NoticeBean.DataBean.NoticeListBean> data) {
        super(R.layout.item_property_notice, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeBean.DataBean.NoticeListBean item) {
        helper.setText(R.id.tv_title_item_property_notice, item.getTitle())
                .setText(R.id.tv_content_item_property_notice, item.getContent())
                .addOnClickListener(R.id.view_root_item_property_notice)
                .setText(R.id.tv_time_item_property_notice, item.getCtime());
    }
}
