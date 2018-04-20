package cn.itsite.amain.s1.entity.bean;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */

public class CommandBean {

    public CommandBean(int deviceId, int nodeId, int action1) {
        this.deviceId = deviceId;
        this.nodeId = nodeId;
        this.action1 = action1;
    }

    /**
     * action1 : 1
     * deviceId : 0
     * nodeId : 0
     */
    private int deviceId;
    private int nodeId;
    private int action1;

    public int getAction1() {
        return action1;
    }

    public void setAction1(int action1) {
        this.action1 = action1;
    }

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
}
