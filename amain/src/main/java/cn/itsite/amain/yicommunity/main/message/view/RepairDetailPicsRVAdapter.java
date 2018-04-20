package cn.itsite.amain.yicommunity.main.message.view;

import android.widget.ImageView;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.App;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author: LiuJia on 2017/5/19 0019 11:00.
 * Email: liujia95me@126.com
 */

public class RepairDetailPicsRVAdapter extends BaseRecyclerViewAdapter<String, BaseViewHolder> {

    public RepairDetailPicsRVAdapter(List<String> datas) {
        super(R.layout.item_image_aftersales, datas);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView imageView = helper.getView(R.id.image_item_image);
        Glide.with(App.mContext)
                .load(item)
                .into(imageView);
    }
}
