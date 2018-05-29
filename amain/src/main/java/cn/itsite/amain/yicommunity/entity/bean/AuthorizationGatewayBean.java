package cn.itsite.amain.yicommunity.entity.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import cn.itsite.amain.yicommunity.main.mine.view.GateguardDataRVAdapter;

/**
 * Created by liujia on 2018/5/22.
 */

public class AuthorizationGatewayBean implements MultiItemEntity{

    public String name;
    public boolean open;

    @Override
    public int getItemType() {
        return GateguardDataRVAdapter.TYPE_GATEGAY;
    }
}
