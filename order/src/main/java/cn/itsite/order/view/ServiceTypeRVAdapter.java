package cn.itsite.order.view;

import com.chad.library.adapter.base.BaseViewHolder;

import cn.itsite.abase.BaseApp;
import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.order.R;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/4/13 0013 16:53
 */

public class ServiceTypeRVAdapter extends BaseRecyclerViewAdapter<Object, BaseViewHolder> {

    private final String[] mDesc;
    private final String[] mTitles;

    public ServiceTypeRVAdapter() {
        super(R.layout.item_service_type);
        mTitles = BaseApp.mContext.getResources().getStringArray(R.array.service_type_title);
        mDesc = BaseApp.mContext.getResources().getStringArray(R.array.service_type_desc);

    }

    @Override
    public int getItemCount() {
        return mTitles.length;
    }

    @Override
    protected void convert(BaseViewHolder helper, Object object) {
        helper.setText(R.id.tv_title, mTitles[helper.getLayoutPosition()])
                .setText(R.id.tv_desc, mDesc[helper.getLayoutPosition()]);
    }
}
