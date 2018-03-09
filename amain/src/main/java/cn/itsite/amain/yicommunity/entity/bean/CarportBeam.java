package cn.itsite.amain.yicommunity.entity.bean;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/6 0006 9:29
 */
public class CarportBeam {

    /**
     * data : {"parkPlace":{"fid":"51b56def-7530-449c-83fa-1d5e0660de6c","communityFid":"KBSJ-agl-00005","communityName":"惠州市 凯宾斯基","regionInfo":"广东省惠州市惠城区","name":"凯宾斯基停车场","address":"广东省惠州市江北凯宾斯基酒店"},"totalSpace":999,"usingSpace":0,"surplusSpace":999}
     * other : {"code":200,"message":"data success","time":"","currpage":0,"next":"","forward":"","refresh":"","first":""}
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
         * parkPlace : {"fid":"51b56def-7530-449c-83fa-1d5e0660de6c","communityFid":"KBSJ-agl-00005","communityName":"惠州市 凯宾斯基","regionInfo":"广东省惠州市惠城区","name":"凯宾斯基停车场","address":"广东省惠州市江北凯宾斯基酒店"}
         * totalSpace : 999
         * usingSpace : 0
         * surplusSpace : 999
         */

        private ParkPlaceBean parkPlace;
        private int totalSpace;
        private int usingSpace;
        private int surplusSpace;

        public ParkPlaceBean getParkPlace() {
            return parkPlace;
        }

        public void setParkPlace(ParkPlaceBean parkPlace) {
            this.parkPlace = parkPlace;
        }

        public int getTotalSpace() {
            return totalSpace;
        }

        public void setTotalSpace(int totalSpace) {
            this.totalSpace = totalSpace;
        }

        public int getUsingSpace() {
            return usingSpace;
        }

        public void setUsingSpace(int usingSpace) {
            this.usingSpace = usingSpace;
        }

        public int getSurplusSpace() {
            return surplusSpace;
        }

        public void setSurplusSpace(int surplusSpace) {
            this.surplusSpace = surplusSpace;
        }

        public static class ParkPlaceBean {
            /**
             * fid : 51b56def-7530-449c-83fa-1d5e0660de6c
             * communityFid : KBSJ-agl-00005
             * communityName : 惠州市 凯宾斯基
             * regionInfo : 广东省惠州市惠城区
             * name : 凯宾斯基停车场
             * address : 广东省惠州市江北凯宾斯基酒店
             */

            private String fid;
            private String communityFid;
            private String communityName;
            private String regionInfo;
            private String name;
            private String address;

            public String getFid() {
                return fid;
            }

            public void setFid(String fid) {
                this.fid = fid;
            }

            public String getCommunityFid() {
                return communityFid;
            }

            public void setCommunityFid(String communityFid) {
                this.communityFid = communityFid;
            }

            public String getCommunityName() {
                return communityName;
            }

            public void setCommunityName(String communityName) {
                this.communityName = communityName;
            }

            public String getRegionInfo() {
                return regionInfo;
            }

            public void setRegionInfo(String regionInfo) {
                this.regionInfo = regionInfo;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }
        }
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
}
