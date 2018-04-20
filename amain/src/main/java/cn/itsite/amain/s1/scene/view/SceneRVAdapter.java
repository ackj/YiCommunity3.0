package cn.itsite.amain.s1.scene.view;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.amain.R;
import cn.itsite.amain.s1.entity.bean.SceneBean;

import com.chad.library.adapter.base.BaseViewHolder;


/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 场景模块。
 */

public class SceneRVAdapter extends BaseRecyclerViewAdapter<SceneBean.DataBean, BaseViewHolder> {

    public SceneRVAdapter() {
        super(R.layout.item_rv_scene);
    }

    @Override
    protected void convert(BaseViewHolder helper, SceneBean.DataBean item) {
        helper.setText(R.id.tv_scene_item_scene, item.getName())
                .addOnClickListener(R.id.tv_delete_item_scene)
                .addOnClickListener(R.id.tv_toggle_item_scene);
    }
}
