package cn.itsite.amain.s1.entity.bean;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Author： Administrator on 2017/8/9 0009.
 * Email： liujia95me@126.com
 */
public class GatewaysBean extends BaseBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements android.os.Parcelable {
        /**
         * isOnline : 0
         * fid : 42c58435-9e86-455c-bd4c-2c584b7da06c
         * defenseStatus : home
         * residence : {"fid":"a7324d3c-d584-4472-845a-1cb803933d69","addrDet":"广东省惠州市惠城区云山西路靠近德威大厦101","lng":"0","addr":"广东省惠州市惠城区云山西路靠近德威大厦101","lat":"0","name":""}
         * status : 0
         * no : G211EU6B1000300
         * name : 300-kerr
         * isManager : 0
         * isCurrent : 1
         * defenseStatusDes : 当前处于在家布防状态，第一防线和24小时防线的探测器处于防御状态
         */

        private int isOnline;
        private String fid;
        private String defenseStatus;
        private ResidenceBean residence;
        private int status;
        private String no;
        private String name;
        private int isManager;
        private int isCurrent;
        private String defenseStatusDes;

        public int getIsOnline() {
            return isOnline;
        }

        public void setIsOnline(int isOnline) {
            this.isOnline = isOnline;
        }

        public String getFid() {
            return fid;
        }

        public void setFid(String fid) {
            this.fid = fid;
        }

        public String getDefenseStatus() {
            return defenseStatus;
        }

        public void setDefenseStatus(String defenseStatus) {
            this.defenseStatus = defenseStatus;
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

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIsManager() {
            return isManager;
        }

        public void setIsManager(int isManager) {
            this.isManager = isManager;
        }

        public int getIsCurrent() {
            return isCurrent;
        }

        public void setIsCurrent(int isCurrent) {
            this.isCurrent = isCurrent;
        }

        public String getDefenseStatusDes() {
            return defenseStatusDes;
        }

        public void setDefenseStatusDes(String defenseStatusDes) {
            this.defenseStatusDes = defenseStatusDes;
        }

        public static class ResidenceBean implements android.os.Parcelable {
            /**
             * fid : a7324d3c-d584-4472-845a-1cb803933d69
             * addrDet : 广东省惠州市惠城区云山西路靠近德威大厦101
             * lng : 0
             * addr : 广东省惠州市惠城区云山西路靠近德威大厦101
             * lat : 0
             * name :
             */

            private String fid;
            private String addrDet;
            private String lng;
            private String addr;
            private String lat;
            private String name;

            public String getFid() {
                return fid;
            }

            public void setFid(String fid) {
                this.fid = fid;
            }

            public String getAddrDet() {
                return addrDet;
            }

            public void setAddrDet(String addrDet) {
                this.addrDet = addrDet;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public String getAddr() {
                return addr;
            }

            public void setAddr(String addr) {
                this.addr = addr;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
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
                dest.writeString(this.fid);
                dest.writeString(this.addrDet);
                dest.writeString(this.lng);
                dest.writeString(this.addr);
                dest.writeString(this.lat);
                dest.writeString(this.name);
            }

            public ResidenceBean() {
            }

            protected ResidenceBean(Parcel in) {
                this.fid = in.readString();
                this.addrDet = in.readString();
                this.lng = in.readString();
                this.addr = in.readString();
                this.lat = in.readString();
                this.name = in.readString();
            }

            public static final Creator<ResidenceBean> CREATOR = new Creator<ResidenceBean>() {
                @Override
                public ResidenceBean createFromParcel(Parcel source) {
                    return new ResidenceBean(source);
                }

                @Override
                public ResidenceBean[] newArray(int size) {
                    return new ResidenceBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.isOnline);
            dest.writeString(this.fid);
            dest.writeString(this.defenseStatus);
            dest.writeParcelable(this.residence, flags);
            dest.writeInt(this.status);
            dest.writeString(this.no);
            dest.writeString(this.name);
            dest.writeInt(this.isManager);
            dest.writeInt(this.isCurrent);
            dest.writeString(this.defenseStatusDes);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.isOnline = in.readInt();
            this.fid = in.readString();
            this.defenseStatus = in.readString();
            this.residence = in.readParcelable(ResidenceBean.class.getClassLoader());
            this.status = in.readInt();
            this.no = in.readString();
            this.name = in.readString();
            this.isManager = in.readInt();
            this.isCurrent = in.readInt();
            this.defenseStatusDes = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeList(this.data);
    }

    public GatewaysBean() {
    }

    protected GatewaysBean(Parcel in) {
        super(in);
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Creator<GatewaysBean> CREATOR = new Creator<GatewaysBean>() {
        @Override
        public GatewaysBean createFromParcel(Parcel source) {
            return new GatewaysBean(source);
        }

        @Override
        public GatewaysBean[] newArray(int size) {
            return new GatewaysBean[size];
        }
    };
}
