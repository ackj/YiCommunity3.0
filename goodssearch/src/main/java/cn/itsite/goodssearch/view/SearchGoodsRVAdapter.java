package cn.itsite.goodssearch.view;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.itsite.goodssearch.R;
import cn.itsite.goodssearch.model.SearchGoodsBean;

/**
 * Author： Administrator on 2018/1/31 0031.
 * Email： liujia95me@126.com
 */

public class SearchGoodsRVAdapter extends BaseMultiItemQuickAdapter<SearchGoodsBean, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    public SearchGoodsRVAdapter() {
        super(null);
        addItemType(SearchGoodsBean.TYPE_HISTORY_TITLE, R.layout.item_search_history_title);
        addItemType(SearchGoodsBean.TYPE_HISTORY_ITEM, R.layout.item_search_history_goods);
        addItemType(SearchGoodsBean.TYPE_SEARCH_STRING, R.layout.item_search_string);
        addItemType(SearchGoodsBean.TYPE_SEARCH_GOODS, R.layout.item_grid_goods);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchGoodsBean item) {
        switch (item.getItemType()) {
            case SearchGoodsBean.TYPE_HISTORY_TITLE:
                helper.setText(R.id.tv_title, item.getTitle())
                        .setVisible(R.id.iv_clear, item.getTitle().contains("历史"))
                        .addOnClickListener(R.id.iv_clear);
                break;
            case SearchGoodsBean.TYPE_HISTORY_ITEM:
                helper.setText(R.id.tv_keyword, item.getKeywordBean().getKeyword());
                break;
            case SearchGoodsBean.TYPE_SEARCH_STRING:
                helper.setText(R.id.tv_keyword, item.getKeywordBean().getKeyword());
                break;
            case SearchGoodsBean.TYPE_SEARCH_GOODS:
                ImageView mIvIcon = helper.getView(R.id.iv_icon);
                Glide.with(mIvIcon.getContext())
                        .load(item.getGoodsBean().getImageUrl())
                        .apply(new RequestOptions().placeholder(R.drawable.ic_img_loading))
                        .apply(new RequestOptions().error(R.drawable.ic_img_error))
                        .into(mIvIcon);
                helper.setText(R.id.tv_name, item.getGoodsBean().getTitle())
                        .setText(R.id.tv_desc, item.getGoodsBean().getDescription())
                        .setText(R.id.tv_price, item.getGoodsBean().getCurrency() + " " + item.getGoodsBean().getPrice());
                break;
            default:
        }
    }
}
