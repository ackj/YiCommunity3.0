package cn.itsite.amain.yicommunity.entity.db;

import org.litepal.crud.DataSupport;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/5 0005 13:01
 */
public class ParkHistoryData extends DataSupport {
    private String name;
    private String parkID;
    private String address;
    private String region;
    private String communityID;
    private String communityName;

    public ParkHistoryData() {
    }

    public ParkHistoryData(String name, String parkID, String address, String region, String communityID, String communityName) {
        this.name = name;
        this.parkID = parkID;
        this.address = address;
        this.region = region;
        this.communityID = communityID;
        this.communityName = communityName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCommunityID() {
        return communityID;
    }

    public void setCommunityID(String communityID) {
        this.communityID = communityID;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParkID() {
        return parkID;
    }

    public void setParkID(String parkID) {
        this.parkID = parkID;
    }
}
