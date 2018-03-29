package cn.itsite.amain.yicommunity.main.home.view;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.amain.R;
import cn.itsite.classify.MenuBean;

/**
 * Author: LiuJia on 2017/9/11 0011 10:53.
 * Email: liujia95me@126.com
 */

public class SmartLifeAdapter extends BaseRecyclerViewAdapter<MenuBean, BaseViewHolder> {
    public static final String TAG = SmartLifeAdapter.class.getSimpleName();

    public SmartLifeAdapter(List<MenuBean> wisdomLife) {
        super(R.layout.item_home_fragment_rv_smart_life, wisdomLife);
    }

    @Override
    protected void convert(BaseViewHolder helper, MenuBean item) {
        ALog.e(TAG, "---item:" + item.getCategory());
        helper.setText(R.id.tv_name_item_smart_life, item.getCategory());
        CardView frameLayout = helper.getView(R.id.cv_smart_life);
        Glide.with(frameLayout.getContext())
                .load(item.getUrl())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        frameLayout.setBackground(resource);
                    }
                });
    }
}
