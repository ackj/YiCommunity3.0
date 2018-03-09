package cn.itsite.amain.s1.scene.view;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.amain.R;

import com.chad.library.adapter.base.BaseViewHolder;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 场景模块。
 */

public class AddDeviceRVAdapter extends BaseRecyclerViewAdapter<String,BaseViewHolder> {

    public AddDeviceRVAdapter() {
        super(R.layout.item_add_device);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
