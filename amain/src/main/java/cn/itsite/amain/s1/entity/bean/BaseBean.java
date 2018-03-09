package cn.itsite.amain.s1.entity.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class BaseBean implements Parcelable {

    /**
     * other : {"code":200,"currpage":1,"first":"http://119.23.129.133:8096/gas/client/info/homePage?page=1&pageSize=10","forward":"","message":"data success","next":"http://119.23.129.133:8096/gas/client/info/homePage?page=2&pageSize=10","refresh":"http://119.23.129.133:8096/gas/client/info/homePage?page=1&pageSize=10","time":""}
     */

    private OtherBean other;

    public OtherBean getOther() {
        return other;
    }

    public void setOther(OtherBean other) {
        this.other = other;
    }

    public static class OtherBean implements Parcelable {
        /**
         * code : 200
         * currpage : 1
         * first : http://119.23.129.133:8096/gas/client/info/homePage?page=1&pageSize=10
         * forward :
         * message : data success
         * next : http://119.23.129.133:8096/gas/client/info/homePage?page=2&pageSize=10
         * refresh : http://119.23.129.133:8096/gas/client/info/homePage?page=1&pageSize=10
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.code);
            dest.writeInt(this.currpage);
            dest.writeString(this.first);
            dest.writeString(this.forward);
            dest.writeString(this.message);
            dest.writeString(this.next);
            dest.writeString(this.refresh);
            dest.writeString(this.time);
        }

        public OtherBean() {
        }

        protected OtherBean(Parcel in) {
            this.code = in.readInt();
            this.currpage = in.readInt();
            this.first = in.readString();
            this.forward = in.readString();
            this.message = in.readString();
            this.next = in.readString();
            this.refresh = in.readString();
            this.time = in.readString();
        }

        public static final Creator<OtherBean> CREATOR = new Creator<OtherBean>() {
            @Override
            public OtherBean createFromParcel(Parcel source) {
                return new OtherBean(source);
            }

            @Override
            public OtherBean[] newArray(int size) {
                return new OtherBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.other, flags);
    }

    public BaseBean() {
    }

    protected BaseBean(Parcel in) {
        this.other = in.readParcelable(OtherBean.class.getClassLoader());
    }

}
