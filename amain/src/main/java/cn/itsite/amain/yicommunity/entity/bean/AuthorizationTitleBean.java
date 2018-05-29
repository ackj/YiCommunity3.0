package cn.itsite.amain.yicommunity.entity.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import cn.itsite.amain.yicommunity.main.mine.view.GateguardDataRVAdapter;

/**
 * Created by liujia on 2018/5/22.
 */

public class AuthorizationTitleBean extends AbstractExpandableItem<AuthorizationGatewayBean> implements MultiItemEntity {

    public String title;
    public String subTitle;

    @Override
    public int getItemType() {
        return GateguardDataRVAdapter.TYPE_TITLE;
    }

    @Override
    public int getLevel() {
        return 0;
    }
}
