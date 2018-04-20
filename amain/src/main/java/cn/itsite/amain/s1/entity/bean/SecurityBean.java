package cn.itsite.amain.s1.entity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author: LiuJia on 2017/4/27 0027 09:03.
 * Email: liujia95me@126.com
 */

public class SecurityBean extends BaseBean{


    /**
     * data : {"gateway":{"defenseStatus":"home","defenseStatusDes":"在家模式，处于【在家开启】模式的探测器处于防御状态。","fid":"269afeb5-0163-4f1d-9d21-5dfe171dbb22","isCurrent":1,"isManager":0,"isOnline":1,"name":"S1智能网关[G111EU6B1000112]","no":"G111EU6B1000112","residence":{"addr":"帝景国际商务中心","addrDet":"801","fid":"701b3a41-c305-400a-a847-26bd7a209ef9","lat":"23.11338","lng":"114.4193","name":""},"status":0},"subDevices":[{"actions":[{"cmd":254,"code":"Trigger","name":"触发"}],"alarmDelay":0,"category":"sensor","defenseLevel":"24hour","deviceId":"","deviceType":"emergency_button","extInfo":{"index":0,"name":"","node":1,"roomId":0},"fid":"","icon":"http://agl.image.alimmdn.com/smartHome/subDeviceType/e_sos1.png","index":0,"isOline":1,"name":"紧急按钮","nodeStatusDes":[],"password":"","status":0,"workStatus":"defense_on"},{"actions":[{"cmd":0,"code":"Arm","name":"离家布防"},{"cmd":1,"code":"HomeArm","name":"在家布防"},{"cmd":2,"code":"Disarm","name":"撤防"},{"cmd":3,"code":"SOS","name":"求救"}],"alarmDelay":0,"category":"sensor","defenseLevel":"first","deviceId":"","deviceType":"remote_control","extInfo":{"index":1,"name":"","node":1,"roomId":0},"fid":"","icon":"http://agl.image.alimmdn.com/smartHome/subDeviceType/e_yaokong.png","index":1,"isOline":1,"name":"我的遥控器","nodeStatusDes":[],"password":"","status":0,"workStatus":"defense_on"},{"actions":[{"cmd":0,"code":"Arm","name":"离家布防"},{"cmd":1,"code":"HomeArm","name":"在家布防"},{"cmd":2,"code":"Disarm","name":"撤防"},{"cmd":3,"code":"SOS","name":"求救"}],"alarmDelay":0,"category":"sensor","defenseLevel":"first","deviceId":"","deviceType":"remote_control","extInfo":{"index":2,"name":"","node":1,"roomId":0},"fid":"","icon":"http://agl.image.alimmdn.com/smartHome/subDeviceType/e_yaokong.png","index":2,"isOline":1,"name":"遥控器","nodeStatusDes":[],"password":"","status":0,"workStatus":"defense_on"}]}
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
         * gateway : {"defenseStatus":"home","defenseStatusDes":"在家模式，处于【在家开启】模式的探测器处于防御状态。","fid":"269afeb5-0163-4f1d-9d21-5dfe171dbb22","isCurrent":1,"isManager":0,"isOnline":1,"name":"S1智能网关[G111EU6B1000112]","no":"G111EU6B1000112","residence":{"addr":"帝景国际商务中心","addrDet":"801","fid":"701b3a41-c305-400a-a847-26bd7a209ef9","lat":"23.11338","lng":"114.4193","name":""},"status":0}
         * subDevices : [{"actions":[{"cmd":254,"code":"Trigger","name":"触发"}],"alarmDelay":0,"category":"sensor","defenseLevel":"24hour","deviceId":"","deviceType":"emergency_button","extInfo":{"index":0,"name":"","node":1,"roomId":0},"fid":"","icon":"http://agl.image.alimmdn.com/smartHome/subDeviceType/e_sos1.png","index":0,"isOline":1,"name":"紧急按钮","nodeStatusDes":[],"password":"","status":0,"workStatus":"defense_on"},{"actions":[{"cmd":0,"code":"Arm","name":"离家布防"},{"cmd":1,"code":"HomeArm","name":"在家布防"},{"cmd":2,"code":"Disarm","name":"撤防"},{"cmd":3,"code":"SOS","name":"求救"}],"alarmDelay":0,"category":"sensor","defenseLevel":"first","deviceId":"","deviceType":"remote_control","extInfo":{"index":1,"name":"","node":1,"roomId":0},"fid":"","icon":"http://agl.image.alimmdn.com/smartHome/subDeviceType/e_yaokong.png","index":1,"isOline":1,"name":"我的遥控器","nodeStatusDes":[],"password":"","status":0,"workStatus":"defense_on"},{"actions":[{"cmd":0,"code":"Arm","name":"离家布防"},{"cmd":1,"code":"HomeArm","name":"在家布防"},{"cmd":2,"code":"Disarm","name":"撤防"},{"cmd":3,"code":"SOS","name":"求救"}],"alarmDelay":0,"category":"sensor","defenseLevel":"first","deviceId":"","deviceType":"remote_control","extInfo":{"index":2,"name":"","node":1,"roomId":0},"fid":"","icon":"http://agl.image.alimmdn.com/smartHome/subDeviceType/e_yaokong.png","index":2,"isOline":1,"name":"遥控器","nodeStatusDes":[],"password":"","status":0,"workStatus":"defense_on"}]
         */

        private GatewayBean gateway;
        private List<SubDevicesBean> subDevices;

        public GatewayBean getGateway() {
            return gateway;
        }

        public void setGateway(GatewayBean gateway) {
            this.gateway = gateway;
        }

        public List<SubDevicesBean> getSubDevices() {
            return subDevices;
        }

        public void setSubDevices(List<SubDevicesBean> subDevices) {
            this.subDevices = subDevices;
        }

        public static class GatewayBean {
            /**
             * defenseStatus : home
             * defenseStatusDes : 在家模式，处于【在家开启】模式的探测器处于防御状态。
             * fid : 269afeb5-0163-4f1d-9d21-5dfe171dbb22
             * isCurrent : 1
             * isManager : 0
             * isOnline : 1
             * name : S1智能网关[G111EU6B1000112]
             * no : G111EU6B1000112
             * residence : {"addr":"帝景国际商务中心","addrDet":"801","fid":"701b3a41-c305-400a-a847-26bd7a209ef9","lat":"23.11338","lng":"114.4193","name":""}
             * status : 0
             */

            private String defenseStatus;
            private String defenseStatusDes;
            private String fid;
            private int isCurrent;
            private int isManager;
            private int isOnline;
            private String name;
            private String no;
            private ResidenceBean residence;
            private int status;

            public String getDefenseStatus() {
                return defenseStatus;
            }

            public void setDefenseStatus(String defenseStatus) {
                this.defenseStatus = defenseStatus;
            }

            public String getDefenseStatusDes() {
                return defenseStatusDes;
            }

            public void setDefenseStatusDes(String defenseStatusDes) {
                this.defenseStatusDes = defenseStatusDes;
            }

            public String getFid() {
                return fid;
            }

            public void setFid(String fid) {
                this.fid = fid;
            }

            public int getIsCurrent() {
                return isCurrent;
            }

            public void setIsCurrent(int isCurrent) {
                this.isCurrent = isCurrent;
            }

            public int getIsManager() {
                return isManager;
            }

            public void setIsManager(int isManager) {
                this.isManager = isManager;
            }

            public int getIsOnline() {
                return isOnline;
            }

            public void setIsOnline(int isOnline) {
                this.isOnline = isOnline;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNo() {
                return no;
            }

            public void setNo(String no) {
                this.no = no;
            }

            public ResidenceBean getResidence() {
                return residence;
            }

            public void setResidence(ResidenceBean residence) {
                this.residence = residence;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public static class ResidenceBean {
                /**
                 * addr : 帝景国际商务中心
                 * addrDet : 801
                 * fid : 701b3a41-c305-400a-a847-26bd7a209ef9
                 * lat : 23.11338
                 * lng : 114.4193
                 * name :
                 */

                private String addr;
                private String addrDet;
                private String fid;
                private String lat;
                private String lng;
                private String name;

                public String getAddr() {
                    return addr;
                }

                public void setAddr(String addr) {
                    this.addr = addr;
                }

                public String getAddrDet() {
                    return addrDet;
                }

                public void setAddrDet(String addrDet) {
                    this.addrDet = addrDet;
                }

                public String getFid() {
                    return fid;
                }

                public void setFid(String fid) {
                    this.fid = fid;
                }

                public String getLat() {
                    return lat;
                }

                public void setLat(String lat) {
                    this.lat = lat;
                }

                public String getLng() {
                    return lng;
                }

                public void setLng(String lng) {
                    this.lng = lng;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }

        public static class SubDevicesBean implements Serializable{
            /**
             * actions : [{"cmd":254,"code":"Trigger","name":"触发"}]
             * alarmDelay : 0
             * category : sensor
             * defenseLevel : 24hour
             * deviceId :
             * deviceType : emergency_button
             * extInfo : {"index":0,"name":"","node":1,"roomId":0}
             * fid :
             * icon : http://agl.image.alimmdn.com/smartHome/subDeviceType/e_sos1.png
             * index : 0
             * isOline : 1
             * name : 紧急按钮
             * nodeStatusDes : []
             * password :
             * status : 0
             * workStatus : defense_on
             */

            private int alarmDelay;
            private String category;
            private String defenseLevel;
            private String deviceId;
            private String deviceType;
            private ExtInfoBean extInfo;
            private String fid;
            private String icon;
            private int index;
            private int isOline;
            private String name;
            private String password;
            private int status;
            private String workStatus;
            private List<ActionsBean> actions;
            private List<?> nodeStatusDes;

            public int getAlarmDelay() {
                return alarmDelay;
            }

            public void setAlarmDelay(int alarmDelay) {
                this.alarmDelay = alarmDelay;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getDefenseLevel() {
                return defenseLevel;
            }

            public void setDefenseLevel(String defenseLevel) {
                this.defenseLevel = defenseLevel;
            }

            public String getDeviceId() {
                return deviceId;
            }

            public void setDeviceId(String deviceId) {
                this.deviceId = deviceId;
            }

            public String getDeviceType() {
                return deviceType;
            }

            public void setDeviceType(String deviceType) {
                this.deviceType = deviceType;
            }

            public ExtInfoBean getExtInfo() {
                return extInfo;
            }

            public void setExtInfo(ExtInfoBean extInfo) {
                this.extInfo = extInfo;
            }

            public String getFid() {
                return fid;
            }

            public void setFid(String fid) {
                this.fid = fid;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public int getIsOline() {
                return isOline;
            }

            public void setIsOline(int isOline) {
                this.isOline = isOline;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getWorkStatus() {
                return workStatus;
            }

            public void setWorkStatus(String workStatus) {
                this.workStatus = workStatus;
            }

            public List<ActionsBean> getActions() {
                return actions;
            }

            public void setActions(List<ActionsBean> actions) {
                this.actions = actions;
            }

            public List<?> getNodeStatusDes() {
                return nodeStatusDes;
            }

            public void setNodeStatusDes(List<?> nodeStatusDes) {
                this.nodeStatusDes = nodeStatusDes;
            }

            public static class ExtInfoBean {
                /**
                 * index : 0
                 * name :
                 * node : 1
                 * roomId : 0
                 */

                private int index;
                private String name;
                private int node;
                private int roomId;

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

                public int getNode() {
                    return node;
                }

                public void setNode(int node) {
                    this.node = node;
                }

                public int getRoomId() {
                    return roomId;
                }

                public void setRoomId(int roomId) {
                    this.roomId = roomId;
                }
            }

            public static class ActionsBean {
                /**
                 * cmd : 254
                 * code : Trigger
                 * name : 触发
                 */

                private int cmd;
                private String code;
                private String name;

                public int getCmd() {
                    return cmd;
                }

                public void setCmd(int cmd) {
                    this.cmd = cmd;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
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
}
