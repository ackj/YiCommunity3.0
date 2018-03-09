package cn.itsite.amain.s1.entity.bean;

import java.util.List;

/**
 * Author: LiuJia on 2017/8/30 0030 10:42.
 * Email: liujia95me@126.com
 */

public class AuthorizationBean extends BaseBean {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * fid : aa5d45bf-5b0d-4258-ae79-308161eeb296
         * mobile : 13421620639
         * name : 牛逼坏好多话
         */

        private String fid;
        private String mobile;
        private String name;

        public String getFid() {
            return fid;
        }

        public void setFid(String fid) {
            this.fid = fid;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
