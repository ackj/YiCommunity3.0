package cn.itsite.amain.s1.entity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author: LiuJia on 2017/8/29 0029 11:05.
 * Email: liujia95me@126.com
 */

public class LinkageBean extends BaseBean {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * day : 3
         * fid :
         * index : 0
         * name : 哈哈啊哈
         * sensorCmd : 254
         * sensorIndex : 1
         * status : 1
         * targetCmd : 1
         * targetIndex : 2
         * targetNodeId : 0
         * targetTimeout : 12
         * targetTimeoutCmd : 0
         * targetType : device
         * time : 0:0
         * triggerType : sensor
         */

        private String day;
        private String fid;
        private int index;
        private String name;
        private int sensorCmd;
        private int sensorIndex;
        private int status;
        private int targetCmd;
        private int targetIndex;
        private int targetNodeId;
        private int targetTimeout;
        private int targetTimeoutCmd;
        private String targetType;
        private String time;
        private String triggerType;

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getFid() {
            return fid;
        }

        public void setFid(String fid) {
            this.fid = fid;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSensorCmd() {
            return sensorCmd;
        }

        public void setSensorCmd(int sensorCmd) {
            this.sensorCmd = sensorCmd;
        }

        public int getSensorIndex() {
            return sensorIndex;
        }

        public void setSensorIndex(int sensorIndex) {
            this.sensorIndex = sensorIndex;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTargetCmd() {
            return targetCmd;
        }

        public void setTargetCmd(int targetCmd) {
            this.targetCmd = targetCmd;
        }

        public int getTargetIndex() {
            return targetIndex;
        }

        public void setTargetIndex(int targetIndex) {
            this.targetIndex = targetIndex;
        }

        public int getTargetNodeId() {
            return targetNodeId;
        }

        public void setTargetNodeId(int targetNodeId) {
            this.targetNodeId = targetNodeId;
        }

        public int getTargetTimeout() {
            return targetTimeout;
        }

        public void setTargetTimeout(int targetTimeout) {
            this.targetTimeout = targetTimeout;
        }

        public int getTargetTimeoutCmd() {
            return targetTimeoutCmd;
        }

        public void setTargetTimeoutCmd(int targetTimeoutCmd) {
            this.targetTimeoutCmd = targetTimeoutCmd;
        }

        public String getTargetType() {
            return targetType;
        }

        public void setTargetType(String targetType) {
            this.targetType = targetType;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTriggerType() {
            return triggerType;
        }

        public void setTriggerType(String triggerType) {
            this.triggerType = triggerType;
        }
    }
}
