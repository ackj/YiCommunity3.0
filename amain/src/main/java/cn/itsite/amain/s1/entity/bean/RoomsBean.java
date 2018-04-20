package cn.itsite.amain.s1.entity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author: LiuJia on 2017/8/9 0009 15:55.
 * Email: liujia95me@126.com
 */

public class RoomsBean extends BaseBean {


    /**
     * data : {"roomList":[{"fid":"100001","index":0,"name":"测试房间","type":"hall"},{"fid":"117ba3e3-88d6-45f5-bd38-cc952a16daa8","index":1,"name":"大厅","type":"hall"},{"fid":"d73820cd-b3b4-4ec0-8c5e-58b93fc77630","index":3,"name":"厨房","type":"kitchen"},{"fid":"914a4a85-7fe0-400d-bac6-09ab2e51f017","index":2,"name":"大厅","type":"hall"},{"fid":"e6d84d94-9eab-4921-a469-ada00697e949","index":4,"name":"卧室","type":"bedroom"},{"fid":"ea9f5b7c-7da4-4f2d-aff2-556942046d21","index":0,"name":"大厅","type":"hall"},{"fid":"c32308f6-570b-4d13-9543-3e0fdd998b74","index":0,"name":"大厅","type":"hall"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<RoomListBean> roomList;

        public List<RoomListBean> getRoomList() {
            return roomList;
        }

        public void setRoomList(List<RoomListBean> roomList) {
            this.roomList = roomList;
        }

        public static class RoomListBean implements Serializable{
            /**
             * fid : 100001
             * index : 0
             * name : 测试房间
             * type : hall
             */

            private String fid;
            private int index;
            private String name;
            private String type;

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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
