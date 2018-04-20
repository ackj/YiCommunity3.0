package cn.itsite.amain.yicommunity.entity.db;

import org.litepal.crud.DataSupport;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/5 0005 13:01
 */
public class PlateHistoryData extends DataSupport {
    private String plate;

    public PlateHistoryData() {
    }

    public PlateHistoryData(String plate) {
        this.plate = plate;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }
}
