package cn.itsite.amain.yicommunity.main.mine.view;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.kyleduo.switchbutton.SwitchButton;

import java.util.List;

import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.entity.bean.AuthorizationGatewayBean;
import cn.itsite.amain.yicommunity.entity.bean.AuthorizationTitleBean;


/**
 * Created by liujia on 2018/5/22.
 */

public class CarTurnoverDataRVAdatper extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_TITLE = 0;
    public static final int TYPE_GATEGAY= 1;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public CarTurnoverDataRVAdatper(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_TITLE, R.layout.item_authorization_title);
        addItemType(TYPE_GATEGAY, R.layout.item_authorization_gateway);
    }

    private boolean isEditModel;
    public void switchEditModel(boolean isEditModel){
        this.isEditModel = isEditModel;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder holder, MultiItemEntity item) {
        switch (holder.getItemViewType()){
            case TYPE_TITLE:
                ImageView ivChoose = holder.getView(R.id.iv_choose);
                ivChoose.setVisibility(isEditModel?View.VISIBLE:View.GONE);
                ivChoose.setSelected(holder.getLayoutPosition()%2==1);
                AuthorizationTitleBean title = (AuthorizationTitleBean) item;
                holder.setText(R.id.tv_title,title.title)
                        .setImageResource(R.id.iv_arrow,title.isExpanded()?R.drawable.ic_arrow_bottom_gray_24dp:R.drawable.ic_arrow_top_gray_24dp);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        if (title.isExpanded()) {
                            collapse(pos, false);
                        } else {
                            expand(pos, false);
                        }
                    }
                });
                break;
            case TYPE_GATEGAY:
                AuthorizationGatewayBean gateway = (AuthorizationGatewayBean) item;
                holder.setText(R.id.tv_name,gateway.name);
                SwitchButton switchButton = holder.getView(R.id.switch_button);
                switchButton.setChecked(gateway.open);
                break;
        }
    }
}
