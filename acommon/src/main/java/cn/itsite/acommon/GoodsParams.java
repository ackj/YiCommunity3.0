package cn.itsite.acommon;

import com.google.gson.Gson;

import java.io.File;
import java.util.List;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/21 0021 16:05
 */

public class GoodsParams {

    public int pageSize = 20;
    public int page = 1;

    public String shoptype;
    public String type;
    public String keyword;
    public String uid;
    public String category;
    public String shopUid;

    public String latitude;
    public String longitude;
    public List<File> files;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
