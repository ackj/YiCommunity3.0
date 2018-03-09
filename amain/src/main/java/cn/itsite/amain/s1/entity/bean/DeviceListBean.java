package cn.itsite.amain.s1.entity.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

import cn.itsite.amain.s1.room.view.RoomDeviceListRVAdapter;

/**
 * Author: LiuJia on 2017/8/28 0028 17:13.
 * Email: liujia95me@126.com
 */

public class DeviceListBean extends BaseBean implements Parcelable {


    /**
     * data : {"subDevices":[{"actions":[{"cmd":1,"code":"Open","name":"打开"},{"cmd":0,"code":"Close","name":"关闭"}],"alarmDelay":0,"category":"device_ctrl","defenseLevel":"24hour","deviceId":"","deviceType":"camera01","extInfo":{"index":999,"name":"","node":1,"roomId":-1},"icon":"http://agl.image.alimmdn.com/smartHome/subDeviceType/e_shexiangtou.png","index":999,"isOline":1,"name":"","password":""},{"actions":[],"alarmDelay":0,"category":"device_ctrl","defenseLevel":"24hour","deviceId":"","deviceType":"camera01","extInfo":{"index":999,"name":"","node":1,"roomId":-1},"icon":"http://agl.image.alimmdn.com/smartHome/subDeviceType/e_shexiangtou.png","index":999,"isOline":1,"name":"","password":""}]}
     */

    private DataBean data;

    protected DeviceListBean(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DeviceListBean> CREATOR = new Creator<DeviceListBean>() {
        @Override
        public DeviceListBean createFromParcel(Parcel in) {
            return new DeviceListBean(in);
        }

        @Override
        public DeviceListBean[] newArray(int size) {
            return new DeviceListBean[size];
        }
    };

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<SubDevicesBean> subDevices;

        public List<SubDevicesBean> getSubDevices() {
            return subDevices;
        }

        public void setSubDevices(List<SubDevicesBean> subDevices) {
            this.subDevices = subDevices;
        }

        public static class SubDevicesBean implements Serializable, Parcelable, MultiItemEntity {
            /**
             * actions : [{"cmd":1,"code":"Open","name":"打开"},{"cmd":0,"code":"Close","name":"关闭"}]
             * alarmDelay : 0
             * category : device_ctrl
             * defenseLevel : 24hour
             * deviceId :
             * deviceType : camera01
             * extInfo : {"index":999,"name":"","node":1,"roomId":-1}
             * icon : http://agl.image.alimmdn.com/smartHome/subDeviceType/e_shexiangtou.png
             * index : 999
             * isOline : 1
             * name :
             * password :
             */

            private int alarmDelay;
            private String category;
            private String defenseLevel;
            private String deviceId;
            private String deviceType;
            private ExtInfoBean extInfo;
            private String icon;
            private int index;
            private int isOline;
            private String name;
            private String password;
            private List<ActionsBean> actions;

            public SubDevicesBean() {
            }

            protected SubDevicesBean(Parcel in) {
                alarmDelay = in.readInt();
                category = in.readString();
                defenseLevel = in.readString();
                deviceId = in.readString();
                deviceType = in.readString();
                icon = in.readString();
                index = in.readInt();
                isOline = in.readInt();
                name = in.readString();
                password = in.readString();
            }

            public static final Creator<SubDevicesBean> CREATOR = new Creator<SubDevicesBean>() {
                @Override
                public SubDevicesBean createFromParcel(Parcel in) {
                    return new SubDevicesBean(in);
                }

                @Override
                public SubDevicesBean[] newArray(int size) {
                    return new SubDevicesBean[size];
                }
            };

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

            public List<ActionsBean> getActions() {
                return actions;
            }

            public void setActions(List<ActionsBean> actions) {
                this.actions = actions;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(alarmDelay);
                dest.writeString(category);
                dest.writeString(defenseLevel);
                dest.writeString(deviceId);
                dest.writeString(deviceType);
                dest.writeString(icon);
                dest.writeInt(index);
                dest.writeInt(isOline);
                dest.writeString(name);
                dest.writeString(password);
            }

            @Override
            public int getItemType() {
                return RoomDeviceListRVAdapter.TYPE_DEVICE;
            }

            public static class ExtInfoBean implements Serializable, Parcelable {
                /**
                 * index : 999
                 * name :
                 * node : 1
                 * roomId : -1
                 */

                private int index;
                private String name;
                private int node;
                private int roomId;

                protected ExtInfoBean(Parcel in) {
                    index = in.readInt();
                    name = in.readString();
                    node = in.readInt();
                    roomId = in.readInt();
                }

                public static final Creator<ExtInfoBean> CREATOR = new Creator<ExtInfoBean>() {
                    @Override
                    public ExtInfoBean createFromParcel(Parcel in) {
                        return new ExtInfoBean(in);
                    }

                    @Override
                    public ExtInfoBean[] newArray(int size) {
                        return new ExtInfoBean[size];
                    }
                };

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

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(index);
                    dest.writeString(name);
                    dest.writeInt(node);
                    dest.writeInt(roomId);
                }
            }

            public static class ActionsBean implements Serializable, Parcelable {
                /**
                 * cmd : 1
                 * code : Open
                 * name : 打开
                 */

                private int cmd;
                private String code;
                private String name;

                protected ActionsBean(Parcel in) {
                    cmd = in.readInt();
                    code = in.readString();
                    name = in.readString();
                }

                public static final Creator<ActionsBean> CREATOR = new Creator<ActionsBean>() {
                    @Override
                    public ActionsBean createFromParcel(Parcel in) {
                        return new ActionsBean(in);
                    }

                    @Override
                    public ActionsBean[] newArray(int size) {
                        return new ActionsBean[size];
                    }
                };

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

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(cmd);
                    dest.writeString(code);
                    dest.writeString(name);
                }
            }
        }
    }
}
