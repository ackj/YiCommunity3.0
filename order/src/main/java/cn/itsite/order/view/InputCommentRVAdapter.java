package cn.itsite.order.view;

import com.chad.library.adapter.base.BaseViewHolder;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.order.R;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/4/13 0013 9:17
 */

public class InputCommentRVAdapter extends BaseRecyclerViewAdapter<String,BaseViewHolder>{

    public InputCommentRVAdapter() {
        super(R.layout.item_input_comment);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
