package cn.itsite.amain.s1.entity.bean;

import java.util.List;

/**
 * Author: LiuJia on 2017/4/27 0027 15:09.
 * Email: liujia95me@126.com
 */

public class SceneBean {
    /**
     * data : [{"name":"场景01","index":0,"sceneActtions":[{"deviceId":5,"nodeId":0,"action1":0,"name":"二路开关"},{"deviceId":5,"nodeId":1,"action1":0,"name":"二路开关"}]}]
     * other : {"code":200,"message":"data success","time":"","currpage":0,"next":"","forward":"","refresh":"","first":""}
     */

    private OtherBean other;
    private List<DataBean> data;

    public OtherBean getOther() {
        return other;
    }

    public void setOther(OtherBean other) {
        this.other = other;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class OtherBean {
        /**
         * code : 200
         * message : data success
         * time :
         * currpage : 0
         * next :
         * forward :
         * refresh :
         * first :
         */

        private int code;
        private String message;
        private String time;
        private int currpage;
        private String next;
        private String forward;
        private String refresh;
        private String first;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getCurrpage() {
            return currpage;
        }

        public void setCurrpage(int currpage) {
            this.currpage = currpage;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public String getForward() {
            return forward;
        }

        public void setForward(String forward) {
            this.forward = forward;
        }

        public String getRefresh() {
            return refresh;
        }

        public void setRefresh(String refresh) {
            this.refresh = refresh;
        }

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }
    }

    public static class DataBean {
        /**
         * name : 场景01
         * index : 0
         * sceneActtions : [{"deviceId":5,"nodeId":0,"action1":0,"name":"二路开关"},{"deviceId":5,"nodeId":1,"action1":0,"name":"二路开关"}]
         */

        private String name;
        private int index;
        private List<SceneActtionsBean> sceneActtions;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public List<SceneActtionsBean> getSceneActtions() {
            return sceneActtions;
        }

        public void setSceneActtions(List<SceneActtionsBean> sceneActtions) {
            this.sceneActtions = sceneActtions;
        }

        public static class SceneActtionsBean {
            /**
             * deviceId : 5
             * nodeId : 0
             * action1 : 0
             * name : 二路开关
             */

            private int deviceId;
            private int nodeId;
            private int action1;
            private String name;

            public int getDeviceId() {
                return deviceId;
            }

            public void setDeviceId(int deviceId) {
                this.deviceId = deviceId;
            }

            public int getNodeId() {
                return nodeId;
            }

            public void setNodeId(int nodeId) {
                this.nodeId = nodeId;
            }

            public int getAction1() {
                return action1;
            }

            public void setAction1(int action1) {
                this.action1 = action1;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
