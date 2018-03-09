package cn.itsite.acommon.widget.goodsgrid;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.itsite.acommon.R;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2018/2/6 0006 9:57
 */
public class GoodsGridView extends LinearLayout{


    private ImageView mIvIcon;
    private TextView mTvName;
    private TextView mTvDesc;
    private TextView mTvPrice;

    public GoodsGridView(Context context) {
        super(context);
    }

    public GoodsGridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GoodsGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.item_grid_goods,null);
        mIvIcon = (ImageView) view.findViewById(R.id.iv_icon);
        mTvName = (TextView) view.findViewById(R.id.tv_name);
        mTvDesc = (TextView) view.findViewById(R.id.tv_desc);
        mTvPrice = (TextView) view.findViewById(R.id.tv_price);
        addView(view);
    }

    public void setName(String name){
        mTvName.setText(name);
    }

    public void setDesc(String desc){
        mTvDesc.setText(desc);
    }

    public void setPrice(String price){
        mTvPrice.setText(price);
    }

    public void setPrice(float price){
        mTvPrice.setText(String.valueOf(false));
    }

}
