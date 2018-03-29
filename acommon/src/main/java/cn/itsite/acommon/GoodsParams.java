package cn.itsite.acommon;

import com.google.gson.Gson;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/21 0021 16:05
 */

public class GoodsParams {

    public String shoptype;
    public String type;
    public String keyword;
    public String uid;
    public String category;
    public String shopUid;

    public String latitude;
    public String longitude;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
