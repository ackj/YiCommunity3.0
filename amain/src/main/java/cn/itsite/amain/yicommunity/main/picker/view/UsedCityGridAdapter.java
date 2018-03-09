package cn.itsite.amain.yicommunity.main.picker.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.itsite.abase.cache.SPCache;
import cn.itsite.abase.log.ALog;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.Constants;


/**
 * author zaaach on 2016/1/26.
 */
public class UsedCityGridAdapter extends BaseAdapter {
    private static final String TAG = UsedCityGridAdapter.class.getSimpleName();
    private Context mContext;
    private List<String> mCities;

    public UsedCityGridAdapter(Context context) {
        this.mContext = context;
        mCities = new ArrayList<>();
        String citysStr = (String) SPCache.get(context, Constants.SP_KEY_USED_CITYS, "");
        ALog.d(TAG, "citysStr:" + citysStr);
        if (!TextUtils.isEmpty(citysStr)) {
            String[] citysArr = citysStr.split(",");
            for (String city : citysArr) {
                mCities.add(city);
                ALog.d(TAG, "city:" + city);
            }
        }
    }

    @Override
    public int getCount() {
        return mCities == null ? 0 : mCities.size();
    }

    @Override
    public String getItem(int position) {
        return mCities == null ? null : mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        HotCityViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.cp_item_hot_city_gridview, parent, false);
            holder = new HotCityViewHolder();
            holder.name = (TextView) view.findViewById(R.id.tv_hot_city_name);
            view.setTag(holder);
        } else {
            holder = (HotCityViewHolder) view.getTag();
        }
        holder.name.setText(mCities.get(position));
        return view;
    }

    public static class HotCityViewHolder {
        TextView name;
    }
}
