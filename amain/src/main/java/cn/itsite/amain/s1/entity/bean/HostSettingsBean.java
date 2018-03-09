package cn.itsite.amain.s1.entity.bean;

/**
 * Created by leguang on 2017/8/31 0031.
 * Email：langmanleguang@qq.com
 */

public class HostSettingsBean {

    /**
     * data : {"alarm":2,"alarm_number1":"","alarm_number2":"","alarm_number3":"","alarm_number4":"","alarm_number5":"","alarm_number6":"","defense_chg":1,"door_close":0,"door_open":0,"gsm":2,"host_power_low":1,"main_number":"","power_fail":1,"power_recover":1,"sensor_power_low":1,"sms_tophone":1,"smspush_number1":"","smspush_number2":"","speech":2,"tone":1,"wifi_connect":1,"wifi_disconnect":1}
     * other : {"code":200,"currpage":0,"first":"","forward":"","message":"data success","next":"","refresh":"","time":""}
     */

    private DataBean data;
    private OtherBean other;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public OtherBean getOther() {
        return other;
    }

    public void setOther(OtherBean other) {
        this.other = other;
    }

    public static class DataBean {
        /**
         * alarm : 2
         * alarm_number1 :
         * alarm_number2 :
         * alarm_number3 :
         * alarm_number4 :
         * alarm_number5 :
         * alarm_number6 :
         * defense_chg : 1
         * door_close : 0
         * door_open : 0
         * gsm : 2
         * host_power_low : 1
         * main_number :
         * power_fail : 1
         * power_recover : 1
         * sensor_power_low : 1
         * sms_tophone : 1
         * smspush_number1 :
         * smspush_number2 :
         * speech : 2
         * tone : 1
         * wifi_connect : 1
         * wifi_disconnect : 1
         */

        private int alarm;
        private String alarm_number1;
        private String alarm_number2;
        private String alarm_number3;
        private String alarm_number4;
        private String alarm_number5;
        private String alarm_number6;
        private int defense_chg;
        private int door_close;
        private int door_open;
        private int gsm;
        private int host_power_low;
        private String main_number;
        private int power_fail;
        private int power_recover;
        private int sensor_power_low;
        private int sms_tophone;
        private String smspush_number1;
        private String smspush_number2;
        private int speech;
        private int tone;
        private int wifi_connect;
        private int wifi_disconnect;

        public int getAlarm() {
            return alarm;
        }

        public void setAlarm(int alarm) {
            this.alarm = alarm;
        }

        public String getAlarm_number1() {
            return alarm_number1;
        }

        public void setAlarm_number1(String alarm_number1) {
            this.alarm_number1 = alarm_number1;
        }

        public String getAlarm_number2() {
            return alarm_number2;
        }

        public void setAlarm_number2(String alarm_number2) {
            this.alarm_number2 = alarm_number2;
        }

        public String getAlarm_number3() {
            return alarm_number3;
        }

        public void setAlarm_number3(String alarm_number3) {
            this.alarm_number3 = alarm_number3;
        }

        public String getAlarm_number4() {
            return alarm_number4;
        }

        public void setAlarm_number4(String alarm_number4) {
            this.alarm_number4 = alarm_number4;
        }

        public String getAlarm_number5() {
            return alarm_number5;
        }

        public void setAlarm_number5(String alarm_number5) {
            this.alarm_number5 = alarm_number5;
        }

        public String getAlarm_number6() {
            return alarm_number6;
        }

        public void setAlarm_number6(String alarm_number6) {
            this.alarm_number6 = alarm_number6;
        }

        public int getDefense_chg() {
            return defense_chg;
        }

        public void setDefense_chg(int defense_chg) {
            this.defense_chg = defense_chg;
        }

        public int getDoor_close() {
            return door_close;
        }

        public void setDoor_close(int door_close) {
            this.door_close = door_close;
        }

        public int getDoor_open() {
            return door_open;
        }

        public void setDoor_open(int door_open) {
            this.door_open = door_open;
        }

        public int getGsm() {
            return gsm;
        }

        public void setGsm(int gsm) {
            this.gsm = gsm;
        }

        public int getHost_power_low() {
            return host_power_low;
        }

        public void setHost_power_low(int host_power_low) {
            this.host_power_low = host_power_low;
        }

        public String getMain_number() {
            return main_number;
        }

        public void setMain_number(String main_number) {
            this.main_number = main_number;
        }

        public int getPower_fail() {
            return power_fail;
        }

        public void setPower_fail(int power_fail) {
            this.power_fail = power_fail;
        }

        public int getPower_recover() {
            return power_recover;
        }

        public void setPower_recover(int power_recover) {
            this.power_recover = power_recover;
        }

        public int getSensor_power_low() {
            return sensor_power_low;
        }

        public void setSensor_power_low(int sensor_power_low) {
            this.sensor_power_low = sensor_power_low;
        }

        public int getSms_tophone() {
            return sms_tophone;
        }

        public void setSms_tophone(int sms_tophone) {
            this.sms_tophone = sms_tophone;
        }

        public String getSmspush_number1() {
            return smspush_number1;
        }

        public void setSmspush_number1(String smspush_number1) {
            this.smspush_number1 = smspush_number1;
        }

        public String getSmspush_number2() {
            return smspush_number2;
        }

        public void setSmspush_number2(String smspush_number2) {
            this.smspush_number2 = smspush_number2;
        }

        public int getSpeech() {
            return speech;
        }

        public void setSpeech(int speech) {
            this.speech = speech;
        }

        public int getTone() {
            return tone;
        }

        public void setTone(int tone) {
            this.tone = tone;
        }

        public int getWifi_connect() {
            return wifi_connect;
        }

        public void setWifi_connect(int wifi_connect) {
            this.wifi_connect = wifi_connect;
        }

        public int getWifi_disconnect() {
            return wifi_disconnect;
        }

        public void setWifi_disconnect(int wifi_disconnect) {
            this.wifi_disconnect = wifi_disconnect;
        }
    }

    public static class OtherBean {
        /**
         * code : 200
         * currpage : 0
         * first :
         * forward :
         * message : data success
         * next :
         * refresh :
         * time :
         */

        private int code;
        private int currpage;
        private String first;
        private String forward;
        private String message;
        private String next;
        private String refresh;
        private String time;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public int getCurrpage() {
            return currpage;
        }

        public void setCurrpage(int currpage) {
            this.currpage = currpage;
        }

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        public String getForward() {
            return forward;
        }

        public void setForward(String forward) {
            this.forward = forward;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public String getRefresh() {
            return refresh;
        }

        public void setRefresh(String refresh) {
            this.refresh = refresh;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
