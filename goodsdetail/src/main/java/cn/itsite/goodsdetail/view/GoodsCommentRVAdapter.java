package cn.itsite.goodsdetail.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import cn.itsite.abase.BaseApp;
import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.acommon.view.PreviewActivity;
import cn.itsite.goodsdetail.R;
import cn.itsite.goodsdetail.model.EvaluatesBean;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/4/12 0012 17:43
 */
public class GoodsCommentRVAdapter extends BaseRecyclerViewAdapter<EvaluatesBean,BaseViewHolder> {

    public GoodsCommentRVAdapter() {
        super(R.layout.item_goods_comment);
    }

    @Override
    protected void convert(BaseViewHolder helper, EvaluatesBean item) {
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(BaseApp.mContext,4));
        ImageRVAdapter adapter = new ImageRVAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setNewData(item.getEvaPictures());

        ImageView ivAvator = helper.getView(R.id.iv_avator);
        Glide.with(ivAvator.getContext())
                .load(item.getMember().getIcon())
                .apply(new RequestOptions().error(R.drawable.ic_default_head_image_200px)
                .placeholder(R.drawable.ic_default_head_image_200px).circleCrop())
                .into(ivAvator);
        helper.setText(R.id.tv_name,item.getMember().getName())
                .setText(R.id.tv_content,item.getEvaDes())
                .setText(R.id.tv_time,item.getEvaTime())
                .setText(R.id.tv_desc,item.getProductDes())
        .setText(R.id.tv_level,item.getEvaLevel()==0?"差评":item.getEvaLevel()==1?"中评":"好评");

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Context context = BaseApp.mContext;
                Intent intent = new Intent(context, PreviewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();

                bundle.putStringArrayList("picsList", (ArrayList<String>) item.getEvaPictures());
                intent.putExtra("pics", bundle);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });
    }
}
