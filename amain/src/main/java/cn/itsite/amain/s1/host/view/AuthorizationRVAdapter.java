package cn.itsite.amain.s1.host.view;

import com.chad.library.adapter.base.BaseViewHolder;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.amain.R;
import cn.itsite.amain.s1.entity.bean.AuthorizationBean;


/**
 * Created by leguang on 2017/6/22 0022.
 * Email：langmanleguang@qq.com
 */

public class AuthorizationRVAdapter extends BaseRecyclerViewAdapter<AuthorizationBean.DataBean,BaseViewHolder> {

    public AuthorizationRVAdapter() {
        super(R.layout.item_authorization);
    }

    @Override
    protected void convert(BaseViewHolder helper, AuthorizationBean.DataBean item) {
        helper.setText(R.id.tv_phone,item.getMobile())
                .addOnClickListener(R.id.tv_unbound);
    }
}
