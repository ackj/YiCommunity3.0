package cn.itsite.login.model;

import cn.itsite.abase.common.BaseBean;

public class CheckTokenBean extends BaseBean {


    /**
     * data : {"status":1,"token":"","des":"token不存在"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * status : 1
         * token :
         * des : token不存在
         */

        private int status;
        private String token;
        private String des;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }
    }
}