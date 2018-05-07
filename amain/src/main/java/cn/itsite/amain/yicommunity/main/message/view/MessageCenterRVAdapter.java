package cn.itsite.amain.yicommunity.main.message.view;


import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.HashMap;
import java.util.List;

import cn.itsite.abase.mvp.view.base.BaseRecyclerViewAdapter;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.entity.bean.MessageCenterBean;

/**
 * Created by leguang on 2017/4/24 0024.
 * Email：langmanleguang@qq.com
 */
public class MessageCenterRVAdapter extends BaseRecyclerViewAdapter<MessageCenterBean.DataBean.MemNewsBean, BaseViewHolder> {

    private HashMap<String, Integer> mTypeResMap;

    public MessageCenterRVAdapter(List data) {
        super(R.layout.item_message_center, data);
        initMap();
    }

    private void initMap() {
        mTypeResMap = new HashMap<>();
        mTypeResMap.put(MessageCenterFragment.SMARTDOOR_PUSHREC,     R.drawable.ic_zhanghu_90px); //公告发布
        mTypeResMap.put(MessageCenterFragment.HOUSE_OWNER_APPLY,     R.drawable.ic_zhanghu_90px);//业主申请
        mTypeResMap.put(MessageCenterFragment.HOUSE_MEMBER_APPLY,    R.drawable.ic_zhanghu_90px);//成员申请
        mTypeResMap.put(MessageCenterFragment.HOUSE_RENTER_APPLY,     R.drawable.ic_zhanghu_90px);//租客申请
        mTypeResMap.put(MessageCenterFragment.HOUSE_OWNER_APPROVE,   R.drawable.ic_zhanghu_90px);//业主申请审核结果
        mTypeResMap.put(MessageCenterFragment.HOUSE_MEMBER_APPROVE, R.drawable.ic_zhanghu_90px);//成员申请审核结果
        mTypeResMap.put(MessageCenterFragment.HOUSE_RENTER_APPROVE, R.drawable.ic_zhanghu_90px);//租客申请审核结果
        mTypeResMap.put(MessageCenterFragment.FEEDBACK_REPLY,        R.drawable.ic_tousu_90px);//反馈回复
        mTypeResMap.put(MessageCenterFragment.REPAIR_REPLY,         R.drawable.ic_weixiu_90px);//物业报修回复
        mTypeResMap.put(MessageCenterFragment.NOTICE_PUBLISH,       R.drawable.ic_zhangdan_90px);//物业账单
        mTypeResMap.put(MessageCenterFragment.PROPERTY_BILL,        R.drawable.ic_zhangdan_90px);//物业账单
        mTypeResMap.put(MessageCenterFragment.COMPLAINT_REPLY,      R.drawable.ic_tousu_90px);//物业投诉回复
        mTypeResMap.put(MessageCenterFragment.SMARTDOOR_CALL_PUSH,      R.drawable.ic_tuisong_90px);//来电推送

        //todo：还有设备报警和车卡办理的类型
    }

    private Integer getResFromType(String type){
        if(mTypeResMap == null ||mTypeResMap.size() == 0){
            initMap();
        }
        return mTypeResMap.get(type);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageCenterBean.DataBean.MemNewsBean item) {
        helper.getView(R.id.v_mark_item_message_center_fragment)
                .setVisibility(item.isRead() ? View.INVISIBLE : View.VISIBLE);
        ImageView ivIcon = helper.getView(R.id.iv_icon);
        Glide.with(ivIcon.getContext())
                .load(getResFromType(item.getOpType()))
                .apply(new RequestOptions().error(R.drawable.ic_img_error).placeholder(R.drawable.ic_img_loading))
                .into(ivIcon);
        helper.addOnClickListener(R.id.ll_layout_item_message_center_fragment)
                .addOnClickListener(R.id.tv_delete_item_message_center)
                .setText(R.id.tv_title_item_message_center_fragment, item.getTitle())
                .setText(R.id.tv_description_item_message_center_fragment, item.getDes())
                .setText(R.id.tv_date_item_message_center_fragment, item.getOpTime().substring(0, 10));
    }
}
