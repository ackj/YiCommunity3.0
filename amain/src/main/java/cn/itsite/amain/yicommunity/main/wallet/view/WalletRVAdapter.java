package cn.itsite.amain.yicommunity.main.wallet.view;

import com.chad.library.adapter.base.BaseViewHolder;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.amain.R;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/4/13 0013 11:11
 */

public class WalletRVAdapter extends BaseRecyclerViewAdapter<String,BaseViewHolder>{

    public WalletRVAdapter() {
        super(R.layout.item_wallet);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
