package cn.itsite.amain.s1.entity.bean;

/**
 * Author: LiuJia on 2017/8/20 0020 17:30.
 * Email: liujia95me@126.com
 */

public class SubDeviceDetBean extends BaseBean {


    /**
     * data : {"alarmDelay":1,"defenseLevel":"first","icon":"http://hygn.image.alimmdn.com/subDeviceType/equipment_doortection.png","index":0,"name":"门磁"}
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
         * alarmDelay : 1
         * defenseLevel : first
         * icon : http://hygn.image.alimmdn.com/subDeviceType/equipment_doortection.png
         * index : 0
         * name : 门磁
         */

        private int alarmDelay;
        private String defenseLevel;
        private String icon;
        private int index;
        private String name;

        public int getAlarmDelay() {
            return alarmDelay;
        }

        public void setAlarmDelay(int alarmDelay) {
            this.alarmDelay = alarmDelay;
        }

        public String getDefenseLevel() {
            return defenseLevel;
        }

        public void setDefenseLevel(String defenseLevel) {
            this.defenseLevel = defenseLevel;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
