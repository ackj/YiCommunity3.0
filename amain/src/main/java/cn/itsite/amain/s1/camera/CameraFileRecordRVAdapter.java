package cn.itsite.amain.s1.camera;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.amain.R;

import com.chad.library.adapter.base.BaseViewHolder;


/**
 * Author: LiuJia on 2017/9/19 0019 10:52.
 * Email: liujia95me@126.com
 */

public class CameraFileRecordRVAdapter extends BaseRecyclerViewAdapter<String,BaseViewHolder> {

    public CameraFileRecordRVAdapter() {
        super(R.layout.item_file_record);
    }

    @Override
    protected void convert(BaseViewHolder helper, String str) {
        helper.setText(R.id.tv_info,str);
    }
}
