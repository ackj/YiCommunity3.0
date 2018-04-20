package cn.itsite.amain.s1.entity.bean;

import java.util.List;

/**
 * Author: LiuJia on 2017/8/23 0023 11:01.
 * Email: liujia95me@126.com
 */

public class DeviceLogBean extends BaseBean {

    /**
     * data : {"logs":[{"des":"wdj学习了一个设备 一路插\u2026","fid":"c0e34d8c-8fd4-4b0c-b9d6-8ecc1cbdf780","logtime":"2017-08-23 08:13:23","title":"设备学习消息"},{"des":"设备 一路插座一路开","fid":"bb9187c4-0234-423e-9529-cd274d871b0a","logtime":"2017-08-23 08:13:56","title":"外设状态变更消息"},{"des":"设备 一路插座一路关","fid":"1657b4c5-5852-4503-867e-cb211948ea85","logtime":"2017-08-23 08:13:59","title":"外设状态变更消息"},{"des":"设备 一路插座一路开","fid":"c33252c6-eefa-4f30-8ae3-c8ce35e93e55","logtime":"2017-08-23 08:14:01","title":"外设状态变更消息"},{"des":"设备 一路插座一路关","fid":"a4267d12-f802-4487-8bd4-77240d12d1b1","logtime":"2017-08-23 08:14:06","title":"外设状态变更消息"},{"des":"设备 一路插座一路开","fid":"1e67aa29-48fa-47dc-8d32-6af41ebbec80","logtime":"2017-08-23 08:16:50","title":"外设状态变更消息"},{"des":"设备 一路插座一路开","fid":"74bd95ad-516f-4e44-8ecf-241cd151f653","logtime":"2017-08-23 08:16:50","title":"外设状态变更消息"},{"des":"设备 一路插座一路关","fid":"5ac4307b-8def-49e0-82e5-9a24090f17c1","logtime":"2017-08-23 08:17:15","title":"外设状态变更消息"},{"des":"设备 一路插座一路开","fid":"48f62e7c-2d0e-41e1-85da-08603cbe73a3","logtime":"2017-08-23 08:17:19","title":"外设状态变更消息"},{"des":"设备 一路插座一路关","fid":"ec4aa20b-81ba-4ef0-a609-28e08521d72d","logtime":"2017-08-23 08:17:21","title":"外设状态变更消息"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<LogsBean> logs;

        public List<LogsBean> getLogs() {
            return logs;
        }

        public void setLogs(List<LogsBean> logs) {
            this.logs = logs;
        }

        public static class LogsBean {
            /**
             * des : wdj学习了一个设备 一路插…
             * fid : c0e34d8c-8fd4-4b0c-b9d6-8ecc1cbdf780
             * logtime : 2017-08-23 08:13:23
             * title : 设备学习消息
             */

            private String des;
            private String fid;
            private String logtime;
            private String title;

            public String getDes() {
                return des;
            }

            public void setDes(String des) {
                this.des = des;
            }

            public String getFid() {
                return fid;
            }

            public void setFid(String fid) {
                this.fid = fid;
            }

            public String getLogtime() {
                return logtime;
            }

            public void setLogtime(String logtime) {
                this.logtime = logtime;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
