package cn.itsite.amain.yicommunity.main.steward.view;

import com.chad.library.adapter.base.BaseViewHolder;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.entity.bean.IconBean;

/**
 * Created by leguang on 2017/4/14 0014.
 * Email：langmanleguang@qq.com
 */

public class StewardRVAdapter extends BaseRecyclerViewAdapter<IconBean, BaseViewHolder> {
    public static final String TAG = StewardRVAdapter.class.getSimpleName();

    public StewardRVAdapter() {
        super(R.layout.item_rv_steward_fragment, null);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder helper, IconBean item) {
        helper.setImageResource(R.id.iv_head_item_comment, item.icon)
                .setText(R.id.tv_title, item.title);
    }
}
