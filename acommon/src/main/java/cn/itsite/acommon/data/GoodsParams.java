package cn.itsite.acommon.data;

import com.google.gson.Gson;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/21 0021 16:05
 * 【宅易购项目】操作非用户相关请求参数封装类
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
        if (keyword != null) {
            try {
                keyword = URLEncoder.encode(keyword, "UTF-8");
            } catch (UnsupportedEncodingException e) {
            }
        }
        return new Gson().toJson(this);
    }
}
