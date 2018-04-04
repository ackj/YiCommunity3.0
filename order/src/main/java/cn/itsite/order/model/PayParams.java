package cn.itsite.order.model;

import java.util.List;

/**
 * @version v0.0.0
 * @Author leguang
 * @E-mail langmanleguang@qq.com
 * @Blog https://github.com/leguang
 * @Time 2018/4/3 0003 17:58
 * @Description
 */
public class PayParams {


    /**
     * orders : ["7af91822-3669-40b9-82a2-cd66e1d40452","f354c594-a0ac-494f-b654-a0af42ce4264"]
     * payment : zfb
     */

    private String payment;
    private List<String> orders;

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public List<String> getOrders() {
        return orders;
    }

    public void setOrders(List<String> orders) {
        this.orders = orders;
    }
}
