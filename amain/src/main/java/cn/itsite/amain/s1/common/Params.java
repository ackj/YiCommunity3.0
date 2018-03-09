package cn.itsite.amain.s1.common;

import java.io.File;

import cn.itsite.amain.yicommunity.common.UserHelper;

/**
 * Created by leguang on 2017/5/6 0006.
 * Email：langmanleguang@qq.com
 */

public class Params {
    public static final String TAG = Params.class.getSimpleName();
    public static String token;//测试用
    public int page = 1;
    public int pageSize = 50;
    public String sc = "AglhzYsq";
    //    public String sc = "AglhzSmart";
    public String user = "";
    public String pwd = "";
    public String pwd0 = "";
    public String pwd1 = "";
    public String pwd2 = "";
    public String account;
    public String verifyCode;
    public String verifyType;
    public String password1;
    public String password2;
    public String phoneNo = "";
    public String sensorType;
    public String defenseLevel;
    public String roomFid;
    public String gateway = "";
    public String roomName = "";
    public String roomTypeFid = "";
    public String residenceFid = "";
    public String dstatus = "";
    public String no = "";
    public String addr = "";
    public String addrDet = "";
    public String lng = "";
    public String lat = "";
    public String name = "";
    public String paramJson = "";
    public String type = "";
    public String subType = "";
    public String val = "";
    public int index;
    public int option;
    public String des;
    public String contact;
    public String deviceType;
    public String category;//
    public int alarmDelay;//
    public int roomId;//roomIndex
    public String nodeId;
    public int status;
    public String triggerType;
    public int cdt_sensorId;
    public String cdt_sensorAct;
    public String cdt_day;
    public String cdt_time;
    public String targetType;
    public String targetId;
    public String act1;
    public String delay;
    public String act2;
    public String mobile;
    public String fid;
    public File file;
    public String deviceId;
    public String deviceName;
    public String devicePassword;
    public String powerCode = "SmartEquipment";
    public String roomDir;
    public String deviceSn;//

    public static Params getInstance() {
        Params params = new Params();
        params.token = UserHelper.token;
        params.deviceSn = UserHelper.deviceSn;
        return params;
    }

    @Override
    public String toString() {
        return "Params{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                ", sc='" + sc + '\'' +
                ", user='" + user + '\'' +
                ", pwd='" + pwd + '\'' +
                ", pwd0='" + pwd0 + '\'' +
                ", pwd1='" + pwd1 + '\'' +
                ", pwd2='" + pwd2 + '\'' +
                ", account='" + account + '\'' +
                ", verifyCode='" + verifyCode + '\'' +
                ", verifyType='" + verifyType + '\'' +
                ", password1='" + password1 + '\'' +
                ", password2='" + password2 + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", sensorType='" + sensorType + '\'' +
                ", defenseLevel='" + defenseLevel + '\'' +
                ", roomFid='" + roomFid + '\'' +
                ", gateway='" + gateway + '\'' +
                ", roomName='" + roomName + '\'' +
                ", roomTypeFid='" + roomTypeFid + '\'' +
                ", residenceFid='" + residenceFid + '\'' +
                ", dstatus='" + dstatus + '\'' +
                ", no='" + no + '\'' +
                ", name='" + name + '\'' +
                ", index=" + index +
                ", des='" + des + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}