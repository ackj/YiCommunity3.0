package cn.itsite.acommon;

import java.util.List;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/3/31 0031 17:58
 * 用于传递操作数据的集中对象
 */

public class OperatorBean {

    public String sku;
    public String uid;
    public String note;
    public String product;
    public String category;

    public List<Product> products;

    public static class Product {
        public String uid;
        public String amount;
        public String sku;
    }
}
